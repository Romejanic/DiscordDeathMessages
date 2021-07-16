package com.romejanic.ddm.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.Util;

public class CommandMotto implements CommandExecutor, TabCompleter {

	private final Config config;
	private final UserConfig userConfig;

	public CommandMotto(Config config, UserConfig userConfig) {
		this.config = config;
		this.userConfig = userConfig;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Sorry, only players can use this command!");
		} else if(!Util.testPermission("motto", sender)) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
		}  else if(args.length < 1) {
			sender.sendMessage(ChatColor.RED + "Usage: /ddmmotto <message|clear>");
		} else {
			Player player = (Player) sender;
			if(args[0].equalsIgnoreCase("clear")) {
				if(args.length == 1) {
					this.userConfig.getData(player).motto = null;
					this.userConfig.save();
					sender.sendMessage(ChatColor.GREEN + "Cleared your motto message.");
				} else if(args.length > 1) {
					if(Util.testPermission("motto.others", sender)) {
						String targetName = args[1];
						OfflinePlayer target = Util.resolvePlayer(targetName);
						
						if(target == null || !this.userConfig.hasDataFor(player)) {
							sender.sendMessage(ChatColor.RED + "No player named \"" + targetName + "\" found!");
						} else {
							this.userConfig.getData(target).motto = null;
							this.userConfig.save();
							sender.sendMessage(ChatColor.GREEN + "Cleared " + target.getName() + "'s motto message.");
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You do not have permission to clear another user's motto!");
					}
				}
			} else {
				String motto = Util.join(args, " ");
				if(motto.length() > 25) {
					sender.sendMessage(ChatColor.RED + "Motto cannot be longer than 25 characters!");
				} else {
					List<String> blockedWords = this.config.getBlockedWords(motto);
					if(blockedWords != null) {
						sender.sendMessage(ChatColor.RED + "That motto message contains one or more banned words!");
						sender.sendMessage(ChatColor.RED + "(" + Util.join(blockedWords, ", ") + ")");
					} else {
						this.userConfig.getData(player).motto = motto;
						this.userConfig.save();
						sender.sendMessage(ChatColor.GREEN + "Set your motto message to \"" + motto + "\".");
					}
				}
			}
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> list = new ArrayList<String>();
		if(args.length == 1) {
			if(args[0].isEmpty()) {
				list.add("clear");
				list.add("<message>");
			} else if("clear".startsWith(args[0])) {
				list.add("clear");
			} else {
				list.add("<message>");
			}
		} else if(args.length == 2 && args[0].equalsIgnoreCase("clear") && Util.testPermission("motto.others", sender)) {
			String partial = args[1].toLowerCase();
			List<String> players = this.userConfig.getOfflinePlayersWithData()
										.stream()
										.filter(p -> p.getName().toLowerCase().startsWith(partial))
										.map(p -> p.getName())
										.collect(Collectors.toList());
			list.addAll(players);
		}
		return list;
	}

}