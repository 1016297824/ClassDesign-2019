package com.classdesign.classdesign.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface CustomizedRepository<T, ID> extends JpaRepository<T, ID> {
    T refresh(T t);
}
