package com.dasi.graduation.finalproject.service;

import com.dasi.graduation.finalproject.entity.User;

import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/8 0008 13:39
 * @Description
 */
public interface UserService {

    List<String> getAllPhone();

    User findByPhone(String phone);

    List<User> findByPhoneLike(String phone);

}
