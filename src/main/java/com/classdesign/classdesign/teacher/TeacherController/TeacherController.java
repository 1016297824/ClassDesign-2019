package com.classdesign.classdesign.teacher.TeacherController;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.UserRepository;
import com.classdesign.classdesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/main/{no}")
    public Map TeacherMain(@PathVariable String no){
        User user=userService.FindByNo(no);
        return Map.of("user", user);
    }

    @PostMapping("/updata")
    public Map SuperManagerUpdata(@RequestBody User user) {
        User user1 = userService.FindByNo(user.getNo());
        user1.setName(user.getName());
        user1.setIntro(user.getIntro());
        user1.setMobile(user.getMobile());
        userRepository.save(user1);
        String res = "已修改！";
        User users = userService.FindByNo(user.getNo());
        return Map.of("user", user, "res", res);
    }
}
