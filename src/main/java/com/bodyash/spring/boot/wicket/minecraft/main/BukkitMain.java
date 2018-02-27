package com.bodyash.spring.boot.wicket.minecraft.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.bodyash.spring.boot.wicket.minecraft.WicketApplicationMain;


public class BukkitMain extends JavaPlugin{
	
	@Override
	public void onEnable() {
		startWebApplication();
		super.onEnable();
	}

	private void startWebApplication() {
		new SpringApplicationBuilder()
		.sources(WicketApplicationMain.class)
		.run();
		
	}
	
	public static void main(String args[]) {
		System.out.println("Please, use it like Spigot plugin.");
	}

}
