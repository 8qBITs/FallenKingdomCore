package me.qbit.core.commands.home;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class homes implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	int max_homes = 3;
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		ConfigurationSection sec = Main.GetPlayerHomes(p);
		if(sec.getKeys(false).size()>0) {
			String home_str = "";
			for(String h : sec.getKeys(false)) {
				home_str += h+", ";
			}
			m.message(p, String.format("&fYour homes (%d): %s",sec.getKeys(false).size(),home_str.substring(0,home_str.length()-2)));
		} else {
			m.message(p, "&fYou haven't set any homes yet!");
		}
		return true;
	}
	
}