package org.springside.examples.miniservice.functional.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springside.examples.miniservice.data.AccountData;
import org.springside.examples.miniservice.entity.account.User;
import org.springside.examples.miniservice.functional.BaseFunctionalTestCase;
import org.springside.examples.miniservice.ws.UserWebService;
import org.springside.examples.miniservice.ws.WsConstants;
import org.springside.examples.miniservice.ws.dto.RoleDTO;
import org.springside.examples.miniservice.ws.dto.UserDTO;
import org.springside.examples.miniservice.ws.result.AuthUserResult;
import org.springside.examples.miniservice.ws.result.CreateUserResult;
import org.springside.examples.miniservice.ws.result.GetAllUserResult;
import org.springside.examples.miniservice.ws.result.WSResult;

/**
 * UserService Web服务的功能测试, 测试主要的接口调用.
 * 
 * 一般使用在Spring applicaitonContext.xml中用<jaxws:client/>创建的Client, 也可以用JAXWS的API自行创建.
 * 
 * @author calvin
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@ContextConfiguration(locations = { "/applicationContext-ws-client.xml" })
public class UserWebServiceTest extends BaseFunctionalTestCase {

	@Resource
	private UserWebService userWebService;

	/**
	 * 测试认证用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void authUser() {
		AuthUserResult result = userWebService.authUser("admin", "admin");
		assertEquals(true, result.isValid());
	}

	/**
	 * 测试创建用户,在Spring applicaitonContext.xml中用<jaxws:client/>创建Client.
	 */
	@Test
	public void createUser() {
		User user = AccountData.getRandomUser();

		UserDTO userDTO = new UserDTO();
		userDTO.setLoginName(user.getLoginName());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());

		RoleDTO roleDTO = new RoleDTO();
		roleDTO.setId(1L);
		userDTO.getRoleList().add(roleDTO);

		CreateUserResult result = userWebService.createUser(userDTO);

		assertEquals(WSResult.SUCCESS, result.getCode());
		assertNotNull(result.getUserId());
	}

	/**
	 * 测试获取全部用户,使用JAXWS的API自行创建Client.
	 */
	@Test
	public void getAllUser() throws MalformedURLException {
		URL wsdlURL = new URL(BASE_URL + "/ws/userservice?wsdl");
		QName UserServiceName = new QName(WsConstants.NS, "UserService");
		Service service = Service.create(wsdlURL, UserServiceName);
		UserWebService userWebService = service.getPort(UserWebService.class);

		GetAllUserResult result = userWebService.getAllUser();

		assertTrue(result.getUserList().size() > 0);
		UserDTO adminUser = result.getUserList().get(0);
		assertEquals("admin", adminUser.getLoginName());
		assertEquals(2, adminUser.getRoleList().size());
	}
}
