package com.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.auth.services.AuthenticationProviderService;

/*
 * @Author : lkb 
 * In authentication service we are using two types of encryption services one is Bcrypt and
 * other is SCrypt 
 * Why BCrypt and SCrypt 
 * There is this concept called adding salts to the encryption Plz refer for more 
 *
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

	private final AuthenticationProviderService authenticationProvider;

	public ProjectConfig(AuthenticationProviderService authenticationProvider) {
		this.authenticationProvider = authenticationProvider;

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/auth/authenticate", "/auth/createUser", "/auth/checkUsername", "/auth/validateToken")
				.permitAll().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

}
