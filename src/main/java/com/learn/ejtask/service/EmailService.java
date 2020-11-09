package com.learn.ejtask.service;

import cn.hutool.extra.mail.MailAccount;
import com.learn.ejtask.exception.BadRequestException;
import com.learn.ejtask.mapper.ToolEmailConfigMapper;
import com.learn.ejtask.model.ToolEmailConfig;
import com.learn.ejtask.model.vo.EmailVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private final ToolEmailConfigMapper toolEmailConfigMapper;

    public ToolEmailConfig config(ToolEmailConfig emailConfig,ToolEmailConfig old){

        emailConfig.setConfigId(1L);
        if(!emailConfig.getPass().equals(old.getPass())){

            emailConfig.setPass(old.getPass());
        }
        toolEmailConfigMapper.insertSelective(emailConfig);

        return emailConfig ;
    }

    public ToolEmailConfig find(){

        return toolEmailConfigMapper.selectByPrimaryKey(1L);
    }

    public void send(EmailVo emailVo ,ToolEmailConfig toolEmailConfig){

        if(toolEmailConfig.getConfigId() == null){
            throw new BadRequestException("请先配置，再操作");
        }
        MailAccount account = new MailAccount();
        account.setUser(toolEmailConfig.getUser());
        account.setHost(toolEmailConfig.getHost());
        account.setPort(Integer.parseInt(toolEmailConfig.getPort()));
        account.setAuth(true);
        //TODO 邮箱功能

    }


}
