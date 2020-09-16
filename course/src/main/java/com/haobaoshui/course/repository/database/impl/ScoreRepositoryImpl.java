package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ScoreRepositoryImpl implements ScoreRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ScoreRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
