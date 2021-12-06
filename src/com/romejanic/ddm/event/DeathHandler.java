package com.romejanic.ddm.event;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scoreboard.Team;

import com.romejanic.ddm.command.WebhookTasks;
import com.romejanic.ddm.util.Config;
import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.UserConfig.User;
import com.romejanic.ddm.util.Util;
import com.romejanic.ddm.webhook.Embed;
import com.romejanic.ddm.webhook.WebhookAuthor;

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
		String uuid  = event.getEntity().getUniqueId().toString().toLowerCase();
		User player  = this.users.getData(event.getEntity());
		Color color  = player.color != null ? player.color : Color.red;
		String motto = player.motto != null ? player.motto : this.config.getRandomDeathMotto();
		
		// generate a random value which gets added to the url, to prevent Discord
		// from caching images
		int cacheValue = this.config.shouldPreventCaching() ? getCacheValue() : 0;
		
		// generate query parameters for thumbnail image
		Map<String, String> params = new HashMap<String, String>();
		if(player.hatEnabled) {
			params.put("overlay", null);
		}
		if(cacheValue > 0) {
			params.put("cache", String.valueOf(cacheValue));
		}
		
		String queryString = Util.createQueryParams(params);
		
		// create embed
		Embed embed = new Embed()
				.setTitle(motto)
				.setDescription(event.getDeathMessage())
				.setColor(color)
				.setThumbnail(getHeadRender(uuid, queryString));
		
		// add player's team if the config allows it
		if(this.config.shouldShowTeam()) {
			Player thePlayer = event.getEntity();
			Team team = Util.getPlayerTeam(thePlayer);
			
			// only add if the player is really on a team
			if(team != null) {
				String teamName = ChatColor.stripColor(team.getDisplayName());
				embed.addField("Team", teamName);
			}
		}
		
		// create author object and execute the webhook
		WebhookAuthor author = new WebhookAuthor(
				ChatColor.stripColor(event.getEntity().getDisplayName()),
				getHeadImage(uuid, queryString)
		);
		this.tasks.sendWebhookEmbed(embed, this.config.getWebhookURL(), author);
	}
	
	private String getHeadRender(String uuid, String params) {
		return "https://crafatar.com/renders/head/" + uuid + params;
	}
	
	private String getHeadImage(String uuid, String params) {
		return "https://crafatar.com/avatars/" + uuid + params;
	}
	
	private int getCacheValue() {
		return 1 + (int)(Math.random() * 255);
	}
	
}