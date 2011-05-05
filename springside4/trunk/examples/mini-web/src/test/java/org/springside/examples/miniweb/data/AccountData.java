package org.springside.examples.miniweb.data;

import java.util.List;

import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.modules.test.utils.DataUtils;

import com.google.common.collect.Lists;

/**
 * Account相关实体测试数据生成.
 * 
 * @author calvin
 */
public class AccountData {

	public static final String DEFAULT_PASSWORD = "123456";

	private static List<Group> defaultGroupList = null;

	private static List<String> defaultPermissionList = null;

	public static User getRandomUser() {
		String userName = DataUtils.randomName("User");

		User user = new User();
		user.setLoginName(userName);
		user.setName(userName);
		user.setPassword(DEFAULT_PASSWORD);
		user.setEmail(userName + "@springside.org.cn");

		return user;
	}

	public static User getRandomUserWithGroup() {
		User user = getRandomUser();
		user.getGroupList().add(getRandomDefaultGroup());

		return user;
	}

	public static Group getRandomGroup() {
		Group group = new Group();
		group.setName(DataUtils.randomName("Group"));

		return group;
	}

	public static Group getRandomRoleWithAuthority() {
		Group group = getRandomGroup();
		group.getAuthorityList().addAll(getRandomDefaultAuthorityList());
		return group;
	}

	public static List<Group> getDefaultRoleList() {
		if (defaultGroupList == null) {
			defaultGroupList = Lists.newArrayList();
			defaultGroupList.add(new Group(1L, "管理员"));
			defaultGroupList.add(new Group(2L, "用户"));
		}
		return defaultGroupList;
	}

	public static Group getRandomDefaultGroup() {
		return DataUtils.randomOne(getDefaultRoleList());
	}

	public static Authority getRandomAuthority() {
		String authName = DataUtils.randomName("Authority");

		Authority authority = new Authority();
		authority.setName(authName);

		return authority;
	}

	public static List<Authority> getDefaultAuthorityList() {
		if (defaultPermissionList == null) {
			defaultPermissionList = Lists.newArrayList();
			defaultPermissionList.add(new Authority(1L, "浏览用户"));
			defaultPermissionList.add(new Authority(2L, "修改用户"));
			defaultPermissionList.add(new Authority(3L, "浏览角色"));
			defaultPermissionList.add(new Authority(4L, "修改角色"));
		}
		return defaultPermissionList;
	}

	public static List<Authority> getRandomDefaultAuthorityList() {
		return DataUtils.randomSome(getDefaultAuthorityList());
	}
}
