package com.dasi.graduation.finalproject.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author zhangqi
 * @Date 2019/5/3 0003 20:25
 * @Description
 */
@Data
public class LifeVo {

    private Long id;

    private String name;

    private String body;

    private String phone;

    private String photo;

    private String address;

    private String date;

    private String userPhoto;
}
