package com.romejanic.ddm;

import org.bukkit.plugin.java.JavaPlugin;

public class DiscordDeathMessages extends JavaPlugin {

	@Override
	public void onEnable() {
		getLogger().info("Enabled DiscordDeathMessages!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}