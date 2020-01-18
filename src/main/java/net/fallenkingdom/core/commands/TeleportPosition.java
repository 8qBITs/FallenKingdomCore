package net.fallenkingdom.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;

public class TeleportPosition implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Teleports layer to random location."));
    private final Optional<Text> help = Optional.of(Text.of("Get teleports anywhere in between 0 - 5000 +- x,z"));
    private final Text usage = Text.of("/rtp");
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		if(!(args.length == 0)) {
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			int z = Integer.parseInt(args[2]);

			Location loc = new Location(p.getWorld(), x, y, z);
			
			PotionEffect potion = PotionEffect.builder()
			        .potionType(PotionEffectTypes.RESISTANCE)
			        .duration(200)
			        .amplifier(4)
			        .build();
			
			PotionEffectData effects = p.getOrCreate(PotionEffectData.class).get();
			effects.addElement(potion);
			
			p.offer(effects);
			
			u.setBackLocation(p.getLocation());
			p.setLocation(loc);
			msg.sendAction(String.format("&eTeleporting to: &f%d %d %d", x,y,z));
		} else {
			msg.sendAction("&cPlease provide arguments! &fx&c,&fy&c,&fz&c.");
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
		return source.hasPermission("core.admin.tppos");
	}

}
