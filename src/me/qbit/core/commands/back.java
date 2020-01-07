package me.qbit.core.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class back implements CommandExecutor {
	util u = new util();
	messenger m = new messenger();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		Location loc = u.getBackLocation(p);
		if(loc==null) {
			m.message(p, "No last location");
		} else {
			u.setBackLocation(p);
			m.message(p, "&fTeleporting back");
			p.teleport(loc);
		}
		return true;
	}
}
