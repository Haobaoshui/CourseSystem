package com.haobaoshui.course.service.courseteachingclass.impl;


import com.haobaoshui.course.exception.StudentExistException;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.courseteachingclass.*;
import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.model.user.StudentViewData;
import com.haobaoshui.course.model.user.Teacher;
import com.haobaoshui.course.model.user.TeacherViewData;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassRepository;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassTeacherRepository;
import com.haobaoshui.course.service.attendance.AttendanceService;
import com.haobaoshui.course.service.course.CourseService;
import com.haobaoshui.course.service.courseteachingclass.*;
import com.haobaoshui.course.service.user.StudentService;
import com.haobaoshui.course.service.user.TeacherService;
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

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CourseTeachingClassServiceImpl implements CourseTeachingClassService {


    private final CourseTeachingClassRepository courseTeachingClassRepository;
    private final CourseTeachingClassTeacherRepository courseTeachingClassTeacherRepository;
    @Autowired
    CourseTeachingClassStudentService courseTeachingClassStudentService;
    @Autowired
    CourseTeachingClassForumTopicService courseTeachingClassForumTopicService;

    @Autowired
    CourseTeachingClassHomeworkTypeService courseTeachingClassHomeworkTypeService;

    @Autowired
    CourseTeachingClassTeacherService courseTeachingClassTeacherService;

    @Autowired
    CourseTeachingClassHomeworkService courseTeachingClassHomeworkService;
    @Autowired
    StudentService studentService;

    @Autowired
    CourseTeachingClassStudentGroupService courseTeachingClassStudentGroupService;
    @Autowired
    CourseTeachingClassReferenceService courseTeachingClassReferenceService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseTeachingTermService courseTeachingTermService;
    @Autowired
    TeachingTypeService teachingTypeService;
    @Autowired
    TeacherService teacherService;

    @Autowired
    public CourseTeachingClassServiceImpl(CourseTeachingClassRepository courseTeachingClassRepository,
                                          CourseTeachingClassTeacherRepository courseTeachingClassTeacherRepository) {
        this.courseTeachingClassRepository = courseTeachingClassRepository;
        this.courseTeachingClassTeacherRepository = courseTeachingClassTeacherRepository;


    }

    /**
     * 从Excel上传学生清单，然后添加到数据库中
     */
    @Override
    public void UploadFromExcel(String t_course_teaching_class_id, MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String localfilename = file.getOriginalFilename();
            try {
                File localfile = new File(localfilename);

                if (localfile.exists()) localfile.delete();

                file.transferTo(localfile);

                String prefix = localfilename.substring(localfilename.lastIndexOf(".") + 1);

                prefix = prefix.toLowerCase();

                if (prefix.equals("xls")) ProcessExcel97(t_course_teaching_class_id, localfilename);
                else if (prefix.equals("xlsx")) ProcessExcel(t_course_teaching_class_id, localfilename);

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
     * @throws Exception
     */
    private void ProcessExcel97(String t_course_teaching_class_id, String localfilename) throws Exception {

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

                    if (naturalclass != null)
                        AddStudent(t_course_teaching_class_id, school, department, naturalclass, name, student_num);

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
    private void ProcessExcel(String t_course_teaching_class_id, String localfilename) throws Exception {


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

                    if (naturalclass != null)
                        AddStudent(t_course_teaching_class_id, school, department, naturalclass, name, student_num);

                }

            }

            file.close();

        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * 添加学生
     *
     * @param t_course_teaching_class_id 教学班id
     * @return User
     * @throws Exception
     */
    @Override
    public String AddStudent(String t_course_teaching_class_id, String school, String department, String naturalclass,
                             String name, String student_num) throws Exception {

        String t_student_id = studentService.AddStudent(school, department, naturalclass, name, student_num);

        if (t_student_id == null) return null;

        int show_index = courseTeachingClassStudentService
                .getShowIndexMaxByCourseTeachingClassId(t_course_teaching_class_id);
        show_index += 10;
        if (courseTeachingClassStudentService.IsStudentExist(t_course_teaching_class_id, t_student_id))
            courseTeachingClassStudentService.update(t_course_teaching_class_id, t_student_id, show_index);
        else
            courseTeachingClassStudentService.add(t_course_teaching_class_id, t_student_id, show_index);

        return t_student_id;

    }


    @Override
    public String add(String t_course_id, String name, String t_course_teaching_term_id, String[] teacherid,
                      String[] teachingtypetypeId) {

        String t_course_teaching_class_id = courseTeachingClassRepository.add(t_course_id, name, t_course_teaching_term_id);

        if (teacherid != null && teacherid.length > 0) for (int i = 0; i < teacherid.length; i++)
            courseTeachingClassTeacherRepository.add(t_course_teaching_class_id, teacherid[i], teachingtypetypeId[i]);

        return t_course_teaching_class_id;

    }

    /**
     * 将学生加入到教学班中
     */
    @Override
    public void add(String teachingclassid, String[] studentid) {

        int show_index = 10;
        for (String stuid : studentid) {

            courseTeachingClassStudentService.add(teachingclassid, stuid, show_index);
            show_index += 10;
        }
    }

    /**
     * 将教师加入到教学班中
     */
    @Override
    public void add(String t_course_teaching_class_id, String[] teacherid, String[] teachingtypeid) {

        // 1.删除旧的该课程所有授课教师信息
        courseTeachingClassTeacherRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);

        if (teacherid == null || teachingtypeid == null) return;

        if (teacherid.length != teachingtypeid.length) return;

        // 2.增加新的授课教师信息
        for (int i = 0; i < teacherid.length; i++)
            courseTeachingClassTeacherRepository.add(t_course_teaching_class_id, teacherid[i], teachingtypeid[i]);
    }

    /**
     * 根据t_course_id删除教学-课程信息
     */
    @Override
    public void deleteByCourseId(HttpServletRequest request, String t_course_id) {
        List<CourseTeachingClass> listCourses = courseTeachingClassRepository.getCourseTeachingClassByCourseId(t_course_id);
        if (listCourses == null) return;
        for (CourseTeachingClass c : listCourses) {
            String t_course_teaching_class_id = c.getId();
            deleteById(request, t_course_teaching_class_id);
        }
    }

    /**
     * 根据t_teaching_class_id删除教学-课程信息
     */
    @Override
    public void deleteByCourseTeachingClassIdAndStudentId(HttpServletRequest request, String t_course_teaching_class_id,
                                                          String t_student_id) {

    }

    /**
     * 根据t_course_id、t_teaching_class_id删除教学-课程信息
     */
    @Override
    public void deleteByCourseIdAndTeachingClassId(HttpServletRequest request, String t_course_id,
                                                   String t_teaching_class_id) {
        List<CourseTeachingClass> listCourses = courseTeachingClassRepository
                .getCourseTeachingClassByCourseIdTeachingClassId(t_course_id, t_teaching_class_id);
        if (listCourses == null) return;
        for (CourseTeachingClass c : listCourses) {
            String t_course_teaching_class_id = c.getId();
            deleteById(request, t_course_teaching_class_id);
        }
    }

    /**
     * 根据t_course_teaching_class_id删除教学-课程信息
     */
    @Override
    public void deleteById(HttpServletRequest request, String t_course_teaching_class_id) {
        // 教学班级-教师
        courseTeachingClassTeacherRepository.deleteByCourseTeachingClassId(t_course_teaching_class_id);

        // 相关论坛
        courseTeachingClassForumTopicService.deleteByCourseTeachingClassID(request, t_course_teaching_class_id);

        // 课程相关资料
        courseTeachingClassReferenceService.deleteByCourseTeachingClassId(request, t_course_teaching_class_id);

        // 课程相关作业类型
        courseTeachingClassHomeworkService.deleteByCourseTeachingClassId(request, t_course_teaching_class_id);

        // 课程相关作业类型
        courseTeachingClassHomeworkTypeService.deleteByCourseTeachingClassId(request, t_course_teaching_class_id);

        // 课程-教师
        courseTeachingClassTeacherService.deleteByCourseTeachingClassId(request, t_course_teaching_class_id);

        // 课程相关考勤
        attendanceService.deleteByCourseTeachingClassId(t_course_teaching_class_id);

        //删除分组
        courseTeachingClassStudentGroupService.deleteByCourseTeachingClassId(t_course_teaching_class_id);

        // 删除自己
        courseTeachingClassRepository.deleteById(t_course_teaching_class_id);
    }


    @Override
    public String update(String t_course_teaching_class_id, String t_ourse_id, String name,
                         String t_course_teaching_term_id, String[] teacherid, String[] teachingtypetypeId) {

        CourseTeachingClass ctc = courseTeachingClassRepository.getCourseTeachingClassById(t_course_teaching_class_id);
        if (ctc == null) return null;

        // 课程授课信息
        courseTeachingClassRepository.update(t_course_teaching_class_id, t_ourse_id, name, t_course_teaching_term_id);

        // 授课教师信息
        add(t_course_teaching_class_id, teacherid, teachingtypetypeId);

        return t_course_teaching_class_id;

    }

    @Override
    public CourseTeachingClass getById(String t_course_teaching_class_id) {
        return courseTeachingClassRepository.getCourseTeachingClassById(t_course_teaching_class_id);
    }

    /**
     * 根据课程-教学班Id得到教学班相关信息
     */
    @Override
    public CourseTeachingClassViewData getTeachingClassViewDataByCourseTeachingClassId(
            String t_course_teaching_class_id) {

        CourseTeachingClassViewData tcvd = new CourseTeachingClassViewData();

        CourseTeachingClass ctc = courseTeachingClassRepository.getCourseTeachingClassById(t_course_teaching_class_id);
        if (ctc != null) {

            tcvd.setCourseTeachingClass(ctc);

            Course c = courseService.getById(ctc.getCourseId());
            tcvd.setCourse(c);

            CourseTeachingTerm term = courseTeachingTermService.getById(ctc.getCourseTeachingTermId());
            tcvd.setTerm(term);

            // 根据教学班得到教师信息
            List<CourseTeachingClassTeacher> teachingclassteacherlist = courseTeachingClassTeacherRepository
                    .getTeachingClassTeacherByTeachingClassId(ctc.getId());
            if (teachingclassteacherlist.size() > 0) {

                TeacherViewData[] tvd = new TeacherViewData[teachingclassteacherlist.size()];

                TeachingType[] tt = new TeachingType[teachingclassteacherlist.size()];

                int index = 0;
                for (CourseTeachingClassTeacher tct : teachingclassteacherlist) {
                    tvd[index] = teacherService.getTeacherViewById(tct.getTeacherId());
                    tt[index] = teachingTypeService.getByID(tct.getTeachingTypeId());
                    index++;

                }

                tcvd.setTeacher(tvd);
                tcvd.setTeachingtype(tt);

            }

        }

        if (tcvd.getCourse() != null) return tcvd;

        return null;
    }


    /**
     * 得到所有教学课程
     */
    @Override
    public Page<CourseTeachingClassViewData> getPage(int pageNo, int pageSize) {
        Page<CourseTeachingClass> courseTeachingClassPage = courseTeachingClassRepository.getPage(pageNo, pageSize);
        if (courseTeachingClassPage == null) return null;


        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<CourseTeachingClassViewData> list = new ArrayList<>();


        for (CourseTeachingClass courseTeachingClass : courseTeachingClassPage.getResult()) {
            CourseTeachingClassViewData courseTeachingClassViewData = getTeachingClassViewDataByCourseTeachingClassId(courseTeachingClass.getId());
            if (courseTeachingClassViewData != null) list.add(courseTeachingClassViewData);
        }


        return new Page<>(startIndex, courseTeachingClassPage.getTotalCount(), pageSize, list);
    }

    /**
     * 得到课程全体教师视图
     */
    @Override
    public List<TeacherViewData> getTeacherPage(String t_course_teaching_class_id) {
        return courseTeachingClassTeacherService.getTeacherList(t_course_teaching_class_id);
    }


    /**
     * 得到课程全体教师视图 如果是学生的话，则会得到所有同他上课的授课教师 如果是教师的话，则会得到所有同他所教授课程相关的授课教师
     */
    @Override
    public Page<TeacherViewData> getTeacherViewPageByUserId(String t_user_id) {
        if (t_user_id == null) return null;

        List<TeacherViewData> data = new ArrayList<>();

        // 得到该教师所有的教学班
        List<CourseTeachingClassViewData> listCourseTeachingClass = getCourseTeachingClassViewDataByUserId(t_user_id);

        if (listCourseTeachingClass == null) return null;

        // 将每个教学班的教师添加到列表中，注意教师不能重复
        for (CourseTeachingClassViewData t : listCourseTeachingClass)
            for (TeacherViewData temp : t.getTeacher()) {

                boolean bflag = true;
                for (TeacherViewData d : data)
                    if (d.getTeacher().getId() == temp.getTeacher().getId()) {
                        bflag = false;
                        break;
                    }

                if (bflag) data.add(temp);
            }
        return new Page<>(0, data.size(), data.size(), data);
    }

    @Override
    public Page<StudentViewData> getStudentViewPageByUserId(String t_user_id) {
        if (t_user_id == null) return null;

        List<StudentViewData> data = new ArrayList<>();

        // 得到该教师所有的教学班,然后将每个教学班的教师添加到列表中
        List<CourseTeachingClassViewData> listCourseTeachingClass = getCourseTeachingClassViewDataByUserId(t_user_id);

        if (listCourseTeachingClass == null) return null;


        for (CourseTeachingClassViewData c : listCourseTeachingClass) {


            // 得到每个教学班的列表
            List<StudentViewData> listStudentViewData = studentService
                    .getStudentViewByCourseTeachingClassId(c.getCourseTeachingClass().getId());

            for (StudentViewData s : listStudentViewData) {

                boolean bflag = true;
                for (StudentViewData temp : data)
                    if (temp.getStudent().getId() == s.getStudent().getId()) {
                        bflag = false;
                        break;
                    }
                if (bflag) data.add(s);
            }
        }
        return new Page<>(0, data.size(), data.size(), data);
    }

    @Override
    public void ShowIndexMoveUp(String t_course_teaching_class_id, String t_student_id) {
        // 1.得到该学生
        CourseTeachingClassStudent stu = courseTeachingClassStudentService
                .getTeachingClassStudentByTeachingClassIdAndStudentId(t_course_teaching_class_id, t_student_id);
        if (stu == null) return;

        // 2.得到前面一个学生
        int show_index_max = courseTeachingClassStudentService
                .getShowIndexMaxLessthanByCourseTeachingClassId(t_course_teaching_class_id, t_student_id);
        CourseTeachingClassStudent pre = courseTeachingClassStudentService
                .getTeachingClassStudentByTeachingClassIdAndShowIndex(t_course_teaching_class_id, show_index_max);
        if (pre == null) return;

        int temp = stu.getShowIndex();
        courseTeachingClassStudentService.update(stu.getId(), show_index_max);
        courseTeachingClassStudentService.update(pre.getId(), temp);

    }

    @Override
    public void ShowIndexMoveDown(String t_course_teaching_class_id, String t_student_id) {
        // 1.得到该学生
        CourseTeachingClassStudent stu = courseTeachingClassStudentService
                .getTeachingClassStudentByTeachingClassIdAndStudentId(t_course_teaching_class_id, t_student_id);
        if (stu == null) return;

        // 2.得到后面一个学生
        int show_index_min = courseTeachingClassStudentService
                .getShowIndexMinMorethanByCourseTeachingClassId(t_course_teaching_class_id, t_student_id);
        CourseTeachingClassStudent next = courseTeachingClassStudentService
                .getTeachingClassStudentByTeachingClassIdAndShowIndex(t_course_teaching_class_id, show_index_min);
        if (next == null) return;

        int temp = stu.getShowIndex();
        courseTeachingClassStudentService.update(stu.getId(), show_index_min);
        courseTeachingClassStudentService.update(next.getId(), temp);

    }

    @Override
    public List<CourseTeachingClassViewData> getTeacherViewBySchoolId() {
        return null;
    }

    /**
     * 根据用户id得到教学班信息
     */
    @Override
    public List<CourseTeachingClassViewData> getCourseTeachingClassViewDataByUserId(String t_user_id) {
        List<CourseTeachingClassViewData> list = new ArrayList<>();

        Teacher teacher = teacherService.getTeacherByUserId(t_user_id);
        if (teacher != null) {

            // 根据教师id得到教学班
            List<CourseTeachingClassTeacher> teachingclassteacherlist = courseTeachingClassTeacherRepository
                    .getTeachingClassTeacherByTeacherId(teacher.getId());

            for (CourseTeachingClassTeacher tct : teachingclassteacherlist) {

                // 根据教学班得到教学班信息
                CourseTeachingClassViewData temp = getTeachingClassViewDataByCourseTeachingClassId(
                        tct.getCourseTeachingClassId());

                list.add(temp);
            }

        } else {
            Student student = studentService.getStudentByStudentId(t_user_id);

            if (student != null) {
                // 根据学生id得到教学班
                List<CourseTeachingClassStudent> teachingclassstudentlist = courseTeachingClassStudentService
                        .getTeachingClassStudentByStudentId(student);

                for (CourseTeachingClassStudent tct : teachingclassstudentlist) {
                    // 根据教学班得到教学班信息
                    CourseTeachingClassViewData temp = getTeachingClassViewDataByCourseTeachingClassId(
                            tct.getCourseTeachingClassId());

                    list.add(temp);
                }
            }
        }
        if (list.size() == 0) return null;
        return list;
    }

}
