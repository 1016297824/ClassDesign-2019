package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CustomizedRepository<User, Integer> {

    @Query("select u from User u where u.no=:no")
    User FindByNO(@Param("no") String no);

    @Query("select u from User u where u.authority=:authority order by u.invigilate")
    List<User> FindByAuthority(@Param("authority") String authority);
}
