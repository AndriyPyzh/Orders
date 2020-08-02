package com.andriypyzh.services.jobs;

import com.andriypyzh.services.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteOutdatedOrdersJob implements Job {
    @Autowired
    private OrderService orderService;

    @Override
    public void execute(JobExecutionContext context){
        orderService.deleteOutdatedOrders();
    }
}
