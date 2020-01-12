package net.fallenkingdom.core;

import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import com.google.inject.Inject;

@Plugin(id = "fkcore", name = "FallenKingdomCore", version = "1.0", authors = "8qBIT, Elipse458")
public class Main {
	
	static Main me;

	@Listener
	public void onPreInit(GamePreInitializationEvent e) {
		me = this;
		// load and init config
	}
	
	@Inject
    private Logger logger;
	
	public Object getMain() {
		return me;
	}

    @Listener
    public void onServerStart(GameStartedServerEvent event) {

        logger.info("It's working?.");
        

    }
	
	
}
