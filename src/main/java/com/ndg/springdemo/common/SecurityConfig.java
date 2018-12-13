package com.ndg.springdemo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.ndg.springdemo.security.AccessDenialHandler;
import com.ndg.springdemo.security.*;
import com.ndg.springdemo.security.AuthSuccessHandler;

//@EnableWebSecurity
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	// Authentication : User --> Roles
//	protected void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance()).withUser("user1").password("secret1")
//				.roles("USER").and().withUser("admin1").password("secret1")
//				.roles("USER", "ADMIN");
//	}
//
//	// Authorization : Role -> Access
//	protected void configure(HttpSecurity http) throws Exception {
//		http.httpBasic().and().authorizeRequests().antMatchers("/employee/**")
//				.hasRole("USER").antMatchers("/**").hasRole("ADMIN").and()
//				.csrf().disable().headers().frameOptions().disable();
//	}
//
//}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@ComponentScan("org.baeldung.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDenialHandler accessDeniedHandler;

    @Autowired
    private RestAuthenticationEntry restAuthenticationEntryPoint;

    @Autowired
    private AuthSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

    public SecurityConfig() {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin").password(encoder().encode("adminPass")).roles("ADMIN")
            .and()
            .withUser("user").password(encoder().encode("userPass")).roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//            .and()
//            .exceptionHandling()
//            .accessDeniedHandler(accessDeniedHandler)
//            .authenticationEntryPoint(restAuthenticationEntryPoint)
//            .and()
//            .authorizeRequests()
////            .antMatchers("/api/csrfAttacker*").permitAll()
//            .antMatchers("/employee").permitAll()
//            .antMatchers("/department").authenticated()
////            .antMatchers("/api/async/**").permitAll()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .and()
//            .formLogin()
//            .successHandler(mySuccessHandler)
//            .failureHandler(myFailureHandler)
//            .and()
//            .httpBasic()
//            .and()
//            .logout();
        
        
        http.httpBasic().and().authorizeRequests().antMatchers("/")
		.hasRole("USER").antMatchers("/employee").hasRole("ADMIN").and()
		.csrf().disable().headers().frameOptions().disable();
    }
    
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
