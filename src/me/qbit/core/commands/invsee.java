package me.qbit.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class invsee implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			if(!(arg3.length == 0)) {
				p.openInventory(Bukkit.getPlayer(arg3[0]).getInventory());
			} else {
				m.message(p, "No player provided.");
			}
			
		}
		
		return true;
	}
	
}
