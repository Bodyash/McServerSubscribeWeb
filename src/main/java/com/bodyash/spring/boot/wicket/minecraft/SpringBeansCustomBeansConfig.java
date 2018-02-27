package com.bodyash.spring.boot.wicket.minecraft;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bodyash.spring.boot.wicket.minecraft.beans.ExternalPropertiesFileConfig;
import com.bodyash.spring.boot.wicket.minecraft.main.BukkitMain;
import com.bodyash.spring.boot.wicket.minecraft.service.MinecraftUserDetailService;


@Configuration
@ComponentScan(basePackages = {"com.spring.boot.wicket.minecraft"}, excludeFilters={
		  @ComponentScan.Filter(type=org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, value=BukkitMain.class)})
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
	
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            container.setPort(8012);
        });
    }
	
	//Beans for WEB PAGES
	@Bean(name="serverConfig")
	ExternalPropertiesFileConfig serverConfig() {
		if (epfc == null) {
			epfc = new ExternalPropertiesFileConfig();
		}
		return epfc;
	}

}
