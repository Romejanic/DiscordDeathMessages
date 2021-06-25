package com.romejanic.ddm.webhook;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.romejanic.ddm.util.Const;

public class WebhookChecker {

	private static final String WEBHOOK_START = "https://discord.com/api/webhooks/";
	
	public static boolean checkWebhookURL(String webhookUrl) throws IOException {
		// check the URL has correct start
		if(!webhookUrl.startsWith(WEBHOOK_START)) {
			return false;
		}
		
		// send HTTP request for webhook
		URL url = new URL(webhookUrl);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.setRequestProperty("User-Agent", WebhookSender.USER_AGENT);

		// check response status
		if(http.getResponseCode() != 200) {
			http.disconnect();
			return false;
		}

		// read response JSON
		JsonReader reader = new JsonReader(new InputStreamReader(http.getInputStream()));
		JsonObject data   = Const.GSON.fromJson(reader, JsonObject.class);
		
		http.disconnect();
		
		// check type is correct
		return data.get("type").getAsInt() == 1;
	}
	
}