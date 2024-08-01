package com.app;

import com.app.entity.ClassInfo;
import com.app.entity.NodeRelationShip;
import com.app.entity.UserInfo;
import com.app.repository.ClassInfoRepository;
import com.app.repository.NodeRelationShipRepository;
import com.app.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4JDataTest {

    @Resource
    UserInfoRepository userInfoRepository;
    @Resource
    ClassInfoRepository classInfoRepository;
    @Resource
    NodeRelationShipRepository nodeRelationShipRepository;

    @Test
    public void testAdd() {
        UserInfo user1 = new UserInfo();
        user1.setName("zs");
        user1.setAge(18);
        user1.setSex("男");
        userInfoRepository.save(user1);

        ClassInfo class1 = new ClassInfo();
        class1.setName("数学");
        class1.setNum(12);
        classInfoRepository.save(class1);

        NodeRelationShip nodeRelationShip1 = new NodeRelationShip();
        nodeRelationShip1.setName("课程关系");
        nodeRelationShip1.setStartNode(user1);
        nodeRelationShip1.setEndNode(class1);
        nodeRelationShip1.setRelation("报名");
        nodeRelationShipRepository.save(nodeRelationShip1);
    }

    @Test
    public void testDel() {
        userInfoRepository.deleteUserInfoNodeByName("zs");
    }

    @Test
    public void testDelAll() {
        userInfoRepository.deleteAll();
        classInfoRepository.deleteAll();
        nodeRelationShipRepository.deleteAll();
    }

}
