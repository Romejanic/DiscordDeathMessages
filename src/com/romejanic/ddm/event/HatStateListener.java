package com.romejanic.ddm.event;

import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperPlayClientSettings;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.romejanic.ddm.util.UserConfig;
import com.romejanic.ddm.util.UserConfig.User;

public class HatStateListener {
	
	private static final int HAT_BIT = 0x40; // https://wiki.vg/Protocol#Client_Settings

	private final UserConfig users;
	private final ProtocolManager protocol;
	
	public HatStateListener(JavaPlugin plugin, UserConfig users) {
		this.users = users;
		this.protocol = ProtocolLibrary.getProtocolManager();
		this.init(plugin);
	}
	
	private void init(JavaPlugin plugin) {
		this.protocol.addPacketListener(new PacketAdapter(
				plugin,
				ListenerPriority.NORMAL,
				PacketType.Play.Client.SETTINGS)
		{
			
			@Override
			public void onPacketReceiving(PacketEvent event) {
				// ignore all other packets
				if(event.getPacketType() != PacketType.Play.Client.SETTINGS) return;
				
				WrapperPlayClientSettings packet = new WrapperPlayClientSettings(event.getPacket());
				
				// get hat layer state
				int skinState = packet.getDisplayedSkinParts();
				boolean hatState = (skinState & HAT_BIT) == HAT_BIT;
				
				// store hat state
				User data = HatStateListener.this.users.getData(event.getPlayer());
				data.hatEnabled = hatState;
				
			}
			
		});
	}
	
	public void disable(JavaPlugin plugin) {
		this.protocol.removePacketListeners(plugin);
	}
	
}