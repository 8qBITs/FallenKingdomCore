package me.qbit.core.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class playerDeath implements Listener {
	
	messenger m = new messenger();
	util u = new util();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity().getPlayer(); 
		
		e.setKeepLevel(u.getKeepXp(p));
		e.setKeepInventory(u.getKeepInventory(p));
		
		u.setBackLocation(p);
		
		if(u.IsVanished(p))
			e.setDeathMessage(null);
	}

}
