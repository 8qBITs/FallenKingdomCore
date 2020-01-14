package net.fallenkingdom.core.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import net.fallenkingdom.core.chunk.ChunkLoader;

public class PlayerJoinEvent implements EventListener<ClientConnectionEvent.Join>{

	ChunkLoader cl = new ChunkLoader();
	
	@Override
	public void handle(ClientConnectionEvent.Join event) throws Exception {
		Player p = (Player)event.getSource();
		if(p.getJoinData().lastPlayed() == null) {
			cl.addNewUser(p.getUniqueId().toString());
		} else {
			// do what?
			
		}
		
	}

}
