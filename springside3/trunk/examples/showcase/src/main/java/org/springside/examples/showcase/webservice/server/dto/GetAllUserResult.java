package org.springside.examples.showcase.webservice.server.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.springside.examples.showcase.webservice.server.WebServiceSupport;

/**
 * GetAllUser方法的返回结果类型.
 * 
 * @author calvin
 */
@XmlType(name = "GetAllUserResult", namespace = WebServiceSupport.NS)
public class GetAllUserResult extends WSResult {

	private List<UserDTO> userList;

	@XmlElementWrapper(name = "userList")
	@XmlElement(name = "user")
	public List<UserDTO> getUserList() {
		return userList;
	}

	public void setUserList(List<UserDTO> userList) {
		this.userList = userList;
	}
}