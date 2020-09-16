package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.exception.UserExistException;
import com.haobaoshui.course.model.authority.PermissionViewData;
import com.haobaoshui.course.model.user.*;
import com.haobaoshui.course.repository.user.UserRepository;
import com.haobaoshui.course.service.authority.PermissionService;
import com.haobaoshui.course.service.courseteachingclass.CourseTeachingClassService;
import com.haobaoshui.course.service.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    UserBasicInfoService userBasicInfoService;

    @Autowired
    UserContactInfoService userContactInfoService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserConfigService userConfigService;

    @Autowired
    CourseTeachingClassService courseTeachingClassService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param user
     */
    @Override
    public String add(User user) throws UserExistException {
        User u = getUserByUserName(user.getUserName());
        if (u != null) throw new UserExistException("用户名已经存在");
        else return userRepository.add(user);
    }

    /**
     * 更新密码，注意传过的值是加密后的值
     */
    @Override
    public void updateUserPassword(String id, String strEncodedPwd) {
        userRepository.updateUserPassword(id, strEncodedPwd);
    }

    /**
     * 根据用户名/密码查询 User对象
     *
     * @return User
     */
    @Override
    public User getUserById(String t_user_id) {
        return userRepository.getUserByID(t_user_id);
    }

    /**
     * 根据用户名/密码查询 User对象
     *
     * @param userName 用户名
     * @return User
     */
    @Override
    public User getUserByUserName(String userName) {

        if (null == userName || userName.isEmpty()) return null;

        return userRepository.getUserByName(userName);
    }

    @Override
    public UserInfoViewData getUserInfoViewDataById(String t_user_id) {
        User user = getUserById(t_user_id);


        if (user == null) return null;
        UserInfoViewData userInfoViewData = new UserInfoViewData();
        userInfoViewData.setUser(user);
        userInfoViewData.setUserbasicinfo(userBasicInfoService.getByUserId(t_user_id));
        userInfoViewData.setUserContactInfoViewDataList(userContactInfoService.getUserContactInfoViewDataByUserId(t_user_id));
        return userInfoViewData;
    }

    @Override
    public void deleteByUserId(String t_user_id) {
        userRepository.deleteById(t_user_id);
    }


    @Override
    public UserSessionInfo getUserSessionInfoByUserId(String t_user_id) {


        if (t_user_id == null) return null;


        UserSessionInfo userSessionInfo = new UserSessionInfo();


        TeacherViewData teacherViewData = teacherService.getTeacherViewByUserId(t_user_id);
        if (teacherViewData != null) userSessionInfo.setTeacherViewData(teacherViewData);
        else {
            StudentViewData studentViewData = studentService.getStudentViewByStudentId(t_user_id);
            userSessionInfo.setStudentViewData(studentViewData);
        }


        //设置页面信息
        userSessionInfo.setPageSize(userConfigService.getPageSize(t_user_id));


        List<PermissionViewData> list = permissionService.getPermissionViewDataByUserId(t_user_id);
        userSessionInfo.setPermissionviewdata(list);


        return userSessionInfo;

    }

}
