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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;

public class Flight implements CommandCallable {

	Utils u = new Utils();
	Messenger msg = new Messenger();
	
	private final Optional<Text> desc = Optional.of(Text.of("Toggles flight."));
    private final Optional<Text> help = Optional.of(Text.of("Toggles flight."));
    private final Text usage = Text.of("/fly");
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		Player p = (Player) source;

		if(p.get(Keys.CAN_FLY).get() == false) {
			p.offer(Keys.CAN_FLY, true);
			msg.sendAction(p, "&eFlight enabled.");
		} else {
			p.offer(Keys.CAN_FLY, false);
			p.offer(Keys.IS_FLYING, false);
			msg.sendAction(p, "&eFlight disabled.");
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
		return source.hasPermission("core.flight");
	}

}
