package com.app.repository;

import com.app.model.Shop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends ElasticsearchRepository<Shop, Long> {
    Shop findByName(String name);

}
