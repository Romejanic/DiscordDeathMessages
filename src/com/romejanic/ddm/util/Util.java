package com.romejanic.ddm.util;

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
	
}