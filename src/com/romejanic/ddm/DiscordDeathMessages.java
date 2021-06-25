package com.romejanic.ddm;

import org.bukkit.plugin.java.JavaPlugin;

public class DiscordDeathMessages extends JavaPlugin {
	
	public static final String VERSION = "1.0.0";

	@Override
	public void onEnable() {
		getLogger().info("Enabled DiscordDeathMessages!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}