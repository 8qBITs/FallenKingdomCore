package net.fallenkingdom.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.util.config.BackStorage;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class Utils {

	Player p;
	
	public Utils(Player p) {
		this.p = p;
	}
	
	public static CommandResult success = CommandResult.success();
	
	CommentedConfigurationNode backConfig = BackStorage.getConfig();
	 
	@SuppressWarnings({ "rawtypes" })
	public Location getBackLocation() {
		try {
			return backConfig.getNode(p.getIdentifier()).getValue(TypeToken.of(Location.class));
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with util getting back location");
			e.printStackTrace();
			return null;
		}
    	
    }
	
	@SuppressWarnings("rawtypes")
	public void setBackLocation(Location loc) {
		String uuid = p.getIdentifier();
		try {
			backConfig.getNode(uuid).setValue(TypeToken.of(Location.class), p.getLocation());
		} catch (ObjectMappingException e) {
			System.out.println("Bad bad with util saving back location");
			e.printStackTrace();
		}
		BackStorage.save();
    	
    }
	
	public World getWorld(String name) {
		return Sponge.getServer().getWorld(name).get();
	}
	
	////////////////// MATH RELATED UTILS ////////////////////////
	
	static final Pattern time_pattern = Pattern.compile("\\d+[hmsdyHMSDY]");
	
	static public long parseTimeFromString(String t) {
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
	
	static public String parseTimeFormat(String t) {
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
	
	static public String parseTimeFormat(long t) {
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
