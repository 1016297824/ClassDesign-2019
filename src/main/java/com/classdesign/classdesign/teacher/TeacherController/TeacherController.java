package com.classdesign.classdesign.teacher.TeacherController;

import com.classdesign.classdesign.entity.*;
import com.classdesign.classdesign.repository.*;
import com.classdesign.classdesign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserMissionService userMissionService;

    @Autowired
    private UserMissionRepository userMissionRepository;

    @GetMapping("/main/{no}")
    public Map TeacherMain(@PathVariable String no) {
        User user = userService.FindByNo(no);
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByUser(user);
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
                        res = res + "地点：" + userInvigilates.get(i).getInvigilate().getPlace() + "\n" + "开始时间：" + userInvigilates.get(i).getInvigilate().getStartTime() + "\n";
                        invigilates.get(i).setReceive(Invigilate.isReceive);
                    }
                }
            }
        }
        invigilateRepository.saveAll(invigilates);

        List<UserMission> userMissions = userMissionService.FindUserMissionByUser(user);
        /*System.out.println(userMissions.get(0).getMission().getNo());*/

        return Map.of("user", user, "invigilates", invigilates, "usermissions", userMissions, "res", res);
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

    @PostMapping("/replaymission")
    public Map ReplayMission(@RequestBody UserMission userMission) {
        /*System.out.println(userMission.getUser().getNo()+userMission.getMission().getNo());*/
        String res = null;
        /*System.out.println(userMission.getReplay());*/
        User user = userService.FindByNo(userMission.getUser().getNo());
        Mission mission = missionService.FindByNO(userMission.getMission().getNo());

        UserMission userMission1 = userMissionService.FindOne(user, mission);
        if (userMission1.getStatus().equals(UserMission.notReplay)) {
            userMission1.setReplay(userMission.getReplay());
            userMission1.setCompleteTime(LocalDateTime.now());
            if (userMission1.getMission().getEndTime().isBefore(LocalDateTime.now())) {
                userMission1.setStatus(UserMission.notTime);
            } else {
                userMission1.setStatus(UserMission.isReplay);
            }
            userMissionRepository.save(userMission1);
            res = "回复成功！";
        } else {
            res = "已回复过此任务！";
        }

        List<UserMission> userMissions = userMissionService.FindUserMissionByUser(user);
        /*System.out.println(userMissions.get(0).getMission().getNo());*/
        return Map.of("usermissions", userMissions, "res", res);
    }
}
