package com.app.component;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * c
 *
 * @author ch
 * @date 2023/12/18 15:30
 */
@Component("c")
public class CCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        DefaultContext contextBean = this.getContextBean(DefaultContext.class);
        Map<String, Object> map = contextBean.getData("map");
        map.put("c", "c");
        contextBean.setData("map", map);
        System.out.println("执行c的逻辑");
    }
}
