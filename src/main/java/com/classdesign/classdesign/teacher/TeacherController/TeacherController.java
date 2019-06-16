package com.classdesign.classdesign.teacher.TeacherController;

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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvigilateService invigilateService;

    @Autowired
    private InvigilateRepository invigilateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInvigilateService userInvigilateService;

    @Autowired
    private UserInvigilateRepository userInvigilateRepository;

    @GetMapping("/main/{no}")
    public Map TeacherMain(@PathVariable String no) {
        User user = userService.FindByNo(no);
        List<UserInvigilate> userInvigilates=userInvigilateService.FindUserInvigilateByUser(user);
        List<Invigilate> invigilates = new ArrayList<>();
        String res = "新消息：\n";
        for (int i = 0; i < userInvigilates.size(); i++) {
            /*System.out.println(userInvigilates.get(i).getId());*/
            invigilates.add(userInvigilates.get(i).getInvigilate());
            /*System.out.println(invigilates.get(i).getId());*/
            if (userInvigilates.get(i).getInvigilate().getStatus().equals(Invigilate.isDistribution)) {
                if (userInvigilates.get(i).getInvigilate().getSend().equals(Invigilate.isSend)) {
                    if (userInvigilates.get(i).getInvigilate().getReceive().equals(Invigilate.notReceive)) {
                        res = res + "教师：" + userInvigilates.get(i).getUser().getNo() + "\n" + "监考次数：" + userInvigilates.get(i).getUser().getInvigilate() + "\n";
                        res = res + "地点：" + userInvigilates.get(i).getInvigilate().getPlace() + "\n" + "开始时间：" + userInvigilates.get(i).getInvigilate().getStartTime() + "\n" ;
                        invigilates.get(i).setReceive(Invigilate.isReceive);
                    }
                }
            }
        }
        invigilateRepository.saveAll(invigilates);
        return Map.of("user", user, "invigilates", invigilates, "res", res);
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
        return Map.of("users", users, "res", res);
    }
}
