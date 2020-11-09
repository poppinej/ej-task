package com.learn.ejtask.service;

import cn.hutool.core.util.IdUtil;
import com.learn.ejtask.mapper.SysQuartzJobMapper;
import com.learn.ejtask.model.SysQuartzJob;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import com.learn.ejtask.utils.QuartzManage;
import com.learn.ejtask.utils.RedisUtils;
import com.learn.ejtask.utils.common.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;

import java.util.List;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class SysQuartzJobService {

    private final SysQuartzJobMapper sysQuartzJobMapper;
    private final QuartzManage quartzManage;
    private final RedisUtils redisUtils;



    public void updateIsPause(SysQuartzJobExt sysQuartzJobExt){
        if(sysQuartzJobExt.getIsPause()){
            quartzManage.resumeJob(sysQuartzJobExt);
            sysQuartzJobExt.setIsPause(false);
        }else {
            quartzManage.pauseJob(sysQuartzJobExt);
            sysQuartzJobExt.setIsPause(true);
        }
        sysQuartzJobMapper.updateByPrimaryKeySelective(sysQuartzJobExt);
    }

    public void execution(SysQuartzJobExt sysQuartzJobExt){
        quartzManage.addJob(sysQuartzJobExt);
    }

    public SysQuartzJobExt findById(Long id){

        SysQuartzJobExt sysQuartzJobExt = sysQuartzJobMapper.selectById(id);
        ValidationUtil.isNull(sysQuartzJobExt.getJobId(),"QuartzJob","id",id);
        return sysQuartzJobExt;
    }

    public void executionSubJob(String[] tasks) throws InterruptedException {

        for(String id : tasks){

            SysQuartzJobExt sysQuartzJobExt = findById(Long.parseLong(id));
              //执行任务
            String uuid = IdUtil.simpleUUID();
            sysQuartzJobExt.setUuid(uuid);

            execution(sysQuartzJobExt);
            //获取执行状态
            Boolean result = (Boolean)redisUtils.get(uuid);

            while(result == null){

                Thread.sleep(5000);
                result = (Boolean)redisUtils.get(uuid);
            }
            if(!result){
                redisUtils.del(uuid);
                break;
            }
        }
    }
}
