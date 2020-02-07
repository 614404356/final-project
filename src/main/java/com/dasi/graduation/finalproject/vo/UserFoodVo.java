package com.dasi.graduation.finalproject.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/4/9 0009 13:42
 * @Description
 */
@Data
public class UserFoodVo {

    private Long id;

    private String date;

    private String bodyText;

}
