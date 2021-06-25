package com.romejanic.ddm.webhook;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.romejanic.ddm.DiscordDeathMessages;

public class WebhookSender {

	private static final Gson GSON = new GsonBuilder().create();
	private static final String USER_AGENT = getUserAgent();
	
	public static boolean sendEmbed(Embed embed, String webhookUrl) throws Exception {
		// open HTTP session with webhook URL
		URL url = new URL(webhookUrl);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		
		// configure HTTP properties
		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.setRequestProperty("Content-Type", "application/json");
		http.setRequestProperty("User-Agent", USER_AGENT);
		System.out.println(USER_AGENT);
		
		// generate JSON output for embed
		JsonObject data = new JsonObject();
		JsonArray embeds = new JsonArray();
		embeds.add(embed.toJSON());
		data.add("embeds", embeds);
		
		byte[] json = GSON.toJson(data).getBytes(StandardCharsets.UTF_8);
		http.setRequestProperty("Content-Length", String.valueOf(json.length));
		
		// send request
		OutputStream out = http.getOutputStream();
		out.write(json);
		out.flush();
		
		// check response code
		int resp = http.getResponseCode();
		http.disconnect();
		
		return resp == 204;
	}
	
	private static String getUserAgent() {
		StringBuilder sb = new StringBuilder();
		sb.append("DDM/").append(DiscordDeathMessages.VERSION).append(" ");
		if(Bukkit.getServer() != null) {
			sb.append("Bukkit/").append(Bukkit.getVersion()).append(" ");
		}
		sb.append("Java/").append(System.getProperty("java.version"));
		return sb.toString();
	}
	
}