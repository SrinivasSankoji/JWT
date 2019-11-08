package com.chary.bhaumik.jwt.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chary.bhaumik.jwt.filter.JwtTokenFilterConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	JwtTokenFilterConfigurer jwtTokenFilterConfigurer;
	
	@Override
	protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception 
	{
		authenticationManagerBuilder.userDetailsService(userDetailsService);
	}
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception 
	{
		httpSecurity.csrf().disable();
		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.authorizeRequests()
		.antMatchers("/users/signin").permitAll()
		.antMatchers("/h2-console/**/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.apply(jwtTokenFilterConfigurer);
	}

	@Override
	public void configure(WebSecurity web) throws Exception 
	{
		web.ignoring().antMatchers("/v2/api-docs")
		.antMatchers("/swagger-resources/**")
		.antMatchers("/swagger-ui.html")
		.antMatchers("/configuration/**")
		.antMatchers("/webjars/**")
		.antMatchers("/public")
		.and().ignoring().antMatchers("/h2-console/**/**");
		;
	}

	/**@Bean
	public PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder(12);
	}**/
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
}
