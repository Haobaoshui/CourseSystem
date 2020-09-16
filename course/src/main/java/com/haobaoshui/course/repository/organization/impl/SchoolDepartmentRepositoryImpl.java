package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.organization.SchoolDepartment;
import com.haobaoshui.course.repository.organization.SchoolDepartmentRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class SchoolDepartmentRepositoryImpl implements SchoolDepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SchoolDepartmentRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String add(String t_school_id, String t_department_id) {
        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_school_department(id,t_school_id,t_department_id) VALUES(?,?,?)", newid, t_school_id, t_department_id);
        return newid;

    }

    @Override
    public int deleteByDepartmentId(String t_department_id) {
        return jdbcTemplate.update("DELETE FROM t_school_department WHERE t_department_id=?", t_department_id);


    }

    @Override
    public int delete(String t_school_id, String t_department_id) {
        return jdbcTemplate.update("DELETE FROM t_school_department WHERE t_school_id=? and t_department_id=?", t_school_id, t_department_id);


    }

    @Override
    public String getSchoolIdByDepartmentId(String t_department_id) {
        return jdbcTemplate.queryForObject("SELECT t_school_id FROM t_school_department WHERE t_department_id=?", new Object[]{t_department_id,},
                String.class);
    }


    @Override
    public String getIdBySchoolIdDepartmentId(String t_school_id, String t_department_id) {

        return jdbcTemplate.queryForObject("SELECT id FROM t_school_department WHERE t_school_id=? and t_department_id=?",
                new Object[]{t_school_id, t_department_id,},
                String.class);
    }

    @Override
    public List<SchoolDepartment> getIdBySchoolIdDepartmentName(String t_school_id, String departmentName) {

        if (t_school_id == null || t_school_id.length() == 0) return null;

        if (getDepartmentCount(t_school_id, departmentName) == 0) return null;

        Object[] params = new Object[]{t_school_id, departmentName};
        int[] types = new int[]{Types.VARCHAR, Types.VARCHAR};

        return jdbcTemplate.query("select * from t_school_department,t_department where  t_school_department.t_department_id=t_department.id and t_school_department.t_school_id=? and t_department.name=?", params, types, new SchoolDepartmentMapper());


    }

    @Override
    public List<SchoolDepartment> getDepartmentIdBySchoolId(String t_school_id) {
        if (t_school_id == null || t_school_id.length() == 0) return null;


        Object[] params = new Object[]{t_school_id};
        int[] types = new int[]{Types.VARCHAR};

        return jdbcTemplate.query("SELECT * FROM t_school_department WHERE t_school_id=?", params, types, new SchoolDepartmentMapper());


    }

    @Override
    public long getDepartmentCount(String t_school_id, String departmentName) {

        return jdbcTemplate.queryForObject("select count(*) from t_school_department,t_department where  t_school_department.t_department_id=t_department.id and t_school_department.t_school_id=? and t_department.name=?", new Object[]{t_school_id, departmentName}, Integer.class);


    }

    private static final class SchoolDepartmentMapper implements RowMapper<SchoolDepartment> {

        @Override
        public SchoolDepartment mapRow(ResultSet rs, int rowNum) throws SQLException {
            SchoolDepartment schoolDepartment = new SchoolDepartment();
            schoolDepartment.setId(rs.getString("id"));
            schoolDepartment.setDepartmentId(rs.getString("t_department_id"));
            schoolDepartment.setSchoolId(rs.getString("t_school_id"));

            return schoolDepartment;
        }
    }

}
