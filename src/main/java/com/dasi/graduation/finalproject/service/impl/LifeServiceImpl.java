package com.dasi.graduation.finalproject.service.impl;

import com.dasi.graduation.finalproject.entity.Life;
import com.dasi.graduation.finalproject.repository.LifeRepository;
import com.dasi.graduation.finalproject.service.LifeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 15:43
 * @Description
 */
@Service
@Slf4j
public class LifeServiceImpl implements LifeService {

    @Autowired
    private LifeRepository lifeRepository;

    @Override
    public Page<Life> findByPhone(String phone, Pageable pageable) {
        Page<Life> lifeList = lifeRepository.findByPhone(phone,pageable);
        return lifeList;
    }

    @Override
    public void deleteOne(Long id) {
        lifeRepository.deleteOne(id);
    }

    @Override
    public List<Life> findByNameLike(String name) {
        List<Life> lifeList = lifeRepository.findByNameLike("%" + name + "%");
        return lifeList;
    }
}
