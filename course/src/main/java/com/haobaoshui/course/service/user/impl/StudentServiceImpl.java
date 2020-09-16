package com.haobaoshui.course.service.user.impl;

import com.haobaoshui.course.exception.StudentExistException;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.organization.*;
import com.haobaoshui.course.model.user.*;
import com.haobaoshui.course.repository.organization.NaturalClassStudentRepository;
import com.haobaoshui.course.repository.authority.UserGroupRepository;
import com.haobaoshui.course.repository.user.StudentRepository;
import com.haobaoshui.course.service.organization.DepartmentNaturalClassService;
import com.haobaoshui.course.service.organization.NaturalClassService;
import com.haobaoshui.course.service.organization.SchoolService;
import com.haobaoshui.course.service.user.StudentService;
import com.haobaoshui.course.service.user.UserBasicInfoService;
import com.haobaoshui.course.service.user.UserContactInfoService;
import com.haobaoshui.course.service.user.UserService;
import com.haobaoshui.course.utility.DateTimeSql;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final UserContactInfoService userContactInfoService;
    private final UserBasicInfoService userBasicInfoService;
    private final NaturalClassService naturalClassService;
    private final UserGroupRepository userGroupRepository;
    private final NaturalClassStudentRepository naturalClassStudentRepository;
    private final SchoolService schoolService;
    private final DepartmentNaturalClassService departmentNaturalClassService;


    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              UserService userService,
                              UserContactInfoService userContactInfoService,
                              UserBasicInfoService userBasicInfoService,
                              NaturalClassService naturalClassService,
                              UserGroupRepository userGroupRepository,
                              NaturalClassStudentRepository naturalClassStudentRepository,
                              DepartmentNaturalClassService departmentNaturalClassService,
                              SchoolService schoolService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
        this.userContactInfoService = userContactInfoService;
        this.userBasicInfoService = userBasicInfoService;
        this.naturalClassService = naturalClassService;
        this.userGroupRepository = userGroupRepository;
        this.naturalClassStudentRepository = naturalClassStudentRepository;
        this.departmentNaturalClassService = departmentNaturalClassService;
        this.schoolService = schoolService;
    }

    /**
     * 从Excel上传学生清单，然后添加到数据库中
     */
    @Override
    public void UploadFromExcel(String[] groupId, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String localfilename = file.getOriginalFilename();
            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                String prefix = localfilename.substring(localfilename.lastIndexOf(".") + 1);

                prefix = prefix.toLowerCase();

                if (prefix.equals("xls")) ProcessExcel97(localfilename, groupId);
                else if (prefix.equals("xlsx")) ProcessExcel(localfilename, groupId);

            } catch (IllegalStateException | IOException e) {
                throw e;

            } catch (StudentExistException e) {
                throw e;
            }
        }

    }

    /**
     * 从97-03格式的Excel(.xls)中导入学生
     *
     * @throws Exception
     */
    private void ProcessExcel97(String localfilename, String[] groupId) throws Exception {

        try {

            File file = new File(localfilename);
            FileInputStream fis = new FileInputStream(file);
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(fis);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(poifsFileSystem);

            int nCount = hssfWorkbook.getNumberOfSheets();

            for (int sheetindex = 0; sheetindex < nCount; sheetindex++) {
                HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetindex);

                int rowstart = sheet.getFirstRowNum();
                int rowEnd = sheet.getLastRowNum();

                int schoolIndex = -1;
                int departIndex = -1;
                int classnoIndex = -1;
                int nameIndex = -1;
                int numIndex = -1;

                for (int i = rowstart; i <= rowEnd; i++) {

                    HSSFRow row = sheet.getRow(i);
                    if (null == row) continue;

                    int cellStart = row.getFirstCellNum();
                    int cellEnd = row.getLastCellNum();

                    String school = null;
                    String department = null;
                    String name = null;
                    String student_num = null;
                    String naturalclass = null;

                    for (int k = cellStart; k <= cellEnd; k++) {
                        HSSFCell cell = row.getCell(k);
                        if (null == cell) continue;

                        String strIndexName = cell.getStringCellValue().trim();

                        if (i == rowstart) {

                            if (strIndexName.equals("学院") || strIndexName.equals("院")) schoolIndex = k;
                            else if (strIndexName.equals("系部") || strIndexName.equals("系") || strIndexName.equals("部"))
                                departIndex = k;
                            else if (strIndexName.equals("班号") || strIndexName.equals("班级") || strIndexName.equals("班"))
                                classnoIndex = k;
                            else if (strIndexName.equals("学号")) numIndex = k;
                            else if (strIndexName.equals("姓名")) nameIndex = k;

                        } else if (k == schoolIndex) school = strIndexName;
                        else if (k == departIndex) department = strIndexName;
                        else if (k == classnoIndex) naturalclass = strIndexName;
                        else if (k == numIndex) student_num = strIndexName;
                        else if (k == nameIndex) name = strIndexName;

                    }

                    if (naturalclass != null) AddStudent(school, department, naturalclass, name, student_num, groupId);

                }

            }

            fis.close();

        } catch (Exception e) {

            throw e;

        }

    }

    /**
     * 从07-10格式的Excel(.xlsx)中导入学生
     *
     * @throws Exception
     * @throws StudentExistException
     */
    private void ProcessExcel(String localfilename, String[] groupId) throws Exception {

        OPCPackage file;

        try {

            file = OPCPackage.open(localfilename);

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);

            for (Sheet sheet : xssfWorkbook) {

                int rowstart = sheet.getFirstRowNum();
                int rowEnd = sheet.getLastRowNum();

                int schoolIndex = -1;
                int departIndex = -1;
                int classnoIndex = -1;
                int nameIndex = -1;
                int numIndex = -1;

                for (int i = rowstart; i <= rowEnd; i++) {

                    Row row = sheet.getRow(i);
                    if (null == row) continue;
                    int cellStart = row.getFirstCellNum();
                    int cellEnd = row.getLastCellNum();

                    String school = null;
                    String department = null;
                    String name = null;
                    String student_num = null;
                    String naturalclass = null;

                    for (int k = cellStart; k <= cellEnd; k++) {
                        Cell cell = row.getCell(k);
                        if (null == cell) continue;

                        String strIndexName = cell.getStringCellValue().trim();

                        if (i == rowstart) {

                            if (strIndexName.equals("学院") || strIndexName.equals("院")) schoolIndex = k;
                            else if (strIndexName.equals("系部") || strIndexName.equals("系") || strIndexName.equals("部"))
                                departIndex = k;
                            else if (strIndexName.equals("班号") || strIndexName.equals("班级") || strIndexName.equals("班"))
                                classnoIndex = k;
                            else if (strIndexName.equals("学号")) numIndex = k;
                            else if (strIndexName.equals("姓名")) nameIndex = k;

                        } else if (k == schoolIndex) school = strIndexName;
                        else if (k == departIndex) department = strIndexName;
                        else if (k == classnoIndex) naturalclass = strIndexName;
                        else if (k == numIndex) student_num = strIndexName;
                        else if (k == nameIndex) name = strIndexName;
                    }

                    if (naturalclass != null) AddStudent(school, department, naturalclass, name, student_num, groupId);

                }

            }

            file.close();

        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * 注册一个新用户,如果用户名已经存在此抛出UserExistException的异常
     *
     * @param student
     */
    @Override
    public String insert(Student student) throws StudentExistException {
		/*
		Student u = this.getStudentByStudentNum(student.getStudentNum());

		if (u != null) {

			throw new StudentExistException("学生学号已经存在");
		} else {

			String t_student_id= studentRepository.add(student);
			if(t_student_id!=null){
				naturalClassStudentRepository.add(student.getNaturalClassId(),t_student_id);
				return t_student_id;
			}

		}
		*/
        return null;
    }


    @Override
    public Student getStudentByStudentNum(String student_num) {
        return studentRepository.getStudentByStudentNum(student_num);
    }

    @Override
    public Student getStudentByStudentId(String t_student_id) {
        return studentRepository.getStudentByID(t_student_id);
    }

    /**
     * 更新学生信息,更新内容较为简单，仅包括基本信息
     */
    @Override
    public void UpdateStudentInfo(String t_user_id, String user_basic_info_birthday, String user_basic_info_sex,
                                  String[] contacttypeId, String[] user_contact_value) {

        userBasicInfoService.updateByUserId(t_user_id, user_basic_info_birthday, user_basic_info_sex);

        userContactInfoService.update(t_user_id, contacttypeId, user_contact_value);

    }

    /**
     * 更新学生信息，更新内容全面
     */
    @Override
    public void UpdateStudentInfo(String t_user_id, String user_contact_info_name, String user_basic_info_birthday,
                                  String user_basic_info_sex, String[] contacttypeId, String[] user_contact_value) {

        userBasicInfoService.updateByUserId(t_user_id, user_contact_info_name, user_basic_info_birthday,
                user_basic_info_sex);

        userContactInfoService.update(t_user_id, contacttypeId, user_contact_value);
    }

    /**
     * 学生是否存在
     */
    @Override
    public String isExist(String t_natural_class_id, String student_name, String student_num) {

        return naturalClassStudentRepository.getStudentId(t_natural_class_id, student_num);
    }

    /**
     * 添加学生
     *
     * @param t_natural_class_id       自然班id
     * @param user_password            用户名密码
     * @param student_num              学号
     * @param userBasicInfoName        姓名
     * @param user_basic_info_birthday 生日
     * @param user_basic_info_sex      性别
     * @param contacttypeId            联系类型id
     * @param user_contact_value       联系类型值
     * @param groupId                  组
     * @return t_student_id
     */
    @Override
    public String AddStudent(String t_natural_class_id, String user_password, String student_num,
                             String userBasicInfoName, String user_basic_info_birthday, String user_basic_info_sex,
                             String[] contacttypeId, String[] user_contact_value, String[] groupId) throws Exception {

        if (t_natural_class_id == null) return null;

        // 学生存在则不添加
        String t_student_id = isExist(t_natural_class_id, userBasicInfoName, student_num);

        if (t_student_id != null) throw new StudentExistException("学生学号已经存在");

        // user基本信息
        User user = new User();
        user.setUserName(student_num);
        user.setUserPassword(user_password);

        Student stu = new Student();
        stu.setStudentNum(student_num);


        UserBasicInfo userbasicinfo = new UserBasicInfo();
        userbasicinfo.setUserBasicInfoName(userBasicInfoName);
        userbasicinfo.setUserBasicInfoBirthday(DateTimeSql.GetDate(user_basic_info_birthday));
        userbasicinfo.setUserBasicInfoSex(Integer.parseInt(user_basic_info_sex));

        // 添加用户
        user.EncoderPassword();
        String t_user_id = userService.add(user);

        // 添加student
        stu.setUserId(t_user_id);
        t_student_id = studentRepository.add(stu);

        // 添加用户基本信息
        userbasicinfo.setUserId(t_user_id);
        userBasicInfoService.add(userbasicinfo);

        userContactInfoService.update(t_user_id, contacttypeId, user_contact_value);

        if (groupId != null) for (String gid : groupId) userGroupRepository.add(gid, t_user_id);

        return t_student_id;

    }

    /**
     * 添加学生
     *
     * @throws Exception
     */

    @Override
    public String AddStudent(String schoolName, String departmentName, String naturalclassname, String student_name,
                             String student_num) throws Exception {

        // 必须有学号
        if (student_num == null || student_num.length() == 0) return null;

        // 必须有姓名
        if (student_name == null || student_name.length() == 0) return null;

        if (schoolName != null && schoolName.trim().length() == 0) schoolName = null;

        if (departmentName != null && departmentName.trim().length() == 0) departmentName = null;

        if (naturalclassname != null && naturalclassname.trim().length() == 0) naturalclassname = null;

        // 取得班级

        String t_natural_class_id = naturalClassService.getNaturalClassId(schoolName, departmentName, naturalclassname);

        if (t_natural_class_id == null) return null;

        // 学生存在则不添加
        String t_student_id = isExist(t_natural_class_id, student_name, student_num);

        if (t_student_id != null) return t_student_id;

        try {

            t_student_id = AddStudent(t_natural_class_id, student_num, student_num, student_name, "1980-01-01", "0",
                    null, null, null);
        } catch (StudentExistException e) {
            throw e;
        }
        return t_student_id;

    }

    /**
     * 添加学生
     *
     * @throws Exception
     */
    @Override
    public String AddStudent(String schoolName, String departmentName, String naturalclassname, String name,
                             String student_num, String[] groupId) throws Exception {

        String t_student_id = AddStudent(schoolName, departmentName, naturalclassname, name, student_num);

        if (t_student_id == null) return null;

        Student stu = studentRepository.getStudentByID(t_student_id);
        if (stu == null) return null;

        if (groupId != null) for (String gid : groupId) userGroupRepository.add(gid, stu.getUserId());

        return t_student_id;

    }

    @Override
    public void updateStudent(Student student, UserBasicInfo userbasicinfo, UserContactInfo[] usercontactinfos,
                              String[] groupId) {

        if (student == null) return;
        String t_student_id = student.getId();

        if (t_student_id == null) return;
        String student_num = student.getStudentNum();

        student = studentRepository.getStudentByID(t_student_id);
        String t_user_id = student.getUserId();
        try {

            // 修改学生
            studentRepository.UpdateStudentNumById(t_student_id, student_num);

            // 修改用户基本信息
            userbasicinfo.setUserId(t_user_id);
            userBasicInfoService.updateByUserId(t_user_id, userbasicinfo);

            // 修改联系方式

            userContactInfoService.update(t_user_id, usercontactinfos);

            // groupid
            userGroupRepository.update(t_user_id, groupId);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 根据学号得到学生视图
     */
    @Override
    public StudentViewData getStudentViewByStudentNum(String student_num) {
        Student student = getStudentByStudentNum(student_num);
        if (student != null) return getStudentViewByUserId(student.getUserId());
        return null;
    }

    /**
     * 根据学生id得到学生视图
     */
    @Override
    public StudentViewData getStudentViewByStudentId(String t_student_id) {
        Student student = studentRepository.getStudentByID(t_student_id);
        if (student != null) return getStudentViewByUserId(student.getUserId());
        return null;
    }


    /**
     * 根据用户id得到学生视图
     */
    @Override
    public StudentViewData getStudentViewByUserId(String t_user_id) {
        User u = userService.getUserById(t_user_id);
        UserBasicInfo userbasicinfo = userBasicInfoService.getByUserId(t_user_id);
        List<UserContactInfoViewData> usercontactinfoviewdata = userContactInfoService
                .getUserContactInfoViewDataByUserId(t_user_id);
        Student student = studentRepository.getStudentByUserId(t_user_id);
        if (student != null) {
            NaturalClassStudent naturalclassstudent = naturalClassStudentRepository
                    .getNaturalClassStudentByStudentId(student.getId());
            if (naturalclassstudent != null) {
                DepartmentNaturalClass naturalclassDepartment = departmentNaturalClassService
                        .getByNaturalClassId(naturalclassstudent.getNaturalClassId());
                if (naturalclassDepartment != null) {
                    Department department = departmentNaturalClassService
                            .getDepartmentByNaturalClassId(naturalclassDepartment.getNaturalClassId());

                    School school = schoolService
                            .getByDepartmentId(department.getId());
                    NaturalClass naturalclass = naturalClassService.getById(naturalclassDepartment.getNaturalClassId());
                    StudentViewData view = new StudentViewData();

                    view.setStudent(student);
                    view.setSchool(school);
                    view.setDepartment(department);
                    view.setNaturalclass(naturalclass);
                    UserInfoViewData userInfoViewData = userService.getUserInfoViewDataById(t_user_id);
                    view.setUserInfoViewData(userInfoViewData);

                    return view;
                }
            }
        }

        return null;
    }

    /**
     * 得到学院全体学生视图
     */
    @Override
    public Page<StudentViewData> getPageByNaturalClassId(String t_naturalclass_id, int pageNo, int pageSize) {

        long totalCount = studentRepository.getCountByNaturalClassId(t_naturalclass_id);
        if (totalCount < 1) return new Page<>();

        List<Student> studentList = studentRepository.getByNaturalClassId(t_naturalclass_id, pageNo, pageSize);


        List<StudentViewData> list = new ArrayList<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return new Page<>(startIndex, totalCount, pageSize, list);


    }

    /**
     * 得到教学班全体学生视图
     */
    @Override
    public Page<StudentViewData> getPageByCourseTeachingClassId(String t_course_teaching_class_id, int pageNo,
                                                                int pageSize) {
        long totalCount = studentRepository.getCountByCourseTeachingClassId(t_course_teaching_class_id);
        if (totalCount < 1) return new Page<>();


        List<Student> studentList = studentRepository.getByCourseTeachingClassId(t_course_teaching_class_id, pageNo, pageSize);


        List<StudentViewData> list = new ArrayList<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return new Page<>(startIndex, totalCount, pageSize, list);

    }

    @Override
    public List<StudentViewData> getStudentViewByCourseTeachingClassId(String t_course_teaching_class_id) {
        List<Student> studentList = studentRepository.getByCourseTeachingClassId(t_course_teaching_class_id);
        if (studentList == null || studentList.size() == 0) return null;
        List<StudentViewData> list = new ArrayList<>();


        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return list;
    }

    /**
     * 得到系部全体学生视图
     */
    @Override
    public List<StudentViewData> getStudentViewByDepartmentId(String t_department_id) {
        List<Student> studentList = studentRepository.getByDepartmentId(t_department_id);
        if (studentList == null || studentList.size() == 0) return null;


        List<StudentViewData> list = new ArrayList<>();


        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return list;
    }

    /**
     * 得到班级全体学生视图
     */
    @Override
    public List<StudentViewData> getStudentViewByNaturalClassId(String t_naturalclass_id) {
        List<Student> studentList = studentRepository.getByNaturalClassId(t_naturalclass_id);


        List<StudentViewData> list = new ArrayList<>();


        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return list;
    }

    /**
     * 删除学生，需要把相关所有同学生有关信息全部删除，然后才能够删除
     */
    @Override
    public void deleteById(String t_student_id) {

		/*
		Student stu = studentRepository.getStudentByID(t_student_id);
		if (stu == null)
			return;

		String t_user_id = stu.getUserId();

		// 删除作业成绩系统
		courseTeachingClassHomeworkStudentScoreService.deleteByStudentID(request, t_student_id);

		// 删除提交作业系统
		courseTeachingClassHomeworkSubmitService.deleteByStudentID(request, t_student_id);

		// 删除论坛系统
		deleteForumTopicAndReplyByUserId(request, t_user_id);

		// 删除学生的考勤情况
		attendanceStudentService.deleteByStudentId(t_student_id);

		// 删除教学班-学生
		List<CourseTeachingClassStudent> listCourseTeachingClassStudent = courseTeachingClassStudentDao
				.getTeachingClassStudentByStudentId(t_student_id);
		for (CourseTeachingClassStudent c : listCourseTeachingClassStudent) {
			deleteStudentFromCourseTeachingClass(request,c.getId(),t_student_id);
		}
		//从教学班中删除学生
		teachingClassStudentService.deleteByStudentId(request, t_student_id);

		//删除自然班-学生
		naturalClassStudentDao.deleteByStudentId(t_student_id);

		// 删除学生
		studentDao.deleteById(t_student_id);

		// 删除邮件
		deleteUserInMail(request, t_user_id);

		//权限
		userGroupService.deleteByUserId( t_user_id);

		// 删除用户基本信息
		deleteUserBasic(t_user_id);
		*/
    }

    /**
     * 得到所有未分组的学生
     */
    @Override
    public List<StudentViewData> getNotGroupedStudent(String t_course_teaching_class_id) {
        List<Student> studentList = studentRepository.getNotGroupedByCourseTeachingClassId(t_course_teaching_class_id);
        if (studentList == null || studentList.size() == 0) return null;

        List<StudentViewData> list = new ArrayList<>();


        for (Student student : studentList) {
            StudentViewData studentViewData = getStudentViewByStudentId(student.getId());
            if (studentViewData != null) list.add(studentViewData);
        }

        return list;
    }


    @Override
    public long getCountByNaturalClassId(String t_natural_class_id) {
        return studentRepository.getCountByNaturalClassId(t_natural_class_id);
    }

    @Override
    public long getCountByCourseTeachingClassId(String t_course_teaching_class_id) {
        return studentRepository.getCountByCourseTeachingClassId(t_course_teaching_class_id);
    }
}
