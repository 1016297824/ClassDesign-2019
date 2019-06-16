package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.repository.InvigilateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;


public class TimerService {

    @Autowired
    private InvigilateService invigilateService;
    @Autowired
    private InvigilateRepository invigilateRepository;

    /*@Scheduled(cron = "0 0 0 0 0 ?")*/
    public void InvigilateSend() {
        List<Invigilate> invigilates = invigilateService.FindAll();
        int now = LocalDateTime.now().getDayOfMonth();
        int tomorrow = now + 1;
        int nowMonth = LocalDateTime.now().getMonthValue();

        for (int i = 0; i < invigilates.size(); i++) {
            LocalDateTime invigilateTime = invigilates.get(i).getStartTime();
            int invigilateMonth = invigilateTime.getMonthValue();
            int invigilateDay = invigilateTime.getDayOfMonth();
            if (invigilateMonth == nowMonth) {
                if (invigilateDay == tomorrow) {
                    invigilates.get(i).setSend(Invigilate.isSend);
                }
            }
        }
        invigilateRepository.saveAll(invigilates);
    }
}
