package com.app.schedule;

import com.app.loader.HotClassLoader;
import org.springframework.stereotype.Component;

/**
 * 调度
 *
 * @author ch
 * @date 2023/11/21 11:43
 */
@Component
public class MySchedule {

//    @Scheduled(fixedDelay = 3 * 1000L)
    public void loadJars() throws Exception {
        HotClassLoader.loadAllJar();
    }
}
