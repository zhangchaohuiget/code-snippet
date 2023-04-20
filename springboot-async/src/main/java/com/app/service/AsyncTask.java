package com.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class AsyncTask {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);

    @Async
    public void dealNoReturnTask() {
        logger.info("返回值为void的异步调用开始" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("返回值为void的异步调用结束" + Thread.currentThread().getName());
    }

    @Async
    public Future<String> dealHaveReturnTask(int i) {
        logger.info("asyncInvokeReturnFuture, parementer=" + i);
        Future<String> future;
        try {
            Thread.sleep(1000L * i);
            future = new AsyncResult<>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<>("error");
        }
        return future;
    }

}
