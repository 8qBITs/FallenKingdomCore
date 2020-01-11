package me.qbit.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.yaml.snakeyaml.Yaml;

import me.qbit.core.ChunkLoader.ChunkHolder;
import me.qbit.core.ChunkLoader.MysqlMethods;
import me.qbit.core.ChunkLoader.ThreadKeepChunksLoaded;
import me.qbit.core.commands.*;
import me.qbit.core.commands.home.*;
import me.qbit.core.commands.mute.*;
import me.qbit.core.commands.tpa.tpa;
import me.qbit.core.commands.tpa.tpaccept;
import me.qbit.core.commands.tpa.tpdeny;
import me.qbit.core.events.asyncPlayerChat;
import me.qbit.core.events.inventoryMoveItem;
import me.qbit.core.events.playerDeath;
import me.qbit.core.events.playerJoin;
import me.qbit.core.events.vehicleExit;
import me.qbit.core.utils.PlayerList;
import me.qbit.core.utils.configuration;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class Main extends JavaPlugin {
	
	messenger m = new messenger();
	util u = new util();
	static List<Player> vanished_pl = new ArrayList<Player>();
	public ArrayList<ChunkHolder> myChunkHolders = new ArrayList<ChunkHolder>();
	public HashMap<Player, Player> tpa = new HashMap<>();
	public ArrayList<Player> tpaSent = new ArrayList<>();
	ThreadKeepChunksLoaded threadKeepChunksLoaded;
	static Main me;
	int timer = 600;
	
	@Override
	public void onEnable() {
		me = this;

		registerCommands();
		registerEvents();
		registerDefaultConfig();
		initSchedulers();

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
	
	private void AutoRestart() {
		BukkitScheduler sch = getServer().getScheduler();
		sch.scheduleSyncRepeatingTask(this, new Runnable(){
			int timer = 601;
			final int[] times = new int[] {600,300,60,30,10};
			@Override
			public void run() {
				timer--;
				if(!Arrays.stream(times).anyMatch(i -> i == timer))
					return;
				int minutes = (int)Math.floor(timer/60), seconds = timer%60;
				m.broadcastNull();
				m.broadcast(String.format(" &4&lWARNING! &ethis server is scheduled to restart in %d minute%s %d second%s!", minutes, minutes>1 ? "s" : "", seconds, seconds>1 ? "s" : ""));
				m.broadcastNull();
				Bukkit.shutdown();
				sch.cancelAllTasks();
			}
		}, 0L, 20L);
		
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
		getCommand("home").setExecutor(new home());
		getCommand("sethome").setExecutor(new sethome());
		getCommand("delhome").setExecutor(new delhome());
		getCommand("homes").setExecutor(new homes());
		getCommand("kickall").setExecutor(new kickall());
		getCommand("enderchest").setExecutor(new enderchest());
		getCommand("invsee").setExecutor(new invsee());
		getCommand("back").setExecutor(new back());
		getCommand("mute").setExecutor(new mute());
		getCommand("unmute").setExecutor(new unmute());
		getCommand("tpa").setExecutor(new tpa());
		getCommand("tpaccept").setExecutor(new tpaccept());
		getCommand("tpdeny").setExecutor(new tpdeny());
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new playerJoin(), this);
		pm.registerEvents(new playerDeath(), this);
		pm.registerEvents(new inventoryMoveItem(), this);
		pm.registerEvents(new vehicleExit(), this);
		pm.registerEvents(new asyncPlayerChat(), this);
	}
	
	private void registerDefaultConfig() {
		configuration mainStorageClass = new configuration("config.yml");
		YamlConfiguration config = mainStorageClass.getConfig();
		
		if(config.getString("ServerName") == null) {
			config.set("ServerName", "&eUnknown server.");
			mainStorageClass.saveConfig();
		}
	}
	
	private void initSchedulers() {
		BukkitScheduler scheduler = getServer().getScheduler();
		
		// Auto restarter
		
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	DateFormat dateformat = new SimpleDateFormat("HH:mm");
	            Date date = new Date();
	            String first = "8:00";
	            String second = "16:00";
	            String third = "24:00";
	            if (dateformat.format(date).equals(first) || dateformat.format(date).equals(second) || 
	              dateformat.format(date).equals(third))
	            	AutoRestart(); 
            }
        }, 0L, 12000L);
		
		// Advertiser
		
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	m.broadcastNull();
                m.broadcast("&eThank you for playing on &6&lF&e&lallen &6&lK&e&lingdom&e, make sure to join our discord server: &fhttps://discord.gg/zhrW4zC");
                m.broadcastNull();
            }
        }, 0L, 12000L);
        
        // mute bullshittery
        
        configuration muteStorageClass = new configuration("mutes.yml");
    	YamlConfiguration mutes = muteStorageClass.getConfig();
        
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
        	@Override
        	public void run() {
        		long curtime = System.currentTimeMillis();
        		for(String uuid : mutes.getKeys(false)) {
        			long time = mutes.getLong(uuid);
        			if(time<=curtime && time!=-1) {
        				mutes.set(uuid, null);
        				m.message(Bukkit.getPlayer(UUID.fromString(uuid)), "&fYour mute has expired");
        			}
        		}
        	}
        }, 0L, 20L);
	}
	
	public void loadFromMysql() {
	    myChunkHolders = MysqlMethods.GetAllChunks();
	  }
	  
	  public ChunkHolder isPartOfChunkLoaderCollection(Chunk chunk) {
	    for (ChunkHolder chunkHolder : myChunkHolders) {
	      if (chunkHolder.isSameChunk(chunk))
	        return chunkHolder; 
	    } 
	    return null;
	  }
	
	  public void reloadChunkDatabase() {
		threadKeepChunksLoaded.run = false;
	    loadFromMysql();
	    this.threadKeepChunksLoaded = new ThreadKeepChunksLoaded();
	    System.out.println("Database reloaded!");
	  }
	
	public static Main getMain() {
		return me;
	}
	 	
}
