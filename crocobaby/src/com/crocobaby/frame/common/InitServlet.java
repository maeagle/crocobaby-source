package com.crocobaby.frame.common;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.crocobaby.frame.db.DataBaseManager;
import com.crocobaby.frame.log.LogManager;

@WebServlet(name = "InitServer", urlPatterns = "/InitServer", loadOnStartup = 0)
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			LogManager.initLog();
			DataBaseManager.initDataBase();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
