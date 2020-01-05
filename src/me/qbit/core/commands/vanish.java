package me.qbit.core.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class vanish implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	Random random = new Random();
	
	List<String> vanished = new ArrayList<String>();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			if(!(arg3.length == 0)) {
				if(arg3[0].equalsIgnoreCase("off")) {
					p.setCanPickupItems(true);
					p.setCollidable(true);
					p.setSleepingIgnored(false);
					vanished.remove(p.getName());
					Main.UpdateVanishList(p,false);
					m.title(p, "&eYou are no longer in vanish.");
				}
			} else {
				p.setCanPickupItems(false);
				p.setCollidable(false);
				p.setSleepingIgnored(true);
				Location location = p.getLocation();
				for (int i = 0; i < 10; i++) {
		            location.getWorld().playEffect(location, Effect.SMOKE, this.random.nextInt(9));
		        }
				Main.UpdateVanishList(p,true);
				m.fullTitle(p, "&c&lPOOF!","&eYou are now vanished.");
			}
			
		}

		return true;
	}
	
}
