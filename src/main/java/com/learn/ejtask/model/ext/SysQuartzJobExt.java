package com.learn.ejtask.model.ext;

import com.learn.ejtask.model.SysQuartzJob;
import lombok.Data;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Data
public class SysQuartzJobExt extends SysQuartzJob {

    public static final String JOB_KEY = "JOB_KEY";

    private String uuid;
}
