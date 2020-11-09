package com.learn.ejtask.utils;

import com.learn.ejtask.mapper.SysQuartzJobMapper;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import com.learn.ejtask.service.SysQuartzJobService;
import com.learn.ejtask.utils.thread.ThreadPoolExecutorUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Async
@SuppressWarnings({"unchecked","all"})
@Component
public class ExecutionJob extends QuartzJobBean {

    private final static ThreadPoolExecutor EXECUTOR = ThreadPoolExecutorUtil.getPoll();


    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext){
        SysQuartzJobExt sysQuartzJobExt = (SysQuartzJobExt)jobExecutionContext.getMergedJobDataMap().get(SysQuartzJobExt.JOB_KEY);

        SysQuartzJobService sysQuartzJobService = SpringContextHolder.getBean(SysQuartzJobService.class);

        RedisUtils redisUtils = SpringContextHolder.getBean(RedisUtils.class);
        String uuid = sysQuartzJobExt.getUuid();
        long startTime = System.currentTimeMillis();

        try{

            System.out.println("--------------------------------");
            System.out.println("任务开始执行，任务名称：" + sysQuartzJobExt.getJobName());
            QuartzRunnable task = new QuartzRunnable(sysQuartzJobExt.getBeanName(),sysQuartzJobExt.getMethodName(),
                    sysQuartzJobExt.getParams());

            Future<?> future = EXECUTOR.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;

            if(StringUtils.isNotBlank(uuid)){

                redisUtils.set(uuid,true);
            }

            System.out.println("任务执行完毕，任务名称：" + sysQuartzJobExt.getJobName() + ",执行时间" + times +"毫秒");
            System.out.println("------------------------------------------");

            if(sysQuartzJobExt.getSubTask() != null ){

                String[] tasks = sysQuartzJobExt.getSubTask().split("[,，]");

                sysQuartzJobService.executionSubJob(tasks);
            }

        }catch (Exception e){
            if(StringUtils.isNotBlank(uuid)){
                redisUtils.set(uuid,false);
            }

            System.out.println("任务执行失败，任务名称：" + sysQuartzJobExt.getJobName());
            System.out.println("----------------------------------------------------");

            if(sysQuartzJobExt.getPauseAfterFailure() != null && sysQuartzJobExt.getPauseAfterFailure()){
                sysQuartzJobExt.setIsPause(false);
                sysQuartzJobService.updateIsPause(sysQuartzJobExt);

            }
            if(sysQuartzJobExt.getEmail() != null){

            }
        }









    }
}
