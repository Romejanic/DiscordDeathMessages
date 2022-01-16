package com.romejanic.ddm.event;


import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.romejanic.ddm.update.UpdateChecker;
import com.romejanic.ddm.update.UpdateStatus;
import com.romejanic.ddm.util.Util;

public class PlayerUpdateNotifier implements Runnable, Listener {

	public UpdateStatus newVersion = null;
	
	private final Plugin plugin;
	private final Logger logger;
	private final int checkTask;
	
	public PlayerUpdateNotifier(Plugin plugin) {
		this.plugin = plugin;
		this.logger = plugin.getLogger();
		this.checkTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 60L * 60L * 20L);
	}
	
	@Override
	public void run() {
		UpdateChecker.checkForUpdates(this.plugin, (status) -> {
			boolean shouldPrint = newVersion == null || !newVersion.latestVersion.equals(status.latestVersion);
			newVersion = status;
			
			// print a message if the version is outdated
			// (and it hasnt been printed already)
			if(shouldPrint && status.isOutdated()) {
				this.logger.info(ChatColor.GREEN + "Found new version (v" + status.latestVersion + ")");
				if(status.urgent) {
					this.logger.info(ChatColor.RED + "!! URGENT UPDATE! Please update as soon as possible !!");
				}
				this.logger.info(ChatColor.GREEN + "Download: " + status.latestURL);
				this.logger.info(ChatColor.GREEN + "Changes:");
				for(String change : status.changelog) {
					this.logger.info(ChatColor.GREEN + " " + change);
				}
			}
		});
	}
	
	@EventHandler
	public void playerJoined(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(newVersion != null && Util.testPermission("updates", player)) {
			player.sendMessage(ChatColor.GREEN + "[DDM] " + ChatColor.BOLD + "New version available! (v " + newVersion.latestVersion + ")");
			if(newVersion.urgent) {
				player.sendMessage(ChatColor.RED + "[DDM] !! URGENT UPDATE! Please update as soon as possible !!");
			}
			player.sendMessage(ChatColor.GREEN + "[DDM] Type " + ChatColor.BOLD + "/ddmversion" + ChatColor.GREEN + " for changes and download link");
		}
	}
	
	public void stopChecking() {
		Bukkit.getScheduler().cancelTask(this.checkTask);
	}
	
}