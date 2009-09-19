#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.unit.service.security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.easymock.classextension.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ${package}.data.SecurityEntityData;
import ${package}.entity.security.Authority;
import ${package}.entity.security.Resource;
import ${package}.service.security.ResourceDetailsServiceImpl;
import ${package}.service.security.SecurityEntityManager;
import org.springside.modules.utils.ReflectionUtils;

public class ResourceDetailsServiceTest extends Assert {

	private ResourceDetailsServiceImpl resourceDetailService = new ResourceDetailsServiceImpl();
	private SecurityEntityManager securityEntityManager = null;

	@Before
	public void setUp() {
		securityEntityManager = EasyMock.createNiceMock(SecurityEntityManager.class);
		ReflectionUtils.setFieldValue(resourceDetailService, "securityEntityManager", securityEntityManager);
	}

	@After
	public void tearDown() {
		EasyMock.verify(securityEntityManager);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getRequestMap() throws Exception {
		//准备数据
		List<Resource> resourceList = new ArrayList<Resource>();

		Authority a1 = SecurityEntityData.getRandomAuthority();
		Authority a2 = SecurityEntityData.getRandomAuthority();

		Resource r1 = SecurityEntityData.getRandomResource();
		r1.getAuthorityList().add(a1);
		resourceList.add(r1);

		Resource r2 = SecurityEntityData.getRandomResource();
		r2.getAuthorityList().add(a1);
		resourceList.add(r2);

		Resource r3 = SecurityEntityData.getRandomResource();
		r3.getAuthorityList().add(a1);
		r3.getAuthorityList().add(a2);
		resourceList.add(r3);
		//录制脚本
		EasyMock.expect(securityEntityManager.getUrlResourceWithAuthorities()).andReturn(resourceList);
		EasyMock.replay(securityEntityManager);

		//验证结果 
		LinkedHashMap<String, String> requestMap = resourceDetailService.getRequestMap();
		assertEquals(3, requestMap.size());
		Object[] requests = requestMap.entrySet().toArray();
		
		assertEquals(r1.getValue(), ((Entry<String, String>) requests[0]).getKey());
		assertEquals(a1.getName(), ((Entry<String, String>) requests[0]).getValue());

		assertEquals(r3.getValue(), ((Entry<String, String>) requests[2]).getKey());
		assertEquals(a1.getName() + "," + a2.getName(), ((Entry<String, String>) requests[2]).getValue());
	}
}