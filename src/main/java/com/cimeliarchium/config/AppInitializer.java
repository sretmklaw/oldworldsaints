package com.cimeliarchium.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class AppInitializer implements WebApplicationInitializer {

	/**
	 * Method used to initialize required web application resources on startup
	 * 
	 * @param servletContext
	 *            the ServletContext
	 * @throws ServletException
	 *             the exception
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {

		// Apply custom Web Configuration to the ServletContext for web security and resource handling
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("com.cimeliarchium.config.AppConfigurer");
		context.setServletContext(servletContext);

		// Apply DispatcherServlet to the ServletContext
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(context));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
}