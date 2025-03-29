package com.cimeliarchium.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.cimeliarchium.service.web.LoginFailureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableWebMvc
@EntityScan(basePackages = { 
	"com.cimeliarchium.model.dao", 
	"com.cimeliarchium.model.dto", 
})
@EnableJpaRepositories(basePackages = {
	"com.cimeliarchium.repository.dao"
})
@ComponentScan(basePackages = {
	"com.cimeliarchium.service.dao", 
	"com.cimeliarchium.service.dto", 
	"com.cimeliarchium.service.helper",
	"com.cimeliarchium.service.web"
})
public class AppConfigurer implements WebMvcConfigurer {

	@Autowired
	private LoginFailureService loginFailureService;

	private static final CacheControl CACHE_CTRL_ONE_YEAR = CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic();

	private static final String[] STATIC_RESOURCE_LOCATIONS = {
			"/static/images/map-tiles/",
			"/resources/",
			"/webjars/"
	};

	private static final String[] MP3_RESOURCE_LOCATIONS = {
			"/static/audio/"
	};

	private static final String[] PDF_RESOURCE_LOCATIONS = {
			"/static/pdfs/"
	};

	private static final String[] JPG_RESOURCE_LOCATIONS = {
			"/static/images/locations/"
	};

	private static final String[] PNG_RESOURCE_LOCATIONS = {
			"/static/images/",
			"/static/images/app-overview/",
			"/static/images/map-tiles/",
			"/static/images/patronages/",
			"/static/images/patronages-sm/",
			"/static/images/stars/",
			"/static/images/constellations/"
	};

	private static final String[] SVG_RESOURCE_LOCATIONS = {
			"/static/images/calendar-icons/",
			"/static/images/hour-icons/",
			"/static/images/location-icons/",
			"/static/images/nation-icons/",
			"/static/images/rite-icons/",
			"/static/images/status-icons/",
			"/static/images/star-icons/"
	};

	private static final String[] GIF_RESOURCE_LOCATIONS = {
			"/static/images/location-icons/",
			"/static/images/status-icons/"
	};

	private static final String[] JS_RESOURCE_LOCATIONS = {
			"/static/js/",
			"/static/js/about/",
			"/static/js/admin/",
			"/static/js/donate/",
			"/static/js/login/",
			"/static/js/main/",
			"/static/js/navbar/",
			"/static/js/register/",
			"/static/js/search/"
	};

	/**
	 * Method used to initialize BCryptPasswordEncoder Bean
	 * 
	 * @return BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/**
	 * Method used to setup InternalResourceViewResolver
	 * 
	 * @return InternalResourceViewResolver
	 */
	@Bean
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/jsp/");
		resolver.setSuffix(".jsp");
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity

		// Authorizations
		.authorizeRequests()
		
			// Unauthenticated User Access
			.antMatchers(

					// Favicon
					"/static/favicon.ico",

					// Static Resources
					"/static/**", 
					"/*.mp3",
					"/*.pdf",
					"/*.gif", 
					"/*.jpg",
					"/*.png", 
					"/*.svg", 
					"/*.js", 
					"/*.css", 
					"/*.woff", 
					"/*.woff2",

					// Public Views
					"/about", 
					"/map", 
					"/map-background", 
					"/region", 
					"/register", 
					"/rite", 
					"/captcha",
					"/error")

				.permitAll()

			// Authorized User Access
			.antMatchers("/admin", 
					"/admin/update-users",
					"/admin/update-reqs",
					"/admin/update-flags",
					"/admin/export-diffs",
					"/admin/update-history")
				.hasAuthority("admin")

			// Authenticated Access
			.anyRequest()
				.authenticated()
				.and()

		// Login
		.formLogin()
			.loginPage("/login")
			.failureHandler(loginFailureService)
			.permitAll()
			.and()

		// Logout
		.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.permitAll();
		return httpSecurity.build();
	}

	@Bean
	public ThreadPoolTaskExecutor asyncTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(10);
		taskExecutor.setMaxPoolSize(10); 
		return taskExecutor;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
		PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("errorcode.properties") };
		propertySources.setLocations(resources);
		propertySources.setIgnoreUnresolvablePlaceholders(true);
		return propertySources;
	}

	@Bean
	public Map<String,String> paypalSdkConfig() {
		Map<String,String> configMap = new HashMap<>();
		configMap.put("mode", mode);
		return configMap;
	}

	@Bean
	public ObjectMapper localDateJsonMapper() {
		ObjectMapper mapper = new ObjectMapper(); 
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("classpath:messages");
		return source;
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations(STATIC_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR)
				.resourceChain(true)
				.addResolver(new WebJarsResourceResolver());
		registry.addResourceHandler("/*.ico")
				.addResourceLocations("/static/")
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.mp3")
				.addResourceLocations(MP3_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.pdf")
				.addResourceLocations(PDF_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.jpg")
				.addResourceLocations(JPG_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.png")
				.addResourceLocations(PNG_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.svg")
				.addResourceLocations(SVG_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.gif")
				.addResourceLocations(GIF_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.js")
				.addResourceLocations(JS_RESOURCE_LOCATIONS)
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.css")
				.addResourceLocations("/static/css/")
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.woff")
				.addResourceLocations("/static/fonts/")
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
		registry.addResourceHandler("/*.woff2")
				.addResourceLocations("/static/fonts/")
				.setCacheControl(CACHE_CTRL_ONE_YEAR);
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(-1);
		configurer.setTaskExecutor(asyncTaskExecutor());
	}

	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Value("${spring.datasource.username}")
	private String dbUsername;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${paypal.mode}")
	private String mode;
}