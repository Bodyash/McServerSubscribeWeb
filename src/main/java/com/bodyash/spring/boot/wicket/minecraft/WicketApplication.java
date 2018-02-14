package com.bodyash.spring.boot.wicket.minecraft;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.ResourceSettings;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;

import com.bodyash.spring.boot.wicket.minecraft.beans.ExternalPropertiesFileConfig;
import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;

@SpringBootApplication
public class WicketApplication{

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder()
			.sources(WicketApplication.class)
			.run(args);
		new ExternalPropertiesFileConfig();
	}

}
