package net.fallenkingdom.core.chunk;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Chunk;

import net.fallenkingdom.core.util.Utils;

public class ChunkHolder {

	String ChunkWorldName;
	  
	  int ChunkX;
	  int ChunkY;
	  int ChunkZ;
	  
	  String owner;
	  
	  boolean isPersonalAnchor;
	  
	  boolean keepLoaded = true;
	  
	  Utils u;
	  
	  public ChunkHolder(Chunk aThis, Player p) {
	    this.ChunkX = aThis.getPosition().getX();
	    this.ChunkY = aThis.getPosition().getY();
	    this.ChunkZ = aThis.getPosition().getZ();
	    this.ChunkWorldName = aThis.getWorld().getName();
	    this.owner = p.getName();
	    this.isPersonalAnchor = false;
		this.u = new Utils(p);
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
	      this.ChunkX == compareChunk.getPosition().getX() && 
	      this.ChunkZ == compareChunk.getPosition().getZ())
	      return true; 
	    return false;
	  }
	  
	  Chunk getChunk() {
	    return u.getWorld(this.ChunkWorldName).getChunkAtBlock(this.ChunkX, this.ChunkY,this.ChunkZ).get();
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
