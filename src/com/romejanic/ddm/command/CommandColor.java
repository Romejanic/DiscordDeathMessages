package com.romejanic.ddm.command;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.Util;

public class CommandColor implements CommandExecutor, TabCompleter {
	
	private final UserConfig config;
	
	public CommandColor(UserConfig config) {
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sorry, only players can use this command!");
		} else if(!Util.testPermission("color", sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		} if(args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /" + label + " <hex color|clear>");
			sender.sendMessage(ChatColor.RED + "Example: /" + label + " #ff23e1");
		} else {
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("clear")) {
				this.config.getData(player).color = null;
				this.config.save();
				sender.sendMessage(ChatColor.GREEN + "Cleared your accent color.");
			} else {
				String colorCode = args[0].toLowerCase();
				Color color = safeParseColor(colorCode);
				if(!colorCode.startsWith("#") || colorCode.length() != 7 || color == null) {
					sender.sendMessage(ChatColor.RED + "The color code you entered was invalid!");
					sender.sendMessage(ChatColor.RED + "Example: /" + label + " #ff23e1");
				} else {
					this.config.getData(player).color = color;
					this.config.save();
					sender.sendMessage(ChatColor.GREEN + "Set your accent color to " + colorCode + "!");
				}
			}
		}
		return true;
	}
	
	private Color safeParseColor(String in) {
		try {
			return Color.decode(in);
		} catch(NumberFormatException e) {
			return null;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		if(args.length == 1) {
			if(args[0].isEmpty()) {
				list.add("#<hex code>");
				list.add("clear");
			} else if("clear".startsWith(args[0])) {
				list.add("clear");
			} else if(args[0].startsWith("#")) {
				list.add("#<hex code>");
			}
		}
		return list;
	}

}