package net.fallenkingdom.core.util;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.config.BackStorage;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;

public class Utils {

	public CommandResult success = CommandResult.success();
	
	CommentedConfigurationNode backConfig = BackStorage.getConfig();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Location getBackLocation(Player p) {
		String uuid = p.getIdentifier();
		
		World world = Sponge.getServer().getWorld(backConfig.getNode(uuid, "world").getString()).get();

		int x = backConfig.getNode(uuid, "x").getInt();
		int y = backConfig.getNode(uuid, "y").getInt();
		int z= backConfig.getNode(uuid, "z").getInt();
		
		Location loc = new Location(world, x, y, z);
		
		return loc;
    	
    }
	
	@SuppressWarnings("rawtypes")
	public void setBackLocation(Location loc, Player p) {
		String uuid = p.getIdentifier();
		
		backConfig.getNode(uuid, "world").setValue(p.getWorld().getName());
		backConfig.getNode(uuid, "x").setValue(loc.getX());
		backConfig.getNode(uuid, "y").setValue(loc.getY());
		backConfig.getNode(uuid, "z").setValue(loc.getZ());
		
		BackStorage.save();
    	
    }
	
}
