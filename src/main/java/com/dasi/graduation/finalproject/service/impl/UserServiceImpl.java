package com.dasi.graduation.finalproject.service.impl;

import com.dasi.graduation.finalproject.entity.User;
import com.dasi.graduation.finalproject.repository.UserRepository;
import com.dasi.graduation.finalproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/8 0008 13:40
 * @Description
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<String> getAllPhone() {
       List<String> phoneList = userRepository.getAllPhone();
        return phoneList;
    }

    @Override
    public User findByPhone(String phone) {
        User user = userRepository.findByPhone(phone);
        return user;
    }

    @Override
    public List<User> findByPhoneLike(String phone) {
        List<User> userList = userRepository.findByPhoneLike("%" + phone +"%");
        return userList;
    }

}
