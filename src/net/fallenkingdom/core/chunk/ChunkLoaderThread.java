package net.fallenkingdom.core.chunk;

import java.util.concurrent.TimeUnit;

import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Chunk;

import net.fallenkingdom.core.Main;

public class ChunkLoaderThread {

	public boolean run = false;
	  
	  public ChunkLoaderThread(Boolean run) {
		  
		this.run = run;
		
		if(run) {
			
			Task task = Task.builder().execute(() -> loadChunks())
				    .async()
				    .interval(50, TimeUnit.MILLISECONDS)
				    .name("ChunkLoader - Load active chunks.").submit(Main.getMain());
		  }
			
		}
	  
	  public void loadChunks() {
		  try {
  	        for (ChunkHolder chunkHolder : Main.getMain().ChunkHolders) {
  	          if (chunkHolder.keepLoaded) {
  	            Chunk chunk = chunkHolder.getChunk();
  	            if (!chunk.isLoaded()) {
  	              boolean load = chunk.loadChunk(true);
  	              if (!load)
  	                Main.getMain().getLogger().error("Failed to load Chunk: " + chunk.toString()); 
  	            } 
  	          } 
  	        } 
  	      } catch (Exception e) {}
	  }

	  /*
	private final Optional<ChunkTicketManager> ticketManager = Sponge.getServiceManager().provide(ChunkTicketManager.class);
	Optional<ChunkTicketManager.LoadingTicket> ticket;
	
	public boolean run = false;
	  
	  public ChunkLoaderThread() {
		run = true;
		
		if(run) {
			
			Task task = Task.builder().execute(() -> loadChunks())
				    .async()
				    .interval(30, TimeUnit.SECONDS)
				    .name("ChunkLoader - Load active chunks.").submit(Main.getMain());
		  }
			
		}
	  
	  public void loadChunks() {
		  try {
			  
  	        for (Chunk chunk : Main.ChunkLoadList) {
  	        	ticket = ticketManager.get().createTicket(Main.getMain(), chunk.getWorld());
  	        	ticket.get().forceChunk(chunk.getPosition());
  	        } 
  	      } catch (Exception e) {}
	  }
	   * */
}
