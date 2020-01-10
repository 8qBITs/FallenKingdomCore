package me.qbit.core.ChunkLoader;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class chunkloader implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		// create command for loading unloading etc..

		return true;
	}

	@SuppressWarnings("deprecation")
	public boolean UnloadChunk(int id) {
	    if (Main.getMain().myChunkHolders.size() <= id) {
	      Chunk tempChunk = ((ChunkHolder)Main.getMain().myChunkHolders.get(id)).getChunk();
	      Main.getMain().myChunkHolders.remove(id);
	      if (tempChunk.isLoaded())
	        return tempChunk.unload(true, true);
	      return false;
	    } 
	    return false;
	  }
	  
	  @SuppressWarnings("deprecation")
	public boolean UnloadChunk(String PlayerName, boolean forceUnload) {
	    boolean success = false;
	    for (ChunkHolder chunkHolder : Main.getMain().myChunkHolders) {
	      if (chunkHolder.getOwner().equalsIgnoreCase(PlayerName)) {
	        success = true;
	        if (!chunkHolder.isPersonalAnchor || forceUnload) {
	          Chunk tempChunk = chunkHolder.getChunk();
	          tempChunk.unload(true, true);
	        } 
	      } 
	    } 
	    return success;
	  }
	  
	  private void setNewChunk(CommandSender sender, String[] args) {
	    if (sender instanceof Player) {
	      Player player = (Player)sender;
	      Chunk thisChunk = player.getLocation().getChunk();
	      if (Main.getMain().isPartOfChunkLoaderCollection(thisChunk) == null) {
	        MysqlMethods.addChunk(thisChunk, player.getName(), false);
	        player.sendMessage("Chunk is now part of Chunk loader go /chunkloader info");
	      } else {
	        player.sendMessage("This Chunk is already part of ChunkLoader go /chunkloader info");
	        return;
	      } 
	      Main.getMain().myChunkHolders.add(new ChunkHolder(Main.getMain(), thisChunk, sender, args));
	    } 
	  }
	  
	  private void removeChunk(CommandSender sender) {
	    if (sender instanceof Player) {
	      Player player = (Player)sender;
	      Chunk thisChunk = player.getLocation().getChunk();
	      ChunkHolder partOfChunkLoaderCollection = Main.getMain().isPartOfChunkLoaderCollection(thisChunk);
	      if (partOfChunkLoaderCollection == null) {
	        player.sendMessage("This Chunk is not part of ChunkLoader go /chunkloader info");
	        return;
	      } 
	      MysqlMethods.RemoveChunk(thisChunk);
	      Main.getMain().myChunkHolders.remove(partOfChunkLoaderCollection);
	      player.sendMessage("removed!");
	    } 
	  }
	  
	  private void infoChunk(CommandSender sender) {
	    if (sender instanceof Player) {
	      Player player = (Player)sender;
	      Chunk thisChunk = player.getLocation().getChunk();
	      ChunkHolder partOfChunkLoaderCollection = Main.getMain().isPartOfChunkLoaderCollection(thisChunk);
	      if (partOfChunkLoaderCollection == null) {
	        player.sendMessage("This Chunk is not part of ChunkLoader go /chunkloader info");
	        return;
	      } 
	      Chunk chunk = partOfChunkLoaderCollection.getChunk();
	      player.sendMessage("Chunk Location (x/z):" + chunk.getX() + ":" + chunk.getZ());
	      player.sendMessage("Chunk Owner = " + partOfChunkLoaderCollection.getOwner());
	      player.sendMessage("Is personalAnchor = " + partOfChunkLoaderCollection.isPersonalAnchor());
	    } 
	  }
	
}
