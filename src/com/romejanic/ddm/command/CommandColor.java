package com.romejanic.ddm.command;

import java.awt.Color;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.romejanic.ddm.util.UserConfig;

public class CommandColor implements CommandExecutor {
	
	private final UserConfig config;
	
	public CommandColor(UserConfig config) {
		this.config = config;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sorry, only players can use this command!");
		} else if(args.length != 1) {
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

}