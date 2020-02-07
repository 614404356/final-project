package com.dasi.graduation.finalproject.repository;

import com.dasi.graduation.finalproject.entity.Food;
import com.dasi.graduation.finalproject.entity.UserFood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/31 0031 13:37
 * @Description
 */
@Repository
public interface UserFoodRepository extends JpaRepository<UserFood,Long> {

    @Modifying
    @Transactional
    @Query("delete from UserFood a where a.id=?1")
    void deleteOne(Long id);

    @Modifying
    @Transactional
    @Query("delete from UserFood a where a.phone =?1")
    void deleteAll(String phone);

    @Query("select a from UserFood a")
    Page<UserFood> getAll(Specification<UserFood> userFoodSpecification, Pageable pageable);

    List<UserFood> findByPhoneAndDateLike(String phone,String date);

    List<UserFood> findByPhone(String phone);

    @Query("select a from UserFood a where a.id=?1")
    UserFood getUserFoodOne(Long id);

}
