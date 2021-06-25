package com.romejanic.ddm;

import org.bukkit.plugin.java.JavaPlugin;

import com.romejanic.ddm.command.CommandClear;
import com.romejanic.ddm.command.CommandSet;
import com.romejanic.ddm.util.Config;

public class DiscordDeathMessages extends JavaPlugin {
	
	private Config config;

	@Override
	public void onEnable() {
		this.config = new Config(getDataFolder(), getLogger());
		getCommand("ddmset").setExecutor(new CommandSet());
		getCommand("ddmclear").setExecutor(new CommandClear());
		getLogger().info("Enabled DiscordDeathMessages!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}