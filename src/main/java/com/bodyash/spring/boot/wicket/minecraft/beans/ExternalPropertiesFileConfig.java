package com.bodyash.spring.boot.wicket.minecraft.beans;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.request.resource.IResource;

public class ExternalPropertiesFileConfig {

	private Properties prop;
	private IResource logo;
	private final static String CONFIG_FILE_NAME 	= "serverConfig.properties";
	private final static String LOGO_FILE_NAME 		= "logo.png";

	public ExternalPropertiesFileConfig() {
		//Load Properties file
		loadFileToProperties();
		loadImgFile();
	}

	private void loadImgFile() {
		File imgFile = new File("logo.png");
		BufferedDynamicImageResource imageResource = null;
		if(!imgFile.exists()) {
			try {
				ExportResource(imgFile, LOGO_FILE_NAME);
				imageResource = getImageResource(imgFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			imageResource = getImageResource(imgFile);
		}
		logo = imageResource;
	}

	private BufferedDynamicImageResource getImageResource(File imgFile) {
		BufferedImage logoImg = null;
		try (ByteArrayInputStream bais = new ByteArrayInputStream(IOUtils.toByteArray(new FileInputStream(imgFile)))){
			logoImg = ImageIO.read(bais);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
		resource.setImage(logoImg);
		return resource;
	}

	private void loadFileToProperties() {
		prop = new Properties();
		File propExtFile = new File("serverConfig.properties");
		//export from jar if not exist
		if (!propExtFile.exists()) {
			try {
				ExportResource(propExtFile, CONFIG_FILE_NAME);
				loadProp(propExtFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			loadProp(propExtFile);
		}
	}
	//read data to properties Java Object
	private void loadProp(File propExtFile) {
		try (FileInputStream fis = new FileInputStream(propExtFile);
				InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"))) {
			prop.load(isr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Resource Exporter
	private void ExportResource(File externalFile, String internalFileName) throws Exception {
		try (InputStream in = this.getClass().getResourceAsStream("/" + internalFileName);
				OutputStream out = new FileOutputStream(new File(internalFileName))) {
			IOUtils.copy(in, out);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Properties getProperties() {
		return prop;
	}
	
	public IResource getLogoImage() {
		return logo;
	}

}
