package com.romejanic.ddm.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.Util;

public class CommandReload implements CommandExecutor {

	private final Config config;
	
	public CommandReload(Config config) {
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!Util.testPermission("reload", sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}
		if(this.config.load()) {
			sender.sendMessage(ChatColor.GREEN + "Reloaded config successfully!");
		} else {
			sender.sendMessage(ChatColor.RED + "Could not reload config! Check the console for more details.");
		}
		return true;
	}

}
