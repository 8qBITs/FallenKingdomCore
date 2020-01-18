package net.fallenkingdom.core.commands.teleportation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;
import net.fallenkingdom.core.util.config.WarpStorage;

public class DelWarp implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Deletes a warp."));
    private final Optional<Text> help = Optional.of(Text.of("Deletes a warp."));
    private final Text usage = Text.of("/delwarp <name>");
	
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

		if(args.length==0 || args[0]=="") {
			msg.sendSubTitle("&cPlease provide a warp name");
		} else {
			Location<World> wl;
			if((wl = WarpStorage.getLocation(args[0].toLowerCase())) != null) {
				WarpStorage.saveLocation(args[0].toLowerCase(), null);
				msg.sendAction("&6Deleted '"+args[0]+"'");
			} else {
				msg.sendSubTitle("&cWarp '"+args[0]+"' doesn't exist");
			}
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
		return source.hasPermission("core.admin.delwarp");
	}

}

