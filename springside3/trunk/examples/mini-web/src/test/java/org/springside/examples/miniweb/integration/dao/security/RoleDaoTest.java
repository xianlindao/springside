package org.springside.examples.miniweb.integration.dao.security;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.miniweb.dao.security.RoleDao;
import org.springside.examples.miniweb.data.SecurityData;
import org.springside.examples.miniweb.entity.security.Role;
import org.springside.modules.test.spring.SpringTxTestCase;

public class RoleDaoTest extends SpringTxTestCase  {
	@Autowired
	private RoleDao entityDao;

	@Test
	public void crudEntity() {
		//new entity and save it. 
		Role entity = SecurityData.getRandomRole();
		entityDao.save(entity);
		flush();
		
		//find entity.
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNotNull(entity);

		//modify entity.
		entity = entityDao.get(entity.getId());
		entity.setName("new value");
		entityDao.save(entity);
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertEquals("new value", entity.getName());
		
		//delete entity.
		entityDao.delete(entity.getId());
		flush();
		entity = entityDao.findUniqueBy("id", entity.getId());
		assertNull(entity);
	}
}

