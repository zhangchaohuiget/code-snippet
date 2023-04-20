package com.app.config;

import com.app.config.annotation.Document;
import org.elasticsearch.action.search.SearchRequest;
import org.springframework.util.StringUtils;

public class ESRequestFactory {

    public static SearchRequest getSearchRequest(Class clazz) {
        Document annotation = (Document) clazz.getAnnotation(Document.class);
        if (null == annotation) {
            throw new IllegalArgumentException(clazz.getName() + "没有Document注解");
        }
        String index = annotation.indexName();
        if (StringUtils.isEmpty(index)) {
            throw new RuntimeException("索引不能为空");
        }
        SearchRequest searchRequest = new SearchRequest(annotation.indexName());
        return searchRequest;
    }
}
