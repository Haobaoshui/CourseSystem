package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.CourseDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class CourseDatabaseRepositoryImpl implements CourseDatabaseRepository {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDatabaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
