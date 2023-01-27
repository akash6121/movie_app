package com.example.movie_app;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecuters {
    private static AppExecuters instance;
    public static AppExecuters getInstance(){
        if(instance==null)
            instance=new AppExecuters();
        return instance;
    }
    private final ScheduledExecutorService executorService= Executors.newScheduledThreadPool(3);
    public ScheduledExecutorService getExecutorService(){
        return executorService;
    }
}
