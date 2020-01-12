package me.qbit.core.commands.mute;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class mute implements CommandExecutor {
	util u = new util();
	messenger m = new messenger();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		if(u.isPlayerAdmin(p)) {
			Player target = (arg3.length>0 && arg3[0]!=null) ? Bukkit.getPlayer(arg3[0]) : null;
			if(target!=null) {
				if(arg3.length<2 || arg3[1]=="" || arg3==null) {
					u.MutePlayer(target, -1);
					m.message(p, "&fMuted "+target.getName());
					m.message(target, "You have been muted");
				} else {
					long time = u.parseTimeFromString(arg3[1]);
					String time_str = u.parseTimeFormat(arg3[1]);
					if(time<=0) {
						m.message(p, "Invalid time");
					}
					u.MutePlayer(target,System.currentTimeMillis()+(time*1000));
					m.message(p, "&fMuted "+target.getName()+" for "+time_str);
					m.message(p, "You have been muted for &c"+time_str);
				}
			} else {
				m.message(p, "Player not found");
			}
		}
		return true;
	}
}