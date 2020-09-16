package com.haobaoshui.course.repository.organization.impl;

import com.haobaoshui.course.model.organization.DepartmentNaturalClass;
import com.haobaoshui.course.repository.organization.DepartmentNaturalClassRepository;
import com.haobaoshui.course.utility.GUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DepartmentNaturalClassRepositoryImpl implements DepartmentNaturalClassRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DepartmentNaturalClassRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 增加记录
     */
    @Override
    public String add(String t_department_id, String t_natural_class_id) {

        if (t_department_id == null || t_natural_class_id == null) return null;

        String newid = GUID.getGUID();

        jdbcTemplate.update("INSERT INTO t_natural_class_department(id,t_natural_class_id,t_department_id) VALUES(?,?,?)", newid, t_natural_class_id, t_department_id);
        return newid;


    }

    /**
     * 删除指定id
     */
    @Override
    public int deleteById(String id) {
        return jdbcTemplate.update("DELETE from  t_natural_class_department where id=?", id);


    }

    /**
     * 删除
     */

    @Override
    public int deleteByDepartmentId(String t_department_id) {
        return jdbcTemplate.update("DELETE FROM t_natural_class_department WHERE t_department_id=?", t_department_id);

    }

    /**
     * 删除
     */
    @Override
    public int deleteByNaturalclassId(String t_natural_class_id) {
        return jdbcTemplate.update("DELETE FROM t_natural_class_department  WHERE t_natural_class_id=?", t_natural_class_id);
    }

    @Override
    public DepartmentNaturalClass getById(String id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_department WHERE id=?", new
                Object[]{id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id,note FROM t_natural_class_department WHERE id=?", new Object[]{id}, new DepartmentNaturalClassMapper());

    }

    /**
     * 根据t_natural_class_id得到记录
     */
    @Override
    public DepartmentNaturalClass getByNaturalclassId(String t_natural_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_department  WHERE t_natural_class_id=?", new
                Object[]{t_natural_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT *   FROM t_natural_class_department  WHERE t_natural_class_id=?", new Object[]{t_natural_class_id}, new DepartmentNaturalClassMapper());


    }

    /*
     * 得到
     */
    @Override
    public DepartmentNaturalClass getByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_department  WHERE t_department_id=? and t_natural_class_id=?", new
                Object[]{t_department_id, t_natural_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT *   FROM t_natural_class_department  WHERE t_department_id=? and t_natural_class_id=?", new Object[]{t_department_id, t_natural_class_id}, new DepartmentNaturalClassMapper());
    }

    /*
     * 得到id
     */
    @Override
    public String getIdByDepartmentNaturalclassId(String t_department_id, String t_natural_class_id) {
        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_department  WHERE t_department_id=? and t_natural_class_id=?", new
                Object[]{t_department_id, t_natural_class_id}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT id FROM t_natural_class_department  WHERE t_department_id=? and t_natural_class_id=?",
                new Object[]{t_department_id, t_natural_class_id}, String.class);


    }

    /*
     * 得到id
     */
    @Override
    public String getIdByDepartmentIdNaturalclassName(String t_department_id, String t_natural_class_name) {
        if (jdbcTemplate.queryForObject("SELECT count(*) "
                + "FROM t_natural_class A " + "left outer join t_natural_class_department B on B.t_natural_class_id=A.id "
                + " WHERE B.t_department_id=? and A.name=?", new
                Object[]{t_department_id, t_natural_class_name}, Integer.class) != 1) return null;

        return jdbcTemplate.queryForObject("SELECT A.id "
                        + "FROM t_natural_class A " + "left outer join t_natural_class_department B on B.t_natural_class_id=A.id "
                        + " WHERE B.t_department_id=? and A.name=?",
                new Object[]{t_department_id, t_natural_class_name}, String.class);

    }


    /**
     * 根据系部id得到该学院下所有的自然班
     */
    @Override
    public List<DepartmentNaturalClass> getByDepartmentId(String t_department_id) {

        if (jdbcTemplate.queryForObject("SELECT count(*) FROM t_natural_class_department WHERE t_department_id=?", new
                Object[]{t_department_id}, Integer.class) != 1) return null;

        return jdbcTemplate.query("SELECT id,name,note FROM t_natural_class_department WHERE t_department_id=?", new
                Object[]{t_department_id}, new DepartmentNaturalClassMapper());

    }


    /*
     * 得到总数
     */
    @Override
    public long getCount(String t_department_id) {

        return jdbcTemplate.queryForObject("SELECT count(*) "
                        + "FROM t_natural_class_department " + "WHERE t_department_id=?",
                new Object[]{t_department_id}, Long.class);
    }


    private static final class DepartmentNaturalClassMapper implements RowMapper<DepartmentNaturalClass> {

        @Override
        public DepartmentNaturalClass mapRow(ResultSet rs, int rowNum) throws SQLException {
            DepartmentNaturalClass departmentNaturalClass = new DepartmentNaturalClass();
            departmentNaturalClass.setId(rs.getString("id"));
            departmentNaturalClass.setDepartmentId(rs.getString("t_department_id"));
            departmentNaturalClass.setNaturalClassId(rs.getString("t_natural_class_id"));

            return departmentNaturalClass;
        }
    }

}
