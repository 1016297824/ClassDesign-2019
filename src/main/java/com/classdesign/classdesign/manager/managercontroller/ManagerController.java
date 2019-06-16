package com.classdesign.classdesign.manager.managercontroller;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.entity.UserInvigilate;
import com.classdesign.classdesign.repository.InvigilateRepository;
import com.classdesign.classdesign.repository.UserInvigilateRepository;
import com.classdesign.classdesign.repository.UserRepository;
import com.classdesign.classdesign.service.InvigilateService;
import com.classdesign.classdesign.service.UserInvigilateService;
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

    @Autowired
    private InvigilateService invigilateService;

    @Autowired
    private InvigilateRepository invigilateRepository;

    @Autowired
    private UserInvigilateService userInvigilateService;

    @Autowired
    private UserInvigilateRepository userInvigilateRepository;

    @GetMapping("/main")
    public Map ManagerMain() {
        List<User> users = userService.FindByAuthority(User.Teacher);
        List<Invigilate> invigilates = invigilateService.FindAll();
        List<UserInvigilate> userInvigilates=userInvigilateRepository.findAll();
        return Map.of("users", users, "invigilates", invigilates,"userinvigilates",userInvigilates);
    }

    @PostMapping("/add")
    public Map ManageAdd(@RequestBody User user) {
        String res = null;
        User user1 = userService.FindByNo(user.getNo());
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
        List<UserInvigilate> userInvigilates=userInvigilateService.FindUserInvigilateByUser(user);
        userInvigilateRepository.deleteAll(userInvigilates);
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

    @PostMapping("/addinvigilate")
    public Map InvigilateAdd(@RequestBody Invigilate invigilate) {
        String res = null;
        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        if (invigilate1 != null) {
            res = "场次已存在！";
        } else {
            if (invigilate.getNo() != null && invigilate.getCourse() != null && invigilate.getPlace() != null && invigilate.getStartTime() != null) {
                invigilate.setEndTime(invigilate.getStartTime().minusHours(-2));
                invigilateRepository.save(invigilate);
                res = "已添加监考！";
            } else {
                res = "填写的信息不全！";
            }
        }
        List<Invigilate> invigilates = invigilateService.FindAll();
        return Map.of("invigilates", invigilates, "res", res);
    }

    @PostMapping("/deletedinvigilate/{no}")
    public Map InvigilateDelete(@PathVariable String no) {
        Invigilate invigilate = invigilateService.FindByNO(no);
        List<UserInvigilate> userInvigilates=userInvigilateService.FindUserInvigilateByInvigilate(invigilate);
        userInvigilateRepository.deleteAll(userInvigilates);
        invigilateRepository.delete(invigilate);
        String res = "已删除！";
        List<Invigilate> invigilates = invigilateService.FindAll();
        return Map.of("invigilates", invigilates, "res", res);
    }

    @PostMapping("/updatainvigilate")
    public Map InvigilateUpdata(@RequestBody Invigilate invigilate) {
        String res = null;
        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        if (invigilate.getNo() != null && invigilate.getCourse() != null && invigilate.getPlace() != null && invigilate.getStartTime() != null) {
            invigilate1.setCourse(invigilate.getCourse());
            invigilate1.setPlace(invigilate.getPlace());
            invigilate1.setStartTime(invigilate.getStartTime());
            invigilate.setEndTime(invigilate.getStartTime().minusHours(-2));
            invigilateRepository.save(invigilate1);
            res = "已修改！";
        } else {
            res = "填写的信息不全！";
        }
        List<Invigilate> invigilates = invigilateService.FindAll();
        return Map.of("invigilates", invigilates, "res", res);
    }

    @PostMapping("/distributeinvigilate/{no}")
    public Map DistributeInvigilate(@PathVariable String no, @RequestBody Invigilate invigilate) {
        User user = userService.FindByNo(no);
        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByUser(user);
        String res = "未发现冲突！";
        for (int i = 0; i < userInvigilates.size(); i++) {
            if (userInvigilates.get(i).getInvigilate().getStartTime().equals(invigilate1.getStartTime())) {
                res="发现冲突:一个老师同时有两个或两个以上监考！";
            }
        }
        return Map.of("res",res);
    }

    @PostMapping("/isdistributeinvigilate/{no}")
    public Map IsDistributeInvigilate(@PathVariable String no, @RequestBody Invigilate invigilate) {
        User user = userService.FindByNo(no);
        user.setInvigilate(user.getInvigilate()+1);

        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        invigilate1.setStatus(Invigilate.isDistribution);

        UserInvigilate userInvigilate=new UserInvigilate();

        userInvigilate.setUser(user);
        userInvigilate.setInvigilate(invigilate1);
        userInvigilateRepository.save(userInvigilate);
        String res="已分配！";
        List<User> users = userService.FindByAuthority(User.Teacher);
        return Map.of("users",users,"res",res);
    }

    @PostMapping("/redistribute")
    public Map RedistributeInvigilate(@RequestBody Invigilate invigilate){
        List<UserInvigilate> userInvigilates=userInvigilateService.FindUserInvigilateByInvigilate(invigilate);
        userInvigilateRepository.deleteAll(userInvigilates);
        invigilate.setStatus(Invigilate.notDistribution);
        invigilate.setSend(Invigilate.notSend);
        invigilate.setReceive(Invigilate.notReceive);
        invigilateRepository.save(invigilate);

        List<Invigilate> invigilates = invigilateService.FindAll();
        List<UserInvigilate> userInvigilates1=userInvigilateRepository.findAll();
        String res="已重置！";
        return Map.of("invigilates", invigilates,"userinvigilates",userInvigilates1,"res",res);
    }
}
