package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.*;
import com.classdesign.classdesign.repository.UserInvigilateRepository;
import com.classdesign.classdesign.repository.UserMissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserMissionService {
    @Autowired
    UserMissionRepository userMissionRepository;

    public List<UserMission> FindUserMissionByUser(User user) {
        return userMissionRepository.FindUserMissionByUser(user);
    }

    public List<UserMission> FindUserMissionByMission(Mission mission) {
        return userMissionRepository.FindUserMissionByMission(mission);
    }

    public List<UserMission> FindAll() {
        return userMissionRepository.FindAll();
    }

    public UserMission FindOne(User user, Mission mission) {
        return userMissionRepository.FindOne(user, mission);
    }
}
