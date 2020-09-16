package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.ExaminationConstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExaminationConstitutionRepositoryImpl implements ExaminationConstitutionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExaminationConstitutionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
