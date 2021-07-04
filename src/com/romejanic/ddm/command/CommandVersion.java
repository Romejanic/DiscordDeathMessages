package com.romejanic.ddm.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.romejanic.ddm.DiscordDeathMessages;
import com.romejanic.ddm.update.UpdateChecker;
import com.romejanic.ddm.update.UpdateState;
import com.romejanic.ddm.util.Util;

public class CommandVersion implements CommandExecutor, TabCompleter {

	private DiscordDeathMessages plugin;
	
	public CommandVersion(DiscordDeathMessages plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!Util.testPermission("version", sender)) {
			sender.sendMessage(ChatColor.RED + "Sorry, you don't have permission to do this!");
		} else {
			String version = this.plugin.getDescription().getVersion();
			String authors = Util.join(this.plugin.getDescription().getAuthors(), ", ");
			
			sender.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() +
					"DiscordDeathMessages v" + version + " by " + authors);
			
			// check for updates if user has the ddm.updates permission
			if(Util.testPermission("updates", sender)) {
				sender.sendMessage(ChatColor.YELLOW + ChatColor.ITALIC.toString() + "Checking for updates...");
				UpdateChecker.checkForUpdates(this.plugin, (status) -> {
					if(status.state == UpdateState.UP_TO_DATE) {
						sender.sendMessage(ChatColor.GREEN + "You are running the latest version!");
					} else if(status.state == UpdateState.OUT_OF_DATE) {
						this.plugin.updateNotifier.newVersion = status;
						
						sender.sendMessage(ChatColor.GREEN + "New update available! (v " + status.latestVersion + ")");
						if(status.urgent) {
							sender.sendMessage(ChatColor.RED + "!! URGENT UPDATE! Please update as soon as possible !!");
						}
						sender.sendMessage(ChatColor.GREEN + "Download: " + ChatColor.BOLD + status.latestURL);
						for(String change : status.changelog) {
							sender.sendMessage(ChatColor.GREEN + change);
						}
					} else if(status.state == UpdateState.FAILED) {
						sender.sendMessage(ChatColor.RED + status.error);
					}
				});
			}
		}
		return true;
	}
	
	
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return Arrays.asList();
	}

}