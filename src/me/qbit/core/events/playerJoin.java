package me.qbit.core.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.qbit.core.Main;
import me.qbit.core.utils.PlayerList;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class playerJoin implements Listener {

	messenger m = new messenger();
	util u = new util();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		for(Player vanished : Main.getVanished()) {
			p.hidePlayer(vanished);
		}
		if(!p.hasPlayedBefore()) {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&e&lWelcome &f&l" + p.getDisplayName() + " &e&lhave fun playing!"));
		}
		m.sendnull(p);
		m.message(p, "&lWARNING! &ePlease use external heaters on your own responsibility as they are known to bug and remove items from furnaces!");
		m.sendnull(p);
		m.message(p, "&6&lINFO! &eAll server features are currently in beta anything could and will change, please be patient..!");
		m.sendnull(p);
		
		// https://bukkit.org/threads/custom-player-lists-create-your-own-tab-list-display.429333/
		
		try {
			PlayerList playerList = new PlayerList(p,PlayerList.SIZE_FOUR);
			String top = "&6&lF&e&lallen &6&lK&e&lingdom";
			String bottom = "&eEngineer's Life";
			playerList.setHeaderFooter(ChatColor.translateAlternateColorCodes('&', top),ChatColor.translateAlternateColorCodes('&', bottom));
		}catch(Exception e2){}
		
	}
	
}
