package com.bodyash.spring.boot.wicket.minecraft.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

public class ExternalPropertiesFileConfig {

	private Properties prop;
	private final static String CONFIG_FILE_NAME = "serverConfig.properties";

	public ExternalPropertiesFileConfig() {
		prop = new Properties();
		File propExtFile = new File("serverConfig.properties");
		if (!propExtFile.exists()) {
			try {
				ExportResource(propExtFile);
				loadFileToProperties(prop, propExtFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			loadFileToProperties(prop, propExtFile);
		}
	}

	private void loadFileToProperties(Properties prop, File propFile) {
		if (propFile.canRead()) {
			try (FileInputStream fis = new FileInputStream(propFile)){
				prop.load(new InputStreamReader(fis, Charset.forName("UTF-8")));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private void ExportResource(File externalFile) throws Exception {
		try (InputStream in = this.getClass().getResourceAsStream("/"+CONFIG_FILE_NAME); OutputStream out = new FileOutputStream(new File(CONFIG_FILE_NAME))){
			IOUtils.copy(in, out); 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Properties getProperties() {
		return prop;
	}

}
