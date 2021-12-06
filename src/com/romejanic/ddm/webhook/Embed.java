package com.romejanic.ddm.webhook;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.romejanic.ddm.util.Util;

public class Embed {

	private String title       = "Embed";
	private Color color        = Color.white;
	private String description = "This is an embed";
	private String thumbnail   = null;
	
	private List<EmbedField> fields = null;

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
	
	public Embed addField(String name, String value) {
		return addField(name, value, false);
	}
	
	public Embed addField(String name, String value, boolean inline) {
		if(this.fields == null) {
			this.fields = new ArrayList<>();
		}
		this.fields.add(new EmbedField(name, value, inline));
		return this;
	}

	public JsonObject toJSON() {
		JsonObject object = new JsonObject();

		// store title and description
		object.addProperty("title", this.title);
		object.addProperty("description", this.description);

		// store color
		long color = Util.getIntFromColor(this.color);
		object.addProperty("color", color);

		// store thumbnail
		if(this.thumbnail != null) {
			JsonObject thumbnailObj = new JsonObject();
			thumbnailObj.addProperty("url", this.thumbnail);
			object.add("thumbnail", thumbnailObj);
		}
		
		// store fields
		if(this.fields != null) {
			JsonArray fieldsArr = new JsonArray();
			
			// convert each field to a json object and append it to
			// the array
			for(EmbedField field : this.fields) {
				JsonObject fieldObj = new JsonObject();
				fieldObj.addProperty("name", field.name);
				fieldObj.addProperty("value", field.value);
				if(field.inline) {
					fieldObj.addProperty("inline", true);
				}
				fieldsArr.add(fieldObj);
			}
			
			// add array to object
			object.add("fields", fieldsArr);
		}

		return object;
	}
	
	public class EmbedField {
		private final String name;
		private final String value;
		private final boolean inline;
		
		public EmbedField(String name, String value, boolean inline) {
			this.name = name;
			this.value = value;
			this.inline = inline;
		}
	}

}