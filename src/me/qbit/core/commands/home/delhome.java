package me.qbit.core.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class delhome implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		ConfigurationSection sec = Main.GetPlayerHomes(p);
		if(arg3.length == 0 || arg3[0].equalsIgnoreCase("home")) {
			if(sec.contains("home")) {				
				m.message(p, "&fDeleted home 'home'");
				sec.set("home", null);
				Main.SaveHomeConfig();
			} else {
				m.message(p, "Home 'home' not set!");				
			}
		} else if(arg3.length!=0) {
			if(sec.contains(arg3[0])) {
				m.message(p, String.format("&fDeleted home '%s'", arg3[0]));
				sec.set(arg3[0], null);
				Main.SaveHomeConfig();
			} else {
				m.message(p, String.format("Home '%s' not set!", arg3[0]));
			}
		}
		
		return true;
	}
	
}