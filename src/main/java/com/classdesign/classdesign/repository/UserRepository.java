package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CustomizedRepository<User, Integer> {
    @Query("select u from User u where u.no=:no")
    User find(@Param("no") String no);
}
