package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.ScoreConstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ScoreConstitutionRepositoryImpl implements ScoreConstitutionRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScoreConstitutionRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
