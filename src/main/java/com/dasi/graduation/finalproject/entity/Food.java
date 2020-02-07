package com.dasi.graduation.finalproject.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author zhangyutao
 * @Date 2019/3/26 0026 17:51
 * @Description
 */
@Data
@Entity
@Table(name = "FOOD")
public class Food {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "POWER")
    private String power;

    @Column(name = "PROTEIN")
    private String protein;

    @Column(name = "FAT")
    private String fat;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "BODY")
    private String body;

}