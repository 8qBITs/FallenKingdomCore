package me.qbit.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.qbit.core.Main;

public class util {
	
	messenger m = new messenger();
	
	public boolean isPlayerAdmin(Player p) {
		if(!p.hasPermission("core.admin"))
			m.fullTitle(p, "&c&lWHOOPS!","&eSorry, but you are not allowed to use this command.");
		return p.hasPermission("core.admin");
	}
	
	public void setBackLocation(Player p) {
		YamlConfiguration backStorage = Main.GetBackStorage();
		Location loc = p.getLocation();
		String k = p.getUniqueId().toString();			
		backStorage.set(k+".world",loc.getWorld().getName());
		backStorage.set(k+".x",loc.getX());
		backStorage.set(k+".y",loc.getY());
		backStorage.set(k+".z",loc.getZ());
		Main.SaveBackStorage();
	}
	
	public void setBackLocation(Player p, Location loc) {
		YamlConfiguration backStorage = Main.GetBackStorage();
		String k = p.getUniqueId().toString();
		if(loc==null) {
			backStorage.set(k,null);
		} else {			
			backStorage.set(k+".world",loc.getWorld().getName());
			backStorage.set(k+".x",loc.getX());
			backStorage.set(k+".y",loc.getY());
			backStorage.set(k+".z",loc.getZ());
		}
		Main.SaveBackStorage();
	}
	
	public boolean IsVanished(Player pl) {
		return pl!=null && Main.getVanished().contains(pl);
	}
	
	public Location getBackLocation(Player p) {
		YamlConfiguration backStorage = Main.GetBackStorage();
		String k = p.getUniqueId().toString();
		if(!backStorage.contains(k))
			return null;
		return new Location(Bukkit.getWorld(backStorage.getString(k+".world")),backStorage.getInt(k+".x"),backStorage.getInt(k+".y"),backStorage.getInt(k+".z"));
	}
	
	public ConfigurationSection getPlayerHomes(Player p) {
		YamlConfiguration homes = Main.GetHomeStorage();
		if(!homes.contains(p.getUniqueId().toString()) || !homes.isConfigurationSection(p.getUniqueId().toString())) {
			homes.createSection(p.getUniqueId().toString());
		}
		return homes.getConfigurationSection(p.getUniqueId().toString());
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
		return isPlayerAdmin(p) || p.hasPermission("core.keepxp");
	}
	
	public boolean getKeepInventory(Player p) {
		return isPlayerAdmin(p) || p.hasPermission("core.keepinventory");
	}
	
}
