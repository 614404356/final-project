package com.dasi.graduation.finalproject.repository;

import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.Life;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 15:42
 * @Description
 */
@Repository
public interface LifeRepository extends JpaRepository<Life,Integer> {

    Page<Life> findByPhone(String phone, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Life a where a.id = ?1")
    void deleteOne(Long id);

    List<Life> findByNameLike(String name);

    Life findByDateAndPhone(String date,String phone);

    @Modifying
    @Transactional
    @Query("update Life u set u.photo = ?1 where u.id = ?2")
    void updateOne(String photo,Long id);

    @Modifying
    @Transactional
    @Query("delete from Life a where a.phone = ?1")
    void deleteByPhone(String phone);
}
