package com.deepak.expense_tracker_api.config;

import java.util.function.Function;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deepak.expense_tracker_api.security.CustomUserDetailsService;
import com.deepak.expense_tracker_api.security.Jwtrequestfilter;

@Configuration
public class Websecurityconfig {
	
	@Autowired
	private CustomUserDetailsService userdetailsserviice;
	
	@Bean
	public Jwtrequestfilter authenticationJwtTokenFilter()
	{
		return new Jwtrequestfilter();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .requestMatchers("/login","register").permitAll()
        .anyRequest().authenticated().and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
        http.httpBasic(Customizer.withDefaults()).userDetailsService(userdetailsserviice);
		return http.build();
    }
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userdetailsserviice);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
	
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 
	 @Bean
	 public DaoAuthenticationProvider authentiationProvider()
	 {
		 DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		 authProvider.setUserDetailsService(userdetailsserviice);
		 authProvider.setPasswordEncoder(passwordEncoder());
		 return authProvider;
		 
	 }
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }
}
