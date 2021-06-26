package com.romejanic.ddm;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.romejanic.ddm.command.CommandClear;
import com.romejanic.ddm.command.CommandSet;
import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.event.DeathHandler;
import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.Const;
import com.romejanic.ddm.util.Metrics;

public class DiscordDeathMessages extends JavaPlugin {
	
	private Config config;
	private WebhookTasks tasks;

	private DeathHandler deathHandler;
	
	@SuppressWarnings("unused")
	private Metrics metrics;
	
	@Override
	public void onEnable() {
		// initialize data
		this.config = new Config(getDataFolder(), getLogger());
		this.tasks = new WebhookTasks(this, this.config);
		this.metrics = new Metrics(this, Const.BSTATS_ID);
		
		// add command executors
		getCommand("ddmset").setExecutor(new CommandSet(this.tasks));
		getCommand("ddmclear").setExecutor(new CommandClear(this.config));
		
		// add event listener
		this.deathHandler = new DeathHandler(this.config, this.tasks);
		getServer().getPluginManager().registerEvents(this.deathHandler, this);
		
		getLogger().info("Enabled DiscordDeathMessages!");
		getLogger().info("If you like this plugin feel free to give it a star on GitHub: " + ChatColor.BOLD + "https://github.com/Romejanic/DiscordDeathMessages");
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}