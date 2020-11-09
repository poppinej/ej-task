package com.learn.ejtask.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Title: <br>
 * Description: <br>
 * Company: wondersgroup.com <br>
 *
 * @author 吴艺杰
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVo {

    private List<String> tos;

    private String subject;

    private String content;
}
