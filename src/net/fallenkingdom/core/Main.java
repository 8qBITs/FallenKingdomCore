package net.fallenkingdom.core;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.Chunk;

import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;

import net.fallenkingdom.core.chunk.ChunkHolder;
import net.fallenkingdom.core.commands.Back;
import net.fallenkingdom.core.commands.Flight;
import net.fallenkingdom.core.commands.GameMode;
import net.fallenkingdom.core.commands.RandomTeleport;
import net.fallenkingdom.core.commands.Speed;
import net.fallenkingdom.core.commands.TeleportPosition;
import net.fallenkingdom.core.events.PlayerJoinEvent;
import net.fallenkingdom.core.util.config.BackStorage;
import net.fallenkingdom.core.util.config.HomeStorage;
import net.fallenkingdom.core.util.config.MainConfig;

@Plugin(id = "fkcore", name = "FallenKingdomCore", version = "0.6", authors = "8qBIT, Elipse458")
public class Main {
	
	static Main me;
	public static ArrayList<ChunkHolder> ChunkHolders = new ArrayList<ChunkHolder>();
	//public static ArrayList<Chunk> ChunkLoadList = new ArrayList<Chunk>();
	
	@Inject
    private Logger logger;
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private File rootDir;

	@Listener
	public void onPreInit(GamePreInitializationEvent e) {
		
		// Load databases
		
		MainConfig.init(rootDir);
		MainConfig.load();
		BackStorage.init(rootDir);
		BackStorage.load();
		HomeStorage.init(rootDir);
		HomeStorage.load();
		
		// Register stuff
		
		registerCommands();
		registerEvents();
	}

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	me = this;
    	
        logger.info("Is now fully loaded.");
        

    }
    
    private void registerCommands() {
    	CommandManager cmdService = Sponge.getCommandManager();
    	cmdService.register(me, new RandomTeleport(), "rtp");
    	cmdService.register(me, new Back(), "back");
    	cmdService.register(me, new GameMode(), "gamemode", "gm");
    	cmdService.register(me, new Flight(), "fly", "flight");
    	cmdService.register(me, new Speed(), "speed");
    	cmdService.register(me, new TeleportPosition(), "tppos");
    }
    
    private void registerEvents() {
    	Sponge.getEventManager().registerListeners(me, new PlayerJoinEvent());
    }
    
    public static Object getMain() {
		return me;
	}
    
    public static Logger getLogger()
	{
		return me.logger;
	}
    
    public static ChunkHolder isPartOfChunkLoaderCollection(Chunk chunk) {
	    for (ChunkHolder chunkHolder : ChunkHolders) {
	      if (chunkHolder.isSameChunk(chunk))
	        return chunkHolder; 
	    } 
	    return null;
	  }
	
	
}
