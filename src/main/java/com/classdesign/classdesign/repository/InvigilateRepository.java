package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvigilateRepository extends CustomizedRepository<Invigilate, Integer> {

    @Query("select i from Invigilate i where i.no=:no")
    Invigilate FindByNO(@Param("no") String no);

    /*@Query("from Invigilat")
    List<Invigilate> FindAll();*/
}
