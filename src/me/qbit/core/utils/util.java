package me.qbit.core.utils;

import org.bukkit.entity.Player;

public class util {
	
	messenger m = new messenger();
	
	public boolean isPlayerAdmin(Player p) {
		
		if(p instanceof Player) {
			if(p.hasPermission("core.admin")) {
				return true;
			} else {
				m.fullTitle(p, "&c&lWHOOPS!","&eSorry, but you are not allowed to use this command.");
			}
		}
		
		return false;
	}
	
}
