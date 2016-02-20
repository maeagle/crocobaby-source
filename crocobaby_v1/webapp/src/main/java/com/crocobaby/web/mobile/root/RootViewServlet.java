package com.crocobaby.web.mobile.root;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.touchkit.server.TouchKitServlet;
import com.vaadin.addon.touchkit.settings.TouchKitSettings;
import com.vaadin.annotations.VaadinServletConfiguration;

@WebServlet(value = { "/VAADIN/*", "/mobile/*" }, asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = RootUI.class)
public class RootViewServlet extends TouchKitServlet {

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		TouchKitSettings setting = getTouchKitSettings();
		setting.getWebAppSettings().setWebAppCapable(true);
		setting.getApplicationCacheSettings().setCacheManifestEnabled(true);
	}
}
