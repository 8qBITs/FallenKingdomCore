package me.qbit.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.qbit.core.commands.discord;
import me.qbit.core.commands.feed;
import me.qbit.core.commands.flight;
import me.qbit.core.commands.gamemode;
import me.qbit.core.commands.heal;
import me.qbit.core.commands.rtp;
import me.qbit.core.commands.teleport;
import me.qbit.core.commands.tppos;
import me.qbit.core.commands.vanish;
import me.qbit.core.events.playerJoin;
import me.qbit.core.utils.PlayerList;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class Main extends JavaPlugin {
	
	messenger m = new messenger();
	util u = new util();
	static List<Player> vanished_pl = new ArrayList<Player>();
	
	
	@Override
	public void onEnable() {
		
		registerCommands();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new playerJoin(), this);
		
        BukkitScheduler scheduler3 = getServer().getScheduler();
        scheduler3.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	m.broadcastNull();
                m.broadcast("&eThank you for playing on &6&lF&e&lallen &6&lK&e&lingdom&e, make sure to join our discord server: &fhttps://discord.gg/zhrW4zC");
                m.broadcastNull();
            }
        }, 0L, 12000L);
	}
	
	public static void UpdateVanishList(Player pl, boolean state) {
		if(state && !vanished_pl.contains(pl)) {
			vanished_pl.add(pl);
			for(Player opl : Bukkit.getOnlinePlayers()) {
				if(opl.equals(pl))
					continue;
				PlayerList.getPlayerList(opl).removePlayer(pl);
				opl.hidePlayer(pl);
			}
		} else if(!state && vanished_pl.contains(pl)) {
			vanished_pl.remove(pl);
			for(Player opl : Bukkit.getOnlinePlayers()) {
				PlayerList.getPlayerList(opl).addPlayer(pl);
				if(opl.equals(pl))
					continue;
				opl.showPlayer(pl);
			}
		}
	}
	
	public static List<Player> getVanished() {
		List<Player> copy = new ArrayList<Player>(vanished_pl.size());
		for(Player p : vanished_pl) {
			copy.add(Bukkit.getPlayer(p.getUniqueId())); // deep copy
		}
		return copy;
	}
	
	public static boolean IsVanished(Player pl) {
		return pl!=null && vanished_pl.contains(pl);
	}

	private void registerCommands() {
		getCommand("fly").setExecutor(new flight());
		getCommand("gamemode").setExecutor(new gamemode());
		getCommand("tppos").setExecutor(new tppos());
		getCommand("vanish").setExecutor(new vanish());
		getCommand("rtp").setExecutor(new rtp());
		getCommand("feed").setExecutor(new feed());
		getCommand("heal").setExecutor(new heal());
		getCommand("teleport").setExecutor(new teleport());
		getCommand("discord").setExecutor(new discord());
	}
	
	public Main getMain() {
		return this;
	}
	 	
}