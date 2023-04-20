package com.app.quartz;

import com.app.service.TestTimerService;
import org.quartz.CronTrigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

@Configuration
public class QuartzTaskConfig {

    /**
     * 任务详情
     */
    @Bean(name = "quartzJobDetail")
    MethodInvokingJobDetailFactoryBean quartzJobDetail(TestTimerService testTimerService) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        // 是否并发执行
        jobDetail.setConcurrent(false);
        // 为需要执行的实体类对应的对象
        jobDetail.setTargetObject(testTimerService);
        // 需要执行的方法
        jobDetail.setTargetMethod("run");
        return jobDetail;
    }

    /**
     * 触发器
     */
    @Bean(name = "quartzJobTrigger")
    CronTriggerFactoryBean recordStatsJobTriggerFactory(
            @Qualifier("quartzJobDetail") MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean) {
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(methodInvokingJobDetailFactoryBean.getObject());
        // cron表达式
        trigger.setCronExpression("0/5 * * * * ? ");
        return trigger;
    }

    @Bean(name = "quartzTrigger")
    CronTrigger recordStatsJobTrigger(
            @Qualifier("quartzJobTrigger") CronTriggerFactoryBean cronTriggerFactoryBean) {
        return cronTriggerFactoryBean.getObject();
    }

}