package me.qbit.core;

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
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class Main extends JavaPlugin {
	
	messenger m = new messenger();
	util u = new util();
	
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
