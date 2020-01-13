package net.fallenkingdom.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class RandomTeleport implements CommandCallable {

	Random rand = new Random();
	
	private final Optional<Text> desc = Optional.of(Text.of("Displays a message to all players"));
    private final Optional<Text> help = Optional.of(Text.of("Displays a message to all players. It has no color support!"));
    private final Text usage = Text.of("<message>");
	
    @Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return null;
		}
		
		Player p = (Player) source;
		
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
		p.sendMessage(Text.of("woosh"));
		
        return CommandResult.success();
    }

	@Override
	public Optional<Text> getHelp(CommandSource source) {
		// TODO Auto-generated method stub
		return help;
	}

	@Override
	public Optional<Text> getShortDescription(CommandSource source) {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public List<String> getSuggestions(CommandSource arg0, String arg1, Location<World> arg2) throws CommandException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public Text getUsage(CommandSource source) {
		// TODO Auto-generated method stub
		return usage;
	}

	@Override
	public boolean testPermission(CommandSource source) {
		// TODO Auto-generated method stub
		return source.hasPermission("core.rtp");
	}

}
