package com.romejanic.ddm.event;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.romejanic.ddm.update.UpdateStatus;
import com.romejanic.ddm.util.Util;

public class PlayerUpdateNotifier implements Listener {

	public UpdateStatus newVersion = null;
	
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
	
}