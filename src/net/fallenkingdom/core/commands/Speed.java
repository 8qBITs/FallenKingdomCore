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

public class Speed implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Toggles flight and walk speed."));
    private final Optional<Text> help = Optional.of(Text.of("Toggles flight and walk speed."));
    private final Text usage = Text.of("/speed");
	
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

		// ERROR WARNING
		// When speed value is not provided it throws an error i have no idea how to solve it
		// Checking for null does not work
		
		if(!(args.length == 0)) {	
			if(args[0].equals("fly")) {
				double SpeedDouble = Double.parseDouble(args[1]);
				
				float speed = (float) SpeedDouble;
			    if(speed > 10 || speed < -1) {
			        msg.sendAction("&cYou have specified an argument out of range!");
			    } else {
			    	p.offer(Keys.FLYING_SPEED, Double.valueOf(0.05D * speed)).isSuccessful();
			    	msg.sendAction("&eYour flight speed has been updated.");
			    }
				
			} else if(args[0].equals("walk")) {

				double SpeedDouble = Double.parseDouble(args[1]);
				
				float speed = (float) SpeedDouble;
			    if(speed > 10 || speed < -1) {
			        msg.sendAction("&cYou have specified an argument out of range!");
			    } else {
			    	p.offer(Keys.WALKING_SPEED, Double.valueOf(0.05D * speed)).isSuccessful();
			    	msg.sendAction("&eYour walking speed has been updated.");
			    }
				
			} else {
				msg.sendAction("&cAvailable arguments: &ffly&c, &fwalk&c.");
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
		return source.hasPermission("core.admin.speed");
	}

}

