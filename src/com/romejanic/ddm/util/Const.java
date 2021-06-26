package com.romejanic.ddm.util;

import java.awt.Color;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class Const {

	public static final String VERSION = "1.0.0";
	public static final int BSTATS_ID  = 11818;
	
	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(Color.class, new ColorSerializer())
			.setPrettyPrinting()
			.serializeNulls()
			.create();
	
	static class ColorSerializer implements JsonSerializer<Color>, JsonDeserializer<Color> {

		@Override
		public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
			JsonArray arr = new JsonArray();
			arr.add(new JsonPrimitive(src.getRed()));
			arr.add(new JsonPrimitive(src.getGreen()));
			arr.add(new JsonPrimitive(src.getBlue()));
			return arr;
		}

		@Override
		public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if(!json.isJsonArray()) {
				throw new JsonParseException("Expecting a JsonArray!");
			}
			JsonArray array = json.getAsJsonArray();
			if(array.size() != 3) {
				throw new JsonParseException("Expecting an array with 3 elements!");
			}
			int red   = array.get(0).getAsInt();
			int green = array.get(1).getAsInt();
			int blue  = array.get(2).getAsInt();
			return new Color(red, green, blue);
		}
		
	}
	
}