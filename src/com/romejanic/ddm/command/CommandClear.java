package com.romejanic.ddm.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.Util;

public class CommandClear implements CommandExecutor {

	private final Config config;
	
	public CommandClear(Config config) {
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!Util.testPermission("clear", sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}
		this.config.setWebhookURL(null);
		Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Cleared Webhook URL!");
		Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Death messages will no longer be sent to Discord.");
		return true;
	}

}
