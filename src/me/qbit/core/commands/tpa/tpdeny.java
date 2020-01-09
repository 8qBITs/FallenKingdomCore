package me.qbit.core.commands.tpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class tpdeny implements CommandExecutor {

	util u = new util();
	messenger m = new messenger();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player)arg0;
		
		if (Main.getMain().tpa.get(p) == null) {
			m.title(p, "&eNo teleport requests to deny.");
	        return true;
	      } 
	      if (Main.getMain().tpa.get(p) != null) {
	    	  m.title(p, "&eTeleport request denied.");
	        ((Player)Main.getMain().tpa.get(p)).sendMessage("RequestDeniedTeleport");
	        if (!((Player)Main.getMain().tpa.get(p)).isOp())
	          Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask((Plugin)this, new Runnable() {
	                public void run() {
	                  Main.getMain().tpaSent.remove(Main.getMain().tpa.get(p));
	                }
	              },  (5 * 20)); 
	        Main.getMain().tpa.put(p, null);
	        return true;
	      } 
	      return true;
	}
	
}
