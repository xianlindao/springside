package org.springside.examples.miniweb.dao.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.miniweb.dao.account.GroupDao;
import org.springside.examples.miniweb.dao.account.UserDao;
import org.springside.examples.miniweb.data.AccountData;
import org.springside.examples.miniweb.entity.account.Group;
import org.springside.examples.miniweb.entity.account.User;
import org.springside.modules.test.data.Fixtures;
import org.springside.modules.test.spring.SpringTxTestCase;

/**
 * GroupDao的测试用例, 测试ORM映射及特殊的DAO操作.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class GroupDaoTest extends SpringTxTestCase {

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private UserDao userDao;

	/**
	 * 载入测试数据.
	 */
	@Before
	public void reloadSampleData() throws Exception {
		Fixtures.reloadAllTable(dataSource, "/data/sample-data.xml");
	}

	/**
	 * 测试删除权限组时删除用户-权限组的中间表.
	 */
	@Test
	public void deleteGroup() {
		//新增测试权限组并与admin用户绑定.
		Group group = AccountData.getRandomGroup();
		groupDao.save(group);

		User user = userDao.get(1L);
		user.getGroupList().add(group);
		userDao.save(user);
		userDao.flush();

		int oldJoinTableCount = countRowsInTable("ACCT_USER_GROUP");
		int oldUserTableCount = countRowsInTable("ACCT_USER");

		//删除用户权限组, 中间表将减少1条记录,而用户表应该不受影响.
		groupDao.delete(group.getId());
		groupDao.flush();

		int newJoinTableCount = countRowsInTable("ACCT_USER_GROUP");
		int newUserTableCount = countRowsInTable("ACCT_USER");
		assertEquals(1, oldJoinTableCount - newJoinTableCount);
		assertEquals(0, oldUserTableCount - newUserTableCount);
	}

	@Test
	public void crudEntityWithGroup() {
		//新建并保存带权限组的用户
		Group group = AccountData.getRandomGroupWithPermissions();
		groupDao.save(group);
		//强制执行sql语句
		groupDao.flush();
		//获取用户
		group = groupDao.findUniqueBy("id", group.getId());
		assertTrue(group.getPermissionList().size() > 0);
	}
}
