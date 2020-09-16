package com.haobaoshui.course.repository.courseteachingclass.impl;

import com.haobaoshui.course.model.courseteachingclass.CourseTeachingClassHomeworkScoreInfo;
import com.haobaoshui.course.repository.courseteachingclass.CourseTeachingClassHomeworkScoreInfoRepository;
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
public class CourseTeachingClassHomeworkScoreInfoRepositoryImpl implements CourseTeachingClassHomeworkScoreInfoRepository {


	private final JdbcTemplate jdbcTemplate;


	@Autowired
	public CourseTeachingClassHomeworkScoreInfoRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void update(String id, String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
					   String t_score_show_type_id) {
		if (id == null) return;

		Object[] params = new Object[]{t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id,
				t_score_show_type_id, id};
		int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

		final String updateSQL = "update t_course_teaching_class_homework_score_info set t_course_teaching_class_homework_baseinfo_id=?, t_score_marking_type_id=?, t_score_show_type_id=?  WHERE id=?";


		jdbcTemplate.update(updateSQL, params, types);
	}

	@Override
	public void updateByCourseTeachingClassIdAndHomeworkTypeId(String t_course_teaching_class_id,
															   String t_course_teaching_class_homeworktype_id, String t_score_marking_type_id,
															   String t_score_show_type_id) {

		Object[] params = new Object[]{t_score_marking_type_id, t_score_show_type_id, t_course_teaching_class_id,
				t_course_teaching_class_homeworktype_id};
		int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

		final String updateSQL = "update t_course_teaching_class_homework_score_info set t_score_marking_type_id=?, t_score_show_type_id=?  WHERE t_course_teaching_class_homework_baseinfo_id in(select id from t_course_teaching_class_homework_baseinfo where t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?)";


		jdbcTemplate.update(updateSQL, params, types);
	}

