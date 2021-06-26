package com.romejanic.ddm.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.Util;

public class CommandMotto implements CommandExecutor {

	private final UserConfig config;

	public CommandMotto(UserConfig config) {
		this.config = config;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sorry, only players can use this command!");
		} else if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /ddmmotto <message|clear>");
		} else {
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("clear")) {
				this.config.getData(player).motto = null;
				this.config.save();
				sender.sendMessage(ChatColor.GREEN + "Cleared your motto message.");
			} else {
				String motto = Util.join(args, " ");
				if(motto.length() > 25) {
					sender.sendMessage(ChatColor.RED + "Motto cannot be longer than 25 characters!");
				} else {
					this.config.getData(player).motto = motto;
					this.config.save();
					sender.sendMessage(ChatColor.GREEN + "Set your motto message to \"" + motto + "\".");
				}
			}
		}
		return true;
	}

}