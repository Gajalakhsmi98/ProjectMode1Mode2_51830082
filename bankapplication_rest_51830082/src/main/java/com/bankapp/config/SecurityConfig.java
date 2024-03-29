package com.bankapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
private UserDetailsService userDetailsService;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		.antMatchers("/api/user/**").hasAnyRole("ADMIN")
		.antMatchers("/api/account/**").hasAnyRole("MGR","ADMIN")
		.antMatchers("/api/transaction/**").hasAnyRole("CLERK","MGR","ADMIN").and().httpBasic();
	}
	@Bean
public BCryptPasswordEncoder  passwordEncoder(){
	return new BCryptPasswordEncoder();
}
}
