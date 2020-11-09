package com.learn.ejtask.utils.common;

import cn.hutool.core.util.ObjectUtil;
import com.learn.ejtask.exception.BadRequestException;
import org.apache.commons.lang3.ObjectUtils;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
public class ValidationUtil {


    /**
     * 验证空
     */
    public static void isNull(Object obj, String entity, String parameter, Object value) {
        if (ObjectUtil.isNull(obj)) {
            String msg = entity + " 不存在: " + parameter + " is " + value;
            throw new BadRequestException(msg);
        }
    }
}
