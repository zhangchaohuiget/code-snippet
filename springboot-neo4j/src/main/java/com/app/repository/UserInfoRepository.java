package com.app.repository;

import com.app.entity.UserInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, Long> {
    @Query("match (n:userInfo) where n.name = $name detach delete n")
    void deleteUserInfoNodeByName(String name);
}
