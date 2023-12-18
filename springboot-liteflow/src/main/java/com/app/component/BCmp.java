package com.app.component;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * b
 *
 * @author ch
 * @date 2023/12/18 15:30
 */
@Component("b")
public class BCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        DefaultContext contextBean = this.getContextBean(DefaultContext.class);
        Map<String, Object> map = contextBean.getData("map");
        map.put("b", "b");
        contextBean.setData("map", map);
        System.out.println("执行b的逻辑");
    }
}
