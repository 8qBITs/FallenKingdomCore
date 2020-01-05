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
	
	public int getMaxHomes(Player p) {
		if(isPlayerAdmin(p)) {
			return 100;
		}
		
		if(p.hasPermission("core.home3")) {
			return 3;
		}
		
		if(p.hasPermission("core.home7")) {
			return 7;
		}
		
		if(p.hasPermission("core.home15")) {
			return 15;
		}

		return 1;
	}
	
	public boolean getKeepXp(Player p) {
		if(isPlayerAdmin(p)) {
			return true;
		} else if(p.hasPermission("core.keepxp")) {
			return true;
		}
		return false;
	}
	
	public boolean getKeepInventory(Player p) {
		if(isPlayerAdmin(p)) {
			return true;
		} else if(p.hasPermission("core.keepinventory")) {
			return true;
		}
		return false;
	}
	
}
