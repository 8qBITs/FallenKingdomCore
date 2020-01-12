package me.qbit.core.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class asyncPlayerChat implements Listener {
	util u = new util();
	messenger m = new messenger();

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(u.isPlayerAdmin(p)) {
			e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
		}
		if(u.IsMuted(p) && !u.isPlayerAdmin(p)) {
			m.message(p, "You're muted");
			e.setCancelled(true);
		}
	}
}
