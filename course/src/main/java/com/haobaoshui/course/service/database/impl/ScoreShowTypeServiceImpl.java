package com.haobaoshui.course.service.database.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.database.ScoreShowType;
import com.haobaoshui.course.repository.database.ScoreShowTypeRepository;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassHomeworkScoreInfoService;
import com.haobaoshui.course.service.database.ScoreShowTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ScoreShowTypeServiceImpl implements ScoreShowTypeService {


    private final ScoreShowTypeRepository scoreShowTypeRepository;
    private final CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService;

    @Autowired
    public ScoreShowTypeServiceImpl(ScoreShowTypeRepository scoreShowTypeRepository,
                                    CourseTeachingClassHomeworkScoreInfoService courseTeachingClassHomeworkScoreInfoService) {
        this.scoreShowTypeRepository = scoreShowTypeRepository;
        this.courseTeachingClassHomeworkScoreInfoService = courseTeachingClassHomeworkScoreInfoService;
    }

    @Override
    public String add(ScoreShowType scoreShowType) {
        return scoreShowTypeRepository.add(scoreShowType);
    }

    @Override
    public String add(String name, String note) {
        return scoreShowTypeRepository.add(name, note);
    }

    @Override
    public int deleteById(String id) {
        return scoreShowTypeRepository.deleteById(id);
    }

    @Override
    public int delete(ScoreShowType scoreShowType) {
        if (scoreShowType == null) return 0;
        return deleteById(scoreShowType.getId());
    }

    @Override
    public int deleteAll() {
        return scoreShowTypeRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return scoreShowTypeRepository.update(id, name, note);
    }

    @Override
    public int update(ScoreShowType scoreShowType) {
        return scoreShowTypeRepository.update(scoreShowType);
    }

    @Override
    public int getCount() {
        return scoreShowTypeRepository.getCount();
    }

    @Override
    public List<ScoreShowType> getAllByLikeName(String name) {
        return scoreShowTypeRepository.getAllByLikeName(name);
    }

    @Override
    public ScoreShowType getByName(String name) {
        return scoreShowTypeRepository.getByName(name);
    }

    @Override
    public ScoreShowType getByID(String id) {
        return scoreShowTypeRepository.getByID(id);
    }

    @Override
    public List<ScoreShowType> getAll() {
        return scoreShowTypeRepository.getAll();
    }

    @Override
    public Page<ScoreShowType> getPage(int pageNo, int pageSize) {
        return scoreShowTypeRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<ScoreShowType> getAllPage() {
        return scoreShowTypeRepository.getAllPage();
    }

    @Override
    public Page<ScoreShowType> getPageByLikeName(String name, int pageNo, int pageSize) {
        return scoreShowTypeRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
