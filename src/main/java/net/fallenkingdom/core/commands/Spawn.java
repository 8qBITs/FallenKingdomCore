package net.fallenkingdom.core.commands;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;
import net.fallenkingdom.core.util.config.MainConfig;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Spawn implements CommandCallable {

	private final Optional<Text> desc = Optional.of(Text.of("Teleports to spawn."));
    private final Optional<Text> help = Optional.of(Text.of("Teleports to spawn."));
    private final Text usage = Text.of("/spawn");
	
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
		
		String[] args = arguments.trim().split(" ");
		if(args.length==0 || args[0].equals("")) {
			Location<World> spawn;
			Vector3d spawn_rot, spawn_head_rot;
			try {
				if((spawn = MainConfig.getConfig().getNode("spawn").getValue(TypeToken.of(Location.class))) != null) {
					p.setLocation(spawn);
					if((spawn_rot = MainConfig.getConfig().getNode("spawn_rot").getValue(TypeToken.of(Vector3d.class)))!=null)
						p.setRotation(spawn_rot);
					if((spawn_head_rot = MainConfig.getConfig().getNode("spawn_head_rot").getValue(TypeToken.of(Vector3d.class)))!=null)
						p.setHeadRotation(spawn_head_rot);
				} else {
					msg.sendSubTitle("&cSpawn not found");
				}
			} catch (ObjectMappingException e) {
				Main.getMain().getLogger().info("Error getting spawn");
				e.printStackTrace();
			}
		} else {
			if(p.hasPermission("core.admin.spawn")) {
				if(args[0].equalsIgnoreCase("set")) {
					try {
						MainConfig.getConfig().getNode("spawn").setValue(TypeToken.of(Location.class), p.getLocation());
						MainConfig.getConfig().getNode("spawn_rot").setValue(TypeToken.of(Vector3d.class), p.getRotation());
						MainConfig.getConfig().getNode("spawn_head_rot").setValue(TypeToken.of(Vector3d.class), p.getHeadRotation());
						MainConfig.save();
					} catch (ObjectMappingException e) {
						Main.getMain().getLogger().info("Error setting spawn");
						e.printStackTrace();
					}
					msg.sendSubTitle("&6Spawn set");
				} else {
					Optional<Player> t;
					if((t = Sponge.getServer().getPlayer(args[0])).isPresent()) {
						Location spawn;
						try {
							if((spawn = MainConfig.getConfig().getNode("spawn").getValue(TypeToken.of(Location.class))) != null) {
								t.get().setLocation(spawn);
							} else {
								msg.sendSubTitle("&cSpawn not found");
							}
						} catch (ObjectMappingException e) {
							Main.getMain().getLogger().info("Error getting spawn");
							e.printStackTrace();
						}
					} else {						
						msg.sendSubTitle("&cPlayer not found");
					}
				}
			}
		}

		
		// do shit here
		
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
		return source.hasPermission("core.spawn");
	}

}

