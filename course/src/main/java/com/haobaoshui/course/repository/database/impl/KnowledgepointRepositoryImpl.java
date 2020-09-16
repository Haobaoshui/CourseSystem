package com.haobaoshui.course.repository.database.impl;

import com.haobaoshui.course.repository.database.KnowledgepointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KnowledgepointRepositoryImpl implements KnowledgepointRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public KnowledgepointRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
