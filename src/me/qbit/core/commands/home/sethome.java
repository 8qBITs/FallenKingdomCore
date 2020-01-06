package me.qbit.core.commands.home;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class sethome implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		int max_homes = u.getMaxHomes(p);
		ConfigurationSection sec = Main.GetPlayerHomes(p);
		Location loc = p.getLocation();
		if(sec.getKeys(false).size()>=max_homes) {
			m.message(p, "You cannot set more homes");
		} else {			
			if(arg3.length == 0 || arg3[0].equalsIgnoreCase("home")) {
				m.message(p, "&fHome 'home' set");
				sec.set("home.world",loc.getWorld().getName());
				sec.set("home.x",loc.getX());
				sec.set("home.y",loc.getY());
				sec.set("home.z",loc.getZ());
			} else {
				m.message(p, String.format("&fHome '%s' set", arg3[0]));
				sec.set(arg3[1]+".world",loc.getWorld().getName());
				sec.set(arg3[1]+".x",loc.getX());
				sec.set(arg3[1]+".y",loc.getY());
				sec.set(arg3[1]+".z",loc.getZ());
			}
			Main.SaveHomeConfig();
		}
		
		return true;
	}
	
}