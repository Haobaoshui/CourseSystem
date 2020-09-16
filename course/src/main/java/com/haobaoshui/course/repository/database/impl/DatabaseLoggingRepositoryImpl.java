package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.DatabaseLoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DatabaseLoggingRepositoryImpl implements DatabaseLoggingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseLoggingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
