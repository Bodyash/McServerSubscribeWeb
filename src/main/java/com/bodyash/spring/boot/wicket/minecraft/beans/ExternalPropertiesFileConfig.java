package com.bodyash.spring.boot.wicket.minecraft.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.scan.JarFileUrlJar;
import org.apache.wicket.util.file.Files;

public class ExternalPropertiesFileConfig {

	Properties prop;
	private final static String CONFIG_FILE_NAME = "serverConfig.properties";

	public ExternalPropertiesFileConfig() {
		prop = new Properties();
		File propExtFile = new File("serverConfig.properties");
		if (!propExtFile.exists()) {
			try {
				ExportResource(propExtFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void ExportResource(File externalFile) throws Exception {
		InputStream in = this.getClass().getResourceAsStream("/"+CONFIG_FILE_NAME);
		IOUtils.copy(in, new FileOutputStream(new File(CONFIG_FILE_NAME))); 
	}

}
