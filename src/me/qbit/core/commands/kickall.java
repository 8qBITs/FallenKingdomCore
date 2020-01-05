package me.qbit.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class kickall implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
	            if (!pl.isOp()) {
	                pl.kickPlayer(ChatColor.translateAlternateColorCodes('&', arg3[0]));
	            }
	        }
			
		}
		
		return true;
	}
	
}