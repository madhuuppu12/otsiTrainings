package org.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private PasswordEncoder paaswordEncoder;

	@Autowired
	private UserDetailsService userDetails;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/rahul").hasRole("CCO")

				.antMatchers("/sanjan").hasRole("RTO").antMatchers("/postData").permitAll().antMatchers("/**")
				.permitAll().and().formLogin();

		// http.userDetailsService(userDetails);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * auth.inMemoryAuthentication().withUser("RAHUL").password(paaswordEncoder.
		 * encode("RAHUL")).roles("DEV").and()
		 * .withUser("SANJAN").password(paaswordEncoder.encode("SANJAN")).roles("CCO");
		 */

		auth.userDetailsService(userDetails);

		System.out.println("data");

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		/*
		 * return new PasswordEncoder() {
		 * 
		 * @Override public String encode(CharSequence rawPassword) { return
		 * rawPassword.toString(); }
		 * 
		 * @Override public boolean matches(CharSequence rawPassword, String
		 * encodedPassword) { return rawPassword.toString().equals(encodedPassword); }
		 * };
		 */
		return new BCryptPasswordEncoder();
	}

}
