package com.learn.ejtask.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Slf4j
public class QuartzRunnable implements Callable {

    private final Object target;
    private final Method method;
    private final String params;

    QuartzRunnable(String beanName,String methodName,String params) throws SecurityException, NoSuchMethodException {

        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;

        if(StringUtils.isNotEmpty(params)){

            this.method = target.getClass().getDeclaredMethod(methodName,String.class);
        }else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }


    }

    @Override
    public Object call() throws Exception {

        ReflectionUtils.makeAccessible(method);
        if(StringUtils.isNotBlank(params)){

            method.invoke(target, params);

        }else {
            method.invoke(target);
        }
        return null;
    }
}
