package me.qbit.core.commands;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
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
		int y = 150;
		
		for(int i=255;i>1;i--) {
			if(new Location(p.getWorld(),x,i,z).getBlock().getType()!=Material.AIR) {
				y=i+2;
				break;
			}
		}
		
		Location loc = new Location(p.getWorld(), x, y, z);
		if(y==150)
			p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 4));
		p.teleport(loc);
		
		m.sendnull(p);
		m.title(p, String.format("Teleporting to: %d %d %d", x,y,z)); 
		
		return true;
	}
	
}
