package com.demo.library.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 

@SuppressWarnings("deprecation")
@EnableWebSecurity

public class SpringSecurityConfig
    extends WebSecurityConfigurerAdapter {
 

    @Override
    protected void
    configure(AuthenticationManagerBuilder auth)
        throws Exception
    {
    	
    	auth.inMemoryAuthentication()
    	.withUser("admin")
    	.password("admin")
        .roles("admin_role")
    	.and()
    	.withUser("user")
    	.password("user")
        .roles("user_role");
    }
 

   @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
 

   @Override
    protected void configure(HttpSecurity http)
        throws Exception
    {
 
	   http.
	   httpBasic()
		   .and()
		   .authorizeRequests()
		   .antMatchers(HttpMethod.DELETE,"/**")
		   .hasRole("admin_role")
		   .antMatchers(HttpMethod.POST,"/**")
		   .hasAnyRole("admin_role","user_role")
		   .antMatchers(HttpMethod.PUT,"/updateBookTitle")
		   .hasRole("user_role")
		   .antMatchers(HttpMethod.PUT,"/updateBookAuthors")
		   .hasRole("admin_role")
		   .and()
		   .csrf()
		   .disable()
		   .formLogin()
		   .disable();
	   
    }
}

