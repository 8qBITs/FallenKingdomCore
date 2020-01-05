package me.qbit.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class home implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	int max_homes = 3;
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		ConfigurationSection sec = Main.GetPlayerHomes(p);
		if(!(arg3.length == 0)) {
			if(arg3[0].equalsIgnoreCase("set")) {
				Location loc = p.getLocation();
				if(arg3.length == 1 || arg3[1].equalsIgnoreCase("home")) {
					m.message(p, "&fHome 'home' set");
					sec.set("home.world",loc.getWorld().getName());
					sec.set("home.x",loc.getX());
					sec.set("home.y",loc.getY());
					sec.set("home.z",loc.getZ());
				} else if(arg3.length>1 && sec.getValues(false).size()<max_homes) {
					m.message(p, String.format("&fHome '%s' set", arg3[1]));
					sec.set(arg3[1]+".world",loc.getWorld().getName());
					sec.set(arg3[1]+".x",loc.getX());
					sec.set(arg3[1]+".y",loc.getY());
					sec.set(arg3[1]+".z",loc.getZ());
				} else if(arg3.length>1 && sec.getValues(false).size()>=max_homes) {
					m.message(p, "You can't set more homes!");
				}
			} else if(arg3[0].equalsIgnoreCase("del")) {
				if(arg3.length == 1 || arg3[1].equalsIgnoreCase("home")) {
					m.message(p, "Deleted home 'home'");
					sec.set("home", null);
				} else if(arg3.length>1) {
					if(sec.contains(arg3[1])) {
						m.message(p, String.format("&fDeleted home '%s'", arg3[1]));
						sec.set(arg3[1], null);
					} else {
						m.message(p, String.format("Home '%s' not set!", arg3[1]));
					}
				}
			} else {
				if(sec.contains(arg3[0])) {
					m.message(p, String.format("&fTeleporting to home '%s'",arg3[0]));
					p.teleport(new Location(Bukkit.getWorld(sec.getString(arg3[1]+".world")),sec.getInt(arg3[1]+".x"),sec.getInt(arg3[1]+".y"),sec.getInt(arg3[1]+".z")));
				} else {
					m.message(p, String.format("Home '%s' doesn't exist",arg3[0]));
				}
			}
		} else {
			if(sec.contains("home")) {
				m.message(p, "&fTeleporting to home");
				p.teleport(new Location(Bukkit.getWorld(sec.getString("home.world")),sec.getInt("home.x"),sec.getInt("home.y"),sec.getInt("home.z")));
			} else {
				m.message(p, "Home 'home' not set!");
			}
		}
		Main.SaveHomeConfig();
		
		return false;
	}
	
}
