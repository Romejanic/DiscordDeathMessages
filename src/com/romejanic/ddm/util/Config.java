package com.romejanic.ddm.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class Config {

	private final Logger logger;
	private final File file;
	
	private String webhookURL = null;
	
	public Config(File pluginFolder, Logger logger) {
		this.file = new File(pluginFolder, "config.json");
		this.logger = logger;
		this.load();
	}
	
	private void load() {
		if(this.file.exists()) {
			try {
				// load JSON from config file
				JsonReader reader = new JsonReader(new FileReader(this.file));
				JsonObject obj = Const.GSON.fromJson(reader, JsonObject.class);
				
				// read config items from file
				this.webhookURL = obj.get("webhookURL").getAsString();
				
				// print message
				reader.close();
				this.logger.info("Read config from file!");
			} catch (IOException e) {
				this.logger.log(Level.SEVERE, "Failed to read config file!", e);
			}
		} else {
			this.logger.info("No existing config file, making one...");
			this.file.getParentFile().mkdirs();
			this.save();
		}
	}
	
	private void save() {
		// convert objects to JSON
		JsonObject out = new JsonObject();
		out.addProperty("webhookURL", this.webhookURL);
		
		// write JSON to file
		String json = Const.GSON.toJson(out);
		try {
			Util.writeFile(json, this.file);
		} catch (IOException e) {
			this.logger.log(Level.SEVERE, "Failed to save config file!", e);
		}
	}
	
	//-----config getters-----//
	
	public boolean isEnabled() {
		return this.webhookURL != null;
	}
	
	public String getWebhookURL() {
		return this.webhookURL;
	}
	
}