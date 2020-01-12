package me.qbit.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.qbit.core.Main;

public class util {
	
	messenger m = new messenger();
	Pattern time_pattern = Pattern.compile("\\d+[hmsdyHMSDY]");
	
	public boolean isPlayerAdmin(Player p) {
		if(!p.hasPermission("core.admin"))
			m.fullTitle(p, "&c&lWHOOPS!","&eSorry, but you are not allowed to use this command.");
		return p.hasPermission("core.admin");
	}
	
	public boolean isPlayerAdminSilent(Player p) {
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
	
	public boolean IsVanished(Player p) {
		return p!=null && Main.getVanished().contains(p);
	}
	
	public boolean IsMuted(Player p) {
		return Main.GetMuteStorage().contains(p.getUniqueId().toString());
	}
	
	public long GetMuteDuration(Player p) {
		return this.IsMuted(p) ? Main.GetMuteStorage().getLong(p.getUniqueId().toString()) : 0;
	}
	
	public void MutePlayer(Player p, long time) {	
		Main.GetMuteStorage().set(p.getUniqueId().toString(), time);
		Main.SaveMuteStorage();
	}
	
	public void UnmutePlayer(Player p) {	
		Main.GetMuteStorage().set(p.getUniqueId().toString(), null);
		Main.SaveMuteStorage();
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
	
	public long parseTimeFromString(String t) {
		Matcher matcher = time_pattern.matcher(t);
		long time = 0;
		while(matcher.find()) {
			String s = matcher.group();
			s = s.toLowerCase();
			String clean_s = s.replaceAll("[hmsdy]", "");
			if(s.contains("y")) {
				time+=Integer.parseInt(clean_s)*31536000;
			} else if(s.contains("d")) {
				time+=Integer.parseInt(clean_s)*86400;
			} else if(s.contains("h")) {
				time+=Integer.parseInt(clean_s)*3600;
			} else if(s.contains("m")) {
				time+=Integer.parseInt(clean_s)*60;
			} else if(s.contains("s")) {
				time+=Integer.parseInt(clean_s);
			}
		}
		return time;
	}
	
	public String parseTimeFormat(String t) {
		Matcher matcher = time_pattern.matcher(t);
		String time_str = "";
		while(matcher.find()) {
			String s = matcher.group();
			s = s.toLowerCase();
			String clean_s = s.replaceAll("[hmsdy]", "");
			if(s.contains("y")) {
				time_str+=clean_s+" year(s) ";
			} else if(s.contains("d")) {
				time_str+=clean_s+" day(s) ";
			} else if(s.contains("h")) {
				time_str+=clean_s+" hour(s) ";
			} else if(s.contains("m")) {
				time_str+=clean_s+" minute(s) ";
			} else if(s.contains("s")) {
				time_str+=clean_s+" second(s) ";
			}
		}
		return time_str;
	}
	
	public String parseTimeFormat(long t) {
		int years,days,hours,minutes;
		String time_str = "";
		if((years = (int)Math.floor(t/31536000))>0)
			time_str+=years+" year(s) "; t-=years*31536000;
		if((days = (int)Math.floor(t/86400))>0)
			time_str+=days+" day(s) "; t-=days*86400;
		if((hours = (int)Math.floor(t/3600))>0)
			time_str+=hours+" hour(s) "; t-=hours*3600;
		if((minutes = (int)Math.floor(t/60))>0)
			time_str+=minutes+" minute(s) "; t-=minutes*60;
		if(t>0)
			time_str+=t+" second(s) ";
		return time_str;
	}
	
}