package com.romejanic.ddm.command;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.webhook.Embed;
import com.romejanic.ddm.webhook.WebhookChecker;
import com.romejanic.ddm.webhook.WebhookSender;

public class WebhookTasks {
	
	private final JavaPlugin plugin;
	private final Config config;
	private final Logger logger;
	
	public WebhookTasks(JavaPlugin plugin, Config config) {
		this.plugin = plugin;
		this.config = config;
		this.logger = plugin.getLogger();
	}

	public void setWebhookURLAsync(String url, CommandSender sender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try {
				if(WebhookChecker.checkWebhookURL(url)) {
					this.config.setWebhookURL(url);
					Command.broadcastCommandMessage(sender, ChatColor.GREEN + "The webhook URL has been set!");
					Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Death messages will now be sent to Discord.");
				} else {
					sender.sendMessage(ChatColor.RED + "Sorry, that webhook URL is invalid.");
					sender.sendMessage(ChatColor.RED + "Please try again with a valid URL.");
				}
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Failed to verify webhook URL!");
				sender.sendMessage(ChatColor.RED + "Ensure your server has an internet connection and try again.");
				this.logger.log(Level.SEVERE, "Failed to verify webhook URL", e);
			}
		});
	}
	
	public void sendWebhookEmbed(Embed embed, String url, Player player, boolean overlay) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
			try {
				if(!WebhookSender.sendEmbed(embed, url, player, overlay)) {
					this.logger.log(Level.SEVERE, "Webhook sent was invalid or failed to send!");
				}
			} catch (Exception e) {
				this.logger.log(Level.SEVERE, "Failed to send webhook!", e);
			}
		});
	}
	
}