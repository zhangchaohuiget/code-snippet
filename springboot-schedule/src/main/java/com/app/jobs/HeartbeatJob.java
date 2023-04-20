package com.app.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class HeartbeatJob {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    /**
     * 检查状态1
     */
    @Scheduled(cron = "0 30 12 * * ?")
    public void checkState1() {
        logger.info(">>>>> cron中午12:30上传检查开始....");
        logger.info(">>>>> cron中午12:30上传检查完成....");
    }

    /**
     * 检查状态2
     */
    @Scheduled(cron = "0 0 18 * * ?")
    public void checkState2() {
        logger.info(">>>>> cron晚上18:00上传检查开始....");
        logger.info(">>>>> cron晚上18:00上传检查完成....");
    }

    /**
     * fixedRate
     */
    @Scheduled(fixedRate = 5 * 1000)
    public void fixedRate() {
        logger.info(">>>>> fixedRate执行了....");
    }

    /**
     * fixedRate
     */
    @Scheduled(fixedDelay = 5 * 1000)
    public void fixedDelay() {
        logger.info(">>>>> fixedDelay执行了....");
    }

}
