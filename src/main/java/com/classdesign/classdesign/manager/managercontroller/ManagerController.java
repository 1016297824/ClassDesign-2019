package com.classdesign.classdesign.manager.managercontroller;

import com.classdesign.classdesign.entity.*;
import com.classdesign.classdesign.repository.*;
import com.classdesign.classdesign.service.*;
import org.apache.catalina.Manager;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private MissionService missionService;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserMissionService userMissionService;

    @Autowired
    private UserMissionRepository userMissionRepository;

    @GetMapping("/main")
    public Map ManagerMain() {
        List<User> users = userService.FindByAuthority(User.Teacher);
        List<Invigilate> invigilates = invigilateService.FindAll();
        List<UserInvigilate> userInvigilates = userInvigilateService.FindAll();
        List<Mission> missions = missionService.FindAll();
        List<UserMission> userMissions=userMissionService.FindAll();
        return Map.of("users", users, "invigilates", invigilates, "userinvigilates", userInvigilates, "missions", missions,"usermissions",userMissions);
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
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByUser(user);
        userInvigilateRepository.deleteAll(userInvigilates);
        List<UserMission> userMissions = userMissionService.FindUserMissionByUser(user);
        userMissionRepository.deleteAll(userMissions);
        userRepository.delete(user);
        String res = "已删除！";
        List<User> users = userService.FindByAuthority(User.Teacher);
        return Map.of("users", users, "res", res);
    }

    @PostMapping("/updata")
    public Map ManagerUpdata(@RequestBody User user) {
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
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByInvigilate(invigilate);
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
    public Map InvigilateDistribute(@PathVariable String no, @RequestBody Invigilate invigilate) {
        User user = userService.FindByNo(no);
        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByUser(user);
        String res = "未发现冲突！";
        for (int i = 0; i < userInvigilates.size(); i++) {
            if (userInvigilates.get(i).getInvigilate().getStartTime().equals(invigilate1.getStartTime())) {
                res = "发现冲突:一个老师同时有两个或两个以上监考！";
            }
        }
        return Map.of("res", res);
    }

    @PostMapping("/isdistributeinvigilate/{no}")
    public Map InvigilateIsDistribute(@PathVariable String no, @RequestBody Invigilate invigilate) {
        User user = userService.FindByNo(no);
        user.setInvigilate(user.getInvigilate() + 1);

        Invigilate invigilate1 = invigilateService.FindByNO(invigilate.getNo());
        invigilate1.setStatus(Invigilate.isDistribution);

        UserInvigilate userInvigilate = new UserInvigilate();

        userInvigilate.setUser(user);
        userInvigilate.setInvigilate(invigilate1);
        userInvigilateRepository.save(userInvigilate);
        String res = "已分配！";
        List<User> users = userService.FindByAuthority(User.Teacher);
        List<Invigilate> invigilates = invigilateService.FindAll();
        List<UserInvigilate> userInvigilates = userInvigilateService.FindAll();
        return Map.of("users", users, "invigilates", invigilates, "userinvigilates", userInvigilates, "res", res);
    }

    @PostMapping("/redistribute")
    public Map InvigilateRedistribute(@RequestBody Invigilate invigilate) {
        List<UserInvigilate> userInvigilates = userInvigilateService.FindUserInvigilateByInvigilate(invigilate);
        userInvigilateRepository.deleteAll(userInvigilates);
        invigilate.setStatus(Invigilate.notDistribution);
        invigilate.setSend(Invigilate.notSend);
        invigilate.setReceive(Invigilate.notReceive);
        invigilateRepository.save(invigilate);

        List<Invigilate> invigilates = invigilateService.FindAll();
        List<UserInvigilate> userInvigilates1 = userInvigilateRepository.findAll();
        String res = "已重置！";
        return Map.of("invigilates", invigilates, "userinvigilates", userInvigilates1, "res", res);
    }

    @PostMapping("/addmission")
    public Map MissionAdd(@RequestBody Mission mission) {
        String res = null;
        Mission mission1 = missionService.FindByNO(mission.getNo());
        if (mission1 != null) {
            res = "编号已存在！";
        } else {
            missionRepository.save(mission);
            res = "已添任务！";
        }
        List<Mission> missions = missionService.FindAll();
        return Map.of("missions", missions, "res", res);
    }

    @PostMapping("/deletedmission/{no}")
    public Map MissionDelete(@PathVariable String no) {
        Mission mission = missionService.FindByNO(no);
        List<UserMission> userMissions=userMissionService.FindUserMissionByMission(mission);
        userMissionRepository.deleteAll(userMissions);
        missionRepository.delete(mission);
        String res = "已删除！";
        List<Mission> missions = missionService.FindAll();
        List<UserMission> userMissions1 = userMissionService.FindAll();
        return Map.of("missions", missions, "usermissions",userMissions1,"res", res);
    }

    @PostMapping("/distributemission")
    public Map MissionDistribute(@RequestBody Mission mission) {
        String res = null;

        List<User> users = userService.FindByAuthority(User.Teacher);

        Mission mission1 = missionService.FindByNO(mission.getNo());

        if (!userMissionService.FindUserMissionByMission(mission1).isEmpty()) {
            res = "请勿重复发布任务！";
        } else {
            for (int i = 0; i < users.size(); i++) {
                UserMission userMission = new UserMission();
                userMission.setUser(users.get(i));
                userMission.setMission(mission1);
                userMissionRepository.save(userMission);
            }
            res = "任务已发布！";
        }
        List<UserMission> userMissions = userMissionService.FindAll();
        return Map.of("usermissions", userMissions, "res", res);
    }

    @PostMapping("/closemission/{no}")
    public Map MissionClose(@PathVariable String no) {

        String res = null;
        Mission mission = missionService.FindByNO(no);

        mission.setModify(Mission.notModify);
        missionRepository.save(mission);
        res = "此任务已关闭！";

        return Map.of("res", res);
    }

    @PostMapping("/updatamission")
    public Map MissionUpdata(@RequestBody Mission mission) {
        String res = null;
        Mission mission1 = missionService.FindByNO(mission.getNo());

        if (mission1.getModify().equals(Mission.isModify)) {
            mission1.setName(mission.getName());
            mission1.setContent(mission.getContent());
            mission1.setEndTime(mission.getEndTime());
            missionRepository.save(mission1);
            res = "已修改";
        } else {
            res = "该任务已关闭，不可修改！";
        }

        List<Mission> missions = missionService.FindAll();
        return Map.of("missions", missions, "res", res);
    }
}
