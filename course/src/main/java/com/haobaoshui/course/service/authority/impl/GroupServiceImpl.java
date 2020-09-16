package com.haobaoshui.course.service.authority.impl;

import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.authority.Group;
import com.haobaoshui.course.repository.authority.GroupRepository;
import com.haobaoshui.course.service.authority.GroupRoleService;
import com.haobaoshui.course.service.authority.GroupService;
import com.haobaoshui.course.service.authority.UserGroupService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassStudentGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupServiceImpl implements GroupService {


    private final GroupRepository groupRepository;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    GroupRoleService groupRoleService;
    @Autowired
    CourseTeachingClassStudentGroupService courseTeachingClassStudentGroupService;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;


    }

    @Autowired
    public void setCourseTeachingClassStudentGroupService(CourseTeachingClassStudentGroupService courseTeachingClassStudentGroupService) {
        this.courseTeachingClassStudentGroupService = courseTeachingClassStudentGroupService;
    }


    @Override
    public String add(Group group) {
        return groupRepository.add(group);
    }

    @Override
    public String add(String name, String note) {
        return groupRepository.add(name, note);
    }

    /**
     * 删除组，在t_group删除之前，必须删除相关外键
     */
    @Override
    public int deleteById(String t_group_id) {

        //用户-组
        userGroupService.deleteByGroupId(t_group_id);

        //组-角色
        groupRoleService.deleteByGroupId(t_group_id);

        //学生-组
        courseTeachingClassStudentGroupService.deleteByGroupId(t_group_id);

        //自己
        groupRepository.deleteById(t_group_id);

        return 1;

    }

    @Override
    public int delete(Group group) {
        if (group == null) return 0;
        return deleteById(group.getId());
    }

    @Override
    public int deleteAll() {
        return groupRepository.deleteAll();
    }

    @Override
    public int update(String id, String name, String note) {
        return groupRepository.update(id, name, note);
    }

    @Override
    public int update(Group group) {
        return groupRepository.update(group);
    }

    @Override
    public int getCount() {
        return groupRepository.getCount();
    }

    @Override
    public List<Group> getAllByLikeName(String name) {
        return groupRepository.getAllByLikeName(name);
    }

    @Override
    public Group getByName(String name) {
        return groupRepository.getByName(name);
    }

    @Override
    public Group getByID(String id) {
        return groupRepository.getByID(id);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.getAll();
    }

    @Override
    public Page<Group> getPage(int pageNo, int pageSize) {
        return groupRepository.getPage(pageNo, pageSize);
    }

    @Override
    public Page<Group> getAllPage() {
        return groupRepository.getAllPage();
    }

    @Override
    public Page<Group> getPageByLikeName(String name, int pageNo, int pageSize) {
        return groupRepository.getPageByLikeName(name, pageNo, pageSize);
    }

}
