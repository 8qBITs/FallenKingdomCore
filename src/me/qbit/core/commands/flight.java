package me.qbit.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class flight implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			if(!(arg3.length == 0)) {	
				if(arg3[0].equals("speed")) {
					double SpeedDouble = Double.parseDouble(arg3[1]);
					
					float speed = (float) SpeedDouble;
				    if(speed > 1 || speed < -1) {
				        m.title(p, "You have specified an argument out of range!");
				    } else {
				    	p.setFlySpeed(speed);
				    	m.title(p, "Your flight speed has been updated.");
				    }
				}
				 
			} else {
				if(p.getAllowFlight() == false) {
					p.setAllowFlight(true);
					m.title(p, "Flight enabled.");
				} else {
					p.setAllowFlight(false);
					m.title(p, "Flight disabled.");
				}
			}
			
		}
		
		return true;
	}

}
