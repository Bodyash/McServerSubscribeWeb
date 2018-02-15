package com.bodyash.spring.boot.wicket.minecraft;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bodyash.spring.boot.wicket.minecraft.beans.ExternalPropertiesFileConfig;
import com.bodyash.spring.boot.wicket.minecraft.service.MinecraftUserDetailService;


@Configuration()
@ComponentScan("com.spring.boot.wicket.minecraft")
public class SpringBeansCustomBeansConfig extends WebMvcConfigurerAdapter{
	
	private ExternalPropertiesFileConfig epfc;
	
	@Bean
	AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(accountDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
	}
	
	@Bean(name="CustomAccountDetailService")
	public UserDetailsService accountDetailService() {
		return new MinecraftUserDetailService();
	}

	@Bean
	Object passwordEncoder() {
		return new Md5PasswordEncoder();
	}
	
	
	//Beans for WEB PAGES
	@Bean(name="serverConfig")
	Properties serverConfig() {
		if (epfc == null) {
			epfc = new ExternalPropertiesFileConfig();
		}
		return epfc.getProperties();
	}

}
