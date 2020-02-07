package com.dasi.graduation.finalproject.repository;

import com.dasi.graduation.finalproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhangyutao
 * @Date 2019/3/8 0008 13:41
 * @Description
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT a.phone from User a")
    List<String> getAllPhone();

    User findByPhone(String phone);


    @Modifying
    @Transactional
    @Query(value="delete from User where phone=?1")
    void deleteUser(String phone);

    List<User> findByPhoneLike(String phone);

}
