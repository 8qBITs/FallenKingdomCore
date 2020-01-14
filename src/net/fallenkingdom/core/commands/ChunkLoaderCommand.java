package net.fallenkingdom.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import net.fallenkingdom.core.chunk.ChunkLoader;

public class ChunkLoaderCommand implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Used to load chunks."));
    private final Optional<Text> help = Optional.of(Text.of("Load chunks"));
    private final Text usage = Text.of("/chunkloader");
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		
		Player p = (Player) source;
		Utils u = new Utils(p);
		Messenger msg = new Messenger(p);
		ChunkLoader cl = new ChunkLoader();
		
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		if(!(testPermission(source))) {
			msg.sendFullTitle("&cUh oh what now?", "&eYou don't have permission to use this!");
			return u.success;
		}
		
		String[] args = arguments.split(" ");

		if(!(args.length == 0)) {
			switch(args[0]) {
			  case "load":
				 try {
					cl.setNewChunk(p);
				} catch (ObjectMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    break;
			  case "unload":
				  try {
					cl.removeChunk(p);
				} catch (ObjectMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    break;
			  case "info":
				 cl.infoChunk(p);
			    break;
			  default:
			    msg.sendAction("&cCommand not found.");
			}
		} else {
			msg.sendAction("&cPlease provide arguments, <load/unload/info>");
		}

        return u.success;
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
		return source.hasPermission("core.chunkload");
	}

}

