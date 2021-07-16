package com.romejanic.ddm.webhook;

import com.google.gson.JsonObject;

public class WebhookAuthor {

	public String name;
	public String avatarUrl;
	
	public WebhookAuthor(String name, String avatarUrl) {
		this.name = name;
		this.avatarUrl = avatarUrl;
	}
	
	public void export(JsonObject webhookData) {
		if(this.name != null) {
			webhookData.addProperty("username", this.name);
		}
		if(this.avatarUrl != null) {
			webhookData.addProperty("avatar_url", this.avatarUrl);
		}
	}
	
}