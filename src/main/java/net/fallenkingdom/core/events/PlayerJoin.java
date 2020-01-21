package net.fallenkingdom.core.events;

import net.fallenkingdom.core.util.Messenger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.tab.TabList;
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
		Messenger msg = new Messenger(p);

		if(!p.hasPlayedBefore()) {
			Location spawn;
			try {
				if((spawn = MainConfig.getConfig().getNode("spawn").getValue(TypeToken.of(Location.class))) != null) {
					p.setLocation(spawn);
				}
			} catch (ObjectMappingException err) {
				Main.getMain().getLogger().info("Error getting spawn");
				err.printStackTrace();
			}
		}

		TabList tablist = p.getTabList();
		tablist.setHeader(msg.iCanHasColor("&6&lF&e&lallen &6&lK&e&lingdom"));

		try{
			MainConfig.getConfig().getNode("server-name").getString().equals(null);
		} catch(NullPointerException err) {
			try {
				MainConfig.getConfig().getNode("server-name").setValue(TypeToken.of(String.class),
						"&fhttps://fallenkingdom.net");
			} catch (ObjectMappingException ex) {
				Main.getMain().getLogger().error("Unable to set default server name!");
				return;
			}
			MainConfig.save();
		}

		tablist.setFooter(msg.iCanHasColor(MainConfig.getConfig().getNode("server-name").getString()));

	}
}
