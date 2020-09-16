package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.ExaminationDatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExaminationDatabaseRepositoryImpl implements ExaminationDatabaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExaminationDatabaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
