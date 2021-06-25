package com.romejanic.ddm.event;

import java.awt.Color;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.webhook.Embed;

public class DeathHandler implements Listener {
	
	private final Config config;
	private final WebhookTasks tasks;
	
	public DeathHandler(Config config, WebhookTasks tasks) {
		this.config = config;
		this.tasks = tasks;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		// skip if no webhook is set
		if(!this.config.isEnabled()) {
			return;
		}
		
		// create embed and send it to Discord
		Embed embed = new Embed()
				.setTitle(event.getEntity().getDisplayName() + " died!")
				.setDescription(event.getDeathMessage())
				.setColor(Color.red)
				.setThumbnail(getHeadRender(event.getEntity().getUniqueId()));
		this.tasks.sendWebhookEmbed(embed, this.config.getWebhookURL());
	}
	
	private String getHeadRender(UUID uuid) {
		return "https://crafatar.com/renders/head/" + uuid.toString().toLowerCase() + "?overlay";
	}
	
}