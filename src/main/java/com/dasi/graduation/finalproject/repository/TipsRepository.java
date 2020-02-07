package com.dasi.graduation.finalproject.repository;

import com.dasi.graduation.finalproject.entity.Life;
import com.dasi.graduation.finalproject.entity.Tips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangqi
 * @Date 2019/4/13 0013 17:41
 * @Description
 */
@Repository
public interface TipsRepository extends JpaRepository<Tips,Integer> {

    List<Tips> findByType(String type);

    Tips findById(Long id);
}
