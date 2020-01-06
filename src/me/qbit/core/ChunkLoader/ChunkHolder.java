package me.qbit.core.ChunkLoader;

import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;

import me.qbit.core.Main;

public class ChunkHolder {

	String ChunkWorldName;
	  
	  int ChunkX;
	  
	  int ChunkZ;
	  
	  String owner;
	  
	  boolean isPersonalAnchor;
	  
	  boolean keepLoaded = true;
	  
	  public ChunkHolder(Main plugin, Chunk aThis, CommandSender sender, String[] args) {
	    this.ChunkX = aThis.getX();
	    this.ChunkZ = aThis.getZ();
	    this.ChunkWorldName = aThis.getWorld().getName();
	    this.owner = sender.getName();
	    this.isPersonalAnchor = false;
	  }
	  
	  public ChunkHolder(String Location, String owner, boolean isPersonalAnchor) {
	    String[] split = Location.split(";");
	    this.ChunkWorldName = split[0];
	    this.ChunkX = Integer.parseInt(split[1]);
	    this.ChunkZ = Integer.parseInt(split[2]);
	    this.owner = owner;
	    this.isPersonalAnchor = isPersonalAnchor;
	  }
	  
	  public String getOwner() {
	    return this.owner;
	  }
	  
	  public boolean isPersonalAnchor() {
	    return this.isPersonalAnchor;
	  }
	  
	  public boolean isSameChunk(Chunk compareChunk) {
	    if (this.ChunkWorldName.equals(compareChunk.getWorld().getName()) && 
	      this.ChunkX == compareChunk.getX() && 
	      this.ChunkZ == compareChunk.getZ())
	      return true; 
	    return false;
	  }
	  
	  Chunk getChunk() {
	    return Main.getMain().getServer().getWorld(this.ChunkWorldName).getChunkAt(this.ChunkX, this.ChunkZ);
	  }
	  
	  public String toString() {
	    return "World:" + this.ChunkWorldName + 
	      " ChunkX: " + 
	      this.ChunkX + 
	      " ChunZ: " + 
	      this.ChunkZ + 
	      " Owner: " + 
	      this.owner + 
	      " isPersonalAnchor: " + 
	      this.isPersonalAnchor;
	  }
	
}
