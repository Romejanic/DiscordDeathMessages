package com.romejanic.ddm.event;


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
	private final int checkTask;
	
	public PlayerUpdateNotifier(Plugin plugin) {
		this.plugin = plugin;
		this.checkTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, 0L, 60L * 60L * 20L);
	}
	
	@Override
	public void run() {
		UpdateChecker.checkForUpdates(this.plugin, (status) -> {
			newVersion = status;
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