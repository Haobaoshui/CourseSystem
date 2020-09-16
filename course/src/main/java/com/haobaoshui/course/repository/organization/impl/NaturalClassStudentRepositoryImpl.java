package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.organization.*;
import com.haobaoshui.course.repository.organization.*;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NaturalClassStudentRepositoryImpl implements NaturalClassStudentRepository {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    DepartmentNaturalClassRepository departmentNaturalClassRepository;
    @Autowired
    SchoolDepartmentRepository schoolDepartmentRepository;
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private NaturalClassRepository naturalClassRepository;

    @Autowired
    public NaturalClassStudentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加记录
     */
    @Override
    public String add(String t_natural_class_id, String t_student_id) {

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_natural_class_student(id,t_natural_class_id,t_student_id) VALUES(?,?,?)", newid, t_natural_class_id, t_student_id);
        return newid;

    }

    /**
     * 删除指定id
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_natural_class_student where id=?", id);


    }

    /**
     * 删除指定自然班学生
     */
    @Override
    public int deleteByNaturalClassId(String t_natural_class_id) {

        return jdbcTemplate.update("DELETE FROM t_natural_class_student WHERE t_natural_class_id=?", t_natural_class_id);


    }

    /**
     * 删除指定自然班学生
     */
    @Override
    public int deleteByStudentId(String t_student_id) {
        return jdbcTemplate.update("DELETE FROM t_natural_class_student WHERE t_student_id=?", t_student_id);
    }

    @Override
    public NaturalClassStudent getNaturalClassStudentById(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_student WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_natural_class_student WHERE id=?", new Object[]{id}, new NaturalClassStudentMapper());
    }

    /**
     * 根据学生id得到记录，注意，一个学生只能存在一个自然班中
     */
    @Override
    public NaturalClassStudent getNaturalClassStudentByStudentId(String t_student_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_student WHERE t_student_id=?", new
                Object[]{t_student_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT * FROM t_natural_class_student WHERE t_student_id=?", new Object[]{t_student_id}, new NaturalClassStudentMapper());


    }

    /**
     * 根据自然班id得到该班的学生id
     */
    @Override
    public List<String> getStudentIdByNaturalClassId(String t_natural_class_id) {

        List<String> list = new ArrayList<>();

        Object[] params = new Object[]{t_natural_class_id};
        int[] types = new int[]{Types.VARCHAR};

        jdbcTemplate.query("SELECT t_student_id FROM t_natural_class_student WHERE t_natural_class_id=?", params, types, new RowCallbackHandler() {

            @Override
            public void processRow(ResultSet rs) throws SQLException {
                list.add(rs.getString("t_student_id"));

            }

        });

        return list;
    }

    /**
     * 根据学生id得到学生所在学院
     */
    @Override
    public School getSchoolByStudentId(String t_student_id) {

        NaturalClassStudent ncs = getNaturalClassStudentByStudentId(t_student_id);

        if (ncs != null) {
            DepartmentNaturalClass ncst = departmentNaturalClassRepository.getByNaturalclassId(ncs.getNaturalClassId());
            if (ncst != null) {
                Department department = getDepartmentByStudentId(ncst.getDepartmentId());
                return schoolRepository.getByID(schoolDepartmentRepository.getSchoolIdByDepartmentId(department.getId()));
            }
        }

        return null;
    }

    /**
     * 根据学生id得到学生所在系部
     */
    @Override
    public Department getDepartmentByStudentId(String t_student_id) {
        NaturalClassStudent ncs = getNaturalClassStudentByStudentId(t_student_id);

        if (ncs != null) {
            DepartmentNaturalClass ncst = departmentNaturalClassRepository.getByNaturalclassId(ncs.getNaturalClassId());
            if (ncst != null) return departmentRepository.getByID(ncst.getDepartmentId());
        }

        return null;
    }

    /**
     * 根据学生id得到学生所在自然班
     */
    @Override
    public NaturalClass getNaturalClassByStudentId(String t_student_id) {
        NaturalClassStudent ncs = getNaturalClassStudentByStudentId(t_student_id);

        if (ncs != null) return naturalClassRepository.getByID(ncs.getNaturalClassId());

        return null;
    }

    /**
     * 指定学号是否存在
     */
    @Override
    public boolean isStudentExist(String t_natural_class_id, String t_student_num) {
        return jdbcTemplate.queryForObject("SELECT count(*) "
                + "FROM t_natural_class_student A " + "left outer join t_student B on A.t_student_id=B.id "
                + "WHERE A.t_natural_class_id=? and B.student_num=?", new Object[]{t_natural_class_id, t_student_num}, Integer.class) > 0;


    }

    /**
     * 获得指定学号的id
     */
    @Override
    public String getStudentId(String t_natural_class_id, String t_student_num) {

        return jdbcTemplate.queryForObject("SELECT t_student_id "
                + " FROM t_natural_class_student A " + "left outer join t_student B on A.t_student_id=B.id "
                + " WHERE A.t_natural_class_id=? and B.student_num=?", new Object[]{t_natural_class_id, t_student_num}, String.class);


    }

    private static final class NaturalClassStudentMapper implements RowMapper<NaturalClassStudent> {

        @Override
        public NaturalClassStudent mapRow(ResultSet rs, int rowNum) throws SQLException {
            NaturalClassStudent naturalClassStudent = new NaturalClassStudent();
            naturalClassStudent.setId(rs.getString("id"));
            naturalClassStudent.setNaturalClassId(rs.getString("t_natural_class_id"));
            naturalClassStudent.setStudentId(rs.getString("t_student_id"));

            return naturalClassStudent;
        }
    }

}
