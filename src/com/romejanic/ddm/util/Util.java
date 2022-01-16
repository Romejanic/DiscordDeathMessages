package com.romejanic.ddm.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Util {
	
	public static final Random RANDOM = new Random();

	public static void writeFile(String data, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		out.write(data.getBytes(StandardCharsets.UTF_8));
		out.flush();
		out.close();
	}

	public static boolean testPermission(String perm, CommandSender sender) {
		return sender.isOp() || sender.hasPermission("ddm.*") || sender.hasPermission("ddm." + perm);
	}

	// source: https://stackoverflow.com/questions/18022364/how-to-convert-rgb-color-to-int-in-java
	public static long getIntFromColor(long red, long green, long blue) {
		red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
		green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
		blue = blue & 0x000000FF; //Mask out anything not blue.
		return red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}

	public static long getIntFromColor(Color color) {
		return getIntFromColor(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public static <T> T pickRandom(List<T> items) {
		return items.get(RANDOM.nextInt(items.size()));
	}
	
	public static String join(String[] arr, String separator) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if(i < arr.length - 1) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}
	
	public static String join(List<String> list, String separator) {
		return join(list.toArray(new String[0]), separator);
	}
	
	public static String createQueryParams(Map<String, String> params) {
		if(params.isEmpty()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("?");
		
		Iterator<Entry<String, String>> iter = params.entrySet().iterator();
		while(iter.hasNext()) {
			Entry<String, String> param = iter.next();
			sb.append(param.getKey());
			if(param.getValue() != null) {
			  sb.append("=")
			    .append(param.getValue());
			}
			if(iter.hasNext()) {
				sb.append("&");
			}
		}
		return sb.toString();
	}
	
	public static OfflinePlayer resolvePlayer(String name) {
		for(OfflinePlayer player : Bukkit.getOfflinePlayers()) {
			if(player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}
	
	public static Team getPlayerTeam(OfflinePlayer player) {
		Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		for(Team team : scoreboard.getTeams()) {
			if(team.hasEntry(player.getName())) {
				return team;
			}
		}
		return null;
	}
	
	// source: https://minecraft.fandom.com/wiki/Death_messages
	public static String getPetDeathMessage(Tameable tameable, String petName) {
		if(tameable.getLastDamageCause() == null) {
			return petName + " died.";
		}
		
		EntityDamageEvent event = tameable.getLastDamageCause();
		EntityDamageByEntityEvent byEntity = event instanceof EntityDamageByEntityEvent ? (EntityDamageByEntityEvent)event : null;
		switch(event.getCause()) {
			case BLOCK_EXPLOSION: return petName + " blew up.";
			case CONTACT: return petName + " walked into a " + getDamageBlockName(event) + ".";
			case CRAMMING: return petName + " was squished to death.";
			case DRAGON_BREATH: return petName + " was roasted in dragon's breath.";
			case DROWNING: return petName + " drowned.";
			case ENTITY_SWEEP_ATTACK:
			case ENTITY_ATTACK: return getDeathMessageEntityItem(petName, "was slain", byEntity);
			case ENTITY_EXPLOSION: return getDeathMessageEntityItem(petName, "was blown up", byEntity);
			case FALL: return petName + " fell from a high place.";
			case FALLING_BLOCK: return petName + " was squashed by a falling " + getDamageBlockName(event) + ".";
			case FIRE: return petName + " went up in flames.";
			case FIRE_TICK: return petName + " burned to death.";
			case FLY_INTO_WALL: return petName + " experienced kinetic energy.";
			case HOT_FLOOR: return petName + " discovered the floor was lava.";
			case LAVA: return petName + " tried to swim in lava.";
			case LIGHTNING: return petName + " was struck by lightning.";
			case MAGIC: return getDeathMessageEntityItem(petName, "was killed with magic", byEntity);
			case POISON: return petName + " was poisoned.";
			case PROJECTILE: return getDeathMessageEntityItem(petName, "was shot", byEntity);
			case STARVATION: return petName + " starved to death.";
			case SUFFOCATION: return petName + " suffocated in a wall.";
			case SUICIDE: return petName + " killed themselves.";
			case THORNS: return petName + " was killed by Thorns.";
			case VOID: return petName + " fell out of the world.";
			case WITHER: return petName + " withered away.";
			case MELTING:
			case CUSTOM:
			default: return petName + " died.";
		}
	}
	
	public static String getEntityDisplayName(Entity entity) {
		return entity.getCustomName() != null ? entity.getCustomName() : entity.getName();
	}
	
	private static String getDeathMessageEntityItem(String name, String message, EntityDamageByEntityEvent event) {
		StringBuilder sb = new StringBuilder()
			.append(name)
			.append(" ")
			.append(message);
		if(event != null) {
			String damager = getEntityDisplayName(event.getDamager());
			sb.append(" by ").append(damager);
			
			if(event.getDamager() instanceof LivingEntity) {
				LivingEntity living = (LivingEntity)event.getDamager();
				ItemStack stack = or(
					living.getEquipment().getItemInMainHand(),
					living.getEquipment().getItemInOffHand()
				);
				
				if(stack != null && stack.getItemMeta() != null && stack.getItemMeta().hasDisplayName()) {
					sb.append(" using ").append(stack.getItemMeta().getDisplayName());
				}
			}
		}
		return sb.append(".").toString();
	}
	
	private static <T> T or(T first, T second) {
		return first != null ? first : second;
	}
	
	private static String getDamageBlockName(EntityDamageEvent event) {
		if(event instanceof EntityDamageByBlockEvent) {
			EntityDamageByBlockEvent byBlock = (EntityDamageByBlockEvent)event;
			return byBlock.getDamager().getBlockData().getMaterial().name().toLowerCase();
		} else {
			return "block";
		}
	}
	
}