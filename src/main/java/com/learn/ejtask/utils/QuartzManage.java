package com.learn.ejtask.utils;

import com.learn.ejtask.exception.BadRequestException;
import com.learn.ejtask.model.SysQuartzJob;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Slf4j
@Component
public class QuartzManage {

    private static final String JOB_NAME = "TASK_";

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(SysQuartzJobExt sysQuartzJob){

        try{
            //构建JOB信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class)
                    .withIdentity(JOB_NAME + sysQuartzJob.getJobId()).build();
            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(JOB_NAME + sysQuartzJob.getJobId()).startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(sysQuartzJob.getCronExpression())).build();

            cronTrigger.getJobDataMap().put(SysQuartzJobExt.JOB_KEY,sysQuartzJob);

            ((CronTriggerImpl)cronTrigger).setStartTime(new Date());

            scheduler.scheduleJob(jobDetail,cronTrigger);

            if(sysQuartzJob.getIsPause()){
                pauseJob(sysQuartzJob);
            }
        } catch (Exception e){
            log.error("更新定时任务失败",e);
            throw new BadRequestException("更新定时任务失败");
        }


    }

    public void runJobNow(SysQuartzJobExt sysQuartzJobExt){

        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + sysQuartzJobExt.getJobId());
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            //如果不存在则创建一个定时任务
            if(trigger == null){
                addJob(sysQuartzJobExt);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(SysQuartzJobExt.JOB_KEY,sysQuartzJobExt);
            JobKey jobKey = JobKey.jobKey(JOB_NAME + sysQuartzJobExt.getJobId());
            scheduler.triggerJob(jobKey,dataMap);
        }catch (Exception e){
            log.error("定时任务执行失败！",e);
            throw new BadRequestException("定时任务执行失败");
        }
    }

    public void pauseJob(SysQuartzJobExt sysQuartzJobExt){

        try{
            JobKey jobKey = JobKey.jobKey(JOB_NAME + sysQuartzJobExt.getJobId());
            scheduler.pauseJob(jobKey);
        }catch (Exception e){
            log.error("定时任务暂停失败",e);
            throw new BadRequestException("定时任务暂停失败");
        }
    }

    public void resumeJob(SysQuartzJobExt sysQuartzJobExt){

        try{
            TriggerKey triggerKey = TriggerKey.triggerKey(JOB_NAME + sysQuartzJobExt.getJobId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //如果不存在则创建一个定时任务
            if(trigger == null){
                addJob(sysQuartzJobExt);
            }
            JobKey jobKey = JobKey.jobKey(JOB_NAME + sysQuartzJobExt.getJobId());
            scheduler.resumeJob(jobKey);
        }catch (Exception e){
            log.error("恢复定时任务失败",e);
            throw new BadRequestException("恢复定时任务失败");
        }
    }
}
