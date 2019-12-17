package com.shsxt.crm.task;

import com.shsxt.crm.service.CustomerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JobService {

    @Resource
    private CustomerService customerService;


   // @Scheduled(cron = "0/5 * * * * ? ")
    public  void job(){
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"JobService-->job定时任务-->hello world");
        customerService.updateCustomerState();
    }




}
