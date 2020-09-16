package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseRepositoryImpl implements DatabaseRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
