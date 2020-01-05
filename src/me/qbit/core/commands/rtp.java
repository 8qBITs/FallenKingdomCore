package me.qbit.core.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class rtp implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	Random rand = new Random();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		
		int x = rand.nextInt(5000);
		int z = rand.nextInt(5000);
		
		Location loc = new Location(p.getWorld(), x, 150,z);
		p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 4));
		p.teleport(loc);
		
		m.sendnull(p);
		m.title(p, "Teleporting to: " + x + " 150 " + z);
		
		return true;
	}
	
}