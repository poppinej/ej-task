package com.learn.ejtask.controller;

import com.learn.ejtask.model.SysQuartzJob;
import com.learn.ejtask.model.ext.SysQuartzJobExt;
import com.learn.ejtask.service.SysQuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@RestController
@RequestMapping("/quartz")
public class SysQuartzJobController {

    @Autowired
    private SysQuartzJobService sysQuartzJobService;



    @RequestMapping("/updateIsPause")
    public Object updateIsPause(Long id){

        SysQuartzJobExt sysQuartzJobExt = sysQuartzJobService.findById(id);

        sysQuartzJobService.updateIsPause(sysQuartzJobExt);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);



    }
}
