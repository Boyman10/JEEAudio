package com.subtitlor.forms;

import javax.servlet.http.HttpServletRequest;

public class Connection {
	private String res;

	
	public void verifyId(HttpServletRequest req) {
		String login = req.getParameter("login");
		String pass = req.getParameter("pass");
		
		if(pass.equals(login + "123")) {
			res = "All good !";
		} else {
			res = "incorrect !";
		}
	}
	
	public String getRes() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}


}