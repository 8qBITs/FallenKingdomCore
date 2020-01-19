package net.fallenkingdom.core.commands.home;

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
import net.fallenkingdom.core.util.config.HomeStorage;

public class Home implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Teleports you home."));
    private final Optional<Text> help = Optional.of(Text.of("Teleports you home."));
    private final Text usage = Text.of("/home [name]");
	
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return Utils.success;
		}

		Player p = (Player) source;
		Utils u = new Utils(p);
		Messenger msg = new Messenger(p);

		if(!(testPermission(source))) {
			msg.sendFullTitle("&cUh oh what now?", "&eYou don't have permission to use this!");
			return u.success;
		}
		
		String[] args = arguments.split(" ");

		String home = args.length!=0 && !args[0].equals("") ? args[0].toLowerCase() : "home";
		
		Location<World> hl;
		if((hl = HomeStorage.getLocation(p.getIdentifier(), home)) != null) {
			u.setBackLocation(p.getLocation());
			p.setLocation(hl);
		} else {
			msg.sendSubTitle("&cHome '"+home+"' doesn't exist!");
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
		return source.hasPermission("core.home.home");
	}

}

