package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.ExaminationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExaminationRepositoryImpl implements ExaminationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExaminationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
