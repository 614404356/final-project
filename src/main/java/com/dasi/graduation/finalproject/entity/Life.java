package com.dasi.graduation.finalproject.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 15:40
 * @Description
 */
@Data
@Entity
@Table(name = "LIFE")
public class Life {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BODY")
    private String body;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE")
    private String date;
}
