package com.app.repository;

import com.app.entity.NodeRelationShip;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRelationShipRepository extends Neo4jRepository<NodeRelationShip, Long> {
}
