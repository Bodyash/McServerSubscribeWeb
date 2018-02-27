package com.bodyash.spring.boot.wicket.minecraft.web.pages.home;

import org.apache.wicket.model.Model;
import org.wicketstuff.annotation.mount.MountPath;

import com.bodyash.spring.boot.wicket.minecraft.web.pages.BasePage;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;

@MountPath("/home")
@WicketHomePage
public class HomePage extends BasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public HomePage() {
		getTitle().setDefaultModel(Model.of("Home Page"));
	}




}
