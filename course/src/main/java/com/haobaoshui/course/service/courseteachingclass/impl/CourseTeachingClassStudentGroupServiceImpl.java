package com.haobaoshui.course.service.courseteachingclass.impl;

import com.haobaoshui.course.model.UserGroup;
import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroup;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroupStatisticsViewData;
import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassStudentGroupViewData;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassStudentGroupRepository;
import com.haobaoshui.course.service.authority.GroupService;
import com.haobaoshui.course.service.authority.UserGroupService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassStudentGroupService;
import com.haobaoshui.course.service.user.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseTeachingClassStudentGroupServiceImpl implements CourseTeachingClassStudentGroupService {

    private final CourseTeachingClassStudentGroupRepository courseTeachingClassStudentGroupRepository;
    @Autowired
    GroupService groupService;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    StudentService studentService;

    @Autowired
    public CourseTeachingClassStudentGroupServiceImpl(CourseTeachingClassStudentGroupRepository courseTeachingClassStudentGroupRepository
    ) {
        this.courseTeachingClassStudentGroupRepository = courseTeachingClassStudentGroupRepository;

    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 添加
     */
    @Override
    public void add(String t_course_teaching_class_id, String t_group_id) {

        if (t_course_teaching_class_id == null || t_group_id == null) return;

        int show_index = courseTeachingClassStudentGroupRepository
                .getShowIndexMaxByCourseTeachingClassId(t_course_teaching_class_id);
        show_index += 10;
        if (courseTeachingClassStudentGroupRepository.IsGroupExist(t_course_teaching_class_id, t_group_id))
            courseTeachingClassStudentGroupRepository.update(t_course_teaching_class_id, t_group_id, show_index);
        else
            courseTeachingClassStudentGroupRepository.add(t_course_teaching_class_id, t_group_id, show_index);

    }

    /**
     * 添加
     */
    @Override
    public void addGroup(String t_course_teaching_class_id, String group_name, String group_note) {
        if (t_course_teaching_class_id == null || group_name == null) return;

        String t_group_id = groupService.add(group_name, group_note);
        if (t_group_id == null) return;

        add(t_course_teaching_class_id, t_group_id);

    }

    @Override
    public void deleteById(String id) {
        courseTeachingClassStudentGroupRepository.deleteById(id);
    }

    @Override
    public void deleteByCourseTeachingClassId(String t_course_teaching_class_id) {
        courseTeachingClassStudentGroupRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);
    }

    /**
     * 根据组删除
     */
    @Override
    public void deleteByGroupId(String t_group_id) {
        courseTeachingClassStudentGroupRepository.deleteByGroupId(t_group_id);
    }

    /**
     * 删除
     */
    @Override
    public void deleteGroup(String t_course_teaching_class_id, String t_course_teaching_class_student_group_id) {
        if (t_course_teaching_class_id == null || t_course_teaching_class_student_group_id == null) return;

        CourseTeachingClassStudentGroup data = courseTeachingClassStudentGroupRepository
                .getById(t_course_teaching_class_student_group_id);

        if (data == null) return;

        if (!data.getCourseTeachingClassId().equals(t_course_teaching_class_id)) return;

        groupService.deleteById(data.getGroupId());

    }

    @Override
    public void ShowIndexMoveUp(String t_course_teaching_class_id, String t_group_id) {

        // 1.得到该学生
        CourseTeachingClassStudentGroup stu = courseTeachingClassStudentGroupRepository
                .getByTeachingClassIdAndGroupId(t_course_teaching_class_id, t_group_id);
        if (stu == null) return;

        // 2.得到前面一个学生
        int show_index_max = courseTeachingClassStudentGroupRepository
                .getShowIndexMaxLessthanByCourseTeachingClassId(t_course_teaching_class_id, t_group_id);
        CourseTeachingClassStudentGroup pre = courseTeachingClassStudentGroupRepository
                .getByTeachingClassIdAndShowIndex(t_course_teaching_class_id, show_index_max);
        if (pre == null) return;

        int temp = stu.getShowIndex();
        courseTeachingClassStudentGroupRepository.update(stu.getId(), show_index_max);
        courseTeachingClassStudentGroupRepository.update(pre.getId(), temp);

    }

    @Override
    public void ShowIndexMoveDown(String t_course_teaching_class_id, String t_group_id) {
        // 1.得到该学生
        CourseTeachingClassStudentGroup stu = courseTeachingClassStudentGroupRepository
                .getByTeachingClassIdAndGroupId(t_course_teaching_class_id, t_group_id);
        if (stu == null) return;

        // 2.得到后面一个学生
        int show_index_min = courseTeachingClassStudentGroupRepository
                .getShowIndexMinMorethanByCourseTeachingClassId(t_course_teaching_class_id, t_group_id);
        CourseTeachingClassStudentGroup next = courseTeachingClassStudentGroupRepository
                .getByTeachingClassIdAndShowIndex(t_course_teaching_class_id, show_index_min);
        if (next == null) return;

        int temp = stu.getShowIndex();
        courseTeachingClassStudentGroupRepository.update(stu.getId(), show_index_min);
        courseTeachingClassStudentGroupRepository.update(next.getId(), temp);

    }

    private CourseTeachingClassStudentGroupViewData getViewData(CourseTeachingClassStudentGroup g) {
        if (g == null) return null;

        CourseTeachingClassStudentGroupViewData data = new CourseTeachingClassStudentGroupViewData();
        data.setCourseTeachingClassStudentGroup(g);

        String t_group_id = g.getGroupId();
        Group group = groupService.getByID(t_group_id);
        data.setGroup(group);

        int nUserCount = (int) userGroupService.getCount(t_group_id);
        List<UserGroup> listUser = userGroupService.getUserGroupByGroupId(t_group_id);
        if (listUser != null && nUserCount > 0 && listUser.size() == nUserCount) {
            StudentViewData[] studentViewDatas = new StudentViewData[nUserCount];

            for (int i = 0; i < nUserCount; i++)
                studentViewDatas[i] = studentService.getStudentViewByUserId(listUser.get(i).getUserId());

            data.setStudentViewDatas(studentViewDatas);
        }
        return data;
    }

    @Override
    public CourseTeachingClassStudentGroupViewData getViewDataByStudentId(String t_course_teaching_class_id, String t_student_id) {
        Student student = studentService.getStudentByStudentId(t_student_id);
        if (student == null) return null;


        CourseTeachingClassStudentGroup courseTeachingClassStudentGroup = courseTeachingClassStudentGroupRepository.getByTeachingClassIdAndUserId(t_course_teaching_class_id, student.getUserId());

        if (courseTeachingClassStudentGroup == null) return null;
        return getViewData(courseTeachingClassStudentGroup);


    }

    @Override
    public List<CourseTeachingClassStudentGroupViewData> getViewData(String t_course_teaching_class_id) {
        if (t_course_teaching_class_id == null) return null;

        List<CourseTeachingClassStudentGroup> list = courseTeachingClassStudentGroupRepository
                .getByCourseTeachingClassId(t_course_teaching_class_id);
        if (list == null) return null;
        if (list.size() == 0) return null;

        List<CourseTeachingClassStudentGroupViewData> result = new ArrayList<>();
        for (CourseTeachingClassStudentGroup g : list) {
            CourseTeachingClassStudentGroupViewData data = getViewData(g);
            if (data != null) result.add(data);
        }

        return result;
    }

    /**
     * 得到所有未分组的学生
     */
    @Override
    public List<StudentViewData> getNotGroupedStudent(String t_course_teaching_class_id) {
        return studentService.getNotGroupedStudent(t_course_teaching_class_id);
    }

    /**
     * 得到特定分组的学生
     */
    @Override
    public List<StudentViewData> getGroupedStudent(String t_course_teaching_class_id, String t_group_id) {
        CourseTeachingClassStudentGroup g = courseTeachingClassStudentGroupRepository
                .getByTeachingClassIdAndGroupId(t_course_teaching_class_id, t_group_id);

        if (g == null) return null;

        List<UserGroup> list = userGroupService.getUserGroupByGroupId(g.getGroupId());

        List<StudentViewData> result = new ArrayList<>();
        for (UserGroup u : list) result.add(studentService.getStudentViewByUserId(u.getUserId()));
        return result;

    }

    @Override
    public void AddStudent2Group(String t_course_teaching_class_id, String t_group_id, String[] userids) {
        if (userids == null || userids.length == 0) return;
        if (t_course_teaching_class_id == null) return;
        if (t_group_id == null) return;
        for (String s : userids) if (s != null && s.length() > 0) userGroupService.add(t_group_id, s);

    }

    @Override
    public void DeleteStudentFromGroup(String t_course_teaching_class_id, String t_group_id, String t_user_id) {
        if (t_user_id == null) return;
        if (t_course_teaching_class_id == null) return;
        if (t_group_id == null) return;

        userGroupService.deleteByGroupIdAndUserId(t_group_id, t_user_id);

    }

    /**
     * 获得分组数量
     */
    @Override
    public int getGroupCountByCourseTeachingClassId(String t_course_teaching_class_id) {
        return courseTeachingClassStudentGroupRepository.getGroupCountByCourseTeachingClassId(t_course_teaching_class_id);
    }

    /**
     * 是否分组
     */
    @Override
    public boolean isGrouped(String t_course_teaching_class_id) {
        return courseTeachingClassStudentGroupRepository.getGroupCountByCourseTeachingClassId(t_course_teaching_class_id) > 0;
    }

    @Override
    public CourseTeachingClassStudentGroupStatisticsViewData getGroupInfo(String t_course_teaching_class_id) {
        if (t_course_teaching_class_id == null) return null;

        CourseTeachingClassStudentGroupStatisticsViewData data = new CourseTeachingClassStudentGroupStatisticsViewData();
        data.setnGroupCount(courseTeachingClassStudentGroupRepository.getGroupCountByCourseTeachingClassId(t_course_teaching_class_id));
        return data;


    }
}
