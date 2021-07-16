package com.romejanic.ddm.event;

import java.awt.Color;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.UserConfig.User;
import com.romejanic.ddm.webhook.Embed;

public class DeathHandler implements Listener {
	
	private final Config config;
	private final UserConfig users;
	private final WebhookTasks tasks;
	
	public DeathHandler(Config config, UserConfig users, WebhookTasks tasks) {
		this.config = config;
		this.users = users;
		this.tasks = tasks;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		// skip if no webhook is set
		if(!this.config.isEnabled()) {
			return;
		}
		
		// get data for player
		User player  = this.users.getData(event.getEntity());
		Color color  = player.color != null ? player.color : Color.red;
		String motto = player.motto != null ? player.motto : this.config.getRandomDeathMotto();
		
		// create embed and send it to Discord
		Embed embed = new Embed()
				.setTitle(motto)
				.setDescription(event.getDeathMessage())
				.setColor(color)
				.setThumbnail(getHeadRender(event.getEntity().getUniqueId(), player.hatEnabled));
		this.tasks.sendWebhookEmbed(embed, this.config.getWebhookURL(), event.getEntity(), player.hatEnabled);
	}
	
	private String getHeadRender(UUID uuid, boolean overlay) {
		return "https://crafatar.com/renders/head/" + uuid.toString().toLowerCase() + (overlay ? "?overlay" : "");
	}
	
}