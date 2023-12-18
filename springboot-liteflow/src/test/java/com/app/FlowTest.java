package com.app;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Map;

/**
 * flow test
 *
 * @author ch
 * @date 2023/12/18 15:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FlowTest {

    @Resource
    private FlowExecutor flowExecutor;

    @Test
    public void test_abc() {
        LiteflowResponse response = flowExecutor.execute2Resp("test-abc", "arg", DefaultContext.class);
        DefaultContext contextBean = response.getContextBean(DefaultContext.class);
        Map map = contextBean.getData("map");
        System.out.println("执行完成，最终结果：" + map);
    }
}
