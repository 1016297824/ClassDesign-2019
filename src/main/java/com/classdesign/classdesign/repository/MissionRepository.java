package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.entity.Mission;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.entity.UserInvigilate;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends CustomizedRepository<Mission, Integer> {

    @Query("select m from Mission m where m.no=:no")
    Mission FindByNO(@Param("no") String no);

    @Query("from Mission m")
    List<Mission> FindAll();
}
