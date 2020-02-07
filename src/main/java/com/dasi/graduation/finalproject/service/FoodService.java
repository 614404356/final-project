package com.dasi.graduation.finalproject.service;

import com.dasi.graduation.finalproject.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 11:07
 * @Description
 */
public interface FoodService {

    Page<Food> getPageFood(Pageable pageable);

    List<Food> findByType(String type);

    List<Food> findByNameLike(String name);

    void deleteOne(Long id);

    List<Food> findByIdIn(List<Long> ids);


}
