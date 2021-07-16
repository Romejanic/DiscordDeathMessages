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
	
}