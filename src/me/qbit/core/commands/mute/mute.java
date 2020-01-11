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
	Pattern pattern = Pattern.compile("\\d+[hmsdyHMSDY]");
	
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
					Matcher matcher = pattern.matcher(arg3[1]);
					long time = 0;
					String time_str = "";
					while(matcher.find()) {
						String s = matcher.group();
						s = s.toLowerCase();
						String clean_s = s.replaceAll("[hmsdy]", "");
						if(s.contains("y")) {
							time+=Integer.parseInt(clean_s)*365*24*60*60;
							time_str+=clean_s+" year(s) ";
						} else if(s.contains("d")) {
							time+=Integer.parseInt(clean_s)*24*60*60;
							time_str+=clean_s+" day(s) ";
						} else if(s.contains("h")) {
							time+=Integer.parseInt(clean_s)*60*60;
							time_str+=clean_s+" hour(s) ";
						} else if(s.contains("m")) {
							time+=Integer.parseInt(clean_s)*60;
							time_str+=clean_s+" minute(s) ";
						} else if(s.contains("s")) {
							time+=Integer.parseInt(clean_s);
							time_str+=clean_s+" second(s) ";
						}
					}
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