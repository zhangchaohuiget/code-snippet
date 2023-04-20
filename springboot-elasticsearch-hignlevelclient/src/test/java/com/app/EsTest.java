package com.app;

import com.app.config.ESClient;
import com.app.config.ESRequestFactory;
import com.app.model.Shop;
import com.app.utils.AndQueryBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.ValueCount;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EsTest {

    @Autowired
    private ESClient esClient;

    @Test
    public void run() throws Exception {

        Shop shop = new Shop();
        shop.setId(1L);
        shop.setName("zs");
        shop.setCreateTime(new Date());
        esClient.saveOrUpdate(shop);

        SearchRequest searchRequest = ESRequestFactory.getSearchRequest(Shop.class);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(10000);

        // 基础条件查询build
        AndQueryBuilder baseQueryBulid = new AndQueryBuilder();
        baseQueryBulid.between("createTime", 1681710850000L, true, 1691710859999L, true);
        BoolQueryBuilder baseBuild = baseQueryBulid.build();

        // 根据类型过滤，should（or）逻辑
        AndQueryBuilder tempQueryBulid1 = new AndQueryBuilder();
        tempQueryBulid1.eq("name", "zs");

        AndQueryBuilder tempQueryBulid2 = new AndQueryBuilder();
        tempQueryBulid2.eq("id", 1);

        AndQueryBuilder shouldQueryBulid = new AndQueryBuilder();
        BoolQueryBuilder shouldBulid = shouldQueryBulid.build();
        shouldBulid.should(tempQueryBulid1.build());
        shouldBulid.should(tempQueryBulid2.build());

        baseBuild.filter(shouldBulid);
        builder.query(baseBuild);

        ValueCountAggregationBuilder count = AggregationBuilders.count("count").field("id");
        builder.aggregation(count);
        searchRequest.source(builder);
        SearchResponse response = null;
        try {
            response = esClient.search(searchRequest);
            System.out.println(response);
            SearchHits hits = response.getHits();
            System.out.println("hits：" + hits);
            ValueCount aggregation1 = response.getAggregations().get("count");
            System.out.println("总数：" + aggregation1.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
