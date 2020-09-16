package com.haobaoshui.course.repository.course.impl;

import com.haobaoshui.course.model.course.Course;
import com.haobaoshui.course.model.Page;
import com.haobaoshui.course.repository.course.CourseRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class CourseRepositoryImpl implements CourseRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 删除
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_course where id=?", id);
    }

    /**
     * 修改
     */
    @Override
    public int update(String id, String name, String englishname, String num,
                      int class_hours, int experiment_hours, String t_course_type_id,
                      String t_course_style_id) {


        if (id == null || name == null) return 0;

        return jdbcTemplate.update("update t_course set name=?,english_name=?,num=?,class_hours=?,experiment_hours=?,t_course_type_id=?,t_course_style_id=? where id=?", name, englishname, num,
                class_hours, experiment_hours, t_course_type_id,
                t_course_style_id, id);


    }

    /**
     * 增加
     */
    @Override
    public String add(String name, String englishname, String num,
                      int class_hours, int experiment_hours, String t_course_type_id,
                      String t_course_style_id) {


        String newid = GUID.getGUID();
        jdbcTemplate.update("INSERT INTO t_course(id,name,english_name,num,class_hours,experiment_hours,t_course_type_id,t_course_style_id) VALUES(?,?,?,?,?,?,?,?)", newid, name, englishname, num,
                class_hours, experiment_hours, t_course_type_id,
                t_course_style_id);
        return newid;


    }

    /**
     * 根据课程id查找课程
     */
    @Override
    public Course getCourseById(String id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("select * "
                + " from t_course where id=?", new Object[]{id}, new CourseMapper());

    }

    /**
     * 根据t_course_type_id查找课程
     */
    @Override
    public List<Course> getByCourseTypeId(String t_course_type_id) {

        if (getCountByCourseType(t_course_type_id) == 0) return null;

        return jdbcTemplate.query("select *  from t_course where t_course_type_id=?", new Object[]{t_course_type_id}, new CourseMapper());


    }

    /**
     * 根据t_course_style_id查找课程
     */
    @Override
    public List<Course> getByCourseStyleId(String t_course_style_id) {
        if (getCountByCourseStyle(t_course_style_id) == 0) return null;

        return jdbcTemplate.query("select *  from t_course where t_course_style_id=?", new Object[]{t_course_style_id}, new CourseMapper());

    }

    /**
     * 根据t_course_type_id,t_course_style_id查找课程
     */
    @Override
    public List<Course> getByCourseTypeIdAndStyleId(String t_course_type_id, String t_course_style_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_course WHERE t_course_type_id=? and t_course_style_id=?", new
                Object[]{t_course_type_id, t_course_style_id}, Integer.class) == 0) return null;

        return jdbcTemplate.query("select *  from t_course where t_course_type_id=? and t_course_style_id=?", new Object[]{t_course_type_id, t_course_style_id}, new CourseMapper());

    }

    /**
     * 根据课程编号查找课程
     */
    @Override
    public Course getCourseByCourseNum(String num) {
        if (getCountByCourseNum(num) == 0) return null;


        return jdbcTemplate.queryForObject("select * from t_course where num=?", new Object[]{num}, new CourseMapper());

    }

    /**
     * 得到所有课程数目
     */
    @Override
    public long getCount() {

        return jdbcTemplate.queryForObject("select count(*) from t_course", Integer.class);
    }

    /**
     * 得到
     */
    @Override
    public long getCountByCourseStyle(String t_course_style_id) {
        return jdbcTemplate.queryForObject("select count(*) from t_course where t_course_style_id=?", new Object[]{t_course_style_id}, Integer.class);

    }

    /**
     * 得到
     */
    @Override
    public long getCountByCourseNum(String num) {
        return jdbcTemplate.queryForObject("select count(*) from t_course where num=?", new Object[]{num}, Integer.class);
    }

    /**
     * 得到
     */
    @Override
    public long getCountByCourseType(String t_course_type_id) {
        return jdbcTemplate.queryForObject("select count(*) from t_course where t_course_type_id=?", new Object[]{t_course_type_id}, Integer.class);
    }

    /**
     * 得到
     */
    @Override
    public long getCountByCourseDepartmentId(String t_department_id) {
        return jdbcTemplate.queryForObject("select count(*)"
                + " from t_course   where id in  ( select t_course_id"
                + " from t_course_department" + " where t_department_id=?" + " )", new Object[]{t_department_id}, Integer.class);
    }


    private List<Course> PageQuery(int PageBegin, int PageSize) {
        if (getCount() == 0) return null;

        return jdbcTemplate.query("select *  from t_course order by t_course.name limit ?,?", new Object[]{PageBegin * PageSize, PageSize}, new CourseMapper());


    }

    @Override
    public Page<Course> getPage(int pageNo, int pageSize) {
        long totalCount = getCount();
        if (totalCount < 1) return new Page<>();

        // 实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);

        List<Course> data = PageQuery(pageNo - 1, pageSize);

        return new Page<>(startIndex, totalCount, pageSize, data);

    }

    private static final class CourseMapper implements RowMapper<Course> {

        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setId(rs.getString("id"));
            course.setName(rs.getString("name"));
            course.setEnglishname(rs.getString("english_name"));
            course.setNum(rs.getString("num"));
            course.setClass_hours(rs.getInt("class_hours"));
            course.setExperiment_hours(rs.getInt("experiment_hours"));

            course.setCourseStyleId(rs
                    .getString("t_course_style_id"));


            course.setCourseTypeId(rs.getString("t_course_type_id"));

            return course;
        }
    }


}
