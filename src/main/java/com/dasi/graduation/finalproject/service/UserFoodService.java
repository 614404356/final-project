package com.dasi.graduation.finalproject.service;

import com.dasi.graduation.finalproject.entity.UserFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 13:38
 * @Description
 */
public interface UserFoodService  {

    void deleteOne(Long id);

    void deleteAll(String phone);

    Page<UserFood> getPageListLike(Map<String, String> param, Pageable pageable);

    List<UserFood> findByPhoneAndDateLike(String phone, String date);

    List<UserFood> findByPhone(String phone);

}
