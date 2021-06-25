package com.romejanic.ddm.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Const {

	public static final String VERSION = "1.0.0";
	
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	
}