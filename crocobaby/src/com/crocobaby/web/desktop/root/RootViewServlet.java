package com.crocobaby.web.desktop.root;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = { "/desktop/*" }, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = RootUI.class)
public class RootViewServlet extends VaadinServlet {

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
	}
}
