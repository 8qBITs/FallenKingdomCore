package net.fallenkingdom.core.chunk;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Chunk;

import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.config.ChunkStorage;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class ChunkLoader {

	CommentedConfigurationNode chunkStorage = ChunkStorage.getConfig();
	
	public void setNewChunk(Player p) {
		Messenger msg = new Messenger(p);
		String uuid = p.getUniqueId().toString();
		
		int chunks_avail = chunkStorage.getNode(uuid, "available-chunks").getInt();
		
		if(chunks_avail > 0) {
			Chunk current = (Chunk) p.getLocation().getChunkPosition();
			
			if(Main.isPartOfChunkLoaderCollection(current) == null) {
				chunks_avail--;
				chunkStorage.getNode(uuid, "available-chunks").setValue(chunks_avail);
					
				String cname = 
						current.getPosition().getX() + "." +
						current.getPosition().getY() + "." +
						current.getPosition().getZ();
					
				chunkStorage.getNode(uuid, "chunks", cname).setValue(null);
				// SAVE CHUNK TO DATABASE
				
				ChunkStorage.save();
				ChunkHolder ch = new ChunkHolder(current, p);
				Main.ChunkHolders.add(ch);
				msg.sendAction("&cStarted loading chunk at: &f" + current.getPosition().getX() + ":" + current.getPosition().getX());
			} else {
				msg.sendAction("&cThis chunk is already chunkloaded.");
			}
			
		} else {
			msg.sendAction("&cYou don't have any chunk tickets.");
		}
		
	}
	
	public void removeChunk(Player p) {
		Messenger msg = new Messenger(p);
		String uuid = p.getUniqueId().toString();
		
		int chunks_avail = chunkStorage.getNode(uuid, "available-chunks").getInt();
		
		Chunk current = (Chunk) p.getLocation().getChunkPosition();
		
		if(Main.isPartOfChunkLoaderCollection(current) != null) {
			chunks_avail++;
			chunkStorage.getNode(uuid, "available-chunks").setValue(chunks_avail);
			
			String cname = 
					current.getPosition().getX() + "." +
					current.getPosition().getY() + "." +
					current.getPosition().getZ();
			chunkStorage.getNode(uuid, "chunks").removeChild(cname);
			// REMOVE CHUNK FROM DATABASE
			
			ChunkStorage.save();
			ChunkHolder ch = new ChunkHolder(current, p);
			Main.ChunkHolders.remove(ch);
			msg.sendAction("&cUnloaded chunk at: &f" + current.getPosition().getX() + ":" + current.getPosition().getX());
		} else {
			msg.sendAction("&cThis chunk is not being chunkloaded.");
		}
		
	}
	
	public void infoChunk(Player p) {
		Messenger msg = new Messenger(p);

		Chunk current = (Chunk) p.getLocation().getChunkPosition();
		ChunkHolder partOfChunkLoaderCollection = Main.isPartOfChunkLoaderCollection(current);
		
		if(partOfChunkLoaderCollection != null) {
			ChunkHolder ch = new ChunkHolder(current, p);
			Main.ChunkHolders.remove(ch);
			Chunk chunk = partOfChunkLoaderCollection.getChunk();
			p.sendMessages(Text.of(" "), 
					Text.of("Chunk Location (x/z): " + chunk.getPosition().getX() + ":" + chunk.getPosition().getX()),
					Text.of("Chunk Owner = " + partOfChunkLoaderCollection.getOwner()), 
					Text.of("Is personalAnchor = " + partOfChunkLoaderCollection.isPersonalAnchor()));
		} else {
			msg.sendAction("&cThis chunk is not being chunkloaded.");
		}
	}
	
	public void addNewUser(String uuid) {
		chunkStorage.getNode(uuid, "available-chunks").setValue(0);
		chunkStorage.getNode(uuid, "chunks").setValue(null);
		
		ChunkStorage.save();
	}
	
	
}
