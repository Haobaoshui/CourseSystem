package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ScoreMarkingType;
import com.haobaoshui.course.repository.database.ScoreMarkingTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkScoreInfoService;
import com.haobaoshui.course.service.database.ScoreMarkingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScoreMarkingTypeServiceImpl implements ScoreMarkingTypeService {
    private final ScoreMarkingTypeRepository scoreMarkingTypeRepository;
    private final CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService;

    @Autowired
    public ScoreMarkingTypeServiceImpl(ScoreMarkingTypeRepository scoreMarkingTypeRepository,
                                       CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService) {
        this.scoreMarkingTypeRepository = scoreMarkingTypeRepository;
        this.courseTeachingClassHomeworkScoreInfoService = courseTeachingClassHomeworkScoreInfoService;
    }

    @Override
    public String add(ScoreMarkingType scoreMarkingType) {
        return scoreMarkingTypeRepository.add(scoreMarkingType);
    }

    @Override
    public String add(String name, String note) {
        return scoreMarkingTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return scoreMarkingTypeRepository.deleteById(id);
    }

    @Override
    public int delete(ScoreMarkingType scoreMarkingType) {
        if (scoreMarkingType == null) return 0;
        return deleteById(scoreMarkingType.getId());
    }

    @Override
    public int deleteAll() {
        return scoreMarkingTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return scoreMarkingTypeRepository.update(id, name, note);
    }

    @Override
    public int update(ScoreMarkingType scoreMarkingType) {
        return scoreMarkingTypeRepository.update(scoreMarkingType);
    }

    @Override
    public int getCount() {
        return scoreMarkingTypeRepository.getCount();
    }

    @Override
    public List<ScoreMarkingType> getAllByLikeName(String name) {
        return scoreMarkingTypeRepository.getAllByLikeName(name);
    }

    @Override
    public ScoreMarkingType getByName(String name) {
        return scoreMarkingTypeRepository.getByName(name);
    }

    @Override
    public ScoreMarkingType getByID(String id) {
        return scoreMarkingTypeRepository.getByID(id);
    }

    @Override
    public List<ScoreMarkingType> getAll() {
        return scoreMarkingTypeRepository.getAll();
    }

    @Override
    public Page<ScoreMarkingType> getPage(int pageNo, int pageSize) {
        return scoreMarkingTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ScoreMarkingType> getAllPage() {
        return scoreMarkingTypeRepository.getAllPage();
    }

    @Override
    public Page<ScoreMarkingType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return scoreMarkingTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
