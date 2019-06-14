package com.classdesign.classdesign.service;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.repository.InvigilateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class InvigilateService {
    @Autowired
    private InvigilateRepository invigilateRepository;

    public Invigilate FindByNO(String no){
        return invigilateRepository.FindByNO(no);
    }

    /*public List<Invigilate> FindAll(){
        return invigilateRepository.FindAll();
    }*/
}
