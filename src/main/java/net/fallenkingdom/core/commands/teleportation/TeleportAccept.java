package net.fallenkingdom.core.commands.teleportation;

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
import net.fallenkingdom.core.util.TPAManager;
import net.fallenkingdom.core.util.Utils;

public class TeleportAccept implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Accepts another players teleport request."));
    private final Optional<Text> help = Optional.of(Text.of("Accepts another players teleport request."));
    private final Text usage = Text.of("/tpaccept");
	
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
		
		TPAManager.AcceptTpa(p);

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
		return source.hasPermission("core.tpaccept");
	}

}

