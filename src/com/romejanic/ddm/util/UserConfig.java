package com.romejanic.ddm.util;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.entity.Player;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

public class UserConfig {

	public class User {
		public Color color;
		public String motto;
		public transient boolean hatEnabled = true;
	}
	
	private final Map<UUID, User> usermap = new HashMap<UUID, User>();
	private final File file;
	private final Logger logger;
	
	public UserConfig(File pluginFolder, Logger logger) {
		this.file = new File(pluginFolder, "users.json");
		this.logger = logger;
		this.load();
	}
	
	private void load() {
		// only load data if the file exists
		if(this.file.exists()) {
			try {
				// load JSON from config file
				JsonReader reader = new JsonReader(new FileReader(this.file));
				JsonObject obj = Const.GSON.fromJson(reader, JsonObject.class);

				// read user data from file
				for(Entry<String, JsonElement> e : obj.entrySet()) {
					User user = Const.GSON.fromJson(e.getValue(), User.class);
					this.usermap.put(UUID.fromString(e.getKey()), user);
				}

				// print message
				reader.close();
				this.logger.info("Read data for " + this.usermap.size() + " users!");
			} catch (IOException e) {
				this.logger.log(Level.SEVERE, "Failed to read user data file!", e);
			}
		}
	}
	
	public void save() {
		if(!this.usermap.isEmpty()) {
			// generate JSON from usermap
			JsonObject obj = new JsonObject();
			
			for(UUID uuid : this.usermap.keySet()) {
				JsonElement user = Const.GSON.toJsonTree(this.usermap.get(uuid));
				obj.add(uuid.toString(), user);
			}
			
			// write JSON to file
			String json = Const.GSON.toJson(obj);
			try {
				Util.writeFile(json, this.file);
			} catch (IOException e) {
				this.logger.log(Level.SEVERE, "Failed to save config file!", e);
			}
			
		} else if(this.file.exists()) {
			// deletes file if no users have data
			this.file.delete();
		}
	}
	
	// get user data
	
	public User getData(Player player) {
		if(!this.usermap.containsKey(player.getUniqueId())) {
			this.usermap.put(player.getUniqueId(), new User());
		}
		return this.usermap.get(player.getUniqueId());
	}
	
}