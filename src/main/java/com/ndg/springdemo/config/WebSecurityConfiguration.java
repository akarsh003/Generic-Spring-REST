package com.ndg.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import com.ndg.springdemo.CustomUserDetailsService;
import com.ndg.springdemo.security.IAuthenticationFacade;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private UserDetailsService userDetailsService;
	private PreAuthenticatedAuthenticationProvider preAuthenticatedProvider;
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	public WebSecurityConfiguration() {
		super();

		userDetailsService = new CustomUserDetailsService();
		UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(
				userDetailsService);

		preAuthenticatedProvider = new PreAuthenticatedAuthenticationProvider();
		preAuthenticatedProvider.setPreAuthenticatedUserDetailsService(wrapper);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		// @formatter:off
		authenticationManagerBuilder //
				.authenticationProvider(preAuthenticatedProvider);
		// @formatter:on
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		RequestHeaderAuthenticationFilter siteMinderFilter = new RequestHeaderAuthenticationFilter();
		siteMinderFilter.setAuthenticationManager(authenticationManager());

		// @formatter:off
		httpSecurity //
				.cors() //
				.and() //
				.addFilter(siteMinderFilter) //
				.authorizeRequests() //
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		// @formatter:on
	}
}
