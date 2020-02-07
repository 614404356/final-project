package com.dasi.graduation.finalproject.service;

import com.dasi.graduation.finalproject.entity.Life;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 15:42
 * @Description
 */
public interface LifeService {

    Page<Life> findByPhone(String phone, Pageable pageable);

    void deleteOne(Long id);

    List<Life> findByNameLike(String name);


}
