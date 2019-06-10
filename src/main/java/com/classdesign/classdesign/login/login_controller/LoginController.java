package com.classdesign.classdesign.login.login_controller;

import com.classdesign.classdesign.component.EncryptorComponent;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.awt.desktop.SystemSleepEvent;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginController {
    private static final String SUPER_MANAGER_ROLE = "SUPER_MANAGER";
    private static final String MANAGER_ROLE = "MANAGER";
    private static final String TEACHER_ROLE = "TEACHER";

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptorComponent encryptorComponent;

    @PostMapping("/login")
    public void Login(@RequestBody User user, HttpServletResponse response) {
        Optional.ofNullable(userService.FindByNo(user.getNo()))
                .or(() -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User name does not exist!");
                })
                .ifPresentOrElse(u -> {
                    if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong password!");
                    }

                    Map map = Map.of("no", u.getNo(), "authority", u.getAuthority());
                    String token = encryptorComponent.encrypt(map);
                    response.setHeader("token", token);

                    String role = null;
                    if (u.getAuthority().equals(User.Super_Manager)) {
                        role = SUPER_MANAGER_ROLE;
                    } else if (u.getAuthority().equals(User.Manager)) {
                        role = MANAGER_ROLE;
                    } else if (u.getAuthority().equals(User.Teacher)) {
                        role = TEACHER_ROLE;
                    }
                    response.setHeader("role", role);

                }, () -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误！");
                });
    }
}
