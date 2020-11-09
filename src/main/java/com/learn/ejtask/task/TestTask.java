package com.learn.ejtask.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class TestTask {

    public void run(){
        log.info("定时任务测试成功！");
    }

    public  void run2(String str) {
        log.info("wdnmd!!!!"+str);
    }
}
