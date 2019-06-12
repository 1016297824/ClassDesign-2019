package com.classdesign.classdesign.manager.managercontroller;

import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.repository.UserRepository;
import com.classdesign.classdesign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/manager")
public class ManagerController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/main")
    public Map ManagerMain(){
        List<User> users=userService.FindByAuthority(User.Teacher);
        return Map.of("users",users);
    }

    @PostMapping("/add")
    public Map ManageAdd(@RequestBody User user){
        String res=null;
        User user1=userService.FindByNo(user.getNo());
        if (user1 != null) {/*System.out.println(user1.getAuthority());*/
            if (user1.getAuthority().equals(User.Super_Manager)) {
                res = "此用户为超级管理员，不可更改！";
            } else if (user1.getAuthority().equals(User.Manager)) {
                res = "此用户为管理员，操作越权！";
            } else if (user1.getAuthority().equals(User.Teacher)) {
                res = "教师已存在！";
            }
        } else {
            if (user.getNo() != null && user.getPassword() != null) {
                user.setAuthority(User.Teacher);
                String password = user.getPassword();
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                res = "已添加教师！";
            } else {
                res = "员工号和密码不能为空！";
            }
        }
        List<User> users = userService.FindByAuthority(User.Teacher);
        return Map.of("users", users, "res", res);
    }

    @PostMapping("/deleted/{no}")
    public Map ManagerDelete(@PathVariable String no) {
        User user = userService.FindByNo(no);
        userRepository.delete(user);
        String res = "已删除！";
        List<User> users = userService.FindByAuthority(User.Teacher);
        /*for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i).getNo() + " " + users.get(i).getName());
        }*/
        return Map.of("users", users, "res", res);
    }

    @PostMapping("/updata")
    public Map SuperManagerUpdata(@RequestBody User user) {
        User user1 = userService.FindByNo(user.getNo());
        user1.setName(user.getName());
        user1.setIntro(user.getIntro());
        user1.setMobile(user.getMobile());
        userRepository.save(user1);
        String res = "已修改！";
        List<User> users = userService.FindByAuthority(User.Teacher);
        return Map.of("users", users, "res", res);
    }
}
