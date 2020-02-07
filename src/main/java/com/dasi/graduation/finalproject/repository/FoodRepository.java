package com.dasi.graduation.finalproject.repository;

import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 11:06
 * @Description
 */
@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Query("SELECT a from Food a")
    Page<Food> getPageFood(Pageable pageable);

    List<Food> findByType(String type);

    List<Food> findByNameLike(String name);

    @Modifying
    @Transactional
    @Query("delete from Food a where a.id = ?1")
    void deleteOne(Long id);

    List<Food> findByIdIn(List<Long> ids);

    Food findByName(String name);

    @Modifying
    @Transactional
    @Query("update Food u set u.photo = ?1 where u.name = ?2")
    void updateOne(String photo,String name);
}
