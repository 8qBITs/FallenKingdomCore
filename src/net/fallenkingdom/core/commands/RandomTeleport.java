package net.fallenkingdom.core.commands;

import java.util.Random;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public class RandomTeleport implements CommandExecutor {

	Random rand = new Random();
	
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		
		if(!(src instanceof Player)) {
			return null;
		}
		
		Player p = (Player) src;
		
		int x = rand.nextInt(5000);
		int z = rand.nextInt(5000);
		int y = 150;
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Location sloc = new Location(p.getWorld(),x,y,z);
		BlockType type = sloc.getBlock().getType();
		
		for(int i=255;i>1;i--) {
			if(type!=BlockTypes.AIR) {
				y=i+2;
				break;
			}
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Location loc = new Location(p.getWorld(), x, y, z);
		
		p.setLocation(loc);
		
		// TODO Auto-generated method stub
		return null;
	}

}
