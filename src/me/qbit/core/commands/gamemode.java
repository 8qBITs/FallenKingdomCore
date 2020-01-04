package me.qbit.core.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class gamemode implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
			switch(arg3[0]) {
			  case "0":
				  p.setGameMode(GameMode.SURVIVAL);
				  m.title(p, "&eGamemode set to SURVIVAL");
			    break;
			  case "1":
				  p.setGameMode(GameMode.CREATIVE);
				  m.title(p, "&eGamemode set to CREATIVE");
			    break;
			  case "2":
				  p.setGameMode(GameMode.ADVENTURE);
				  m.title(p, "&eGamemode set to ADVENTURE");
			    break;
			  case "3":
				  p.setGameMode(GameMode.SPECTATOR);
				  m.title(p, "&eGamemode set to SPECTATOR");
			    break;
			  default:
			    m.title(p, "&cSorry, this gamemode does not exist.");
			}
		}
		
	return true;
	}
	
}
