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

	private final Optional<Text> desc = Optional.of(Text.of("Changes your Gamemode."));
    private final Optional<Text> help = Optional.of(Text.of("Change your gamemode."));
    private final Text usage = Text.of("/gamemode");
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		
		Player p = (Player) source;
		Utils u = new Utils(p);
		Messenger msg = new Messenger(p);
		
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		if(!(testPermission(source))) {
			msg.sendFullTitle("&cUh oh what now?", "&eYou don't have permission to use this!");
			return u.success;
		}
		
		String[] args = arguments.split(" ");

		if(args.length!=0 && args[0]!="") {
            if(args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("s")) {
                p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
                msg.sendAction("&eGamemode set to &fSURVIVAL");
            } else if(args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("c")) {
                p.offer(Keys.GAME_MODE, GameModes.CREATIVE);
                msg.sendAction("&eGamemode set to &fCREATIVE");
            } else if(args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("a")) {
                p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
                msg.sendAction("&eGamemode set to &fADVENTURE");
            } else if(args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("sp")) {
                p.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
                msg.sendAction("&eGamemode set to &fSPECTATOR");
            } else {
                msg.sendAction("&cSorry, this gamemode does not exist.");
            }
        } else {
            msg.sendAction("&cPlease provide a gamemode.");
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

