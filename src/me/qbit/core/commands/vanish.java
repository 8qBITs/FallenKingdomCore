package me.qbit.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class vanish implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		if (u.isPlayerAdmin(p)) {
		
			if(!(arg3.length == 0)) {
				if(arg3[0].equalsIgnoreCase("off")) {
					p.removePotionEffect(PotionEffectType.INVISIBILITY);
					m.title(p, "&eYou are no longer in vanish.");
				}
			} else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 2));
				m.fullTitle(p, "&c&lSPOOKY!","&eYou are now vanished.");
			}
			
		}

		return true;
	}
	
}
