package com.learn.ejtask.utils.thread;

import com.learn.ejtask.model.thread.AsyncTaskProperties;
import com.learn.ejtask.utils.SpringContextHolder;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
public class ThreadPoolExecutorUtil {

    public static ThreadPoolExecutor getPoll(){

//        AsyncTaskProperties properties = SpringContextHolder.getBean(AsyncTaskProperties.class);
        return new ThreadPoolExecutor(
                10,
                30,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(50),
                new ThreadFactoryName()
        );
    }
}
