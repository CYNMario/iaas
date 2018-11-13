package com.han.iaas.dao;

import javax.servlet.http.HttpSession;

public class SessionEntity {
	private String userName;
	private HttpSession session;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	@Override
	public String toString() {
		return "SessionEntity [userName=" + userName + "]";
	}
	
}
