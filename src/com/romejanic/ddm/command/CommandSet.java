package com.romejanic.ddm.command;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.romejanic.ddm.util.Util;

public class CommandSet implements CommandExecutor, TabCompleter {
	
	private final WebhookTasks tasks;
	
	public CommandSet(WebhookTasks tasks) {
		this.tasks = tasks;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!Util.testPermission("set", sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
			return true;
		}
		if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /ddmset <url>");
		} else {
			this.tasks.setWebhookURLAsync(args[0], sender);
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(args.length == 1) {
			return Arrays.asList("<url>");
		} else {
			return null;
		}
	}
	
}