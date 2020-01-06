package me.qbit.core.commands.home;

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
		if(arg3.length != 0) {
			if(sec.contains(arg3[0])) {
				m.message(p, String.format("&fTeleporting to home '%s'",arg3[0]));
				p.teleport(new Location(Bukkit.getWorld(sec.getString(arg3[0]+".world")),sec.getInt(arg3[0]+".x"),sec.getInt(arg3[0]+".y"),sec.getInt(arg3[0]+".z")));
			} else {
				m.message(p, String.format("Home '%s' doesn't exist",arg3[0]));
			}
		} else {
			if(sec.contains("home")) {
				m.message(p, "&fTeleporting to home");
				p.teleport(new Location(Bukkit.getWorld(sec.getString("home.world")),sec.getInt("home.x"),sec.getInt("home.y"),sec.getInt("home.z")));
			} else {
				m.message(p, "Home 'home' not set!");
			}
		}
		
		return true;
	}
	
}