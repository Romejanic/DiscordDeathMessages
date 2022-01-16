package com.romejanic.ddm.util;

import java.awt.Color;
import java.lang.reflect.Type;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Mule;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.SkeletonHorse;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.TraderLlama;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.ZombieHorse;

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
	
	public static String getAnimalMotto(EntityType type) {
		switch(type) {
			case WOLF: return "Woof!";
			case CAT: return "Meow!";
			case PARROT: return "Squawk!";
			case HORSE: return "Neigh!";
			default: return "";
		}
	}
	
	// this method brings me great shame, but i'm not sure how else i could do it
	public static String getPetRenderURL(Tameable tameable) {
		if(tameable instanceof Wolf) {
			// wolf album: https://imgur.com/a/uQFXkLs
			Wolf wolf = (Wolf)tameable;
			switch(wolf.getCollarColor()) {
				case BLACK: return "https://i.imgur.com/gTO7mqC.png";
				case BLUE: return "https://i.imgur.com/dEO3F0g.png";
				case BROWN: return "https://i.imgur.com/qdfsYCI.png";
				case CYAN: return "https://i.imgur.com/WPkdyUI.png";
				case GRAY: return "https://i.imgur.com/1VByHqa.png";
				case GREEN: return "https://i.imgur.com/Ru2bsA1.png";
				case LIGHT_BLUE: return "https://i.imgur.com/0H90KVN.png";
				case LIGHT_GRAY: return "https://i.imgur.com/xt3inQK.png";
				case LIME: return "https://i.imgur.com/N20UqDF.png";
				case MAGENTA: return "https://i.imgur.com/eMUx2oQ.png";
				case ORANGE: return "https://i.imgur.com/IjlmnFh.png";
				case PINK: return "https://i.imgur.com/cWfflxk.png";
				case PURPLE: return "https://i.imgur.com/QupPZc4.png";
				case RED: return "https://i.imgur.com/mFoZPP1.png";
				case WHITE: return "https://i.imgur.com/bJFcWjg.png";
				case YELLOW: return "https://i.imgur.com/assiEus.png";
			}
		} else if(tameable instanceof Cat) {
			// cat album: https://imgur.com/a/iMbWigf
			Cat cat = (Cat)tameable;
			switch(cat.getCatType()) {
				case ALL_BLACK: return "https://i.imgur.com/GhF4Ew4.png";
				case BLACK: return "https://i.imgur.com/GrJhcC5.png";
				case BRITISH_SHORTHAIR: return "https://i.imgur.com/WrF96J7.png";
				case CALICO: return "https://i.imgur.com/EANHFkj.png";
				case JELLIE: return "https://i.imgur.com/rCNkqa6.png";
				case PERSIAN: return "https://i.imgur.com/urjS4bj.png";
				case RAGDOLL: return "https://i.imgur.com/eKEUgmA.png";
				case RED: return "https://i.imgur.com/rZCzifS.png";
				case SIAMESE: return "https://i.imgur.com/cPmYdAY.png";
				case TABBY: return "https://i.imgur.com/GwBWg5v.png";
				case WHITE: return "https://i.imgur.com/RcmXiHc.png";
			}
		} else if(tameable instanceof Parrot) {
			// parrot album: https://imgur.com/a/E1IczSq
			Parrot parrot = (Parrot)tameable;
			switch(parrot.getVariant()) {
				case BLUE: return "https://i.imgur.com/WWZE8Wg.png";
				case CYAN: return "https://i.imgur.com/Bp8iqmU.png";
				case GRAY: return "https://i.imgur.com/7KP2Idg.png";
				case GREEN: return "https://i.imgur.com/cH3UXEZ.png";
				case RED: return "https://i.imgur.com/tI5zHFx.png";
			}
		} else if(tameable instanceof Horse) {
			// horse album: https://imgur.com/a/noE094n
			Horse horse = (Horse)tameable;
			switch(horse.getColor()) {
			case BLACK:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/r1qixpt.png";
					case BLACK_DOTS: return "https://i.imgur.com/m2TXL8N.png";
					case WHITE: return "https://i.imgur.com/r1qixpt.png";
					case WHITEFIELD: return "https://i.imgur.com/ApVYeVp.png";
					case WHITE_DOTS: return "https://i.imgur.com/uqtJopS.png";
				}
				break;
			case BROWN:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/U5I9Ejh.png";
					case BLACK_DOTS: return "https://i.imgur.com/NfZ6PEu.png";
					case WHITE: return "https://i.imgur.com/99ohg6A.png";
					case WHITEFIELD: return "https://i.imgur.com/nhx8QVz.png";
					case WHITE_DOTS: return "https://i.imgur.com/JQPhWuT.png";
				}
				break;
			case CHESTNUT:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/GqFSApr.png";
					case BLACK_DOTS: return "https://i.imgur.com/8VnSsKq.png";
					case WHITE: return "https://i.imgur.com/5h1Dqhx.png";
					case WHITEFIELD: return "https://i.imgur.com/wv8j7rm.png";
					case WHITE_DOTS: return "https://i.imgur.com/2hW7H6b.png";
				}
				break;
			case CREAMY:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/7C18Kp2.png";
					case BLACK_DOTS: return "https://i.imgur.com/GDQrBKe.png";
					case WHITE: return "https://i.imgur.com/XSqpXp7.png";
					case WHITEFIELD: return "https://i.imgur.com/zo6mptI.png";
					case WHITE_DOTS: return "https://i.imgur.com/czw7vnk.png";
				}
				break;
			case DARK_BROWN:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/AnoSjNj.png";
					case BLACK_DOTS: return "https://i.imgur.com/RayE6kC.png";
					case WHITE: return "https://i.imgur.com/BTBhJOu.png";
					case WHITEFIELD: return "https://i.imgur.com/8hBAJ0h.png";
					case WHITE_DOTS: return "https://i.imgur.com/5dNaR9H.png";
				}
				break;
			case GRAY:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/HE6pptQ.png";
					case BLACK_DOTS: return "https://i.imgur.com/KsLvc2y.png";
					case WHITE: return "https://i.imgur.com/qAR0bfk.png";
					case WHITEFIELD: return "https://i.imgur.com/gHldR0h.png";
					case WHITE_DOTS: return "https://i.imgur.com/HmcAXSv.png";
				}
				break;
			case WHITE:
				switch(horse.getStyle()) {
					case NONE: return "https://i.imgur.com/wmEUcrt.png";
					case BLACK_DOTS: return "https://i.imgur.com/Yqg2itT.png";
					case WHITE: return "https://i.imgur.com/ROZgn5z.png";
					case WHITEFIELD: return "https://i.imgur.com/YnzqD56.png";
					case WHITE_DOTS: return "https://i.imgur.com/os5rPzg.png";
				}
				break;
			}
			
			// horse variant album: https://imgur.com/a/INAScOx
		} else if(tameable instanceof Mule) { return "https://i.imgur.com/p1KizB1.png";
		} else if(tameable instanceof Donkey) { return "https://i.imgur.com/99OLvpa.png";
		} else if(tameable instanceof SkeletonHorse) { return "https://i.imgur.com/bRjJlmU.png";
		} else if(tameable instanceof ZombieHorse) { return "https://i.imgur.com/2OqkFDp.png";
		} else if(tameable instanceof Llama) {
			// llama album: https://imgur.com/a/Y8T4Tke
			Llama llama = (Llama)tameable;
			if(tameable instanceof TraderLlama) {
				switch(llama.getColor()) {
					case BROWN: return "https://i.imgur.com/CYcEmZn.png";
					case CREAMY: return "https://i.imgur.com/k2sT4wN.png";
					case GRAY: return "https://i.imgur.com/Yv0HVmo.png";
					case WHITE: return "https://i.imgur.com/Jcx2QLJ.png";
				}
			} else {
				switch(llama.getColor()) {
					case BROWN: return "https://i.imgur.com/WpanBgW.png";
					case CREAMY: return "https://i.imgur.com/fOeXFUT.png";
					case GRAY: return "https://i.imgur.com/NGyB14q.png";
					case WHITE: return "https://i.imgur.com/iuMURBa.png";
				}
			}
		}
		return "";
	}
	
}