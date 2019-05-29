package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class InitService implements InitializingBean {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (userRepository.count() == 0) {
            User user = new User();
            user.setAuthority(User.Super_Manager);
            user.setNo("10001");
            user.setPassword(passwordEncoder.encode("138992"));
            user.setName("张坤");
            user.setIntro("管理所有其他管理员");
            user.setMobile("15546632665");
            userRepository.save(user);
        }
    }
}
