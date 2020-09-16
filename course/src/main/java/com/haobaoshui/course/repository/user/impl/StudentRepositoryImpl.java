package com.haobaoshui.course.repository.user.impl;

import com.haobaoshui.course.model.user.Student;
import com.haobaoshui.course.repository.user.StudentRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*增加*/
    @Override
    public String add(Student stu) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_student(id,t_user_id,student_num) VALUES(?,?,?)", newid, stu.getUserId(), stu.getStudentNum());
        return newid;

    }

    @Override
    public int UpdateStudentNumById(String t_student_id, String student_num) {
        if (t_student_id == null || student_num == null) return 0;

        return jdbcTemplate.update("update t_student set student_num=? WHERE id=?", student_num, t_student_id);


    }

    /*删除
     * */
    @Override
    public int deleteById(String t_student_id) {
        return jdbcTemplate.update("DELETE from  t_student where id=?", t_student_id);
    }

    /*根据用户ID得到用户
     * */
    @Override
    public Student getStudentByID(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_student WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_student WHERE id=?", new Object[]{id}, new StudentMapper());


    }

    /*根据学号ID得到用户
     * */
    @Override
    public Student getStudentByStudentNum(String student_num) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_student WHERE student_num=?", new
                Object[]{student_num}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_student WHERE student_num=?", new Object[]{student_num}, new StudentMapper());
    }

    /*根据t_user_id得到用户
     * */
    @Override
    public Student getStudentByUserId(String t_user_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_student WHERE t_user_id=?", new
                Object[]{t_user_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_student WHERE t_user_id=?", new Object[]{t_user_id}, new StudentMapper());


    }

    @Override
    public List<Student> getByNaturalClassId(String t_natural_class_id) {

        if (getCountByNaturalClassId(t_natural_class_id) == 0) return null;
        return jdbcTemplate.query("select * "
                        + " from t_student where id in(select t_student_id from t_natural_class_student where t_natural_class_id=?) order by student_num", new Object[]{t_natural_class_id},
                new StudentMapper());


    }

    @Override
    public List<Student> getByNaturalClassId(String t_natural_class_id, int PageBegin, int PageSize) {
        if (getCountByNaturalClassId(t_natural_class_id) == 0) return null;

        return jdbcTemplate.query("select * "
                        + " from t_student where id in(select t_student_id from t_natural_class_student where t_natural_class_id=?) order by student_num limit ?,?",
                new Object[]{t_natural_class_id, PageBegin * PageSize, PageSize}, new StudentMapper());


    }

    @Override
    public List<Student> getByDepartmentId(String t_department_id) {

        if (jdbcTemplate.queryForObject("select count(*)  from t_student where id in(select t_student_id from t_natural_class_student where  t_natural_class_id in (select t_natural_class_id from t_natural_class_department where t_department_id=?)) order by student_num",
                new Object[]{t_department_id}, Long.class) == 0) return null;


        return jdbcTemplate.query("select * "
                        + " from t_student where id in(select t_student_id from t_natural_class_student where  t_natural_class_id in (select t_natural_class_id from t_natural_class_department where t_department_id=?)) order by student_num",
                new Object[]{t_department_id}, new StudentMapper());
    }

    @Override
    public List<Student> getByCourseTeachingClassId(String t_course_teaching_class_id, int PageBegin,
                                                    int PageSize) {


        if (getCountByCourseTeachingClassId(t_course_teaching_class_id) == 0) return null;


        return jdbcTemplate.query("select A.*  from t_student A "
                        + "LEFT OUTER JOIN t_course_teaching_class_student  B ON B.t_student_id= A.id "
                        + "where B.t_course_teaching_class_id =?  order by show_index  limit ?,?",
                new Object[]{t_course_teaching_class_id, PageBegin * PageSize, PageSize}, new StudentMapper());


    }

    @Override
    public List<Student> getByCourseTeachingClassId(String t_course_teaching_class_id) {

        if (getCountByCourseTeachingClassId(t_course_teaching_class_id) == 0) return null;

        return jdbcTemplate.query("select A.* "
                        + "from t_student A "
                        + "left outer join t_course_teaching_class_student C on A.id=C.t_student_id "
                        + "left outer join t_natural_class_student D on A.id=D.t_student_id "
                        + "left outer join t_natural_class B on B.id=D.t_natural_class_id "
                        + "where C.t_course_teaching_class_id =? "
                        + "order by show_index,name,student_num",
                new Object[]{t_course_teaching_class_id}, new StudentMapper());


    }

    @Override
    public long getCountByNaturalClassId(String t_natural_class_id) {

        return jdbcTemplate.queryForObject("select count(*)" + " from t_student"
                        + " where id in(select t_student_id from t_natural_class_student where t_natural_class_id=?)",
                new Object[]{t_natural_class_id}, Long.class);
    }

    @Override
    public long getCountByCourseTeachingClassId(String t_course_teaching_class_id) {

        return jdbcTemplate.queryForObject("select count(*)"
                        + " from t_course_teaching_class_student" + " where t_course_teaching_class_id=?",
                new Object[]{t_course_teaching_class_id}, Long.class);
    }

    /**
     * 得到所有未分组的学生
     */
    @Override
    public List<Student> getNotGroupedByCourseTeachingClassId(String t_course_teaching_class_id) {

        final String GET_COUNT_NOT_GROUPED_BY_COURSE_TEACHING_CLASS_ID = "select count(B.*) "
                + "from t_course_teaching_class_student A LEFT OUTER JOIN t_student  B ON B.id= A.t_student_id "
                + "where A.t_course_teaching_class_id=? and B.t_user_id not in (  select t_user_id  from t_user_group "
                + "where t_group_id in ( select t_group_id  from t_course_teaching_class_student_group "
                + "where t_course_teaching_class_id=? )) order by A.show_index";

        final String GET_STUDENTRVIEWDATA_NOT_GROUPED_BY_COURSE_TEACHING_CLASS_ID = "select B.*"
                + "from t_course_teaching_class_student A LEFT OUTER JOIN t_student  B ON B.id= A.t_student_id "
                + "where A.t_course_teaching_class_id=? and B.t_user_id not in (  select t_user_id  from t_user_group "
                + "where t_group_id in ( select t_group_id  from t_course_teaching_class_student_group "
                + "where t_course_teaching_class_id=? )) order by A.show_index";

        if (jdbcTemplate.queryForObject(GET_COUNT_NOT_GROUPED_BY_COURSE_TEACHING_CLASS_ID,
                new Object[]{t_course_teaching_class_id, t_course_teaching_class_id}, Long.class) == 0) return null;

        return jdbcTemplate.query(GET_STUDENTRVIEWDATA_NOT_GROUPED_BY_COURSE_TEACHING_CLASS_ID,
                new Object[]{t_course_teaching_class_id, t_course_teaching_class_id}, new StudentMapper());


    }

    private static final class StudentMapper implements RowMapper<Student> {

        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getString("id"));
            student.setStudentNum(rs.getString("student_num"));
            student.setUserId(rs.getString("t_user_id"));


            return student;
        }
    }


}
