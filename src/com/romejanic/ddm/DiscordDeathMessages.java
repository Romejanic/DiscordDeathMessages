package com.romejanic.ddm;

import org.bukkit.plugin.java.JavaPlugin;

import com.romejanic.ddm.command.CommandClear;
import com.romejanic.ddm.command.CommandSet;
import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.util.Config;

public class DiscordDeathMessages extends JavaPlugin {
	
	private Config config;
	private WebhookTasks tasks;

	@Override
	public void onEnable() {
		// initialize data
		this.config = new Config(getDataFolder(), getLogger());
		this.tasks = new WebhookTasks(this, this.config);
		
		// add command executors
		getCommand("ddmset").setExecutor(new CommandSet(this.tasks));
		getCommand("ddmclear").setExecutor(new CommandClear());
		
		getLogger().info("Enabled DiscordDeathMessages!");
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}