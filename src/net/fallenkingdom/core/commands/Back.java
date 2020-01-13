package net.fallenkingdom.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

public class Back implements CommandCallable {

	Utils u = new Utils();
	Messenger msg = new Messenger();
	
	private final Optional<Text> desc = Optional.of(Text.of("Teleports you to previous location."));
    private final Optional<Text> help = Optional.of(Text.of("Get teleported to your previous location."));
    private final Text usage = Text.of("/back");
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		Player p = (Player) source;
		
		if(u.getBackLocation(p) != null) {
			Location current = p.getLocation();
			msg.sendAction(p, String.format("&eTeleporting back."));
			p.setLocation(u.getBackLocation(p));
			u.setBackLocation(current, p);
			return u.success;
		}
		
		msg.sendAction(p, String.format("&eNo previous locations found."));
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
		return source.hasPermission("core.back");
	}

}

