package com.app.service;

import com.app.model.Shop;
import com.app.repository.ShopRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShopServiceTests {

    @Autowired
    ShopRepository shopRepository;

    @Test
    public void insert() {
        Shop shop = new Shop();
        shop.setId(1L);
        shop.setName("name1");
        shop.setCreateTime(new Date());
        shopRepository.save(shop);

        long count = shopRepository.count();
        System.out.println(count);
    }


    @Test
    public void query() {
        TermQueryBuilder termQuery = QueryBuilders.termQuery("name", "name1");
        Iterable<Shop> search = shopRepository.search(termQuery);
        System.out.println(search);
        // 另外还有其它基本查询
        // QueryBuilders.matchQuery(name, text);
        // QueryBuilders.rangeQuery("").lt(to);

        Shop byName = shopRepository.findByName("name1");
        System.out.println(byName);
    }


    @Test
    public void search() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 多条件查询
        queryBuilder.withQuery(QueryBuilders.termQuery("name", "name1"));
        queryBuilder.withQuery(QueryBuilders.rangeQuery("createTime").gte("1681710855146"));
        // 设置分页参数
        queryBuilder.withPageable(PageRequest.of(0, 10));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        // 执行查询
        Iterable<Shop> search = shopRepository.search(queryBuilder.build());
        System.out.println(search);

        // 聚合,类型为terms，聚合名称为names，聚合字段为name
        queryBuilder.addAggregation(
                AggregationBuilders.terms("names").field("name"));
        // 执行查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Shop> aggPage = (AggregatedPage<Shop>) this.shopRepository.search(queryBuilder.build());
        System.out.println(aggPage);


        // 组合查询
        BoolQueryBuilder baseQueryBuild = QueryBuilders.boolQuery();
        // 查询 id=1 and (name = name1 or name = zs)
        BoolQueryBuilder shouldQueryBuild = QueryBuilders.boolQuery();
        shouldQueryBuild.should(QueryBuilders.termQuery("name", "name1"));
        shouldQueryBuild.should(QueryBuilders.termQuery("name", "zs"));
        baseQueryBuild.filter(QueryBuilders.termQuery("id", 1L));
        baseQueryBuild.filter(shouldQueryBuild);
        // 执行查询
        search = shopRepository.search(baseQueryBuild);
        System.out.println(search);
    }

}
