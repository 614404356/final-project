package com.dasi.graduation.finalproject.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zhangqi
 * @Date 2019/4/13 0013 17:22
 * @Description
 */
@Data
@Entity
@Table(name = "TIPS")
public class Tips {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BODY")
    private String body;

    @Column(name = "TYPE")
    private String type;
}
