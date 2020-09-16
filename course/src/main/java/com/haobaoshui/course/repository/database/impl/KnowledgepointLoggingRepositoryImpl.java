package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.KnowledgepointLoggingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KnowledgepointLoggingRepositoryImpl implements KnowledgepointLoggingRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public KnowledgepointLoggingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
