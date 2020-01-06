package me.qbit.core.ChunkLoader;

import org.bukkit.Chunk;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.qbit.core.Main;

public class ThreadKeepChunksLoaded {

	public boolean run = false;
	  
	  public ThreadKeepChunksLoaded() {
		  
		run = true;
		
		if(run) {
			
			BukkitScheduler scheduler = Main.getMain().getServer().getScheduler();
		      scheduler.scheduleSyncRepeatingTask((Plugin) this, new Runnable() {
		          @Override
		          public void run() {
		        	  try {
		        	        for (ChunkHolder chunkHolder : Main.getMain().myChunkHolders) {
		        	          if (chunkHolder.keepLoaded) {
		        	            Chunk chunk = chunkHolder.getChunk();
		        	            if (!chunk.isLoaded()) {
		        	              boolean load = chunk.load();
		        	              if (!load)
		        	                System.out.println("Failed to load Chunk: " + chunk.toString()); 
		        	            } 
		        	          } 
		        	        } 
		        	      } catch (Exception e) {}
	  
		          }
		      }, 0L, 100L);
		  }
			
		}
}
