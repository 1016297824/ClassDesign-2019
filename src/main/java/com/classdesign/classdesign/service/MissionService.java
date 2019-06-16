package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.entity.Mission;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.InvigilateRepository;
import com.classdesign.classdesign.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    public Mission FindByNO(String no){
        return missionRepository.FindByNO(no);
    }

    public List<Mission> FindAll(){
        return missionRepository.FindAll();
    }

    public List<Mission> FindMissionByUser(User user){
        return missionRepository.FindMissionByUser(user);
    }
}
