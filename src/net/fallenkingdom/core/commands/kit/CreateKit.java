package net.fallenkingdom.core.commands.kit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;
import net.fallenkingdom.core.util.config.KitStorage;

public class CreateKit implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Creates kit."));
    private final Optional<Text> help = Optional.of(Text.of("Creates kit."));
    private final Text usage = Text.of("/createkit <name> [delay] [permission]");
	
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
		
		List<ItemStack> kit = new ArrayList<ItemStack>();
		
		if(args.length!=0) {
			p.getInventory().slots().forEach(slot -> {
				if(slot.peek().isPresent()) {
					kit.add(slot.peek().get());
				}
			});
		}
		
		if(args.length==0 || args[0].contentEquals("")) {			
			new Messenger(p).sendSubTitle("&cInvalid arguments");
		} else if(args.length==1) {
			KitStorage.saveKit(args[0].toLowerCase(), kit);
		} else if(args.length==2) {
			KitStorage.saveKit(args[0].toLowerCase(), kit, Utils.parseTimeFromString(args[1]));
		} else {
			KitStorage.saveKit(args[0].toLowerCase(), kit, Utils.parseTimeFromString(args[1]), true);
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
		return source.hasPermission("core.admin.createkit");
	}

}