	/*
	 * 根据ID得到用户
	 */
	@Override
	public CourseTeachingClassHomeworkScoreInfo getByID(String id) {

		final String GET_BY_ID = "SELECT t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id, t_score_show_type_id FROM t_course_teaching_class_homework_score_info WHERE id=?";

		return jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id}, new CourseTeachingClassHomeworkScoreInfoMapper());

	}

	/**
	 * 根据
	 */
	@Override
	public CourseTeachingClassHomeworkScoreInfo getByCourseTeachingClassHomeworkBaseinfoID(
			String t_course_teaching_class_homework_baseinfo_id) {

		final String GET_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID = "SELECT id, t_score_marking_type_id, t_score_show_type_id  FROM t_course_teaching_class_homework_score_info WHERE t_course_teaching_class_homework_baseinfo_id=?";

		return jdbcTemplate.queryForObject(GET_BY_COURSE_TEACHING_CLASS_HOMEWORK_BASEINFO_ID,
				new Object[]{t_course_teaching_class_homework_baseinfo_id}, new CourseTeachingClassHomeworkScoreInfoMapper());

	}

	/**
	 * 根据
	 */
	@Override
	public List<CourseTeachingClassHomeworkScoreInfo> getByScoreMarkingTypeID(String t_score_marking_type_id) {

		final String GET_BY_SCORE_MARKING_TYPE_ID = "SELECT id, t_course_teaching_class_homework_baseinfo_id, t_score_show_type_id  FROM t_course_teaching_class_homework_score_info WHERE t_score_marking_type_id=?";

		return jdbcTemplate.query(GET_BY_SCORE_MARKING_TYPE_ID, new Object[]{t_score_marking_type_id},
				new CourseTeachingClassHomeworkScoreInfoMapper());


	}

	/**
	 * 根据
	 */
	@Override
	public List<CourseTeachingClassHomeworkScoreInfo> getByScoreShowTypeID(String t_score_show_type_id) {

		final String GET_BY_SCORE_SHOW_TYPE_ID = "SELECT id, t_course_teaching_class_homework_baseinfo_id,t_score_marking_type_id  FROM t_course_teaching_class_homework_score_info WHERE t_score_show_type_id=?";


		return jdbcTemplate.query(GET_BY_SCORE_SHOW_TYPE_ID, new Object[]{t_score_show_type_id},
				new CourseTeachingClassHomeworkScoreInfoMapper());


	}

	/**
	 * 根据
	 */
	@Override
	public List<CourseTeachingClassHomeworkScoreInfo> PageQuery(String t_course_teaching_class_id,
																String t_course_teaching_class_homeworktype_id, int PageBegin, int PageSize) {
		final String querySQL = "SELECT A.* "
				+ " FROM t_course_teaching_class_homework_score_info A "
				+ " left outer join t_course_teaching_class_homework_baseinfo B on A.t_course_teaching_class_homework_baseinfo_id=B.id "
				+ " where B.t_course_teaching_class_id=? and B.t_course_teaching_class_homework_type_id=? "
				+ " order by pubdate desc   limit ?,?";

		return jdbcTemplate.query(
				querySQL, new Object[]{t_course_teaching_class_id,
						t_course_teaching_class_homeworktype_id, PageBegin * PageSize, PageSize},
				new CourseTeachingClassHomeworkScoreInfoMapper());


	}

	/**
	 * 根据
	 */
	@Override
	public List<CourseTeachingClassHomeworkScoreInfo> getAllOrderByAsc(String t_course_teaching_class_id,
																	   String t_course_teaching_class_homeworktype_id) {
		final String querySQL = "SELECT A.* "
				+ " FROM t_course_teaching_class_homework_score_info A "
				+ " left outer join t_course_teaching_class_homework_baseinfo B on A.t_course_teaching_class_homework_baseinfo_id=B.id "
				+ " where B.t_course_teaching_class_id=? and B.t_course_teaching_class_homework_type_id=?"
				+ " order by pubdate asc";

		return jdbcTemplate.query(querySQL,
				new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id},
				new CourseTeachingClassHomeworkScoreInfoMapper());


	}

	/**
	 * 根据
	 */
	@Override
	public List<CourseTeachingClassHomeworkScoreInfo> getAllOrderByDesc(String t_course_teaching_class_id,
																		String t_course_teaching_class_homeworktype_id) {

		final String querySQL = "SELECT A.* "
				+ " FROM t_course_teaching_class_homework_score_info A "
				+ " left outer join t_course_teaching_class_homework_baseinfo B on A.t_course_teaching_class_homework_baseinfo_id=B.id "
				+ " where B.t_course_teaching_class_id=? and B.t_course_teaching_class_homework_type_id=?"
				+ " order by pubdate desc";


		return jdbcTemplate.query(querySQL,
				new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id},
				new CourseTeachingClassHomeworkScoreInfoMapper());


	}

	/* 增加用户 */
	@Override
	public String add(CourseTeachingClassHomeworkScoreInfo score) {

		return add(score.getCourseTeachingClassHomeworkBaseinfoId(),
				score.getScoreMarkingTypeId(), score.getScoreShowTypeId());

	}

	@Override
	public String add(String t_course_teaching_class_homework_baseinfo_id, String t_score_marking_type_id,
					  String t_score_show_type_id) {

		final String insertSQL = "INSERT INTO "
				+ " t_course_teaching_class_homework_score_info( id,t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id, t_score_show_type_id) "
				+ " VALUES(?,?,?,?)";

		String id = GUID.getGUID();

		Object[] params = new Object[]{id, t_course_teaching_class_homework_baseinfo_id, t_score_marking_type_id,
				t_score_show_type_id};
		int[] types = new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};

		jdbcTemplate.update(insertSQL, params, types);
		return id;
	}

	@Override
	public String add(String t_course_teaching_class_homework_baseinfo_id) {

		String id = GUID.getGUID();

		Object[] params = new Object[]{id, t_course_teaching_class_homework_baseinfo_id};
		int[] types = new int[]{Types.VARCHAR, Types.VARCHAR};

		final String insertSQL = "INSERT INTO "
				+ " t_course_teaching_class_homework_score_info( id,t_course_teaching_class_homework_baseinfo_id,t_score_marking_type_id, t_score_show_type_id) "
				+ " VALUES(?,?,null,null)";

		jdbcTemplate.update(insertSQL, params, types);
		return id;
	}

	@Override
	public void deleteById(String id) {
		Object[] params = new Object[]{id};
		int[] types = new int[]{Types.VARCHAR};

		final String deleteSQL = "DELETE FROM t_course_teaching_class_homework_score WHERE id=?";

		jdbcTemplate.update(deleteSQL, params, types);
	}

	@Override
	public void deleteByCourseTeachingClassHomeworkInfoId(String t_course_teaching_class_homework_baseinfo_id) {
		Object[] params = new Object[]{t_course_teaching_class_homework_baseinfo_id};
		int[] types = new int[]{Types.VARCHAR};

		final String deleteSQL = "DELETE FROM t_course_teaching_class_homework_score_info WHERE t_course_teaching_class_homework_baseinfo_id=?";


		jdbcTemplate.update(deleteSQL, params, types);
	}

	@Override
	public void deleteByScoreMarkingTypeId(String t_score_marking_type_id) {
		Object[] params = new Object[]{t_score_marking_type_id};
		int[] types = new int[]{Types.VARCHAR};

		final String deleteSQL = "DELETE FROM t_course_teaching_class_homework_score_info WHERE t_score_marking_type_id=?";


		jdbcTemplate.update(deleteSQL, params, types);
	}

	@Override
	public void deleteByScoreShowTypeId(String t_score_show_type_id) {
		Object[] params = new Object[]{t_score_show_type_id};
		int[] types = new int[]{Types.VARCHAR};

		final String deleteSQL = "DELETE FROM t_course_teaching_class_homework_score_info WHERE t_score_show_type_id=?";


		jdbcTemplate.update(deleteSQL, params, types);
	}

	@Override
	public long getCount(String t_course_teaching_class_id, String t_course_teaching_class_homeworktype_id) {

		final String countSQL = "SELECT count(*) FROM t_course_teaching_class_homework_score_info where t_course_teaching_class_homework_baseinfo_id in(select id from t_course_teaching_class_homework_baseinfo where t_course_teaching_class_id=? and t_course_teaching_class_homework_type_id=?)";

		return jdbcTemplate.queryForObject(countSQL,
				new Object[]{t_course_teaching_class_id, t_course_teaching_class_homeworktype_id},
				new int[]{Types.VARCHAR, Types.VARCHAR}, Long.class);
	}

	private static final class CourseTeachingClassHomeworkScoreInfoMapper implements RowMapper<CourseTeachingClassHomeworkScoreInfo> {

		@Override
		public CourseTeachingClassHomeworkScoreInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseTeachingClassHomeworkScoreInfo courseTeachingClassHomeworkScoreInfo = new CourseTeachingClassHomeworkScoreInfo();


			courseTeachingClassHomeworkScoreInfo.setId(rs.getString("id"));
			courseTeachingClassHomeworkScoreInfo.setCourseTeachingClassHomeworkBaseinfoId(
					rs.getString("t_course_teaching_class_homework_baseinfo_id"));
			courseTeachingClassHomeworkScoreInfo.setScoreMarkingTypeId(rs.getString("t_score_marking_type_id"));
			courseTeachingClassHomeworkScoreInfo.setScoreShowTypeId(rs.getString("t_score_show_type_id"));

			return courseTeachingClassHomeworkScoreInfo;
		}
	}

}
