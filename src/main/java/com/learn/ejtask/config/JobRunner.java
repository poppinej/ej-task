package com.learn.ejtask.config;

import com.learn.ejtask.mapper.SysQuartzJobMapper;
import com.learn.ejtask.model.dto.JobDto;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import com.learn.ejtask.utils.QuartzManage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(JobRunner.class);
    private final SysQuartzJobMapper sysQuartzJobMapper;
    private final QuartzManage quartzManage;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("-------------------注入定时任务---------------");
        JobDto jobDto = new JobDto();
        jobDto.setIsPause(0);
        List<SysQuartzJobExt> quartzJobExts = sysQuartzJobMapper.getJobExtListByItem(jobDto);
        quartzJobExts.forEach(quartzManage::addJob);
        log.info("--------------注入完成！----------------------");

    }
}
