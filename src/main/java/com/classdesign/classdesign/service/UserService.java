package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User FindByNo(String no) {
        return userRepository.FindByNO(no);
    }

    public List<User> FindByAuthority(String authority){
        return userRepository.FindByAuthority(authority);
    }
}
