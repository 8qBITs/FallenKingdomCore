package me.qbit.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class tppos implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {

			Location loc = new Location(p.getWorld(), Integer.parseInt(arg3[0]), Integer.parseInt(arg3[1]), Integer.parseInt(arg3[2]));
			p.teleport(loc);
			
			m.title(p, "Teleported to: " + arg3[0] + " " + arg3[1] + " " + arg3[2]);
			
		}
		
		return true;
	}
	
}
