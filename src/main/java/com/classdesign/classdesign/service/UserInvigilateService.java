package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.entity.UserInvigilate;
import com.classdesign.classdesign.repository.UserInvigilateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserInvigilateService {
    @Autowired
    UserInvigilateRepository userInvigilateRepository;

    public List<UserInvigilate> FindUserInvigilateByUser(User user){
        return userInvigilateRepository.FindUserInvigilateByUser(user);
    }

    public List<UserInvigilate> FindUserInvigilateByInvigilate(Invigilate invigilate){
        return userInvigilateRepository.FindUserInvigilateByInvigilate(invigilate);
    }

    public List<UserInvigilate> FindAll(){
        return userInvigilateRepository.FindAll();
    }
}
