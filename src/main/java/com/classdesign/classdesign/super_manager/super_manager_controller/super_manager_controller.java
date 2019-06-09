package com.classdesign.classdesign.super_manager.super_manager_controller;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.service.UserService;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/super_manager")
public class super_manager_controller {

    @Autowired
    private UserService userService;

    @GetMapping("/main")
    public Map super_manager_main(@RequestAttribute String no, @RequestAttribute String autontity) {
        List<User> users = userService.FindByAuthority(User.Super_Manager);
        return Map.of("user",users);
    }
}
