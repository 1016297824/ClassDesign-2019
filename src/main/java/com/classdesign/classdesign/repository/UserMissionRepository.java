package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.*;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMissionRepository extends CustomizedRepository<UserMission, Integer> {

    @Query("from UserMission um where um.user=:user")
    List<UserMission> FindUserMissionByUser(@Param("user") User user);

    @Query("from UserMission um where um.mission=:mission")
    List<UserMission> FindUserMissionByMission(@Param("mission") Mission mission);

    @Query("from UserMission um order by um.mission")
    List<UserMission> FindAll();

    @Query("from UserMission um where um.user=:user and um.mission=:mission")
    UserMission FindOne(@Param("user") User user, @Param("mission") Mission mission);
}
