package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.UserGroup;
import com.haobaoshui.course.model.UserGroupViewData;
import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.Teacher;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.authority.UserGroupRepository;
import com.haobaoshui.course.service.authority.GroupService;
import com.haobaoshui.course.service.authority.UserGroupService;
import com.haobaoshui.course.service.organization.SchoolService;
import com.haobaoshui.course.service.user.StudentService;
import com.haobaoshui.course.service.user.TeacherService;
import com.haobaoshui.course.service.user.UserBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserGroupServiceImpl implements UserGroupService {


    private final UserGroupRepository userGroupRepository;

    @Autowired
    UserBasicInfoService userBasicInfoService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    SchoolService schoolService;

    @Autowired
    StudentService studentService;

    @Autowired
    GroupService groupService;


    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public String add(String t_group_id, String t_user_id) {
        return userGroupRepository.add(t_group_id, t_user_id);
    }


    @Override
    public Page<UserGroupViewData> getPageByGroupId(String t_group_id, int pageNo, int pageSize) {
        Page<UserGroup> userGroupPage = userGroupRepository.getPageByGroupId(t_group_id, pageNo, pageSize);

        List<UserGroupViewData> data = new ArrayList<>();
        for (UserGroup userGroup : userGroupPage.getResult()) {
            UserGroupViewData userGroupViewData = getUserGroupViewData(userGroup);
            if (userGroupViewData != null) data.add(userGroupViewData);
        }


        return new Page<>(userGroupPage.getStart(), userGroupPage.getTotalCount(), pageSize, data);
    }

    @Override
    public UserGroupViewData getUserGroupViewData(UserGroup ug) {
        UserGroupViewData userGroupViewData = new UserGroupViewData();


        Teacher t = teacherService.getTeacherByUserId(ug.getUserId());

        if (t != null) {
            TeacherViewData teacherViewData = teacherService.getTeacherViewById(t.getId());
            userGroupViewData.setTeacherViewData(teacherViewData);


        } else {
            // 说明是学生
            Student stu = studentService.getStudentByStudentId(ug.getUserId());

            StudentViewData studentViewData = studentService.getStudentViewByStudentId(stu.getId());
            userGroupViewData.setStudentViewData(studentViewData);

        }

        return userGroupViewData;

    }

    @Override
    public List<UserGroup> getUserGroupByGroupId(String t_group_id) {
        return userGroupRepository.getUserGroupByGroupId(t_group_id);
    }

    @Override
    public List<Group> getGroupByUserId(String t_user_id) {
        List<UserGroup> userGroupList = userGroupRepository.getUserGroupByUserId(t_user_id);

        List<Group> data = new ArrayList<>();
        for (UserGroup userGroup : userGroupList) {
            Group group = groupService.getByID(userGroup.getGroupId());
            if (group != null) data.add(group);
        }
        return data;
    }

    @Override
    public List<UserGroupViewData> getUserGroupViewDataByGroupId(String t_group_id) {
        List<UserGroup> userGroupList = userGroupRepository.getUserGroupByGroupId(t_group_id);

        List<UserGroupViewData> data = new ArrayList<>();
        for (UserGroup userGroup : userGroupList) {
            UserGroupViewData userGroupViewData = getUserGroupViewData(userGroup);
            if (userGroupViewData != null) data.add(userGroupViewData);
        }
        return data;
    }

    @Override
    public long getCount(String t_group_id) {
        return userGroupRepository.getCount(t_group_id);
    }


    @Override
    public void deleteById(String t_user_group_id) {
        userGroupRepository.deleteById(t_user_group_id);
    }

    @Override
    public void deleteByGroupId(String t_group_id) {
        userGroupRepository.deleteByGroupId(t_group_id);
    }

    @Override
    public void deleteByUserId(String t_user_id) {
        userGroupRepository.deleteByUserId(t_user_id);
    }

    @Override
    public void deleteByGroupIdAndUserId(String t_group_id, String t_user_id) {
        userGroupRepository.deleteByGroupIdAndUserId(t_group_id, t_user_id);
    }

}
