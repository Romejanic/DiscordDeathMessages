package com.romejanic.ddm;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import com.romejanic.ddm.update.UpdateChecker;
import com.romejanic.ddm.command.*;
import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.event.DeathHandler;
import com.romejanic.ddm.event.HatStateListener;
import com.romejanic.ddm.event.PlayerUpdateNotifier;
import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.Const;
import com.romejanic.ddm.util.Metrics;
import com.romejanic.ddm.util.UserConfig;

public class DiscordDeathMessages extends JavaPlugin {
	
	private Config config;
	private UserConfig userConfig;
	private WebhookTasks tasks;

	private DeathHandler deathHandler;
	public PlayerUpdateNotifier updateNotifier;
	private HatStateListener hatState;
	
	@SuppressWarnings("unused")
	private Metrics metrics;
	
	@Override
	public void onEnable() {
		// initialize data
		this.config = new Config(getDataFolder(), getLogger());
		this.userConfig = new UserConfig(getDataFolder(), getLogger());
		this.tasks = new WebhookTasks(this, this.config);
		this.metrics = new Metrics(this, Const.BSTATS_ID);
		this.hatState = new HatStateListener(this, this.userConfig);
		
		// add command executors
		getCommand("ddmset").setExecutor(new CommandSet(this.tasks));
		getCommand("ddmclear").setExecutor(new CommandClear(this.config));
		getCommand("ddmmotto").setExecutor(new CommandMotto(this.config, this.userConfig));
		getCommand("ddmcolor").setExecutor(new CommandColor(this.userConfig));
		getCommand("ddmversion").setExecutor(new CommandVersion(this));
		getCommand("ddmreload").setExecutor(new CommandReload(this.config));
		
		// add event listeners
		this.deathHandler = new DeathHandler(this.config, this.userConfig, this.tasks);
		this.updateNotifier = new PlayerUpdateNotifier(this);
		getServer().getPluginManager().registerEvents(this.deathHandler, this);
		getServer().getPluginManager().registerEvents(this.updateNotifier, this);
		
		getLogger().info("Enabled DiscordDeathMessages!");
		getLogger().info("If you like this plugin feel free to give it a star on GitHub: " + ChatColor.BOLD + "https://github.com/Romejanic/DiscordDeathMessages");
		
		// add the 'enabled' metric
		this.metrics.addCustomChart(new Metrics.SimplePie("enabled", () -> {
			return this.config.getWebhookURL() != null ? "true" : "false";
		}));
		
		// check for updates
		UpdateChecker.checkForUpdates(this, (status) -> {
			if(status.isOutdated()) {
				this.updateNotifier.newVersion = status;
				
				getLogger().info(ChatColor.GREEN + "Found new version (v" + status.latestVersion + ")");
				if(status.urgent) {
					getLogger().info(ChatColor.RED + "!! URGENT UPDATE! Please update as soon as possible !!");
				}
				getLogger().info(ChatColor.GREEN + "Download: " + status.latestURL);
				getLogger().info(ChatColor.GREEN + "Changes:");
				for(String change : status.changelog) {
					getLogger().info(ChatColor.GREEN + " " + change);
				}
			}
		});
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		this.hatState.disable(this);
		this.updateNotifier.stopChecking();
		getLogger().info("Disabled DiscordDeathMessages!");
	}
	
}