package com.cmancode.clientes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmancode.clientes.app.outh.handler.LoginSucessHandler;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
		
	@Autowired
	private LoginSucessHandler seccessHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/","/clientes","/css/**","/img/**","/js/**", "/uploads/**").permitAll() 
		/*.antMatchers("/cliente").hasAnyRole("ADMIN")
		.antMatchers("/cliente/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")
		.antMatchers("/detalle/**").hasAnyRole("ADMIN")
		.antMatchers("/detalle/**").hasAnyRole("USER")*/
		.anyRequest().authenticated()
		.and()
		.formLogin()
			.successHandler(seccessHandler)
			.loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and().exceptionHandling().accessDeniedPage("/error_403");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
		
		PasswordEncoder encoder = this.passwordEncoder;
		UserBuilder user = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
		.withUser(user.username("manti").password("123456").roles("ADMIN", "USER"))
		.withUser(user.username("cmancode").password("123456").roles("USER"));
		
	}

}
