package com.app;

import com.app.entity.ClassInfo;
import com.app.entity.ItemInfo;
import com.app.entity.NodeRelationShip;
import com.app.entity.UserInfo;
import com.app.repository.ClassInfoRepository;
import com.app.repository.ItemInfoRepository;
import com.app.repository.NodeRelationShipRepository;
import com.app.repository.UserInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Neo4JDataTest {

    @Resource
    UserInfoRepository userInfoRepository;
    @Resource
    ClassInfoRepository classInfoRepository;
    @Resource
    ItemInfoRepository itemInfoRepository;
    @Resource
    NodeRelationShipRepository nodeRelationShipRepository;

    @Test
    public void testAdd() {
        UserInfo user1 = new UserInfo();
        user1.setUserId("1");
        user1.setName("zs");
        user1.setAge(18);
        user1.setSex("男");
        userInfoRepository.save(user1);

        UserInfo user2 = new UserInfo();
        user2.setUserId("2");
        user2.setName("lili");
        user2.setAge(18);
        user2.setSex("女");
        userInfoRepository.save(user2);

        ClassInfo class1 = new ClassInfo();
        class1.setClassId("1");
        class1.setName("数学");
        class1.setNum(12);
        classInfoRepository.save(class1);

        ClassInfo class2 = new ClassInfo();
        class2.setClassId("2");
        class2.setName("语文");
        class2.setNum(12);
        classInfoRepository.save(class2);

        ItemInfo itemInfo1 = new ItemInfo();
        itemInfo1.setItemId("1");
        itemInfo1.setTime("08:00");
        itemInfoRepository.save(itemInfo1);

        ItemInfo itemInfo2 = new ItemInfo();
        itemInfo2.setItemId("2");
        itemInfo2.setTime("09:00");
        itemInfoRepository.save(itemInfo2);

        NodeRelationShip nodeRelationShip1 = new NodeRelationShip();
        nodeRelationShip1.setName("课程关系");
        nodeRelationShip1.setStartNode(user1);
        nodeRelationShip1.setEndNode(class1);
        nodeRelationShip1.setRelation("报名");
        nodeRelationShipRepository.save(nodeRelationShip1);

        NodeRelationShip nodeRelationShip2 = new NodeRelationShip();
        nodeRelationShip2.setName("课程关系");
        nodeRelationShip2.setStartNode(user1);
        nodeRelationShip2.setEndNode(class2);
        nodeRelationShip2.setRelation("报名");
        nodeRelationShipRepository.save(nodeRelationShip2);

        NodeRelationShip nodeRelationShip3 = new NodeRelationShip();
        nodeRelationShip3.setName("课时关系");
        nodeRelationShip3.setStartNode(class1);
        nodeRelationShip3.setEndNode(itemInfo1);
        nodeRelationShip3.setRelation("课时");
        nodeRelationShipRepository.save(nodeRelationShip3);

        NodeRelationShip nodeRelationShip4 = new NodeRelationShip();
        nodeRelationShip4.setName("课时关系");
        nodeRelationShip4.setStartNode(class2);
        nodeRelationShip4.setEndNode(itemInfo2);
        nodeRelationShip4.setRelation("课时");
        nodeRelationShipRepository.save(nodeRelationShip4);

    }

    @Test
    public void testDelAll() {
        userInfoRepository.deleteAll();
        classInfoRepository.deleteAll();
        nodeRelationShipRepository.deleteAll();
    }

    @Test
    public void testDel() {
        userInfoRepository.deleteUserInfoById("2");
    }

    @Test
    public void testDelUserAndChild() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("查询");
        // 查询用户关联课程
        List<ClassInfo> classInfos = classInfoRepository.getClassInfoByUserId("1");
        List<String> classIds = classInfos.stream().map(ClassInfo::getClassId).collect(Collectors.toList());
        // 查询课程关联课时
        List<ItemInfo> itemInfos = itemInfoRepository.getItemInfosByClassIds(classIds);
        List<String> itemIds = itemInfos.stream().map(ItemInfo::getItemId).collect(Collectors.toList());
        stopWatch.stop();
        stopWatch.start("删除");
        // 删除用户
        userInfoRepository.deleteUserInfoByIds(Collections.singletonList("1"));
        // 删除课程
        classInfoRepository.deleteClassInfoByIds(classIds);
        // 删除课时
        itemInfoRepository.deleteClassInfoByIds(itemIds);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
