package me.qbit.core.commands.mute;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class unmute implements CommandExecutor {
	util u = new util();
	messenger m = new messenger();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		if(u.isPlayerAdmin(p)) {
			if(arg3.length>0 && arg3[0]!="" && arg3[0]!=null) {
				Player target = Bukkit.getPlayer(arg3[0]);
				if(target==null) {
					m.message(p, "Player not found");
				} else {
					if(u.IsMuted(p)) {
						u.UnmutePlayer(target);
						m.message(p, "&fUnmuted "+target.getName());
						m.message(target, "&fYou have been unmuted");
					} else {
						m.message(p, "Player "+target.getName()+" is not muted");
					}
				}
			} else {
				m.message(p, "Target not provided");
			}
		}
		return true;
	}
}