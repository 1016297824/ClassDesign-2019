package com.classdesign.classdesign.repository;

import com.classdesign.classdesign.entity.Invigilate;
import com.classdesign.classdesign.entity.User;
import com.classdesign.classdesign.entity.UserInvigilate;
import com.classdesign.classdesign.repository.impl.CustomizedRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserInvigilateRepository extends CustomizedRepository<UserInvigilate, Integer> {

    @Query("from UserInvigilate ui where ui.user=:user")
    List<UserInvigilate> FindUserInvigilateByUser(@Param("user") User user);

    @Query("from UserInvigilate ui where ui.invigilate=:invigilate")
    List<UserInvigilate> FindUserInvigilateByInvigilate(@Param("invigilate") Invigilate invigilate);

    @Query("from UserInvigilate ui order by ui.invigilate")
    List<UserInvigilate> FindAll();
}
