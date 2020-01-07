package me.qbit.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class teleport implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if(!(arg3.length == 0)) {
			try {
			Player target = Bukkit.getPlayer(arg3[0]);
			Location location = target.getLocation();
			u.setBackLocation(p);
			p.teleport(location);
			m.title(p, "&eTeleported to: " + target.getName());
			} catch(Exception e) {
				m.message(p, "Please input a valid playername.");
			}
		} else {
			m.message(p, "Please input a valid playername.");
		}
		
		
		return false;
	}

}
