package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.repository.InvigilateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TimerService {

    @Autowired
    private InvigilateService invigilateService;
    @Autowired
    private InvigilateRepository invigilateRepository;

    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "* */5 * * * ?")
    public void InvigilateSend() {
        List<Invigilate> invigilates = invigilateService.FindAll();
        int now = LocalDateTime.now().getDayOfMonth();
        int tomorrow = now + 1;
        int nowMonth = LocalDateTime.now().getMonthValue();
        int nowYear = LocalDateTime.now().getYear();
        for (int i = 0; i < invigilates.size(); i++) {
            LocalDateTime invigilateTime = invigilates.get(i).getStartTime();
            int invigilateMonth = invigilateTime.getMonthValue();
            int invigilateDay = invigilateTime.getDayOfMonth();
            int invigilateYear = invigilateTime.getYear();
            if (invigilateYear == nowYear) {
                if (invigilateMonth == nowMonth) {
                    if (invigilateDay == tomorrow) {
                        invigilates.get(i).setSend(Invigilate.isSend);
                    }
                }
            }
            if (invigilateTime.isBefore(LocalDateTime.now())) {
                invigilates.get(i).setStatus(Invigilate.done);
            }
        }
        invigilateRepository.saveAll(invigilates);
    }
}

