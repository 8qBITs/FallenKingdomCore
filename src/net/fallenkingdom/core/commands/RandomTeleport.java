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

public class RandomTeleport implements CommandCallable {

	Random rand = new Random();
	Utils u = new Utils();
	Messenger msg = new Messenger();
	
	private final Optional<Text> desc = Optional.of(Text.of("Teleports layer to random location."));
    private final Optional<Text> help = Optional.of(Text.of("Get teleports anywhere in between 0 - 5000 +- x,z"));
    private final Text usage = Text.of("/rtp");
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public CommandResult process(CommandSource source, String arguments) throws CommandException {
		if(!(source instanceof Player)) {
			return u.success;
		}
		
		Player p = (Player) source;
		
		int x = rand.nextInt(5000);
		int z = rand.nextInt(5000);
		int y = 150;

		for(int i=255;i>1;i--) {
			if(new Location(p.getWorld(),x,i,z).getBlock().getType()!=BlockTypes.AIR) {
				y=i+2;
				break;
			}
		}

		Location loc = new Location(p.getWorld(), x, y, z);
		
		if(y==150) {
			PotionEffect potion = PotionEffect.builder()
			        .potionType(PotionEffectTypes.RESISTANCE)
			        .duration(200)
			        .amplifier(4)
			        .build();
			
			PotionEffectData effects = p.getOrCreate(PotionEffectData.class).get();
			effects.addElement(potion);
			
			p.offer(effects);
		}
		
		u.setBackLocation(p.getLocation(), p);
		p.setLocation(loc);
		msg.sendAction(p, String.format("&eTeleporting to: &f%d %d %d", x,y,z));
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
		return source.hasPermission("core.rtp");
	}

}
