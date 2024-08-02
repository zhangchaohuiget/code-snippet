package com.app.repository;

import com.app.entity.UserInfo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserInfoRepository extends Neo4jRepository<UserInfo, Long> {
    @Query("match (n:userInfo) where n.userId = $userId detach delete n")
    void deleteUserInfoById(String userId);

    @Query("match (n:userInfo) where n.userId in $userIds detach delete n")
    void deleteUserInfoByIds(List<String> userIds);
}
