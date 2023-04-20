package com.app.config;

import com.alibaba.fastjson.JSON;
import com.app.config.annotation.Document;
import com.app.config.annotation.Id;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ESClient implements Closeable {

    private RestHighLevelClient client;
    private RequestOptions options;

    public ESClient(RestHighLevelClient client, RequestOptions options) {
        this.client = client;
        this.options = options;
    }

    public final MainResponse info() throws IOException {
        return client.info(options);
    }

    public final <T> List<T> select(T t) throws Exception {
        Class<T> clazz = (Class<T>) t.getClass();
        SearchRequest searchRequest = ESRequestFactory.getSearchRequest(clazz);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            Class<?> type = field.getType();

            //获取对应属性的属性值
            field.setAccessible(true);
            Object value = field.get(t);
            if (!Objects.isNull(value) && !type.isAssignableFrom(Date.class) && !type.isAssignableFrom(List.class)) {
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(name, value);
                boolQueryBuilder.must(termQueryBuilder);
            }
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, options);
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        List<T> results = new ArrayList<>();
        if (0 == hits1.length) {
            return results;
        }
        for (SearchHit hit : hits1) {
            String sourceAsString = hit.getSourceAsString();
            T t1 = JSON.parseObject(sourceAsString, clazz);
            results.add(t1);
        }
        return results;
    }

    public final <T> T findById(T t) throws Exception {
        Class<T> clazz = (Class<T>) t.getClass();
        SearchRequest searchRequest = ESRequestFactory.getSearchRequest(t.getClass());
        Field[] fields = clazz.getDeclaredFields();
        Object id = null;
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (null != idAnnotation) {
                field.setAccessible(true);
                id = field.get(t);
                if (Objects.isNull(id)) {
                    throw new RuntimeException("id不能为空");
                }
                break;
            }
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery().addIds((String) id);
        searchSourceBuilder.query(idsQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest, options);
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        if (0 == hits1.length) {
            return null;
        }
        SearchHit searchHit = hits1[0];
        String sourceAsString = searchHit.getSourceAsString();
        return JSON.parseObject(sourceAsString, clazz);
    }

    /**
     * 存储数据，默认不只用自增主键，使用自定义主键
     * 默认立即刷新
     *
     * @param t   数据
     * @param <T>
     * @return
     * @throws Exception
     */
    public final <T> T saveOrUpdate(T t) throws Exception {
        return saveOrUpdate(t, false);
    }

    /**
     * 存储数据
     *
     * @param t            数据
     * @param delayRefresh 是否延迟刷新
     */
    public final <T> T saveOrUpdate(T t, boolean delayRefresh) throws Exception {
        Class clazz = t.getClass();
        Document annotation = (Document) clazz.getAnnotation(Document.class);
        if (null == annotation) {
            throw new RuntimeException("无法使用ES save方法");
        }
        String index = annotation.indexName();
        if (StringUtils.isEmpty(index)) {
            throw new RuntimeException("索引不能为空");
        }
        Field[] fields = clazz.getDeclaredFields();
        int idCount = 0;
        Field idField = null;
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (null != idAnnotation) {
                idCount++;
                idField = field;
            }
        }
        /*if (idCount == 0) {
            throw new RuntimeException(clazz.getName() + "没有id字段");
        }*/
        if (idCount > 1) {
            throw new RuntimeException(clazz.getName() + "有两个id");
        }
        String s = JSON.toJSONString(t);

        IndexRequest indexRequest = new IndexRequest(index);
        if (delayRefresh) {
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
        } else {
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        }
        //是否有id，没有id使用默认
        if (null != idField) {
            idField.setAccessible(true);
            Object id = idField.get(t);
            if (null != id) {
                indexRequest.id(id.toString());
            }
        }
        indexRequest.source(s, XContentType.JSON);
        IndexResponse indexResponse = this.index(indexRequest);
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED
                || indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return t;
        }
        return null;
    }

    public final <T> BulkResponse batchUpsert(List<T> tList) throws Exception {
        return batchUpsert(tList, false);
    }

    public final <T> BulkResponse batchUpsert(List<T> tList, boolean delayRefresh) throws Exception {
        BulkRequest bulkRequest = null;
        int count = 0;
        for (T t : tList) {
            if (null == bulkRequest) {
                bulkRequest = new BulkRequest();
                if (delayRefresh) {
                    bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
                } else {
                    bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
                }
            }
            Class clazz = t.getClass();
            Document annotation = (Document) clazz.getAnnotation(Document.class);
            if (null == annotation) {
                throw new RuntimeException("无法使用ES upsert方法");
            }
            String index = annotation.indexName();
            if (StringUtils.isEmpty(index)) {
                throw new RuntimeException("索引不能为空");
            }
            Field[] fields = clazz.getDeclaredFields();
            Field idField = null;
            for (Field field : fields) {
                Id idAnnotation = field.getAnnotation(Id.class);
                if (null != idAnnotation) {
                    idField = field;
                    break;
                }
            }
            if (Objects.isNull(idField)) {
                throw new NullPointerException("没有id字段");
            }
            idField.setAccessible(true);
            Object id = idField.get(t);
            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("id不能为空");
            }
            UpdateRequest request = new UpdateRequest(index, (String) id);
            String json = JSON.toJSONString(t);
            request.doc(json, XContentType.JSON);
            request.docAsUpsert(true);

            bulkRequest.add(request);
            count++;
            if (count > 10000) {
                BulkResponse bulk = this.bulk(bulkRequest);
                boolean hasFailures = bulk.hasFailures();
                if (hasFailures) {
                    return bulk;
                } else {
                    count = 0;
                    bulkRequest = null;
                }
            }
        }
        BulkResponse bulk = this.bulk(bulkRequest);
        return bulk;
    }

    /**
     * 有则更新，无则插入，默认不适用延迟刷新
     *
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public final <T> String upsert(T t) throws Exception {
        return upsert(t, false);
    }

    /**
     * 有则更新，无则插入
     *
     * @param t            数据
     * @param delayRefresh 是否延迟刷新
     * @param <T>
     * @return
     */
    public final <T> String upsert(T t, boolean delayRefresh) throws Exception {
        if (Objects.isNull(t)) {
            throw new NullPointerException("参数不能为空");
        }
        Class clazz = t.getClass();
        Document annotation = (Document) clazz.getAnnotation(Document.class);
        if (null == annotation) {
            throw new RuntimeException("无法使用ES upsert方法");
        }
        String index = annotation.indexName();
        if (StringUtils.isEmpty(index)) {
            throw new RuntimeException("索引不能为空");
        }
        Field[] fields = clazz.getDeclaredFields();
        Field idField = null;
        for (Field field : fields) {
            Id idAnnotation = field.getAnnotation(Id.class);
            if (null != idAnnotation) {
                idField = field;
                break;
            }
        }
        if (Objects.isNull(idField)) {
            throw new NullPointerException("没有id字段");
        }
        idField.setAccessible(true);
        Object id = idField.get(t);
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("id不能为空");
        }
        UpdateRequest request = new UpdateRequest(index, (String) id);
        String json = JSON.toJSONString(t);
        request.doc(json, XContentType.JSON);
        request.docAsUpsert(true);
        if (delayRefresh) {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
        } else {
            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
        }
        UpdateResponse response = this.update(request);
        return response.getResult().getLowercase();
    }

    public final UpdateResponse update(UpdateRequest request) throws IOException {
        return client.update(request, options);
    }

    public final <T> BulkResponse batchSaveOrUpdate(List<T> tList) throws Exception {
        return batchSaveOrUpdate(tList, false);
    }

    public final <T> BulkResponse batchSaveOrUpdate(List<T> tList, boolean delayRefresh) throws Exception {
        BulkRequest bulkRequest = null;
        int count = 0;
        for (T t : tList) {
            if (null == bulkRequest) {
                bulkRequest = new BulkRequest();
                if (delayRefresh) {
                    bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.NONE);
                } else {
                    bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
                }
            }
            Class clazz = t.getClass();
            Document annotation = (Document) clazz.getAnnotation(Document.class);
            if (null == annotation) {
                throw new RuntimeException("无妨使用ES save方法");
            }
            String index = annotation.indexName();
            if (StringUtils.isEmpty(index)) {
                throw new RuntimeException("索引不能为空");
            }
            Field[] fields = clazz.getDeclaredFields();
            int idCount = 0;
            Field idField = null;
            for (Field field : fields) {
                Id idAnnotation = field.getAnnotation(Id.class);
                if (null != idAnnotation) {
                    idCount++;
                    idField = field;
                }
            }
            /*if (idCount == 0) {
                throw new RuntimeException(clazz.getName() + "没有id字段");
            }*/
            if (idCount > 1) {
                throw new RuntimeException(clazz.getName() + "有两个id");
            }

            String s = JSON.toJSONString(t);

            IndexRequest indexRequest = new IndexRequest(index);
            //是否有id，没有id使用默认
            if (null != idField) {
                idField.setAccessible(true);
                Object id = idField.get(t);
                if (null != id) {
                    indexRequest.id(id.toString());
                }
            }
            indexRequest.source(s, XContentType.JSON);

            bulkRequest.add(indexRequest);
            count++;
            if (count > 10000) {
                BulkResponse bulk = this.bulk(bulkRequest);
                boolean hasFailures = bulk.hasFailures();
                if (hasFailures) {
                    return bulk;
                } else {
                    count = 0;
                    bulkRequest = null;
                }
            }
        }

        BulkResponse bulk = this.bulk(bulkRequest);
        return bulk;
    }

    public final IndexResponse index(IndexRequest indexRequest) throws IOException {
        return client.index(indexRequest, options);
    }

    public final BulkResponse bulk(BulkRequest bulkRequest) throws IOException {
        return client.bulk(bulkRequest, options);
    }

    public final DeleteResponse delete(DeleteRequest deleteRequest) throws IOException {
        return client.delete(deleteRequest, options);
    }

    public final SearchResponse search(SearchRequest searchRequest) throws IOException {
        return client.search(searchRequest, options);
    }

    public final IndicesClient indices() {
        return client.indices();
    }

    public final boolean exists(GetIndexRequest getIndexRequest) throws IOException {
        return client.indices().exists(getIndexRequest, options);
    }

    public final AcknowledgedResponse create(CreateIndexRequest createIndexRequest) throws IOException {
        return client.indices().create(createIndexRequest, options);
    }

    public final AcknowledgedResponse putMapping(PutMappingRequest putMappingRequest) throws IOException {
        return client.indices().putMapping(putMappingRequest, options);
    }

    public final BulkByScrollResponse deleteByQuery(DeleteByQueryRequest request) throws IOException {
        return client.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
