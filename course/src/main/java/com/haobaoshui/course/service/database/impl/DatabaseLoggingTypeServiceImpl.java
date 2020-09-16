package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.DatabaseLoggingType;
import com.haobaoshui.course.repository.database.DatabaseLoggingTypeRepository;
import com.haobaoshui.course.service.database.DatabaseLoggingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DatabaseLoggingTypeServiceImpl implements DatabaseLoggingTypeService {


    private final DatabaseLoggingTypeRepository databaseLoggingTypeRepository;

    @Autowired
    public DatabaseLoggingTypeServiceImpl(DatabaseLoggingTypeRepository databaseLoggingTypeRepository) {
        this.databaseLoggingTypeRepository = databaseLoggingTypeRepository;
    }

    @Override
    public String add(DatabaseLoggingType databaseLoggingType) {
        return databaseLoggingTypeRepository.add(databaseLoggingType);
    }

    @Override
    public String add(String name, String note) {
        return databaseLoggingTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return databaseLoggingTypeRepository.deleteById(id);
    }

    @Override
    public int delete(DatabaseLoggingType databaseLoggingType) {
        if (databaseLoggingType == null) return 0;
        return deleteById(databaseLoggingType.getId());
    }

    @Override
    public int deleteAll() {
        return databaseLoggingTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return databaseLoggingTypeRepository.update(id, name, note);
    }

    @Override
    public int update(DatabaseLoggingType databaseLoggingType) {
        return databaseLoggingTypeRepository.update(databaseLoggingType);
    }

    @Override
    public int getCount() {
        return databaseLoggingTypeRepository.getCount();
    }

    @Override
    public List<DatabaseLoggingType> getAllByLikeName(String name) {
        return databaseLoggingTypeRepository.getAllByLikeName(name);
    }

    @Override
    public DatabaseLoggingType getByName(String name) {
        return databaseLoggingTypeRepository.getByName(name);
    }

    @Override
    public DatabaseLoggingType getByID(String id) {
        return databaseLoggingTypeRepository.getByID(id);
    }

    @Override
    public List<DatabaseLoggingType> getAll() {
        return databaseLoggingTypeRepository.getAll();
    }

    @Override
    public Page<DatabaseLoggingType> getPage(int pageNo, int pageSize) {
        return databaseLoggingTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<DatabaseLoggingType> getAllPage() {
        return databaseLoggingTypeRepository.getAllPage();
    }

    @Override
    public Page<DatabaseLoggingType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return databaseLoggingTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }
}
