package com.romejanic.ddm.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.bukkit.command.CommandSender;

public class Util {

	public static void writeFile(String data, File file) throws IOException {
		OutputStream out = new FileOutputStream(file);
		out.write(data.getBytes(StandardCharsets.UTF_8));
		out.flush();
		out.close();
	}

	public static boolean testPermission(String perm, CommandSender sender) {
		return sender.isOp() || sender.hasPermission("ddm.*") || sender.hasPermission("ddm." + perm);
	}

	// source: https://stackoverflow.com/questions/18022364/how-to-convert-rgb-color-to-int-in-java
	public static long getIntFromColor(long red, long green, long blue) {
		red = (red << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
		green = (green << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
		blue = blue & 0x000000FF; //Mask out anything not blue.
		return red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}

	public static long getIntFromColor(Color color) {
		return getIntFromColor(color.getRed(), color.getGreen(), color.getBlue());
	}
	
}