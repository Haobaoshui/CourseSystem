package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.DatabaseKnowledgepointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DatabaseKnowledgepointRepositoryImpl implements DatabaseKnowledgepointRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseKnowledgepointRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
