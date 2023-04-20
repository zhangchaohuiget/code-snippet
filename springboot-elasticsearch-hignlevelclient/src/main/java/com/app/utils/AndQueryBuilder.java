package com.app.utils;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * QueryBuilder增强
 */
public class AndQueryBuilder {

    private BoolQueryBuilder boolQueryBuilder;

    public AndQueryBuilder() {
        boolQueryBuilder = QueryBuilders.boolQuery();
    }

    /**
     * where and field = value
     */
    public AndQueryBuilder eq(String field, String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.termQuery(field, value));
        return this;
    }

    /**
     * where and field = value
     */
    public AndQueryBuilder eq(String field, Integer value) {
        if (null == value) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.termQuery(field, value));
        return this;
    }

    /**
     * where and field in values
     */
    public AndQueryBuilder in(String field, String... values) {
        if (null == values || values.length == 0) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.termsQuery(field, values));
        return this;
    }

    /**
     * where and field in values
     */
    public AndQueryBuilder in(String field, Integer... values) {
        if (null == values || values.length == 0) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.termsQuery(field, values));
        return this;
    }

    /**
     * where and field in values
     */
    public AndQueryBuilder in(String field, Collection<?> values) {
        if (CollectionUtils.isEmpty(values)) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.termsQuery(field, values));
        return this;
    }

    /**
     * where field like *value*
     */
    public AndQueryBuilder like(String field, String value) {
        if (StringUtils.isEmpty(value)) {
            return this;
        }
        boolQueryBuilder.filter(QueryBuilders.wildcardQuery(field, "*" + value + "*"));
        return this;
    }

    /**
     * where filed > from and field < to
     */
    public AndQueryBuilder between(String field, Object from, boolean includeLower, Object to, boolean includeUpper) {
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(field);
        if (null == from && null == to) {
            return this;
        }
        if (null != from) {
            rangeQueryBuilder.from(from, includeLower);
        }
        if (null != to) {
            rangeQueryBuilder.to(to, includeUpper);
        }
        boolQueryBuilder.filter(rangeQueryBuilder);
        return this;
    }

    /**
     * where field is not null and field.length != ""
     */
    public AndQueryBuilder exists(String field, boolean includeNullString) {
        boolQueryBuilder.filter(QueryBuilders.existsQuery(field));
        if (!includeNullString) {
            BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();
            subBoolQueryBuilder.mustNot(QueryBuilders.termQuery(field, ""));
            boolQueryBuilder.filter(subBoolQueryBuilder);
        }
        return this;
    }

    /**
     * where (field is null or field = '')
     */
    public AndQueryBuilder notExists(String field, boolean includeNullString) {
        BoolQueryBuilder subBoolQueryBuilder = QueryBuilders.boolQuery();
        if (includeNullString) {
            subBoolQueryBuilder.should(QueryBuilders.termQuery(field, ""));
            subBoolQueryBuilder.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(field)));
        } else {
            subBoolQueryBuilder.mustNot(QueryBuilders.existsQuery(field));
        }
        boolQueryBuilder.filter(subBoolQueryBuilder);
        return this;
    }

    public BoolQueryBuilder build() {
        return boolQueryBuilder;
    }
}
