package net.fallenkingdom.core;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import com.google.inject.Inject;

import net.fallenkingdom.core.commands.RandomTeleport;

@Plugin(id = "fkcore", name = "FallenKingdomCore", version = "1.0", authors = "8qBIT, Elipse458")
public class Main {
	
	static Main me;

	@Listener
	public void onPreInit(GamePreInitializationEvent e) {
		me = this;
		registerCommands();
		// load and init config
	}
	
	@Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

    	
        logger.info("It's working?.");
        

    }
    
    private void registerCommands() {
    	CommandManager cmdService = Sponge.getCommandManager();
    	cmdService.register(me, new RandomTeleport(), "rtp");
    }
    
    public Object getMain() {
		return me;
	}
    
    public static Logger getLogger()
	{
		return me.logger;
	}
	
	
}
