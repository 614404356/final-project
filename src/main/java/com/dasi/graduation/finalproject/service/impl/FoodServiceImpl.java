package com.dasi.graduation.finalproject.service.impl;

import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.repository.FoodRepository;
import com.dasi.graduation.finalproject.service.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 11:07
 * @Description
 */
@Slf4j
@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Page<Food> getPageFood(Pageable pageable) {
        Page<Food> page = foodRepository.getPageFood(pageable);
        return page;
    }

    @Override
    public List<Food> findByType(String type) {
        List<Food> foodList = foodRepository.findByType(type);
        return foodList;
    }

    @Override
    public List<Food> findByNameLike(String name) {
        List<Food> foodList = foodRepository.findByNameLike("%" + name +"%");
        return foodList;
    }

    @Override
    public void deleteOne(Long id) {
        foodRepository.deleteOne(id);
    }

    @Override
    public List<Food> findByIdIn(List<Long> ids) {
        List<Food> foodList = foodRepository.findByIdIn(ids);
        return foodList;
    }
}
