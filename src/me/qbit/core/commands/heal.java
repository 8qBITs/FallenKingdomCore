package me.qbit.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class heal implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			p.setFoodLevel(20);
			p.setHealth(20.00);
			p.setFireTicks(0);
			
			m.title(p, "&eYou have been healed.");
			
		}
		
		return true;
	}
	
}
