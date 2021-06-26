package com.romejanic.ddm.webhook;

import java.awt.Color;

import com.google.gson.JsonObject;
import com.romejanic.ddm.util.Util;

public class Embed {

	private String title       = "Embed";
	private Color color        = Color.white;
	private String description = "This is an embed";
	private String thumbnail   = null;

	public Embed setTitle(String title) {
		this.title = title != null ? title : "Embed";
		return this;
	}

	public Embed setColor(Color color) {
		this.color = color != null ? color : Color.white;
		return this;
	}

	public Embed setDescription(String description) {
		this.description = description != null ? description : "This is an embed";
		return this;
	}

	public Embed setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
		return this;
	}

	public JsonObject toJSON() {
		JsonObject object = new JsonObject();

		// store title and description
		object.addProperty("title", this.title);
		object.addProperty("description", this.description);

		// store color
		int color = Util.getIntFromColor(this.color);
		object.addProperty("color", color);

		// store thumbnail
		if(this.thumbnail != null) {
			JsonObject thumbnailObj = new JsonObject();
			thumbnailObj.addProperty("url", this.thumbnail);
			object.add("thumbnail", thumbnailObj);
		}

		return object;
	}

}