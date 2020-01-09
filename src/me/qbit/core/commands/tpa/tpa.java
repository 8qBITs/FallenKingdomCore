package me.qbit.core.commands.tpa;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class tpa implements CommandExecutor {

	util u = new util();
	messenger m = new messenger();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		
		if (Main.getMain().tpaSent.contains(p)) {
			m.title(p, "&eYou currently have a tpa cooldown.");
	      } else {
	        if (arg3.length == 0) {
	          m.title(p, "&ePlease specify a player.");
	          return true;
	        } 
	        Player target = Bukkit.getServer().getPlayer(arg3[0]);
	        if (target == null) {
	        	m.title(p, "&eThat player does not exist.");
	          return true;
	        } 
	        if (p.getWorld() == target.getWorld()) {
	          Main.getMain().tpa.put(target, p);
	          if (!p.isOp())
	            Main.getMain().tpaSent.add(p); 
	          	m.title(p, "&eSent teleport request.");
	          	m.message(target, "&eNew TPA request from: &f" + p.getName() + " &euse &f/tpaccept &eto accept.");

	          Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Main.getMain(), new Runnable() {
	                public void run() {
	                  Main.getMain().tpaSent.remove(p);
	                }
	              },  30 * 20);
	          return true;
	        } 
	        m.title(p, "&eCannot teleport, player in another dimension.");
	        return true;
	      } 
	      return true;
	}

}
