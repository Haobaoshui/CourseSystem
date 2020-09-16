package com.haobaoshui.course.service.authority;

import com.haobaoshui.course.model.authority.GroupRole;
import com.haobaoshui.course.model.authority.GroupRoleViewData;
import com.haobaoshui.course.model.Page;

import java.util.List;


public interface GroupRoleService {


	void add(String t_group_id, String t_role_id);

	void deleteById(String id);

	/**
	 * 删除组
	 */
	void deleteByGroupId(String t_group_id);

	GroupRole getById(String id);

	GroupRoleViewData getGroupRoleViewDataById(String id);

	List<GroupRole> getListByGroupId(String t_group_id);

	List<GroupRole> getListByRoleId(String t_role_id);

	Page<GroupRoleViewData> getPageByGroupId(String t_group_id, int pageNo, int pageSize);
}
