package me.qbit.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class discord implements CommandExecutor {
	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		m.sendnull(p);
		m.message(p, "&eJoin us here! &fhttps://discord.gg/zhrW4zC");
		m.sendnull(p);
		
		return true;
	}
}
