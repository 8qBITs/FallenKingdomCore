package net.fallenkingdom.core.events;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.world.Location;

import com.google.common.reflect.TypeToken;

import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.config.MainConfig;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class PlayerJoin {
	@Listener
	public void onPlayerJoin(ClientConnectionEvent.Join e) {
		Player p = (Player)e.getSource();
		if(!p.hasPlayedBefore()) {
			Location spawn;
			try {
				if((spawn = MainConfig.getConfig().getNode("spawn").getValue(TypeToken.of(Location.class))) != null) {
					p.setLocation(spawn);
				}
			} catch (ObjectMappingException ex) {
				Main.getMain().getLogger().info("Error getting spawn");
				ex.printStackTrace();
			}
		}
	}
}
