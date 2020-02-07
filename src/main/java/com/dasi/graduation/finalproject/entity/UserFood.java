package com.dasi.graduation.finalproject.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 11:09
 * @Description
 */
@Data
@Entity
@Table(name = "USER_FOOD")
public class UserFood {

    @Id
    @GeneratedValue
    private Long id;

    //2019-03-31 早
    @Column(name = "DATE")
    private String date;

    @Column(name = "BODY")
    private String body;

    @Column(name = "PHONE")
    private String phone;

}
