package com.haobaoshui.course.service.fileparser;

import com.haobaoshui.course.model.FileNodePropertyManager;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 文件格式
 * <p>
 * 支持下列操作符 {学院}:学生所在的学院 {系别}:学生所在的系别 {教学班}:学生所在的教学班 {自然班}:学生所在的自然班（行政班）
 * {学号}:学生的学号 {姓名}:学生的姓名 {作业类型}:本次作业的类型 {作业名称}:本次作业的名称 {数字}:数字，从0开始的整数，不能是负数或小数。
 * {?}:一个字符 {*}:任意长度的字符
 */

public interface FileNameFormatParser {


    /**
     * 是否符合格式 只有支持的操作符才能够识别
     */
    boolean IsFileNameFormatRight(String strFormat);


    // 生成正确的示例性的文件名字
    List<String> getRightExampleFileName(String t_course_teaching_class_homework_baseinfo_id, int nodeID,
                                         String t_student_id);

    /**
     * 提交的文件个数是否符合要求,符合要求返回true,否则返回false
     */
    boolean IsSubmitFileCountRight(HttpServletRequest request, FileNodePropertyManager fileNodePropertyManager,
                                   MultipartFile[] files, String t_course_teaching_class_homework_baseinfo_id);

    /**
     * 提交的文件是否有为空的文件,不为空，则发挥true，否则返回false
     */

    boolean IsSubmitFileContentIsNotNull(HttpServletRequest request, MultipartFile[] files);

    /**
     * 提交的文件名称是否符合要求,符合要求返回true,否则返回false
     */
    boolean IsSubmitFileNameFormatRight(HttpServletRequest request,
                                        FileNodePropertyManager fileNodePropertyManager, MultipartFile[] files,
                                        String t_course_teaching_class_homework_baseinfo_id, String t_student_id);
}
