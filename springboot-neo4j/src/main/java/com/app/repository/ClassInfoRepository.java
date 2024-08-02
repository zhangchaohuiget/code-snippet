package com.app.repository;

import com.app.entity.ClassInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassInfoRepository extends Neo4jRepository<ClassInfo, Long> {

    @Query("match (m:userInfo)-[r]-(n:classInfo) where m.userId=$userId return n")
    List<ClassInfo> getClassInfoByUserId(String userId);

    @Query("match (n:classInfo) where n.classId in $classIds detach delete n")
    void deleteClassInfoByIds(List<String> classIds);
}