package com.app.repository;

import com.app.entity.ItemInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemInfoRepository extends Neo4jRepository<ItemInfo, Long> {

    @Query("match (m:classInfo)-[r]-(n:itemInfo) where m.classId in $classIds return n")
    List<ItemInfo> getItemInfosByClassIds(List<String> classIds);

    @Query("match (n:itemInfo) where n.itemId in $itemIds detach delete n")
    void deleteClassInfoByIds(List<String> itemIds);
}