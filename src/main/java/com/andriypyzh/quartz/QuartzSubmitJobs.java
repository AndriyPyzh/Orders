package com.andriypyzh.quartz;

import com.andriypyzh.services.jobs.DeleteOutdatedOrdersJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzSubmitJobs {
    private static final String CRON_EVERY_MINUTE = "0 0/1 * ? * * *";

    @Bean(name = "DeleteOutdatedOrdersJob")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(DeleteOutdatedOrdersJob.class, "DeleteOutdatedOrdersJob");
    }

    @Bean(name = "DeleteOutdatedOrdersTrigger")
    public CronTriggerFactoryBean triggerMemberStats(@Qualifier("DeleteOutdatedOrdersJob") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_MINUTE, "Delete Outdated Orders Trigger");
    }

}
