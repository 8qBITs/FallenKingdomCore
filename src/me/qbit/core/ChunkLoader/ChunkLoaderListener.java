package me.qbit.core.ChunkLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import me.qbit.core.Main;

public class ChunkLoaderListener implements Listener {

	  @EventHandler
	  public void onChunkUnload(ChunkUnloadEvent event) {
	    ChunkHolder myChunkLoader = Main.getMain().isPartOfChunkLoaderCollection(event.getChunk());
	    if (myChunkLoader == null)
	      return; 
	    if (myChunkLoader.isPersonalAnchor()) {
	      Player player = Main.getMain().getServer().getPlayer(myChunkLoader.getOwner());
	      if (player.isOnline())
	        event.setCancelled(true); 
	    } else {
	      event.setCancelled(true);
	    } 
	  }
	
}
