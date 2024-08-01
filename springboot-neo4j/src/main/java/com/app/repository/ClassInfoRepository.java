package com.app.repository;

import com.app.entity.ClassInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassInfoRepository extends Neo4jRepository<ClassInfo, Long> {

}