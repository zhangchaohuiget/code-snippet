package com.app.component;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.slot.DefaultContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * a
 *
 * @author ch
 * @date 2023/12/18 15:30
 */
@Component("a")
public class ACmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        Map<String, Object> map = new HashMap<>(16);
        map.put("a", "a");
        DefaultContext contextBean = this.getContextBean(DefaultContext.class);
        contextBean.setData("map", map);
        System.out.println("执行a的逻辑");
    }
}
