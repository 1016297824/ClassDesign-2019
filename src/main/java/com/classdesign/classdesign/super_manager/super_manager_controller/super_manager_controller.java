package com.classdesign.classdesign.super_manager.super_manager_controller;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.UserRepository;
import com.classdesign.classdesign.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/super_manager")
public class super_manager_controller {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/main")
    public Map SuperManagerMain() {

        List<User> users = userService.FindByAuthority(User.Manager);
        /*for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getNo() + " " + users.get(i).getName());
        }*/
        return Map.of("user", users);
    }

    @PostMapping("/main/deleted/{no}")
    public Map SuperManagerDelete(@PathVariable String no) {
        User user = userService.FindByNo(no);
        userRepository.delete(user);
        List<User> users = userService.FindByAuthority(User.Manager);
        /*for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getNo() + " " + users.get(i).getName());
        }*/
        return Map.of("user", users);
    }

    @PostMapping("/main/add")
    public Map SuperManagerAdd(@RequestBody User user) {
        String res = null;
        User user1 = userService.FindByNo(user.getNo());
        /*System.out.println(user1.getAuthority());*/
        if (user1 != null) {/*System.out.println(user1.getAuthority());*/
            if (user1.getAuthority().equals(User.Super_Manager)) {
                res = "此用户为超级管理员，不可更改！";
            } else if (user1.getAuthority().equals(User.Manager)) {
                res = "管理员已存在！";
            } else if (user1.getAuthority().equals(User.Teacher)) {
                res = "已添加管理员！";
                user1.setAuthority(User.Manager);
                user1.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user1);
            }
        } else if (user.getNo() != null && user.getPassword() != null) {
            res = "已添加管理员！";
            user.setAuthority(User.Manager);
            String password = user.getPassword();
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        } else {
            res = "输入编号和密码不能为空！";
        }
        List<User> users = userService.FindByAuthority(User.Manager);
        /*JOptionPane.showMessageDialog(null, res);*/
        return Map.of("user", users);
    }
}
