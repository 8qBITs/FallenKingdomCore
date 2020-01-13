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

public class GameMode implements CommandCallable {

	Utils u = new Utils();
	Messenger msg = new Messenger();
	
	private final Optional<Text> desc = Optional.of(Text.of("Changes your Gamemode."));
    private final Optional<Text> help = Optional.of(Text.of("Change your gamemode."));
    private final Text usage = Text.of("/gamemode");
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		Player p = (Player) source;
		String[] args = arguments.split(" ");
		// stuff
		
		if(!(args.length == 0)) {
			switch(args[0]) {
			  case "0":
				 p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
				  msg.sendAction(p, "&eGamemode set to &fSURVIVAL");
			    break;
			  case "survival":
				 p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
				  msg.sendAction(p, "&eGamemode set to &fSURVIVAL");
				  break;
			  case "1":
				 p.offer(Keys.GAME_MODE, GameModes.CREATIVE);
				  msg.sendAction(p, "&eGamemode set to &fCREATIVE");
			    break;
			  case "creative":
				 p.offer(Keys.GAME_MODE, GameModes.CREATIVE);
				  msg.sendAction(p, "&eGamemode set to &fCREATIVE");
			    break;
			  case "2":
				 p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
				  msg.sendAction(p, "&eGamemode set to &fADVENTURE");
			    break;
			  case "adventure":
				 p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
				  msg.sendAction(p, "&eGamemode set to &fADVENTURE");
			    break;
			  case "3":
				 p.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
				  msg.sendAction(p, "&eGamemode set to &fSPECTATOR");
			    break;
			  case "spectator":
				 p.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
				  msg.sendAction(p, "&eGamemode set to &fSPECTATOR");
			    break;
			  default:
			    msg.sendAction(p, "&cSorry, this gamemode does not exist.");
			}
		} else {
			msg.sendAction(p, "&cPlease provide a gamemode.");
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
		return source.hasPermission("core.gamemode");
	}

}

