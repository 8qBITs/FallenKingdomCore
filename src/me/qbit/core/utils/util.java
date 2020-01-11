package me.qbit.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.qbit.core.Main;

public class util {
	
	messenger m = new messenger();
	configuration backStorageClass = new configuration("backs.yml");
	YamlConfiguration backStorage = backStorageClass.getConfig();
	configuration homeStorageClass = new configuration("homes.yml");
	YamlConfiguration homes = homeStorageClass.getConfig();
	configuration muteStorageClass = new configuration("mutes.yml");
	YamlConfiguration mutes = muteStorageClass.getConfig();
	
	public boolean isPlayerAdmin(Player p) {
		if(!p.hasPermission("core.admin"))
			m.fullTitle(p, "&c&lWHOOPS!","&eSorry, but you are not allowed to use this command.");
		return p.hasPermission("core.admin");
	}
	
	public void setBackLocation(Player p) {
		Location loc = p.getLocation();
		String k = p.getUniqueId().toString();			
		backStorage.set(k+".world",loc.getWorld().getName());
		backStorage.set(k+".x",loc.getX());
		backStorage.set(k+".y",loc.getY());
		backStorage.set(k+".z",loc.getZ());
		backStorageClass.saveConfig();
	}
	
	public void setBackLocation(Player p, Location loc) {
		String k = p.getUniqueId().toString();
		if(loc==null) {
			backStorage.set(k,null);
		} else {			
			backStorage.set(k+".world",loc.getWorld().getName());
			backStorage.set(k+".x",loc.getX());
			backStorage.set(k+".y",loc.getY());
			backStorage.set(k+".z",loc.getZ());
		}
		backStorageClass.saveConfig();
	}
	
	public boolean IsVanished(Player p) {
		return p!=null && Main.getVanished().contains(p);
	}
	
	public boolean IsMuted(Player p) {
		return mutes.contains(p.getUniqueId().toString());
	}
	
	public long GetMuteDuration(Player p) {
		return this.IsMuted(p) ? mutes.getLong(p.getUniqueId().toString()) : 0;
	}
	
	public void MutePlayer(Player p, long time) {	
		mutes.set(p.getUniqueId().toString(), time);
		muteStorageClass.saveConfig();
	}
	
	public void UnmutePlayer(Player p) {	
		mutes.set(p.getUniqueId().toString(), null);
		muteStorageClass.saveConfig();
	}
	
	public Location getBackLocation(Player p) {
		String k = p.getUniqueId().toString();
		if(!backStorage.contains(k))
			return null;
		return new Location(Bukkit.getWorld(backStorage.getString(k+".world")),backStorage.getInt(k+".x"),backStorage.getInt(k+".y"),backStorage.getInt(k+".z"));
	}
	
	public ConfigurationSection getPlayerHomes(Player p) {
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
