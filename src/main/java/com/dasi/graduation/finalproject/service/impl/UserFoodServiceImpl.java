package com.dasi.graduation.finalproject.service.impl;

import com.dasi.graduation.finalproject.entity.UserFood;
import com.dasi.graduation.finalproject.repository.UserFoodRepository;
import com.dasi.graduation.finalproject.service.UserFoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 13:38
 * @Description
 */
@Slf4j
@Service
public class UserFoodServiceImpl implements UserFoodService {

    @Autowired
    private UserFoodRepository userFoodRepository;

    @Override
    public void deleteOne(Long id) {
        userFoodRepository.deleteOne(id);
    }

    @Override
    public void deleteAll(String phone) {
        userFoodRepository.deleteAll(phone);
    }

    @Override
    public Page<UserFood> getPageListLike(Map<String, String> param, Pageable pageable) {
        pageable = getPageRequest(pageable);
        return userFoodRepository.getAll((Specification<UserFood>) (root, query, cb) -> {
            List<Predicate> predicate = new ArrayList<>();
            String name = param.get("name");
            if (!StringUtils.isEmpty(name)) {
                predicate.add(cb.like(root.get("name"), "%" + name + "%"));
            }
            if(param.containsKey("date")) {
                String date = param.get("date");
                predicate.add(cb.equal(root.get("date"), date));
            }
            Predicate[] pre = new Predicate[predicate.size()];
            return query.where(predicate.toArray(pre)).getRestriction();
        }, pageable);
    }

    @Override
    public List<UserFood> findByPhoneAndDateLike(String phone, String date) {
        List<UserFood> userFoodList = userFoodRepository.findByPhoneAndDateLike(phone,"%" + date + "%");
        return userFoodList;
    }

    @Override
    public List<UserFood> findByPhone(String phone) {
        List<UserFood> userFoodList = userFoodRepository.findByPhone(phone);
        return userFoodList;
    }

    protected Pageable getPageRequest(Pageable pageRequest) {
        if (pageRequest == null) {
            return PageRequest.of(0, 100);
        } else if (pageRequest.getPageSize() > 100) {
            pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                    pageRequest.getSort());
        }
        return pageRequest;
    }
}
