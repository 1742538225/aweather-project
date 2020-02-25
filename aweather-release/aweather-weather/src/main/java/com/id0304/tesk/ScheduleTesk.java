package com.id0304.tesk;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTesk {
    @Scheduled(cron ="*/5 * * * * ?" )
    public void hello(){
        System.out.println("hello");
    }
}
