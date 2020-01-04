package me.qbit.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class messenger {
	public void message(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cCore&8] &4" + msg));
	}
	
	public void broadcast(String msg) {
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public void broadcastNull() {
		Bukkit.broadcastMessage(" ");
	}

	public void sendnull(Player p) {
		p.sendMessage(" ");
	}
	
	public void sendraw(Player p, String msg) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	@SuppressWarnings("deprecation")
	public void fullTitle(Player p, String msg1, String msg2) {
		p.sendTitle(ChatColor.translateAlternateColorCodes('&', msg1), ChatColor.translateAlternateColorCodes('&', msg2));
	}
	
	@SuppressWarnings("deprecation")
	public void title(Player p, String msg) {
		p.sendTitle(" ", ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	
}
