package com.romejanic.ddm.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class Config {

	private static final String[] DEFAULT_DEATH_MOTTOS = {
		"Ouch!", "Rest In Peace", "Yikes", "You Died", "I Am Dead",
		"Destroyed!", "Rekt!", "GG", "You're Fired!", "Git Gud",
		"Killed!", "Slaughtered!", "Owned!", "Goodbye!"
	};

	private final Logger logger;
	private final File file;

	private String webhookURL = null;
	private List<String> deathMottos = new ArrayList<String>();
	private Set<String> blockedWords = new HashSet<String>();
	private boolean preventCaching = true;
	
	private boolean showTeam = false;
	private String teamLabel = "Team";
	
	private boolean showPetDeaths = true;

	public Config(File pluginFolder, Logger logger) {
		this.file = new File(pluginFolder, "config.json");
		this.logger = logger;
		this.load();
	}

	public boolean load() {
		if(this.file.exists()) {
			this.deathMottos.clear();
			this.blockedWords.clear();
			try {
				// load JSON from config file
				JsonReader reader = new JsonReader(new FileReader(this.file));
				JsonObject obj = Const.GSON.fromJson(reader, JsonObject.class);

				// read config items from file
				JsonElement webhookURL = obj.get("webhookURL");
				this.webhookURL = webhookURL.isJsonNull() ? null : webhookURL.getAsString();
				
				JsonArray mottosJson = obj.get("deathMottos").getAsJsonArray();
				for(JsonElement motto : mottosJson) {
					this.deathMottos.add(motto.getAsString());
				}
				
				if(obj.has("blockedWords")) {
					JsonArray wordsJson = obj.get("blockedWords").getAsJsonArray();
					for(JsonElement word : wordsJson) {
						this.blockedWords.add(word.getAsString());
					}
				}
				
				this.preventCaching = obj.has("preventCaching") ? obj.get("preventCaching").getAsBoolean() : true;
				this.showTeam = obj.has("showTeam") ? obj.get("showTeam").getAsBoolean() : false;
				this.teamLabel = obj.has("teamLabel") ? obj.get("teamLabel").getAsString() : "Team";
				this.showPetDeaths = obj.has("showPetDeaths") ? obj.get("showPetDeaths").getAsBoolean() : true;
				
				// print message
				reader.close();
				this.logger.info("Read config from file!");
				
				// save the config again incase any options were
				// added in the last update
				this.save();
				
				return true;
			} catch (IOException e) {
				this.logger.log(Level.SEVERE, "Failed to read config file!", e);
				return false;
			}
		} else {
			this.logger.info("No existing config file, making one...");
			// add default death mottos
			this.deathMottos.addAll(Arrays.asList(DEFAULT_DEATH_MOTTOS));
			// save config file
			this.file.getParentFile().mkdirs();
			this.save();
			return true;
		}
	}

	private void save() {
		// convert objects to JSON
		JsonObject out = new JsonObject();
		out.addProperty("webhookURL", this.webhookURL);
		
		JsonArray mottos = new JsonArray();
		for(String motto : this.deathMottos) {
			mottos.add(motto);
		}
		out.add("deathMottos", mottos);
		
		JsonArray words = new JsonArray();
		for(String word : this.blockedWords) {
			words.add(word);
		}
		out.add("blockedWords", words);

		out.addProperty("preventCaching", this.preventCaching);
		out.addProperty("showTeam", this.showTeam);
		out.addProperty("teamLabel", this.teamLabel);
		out.addProperty("showPetDeaths", this.showPetDeaths);
		
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
	
	public String getRandomDeathMotto() {
		return Util.pickRandom(this.deathMottos);
	}
	
	public boolean shouldPreventCaching() {
		return this.preventCaching;
	}
	
	public boolean shouldShowTeam() {
		return this.showTeam;
	}
	
	public String getTeamLabel() {
		return this.teamLabel;
	}
	
	public boolean showPetDeaths() {
		return this.showPetDeaths;
	}
	
	public List<String> getBlockedWords(String motto) {
		if(this.blockedWords.isEmpty()) {
			return null;
		}
		motto = motto.toLowerCase();
		List<String> matches = new ArrayList<String>();
		for(String word : this.blockedWords) {
			if(motto.contains(word.toLowerCase())) {
				matches.add(word);
			}
		}
		return matches.isEmpty() ? null : matches;
	}

	//-----config setters-----//

	public void setWebhookURL(String url) {
		this.webhookURL = url;
		this.save();
	}

}