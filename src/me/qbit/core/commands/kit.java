package me.qbit.core.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class kit implements CommandExecutor {

	messenger m = new messenger();
	util u = new util();
	
	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		Player p = (Player) arg0;
		YamlConfiguration kits = Main.GetKitStorage();
		if(arg3.length==0) {
			String kits_s = "";
			for(String k : kits.getKeys(false)) {
				ConfigurationSection kit = kits.getConfigurationSection(k);
				if(kit.contains("permission") && !p.hasPermission("core.kit."+kit.getString("permission")) && !u.isPlayerAdminSilent(p))
					continue;
				kits_s+=k+", ";
			}
			m.message(p, "&fAvailable kits: "+kits_s.substring(0,kits_s.length()>0 ? kits_s.length()-2 : 0));
		} else {
			if((arg3[0].equalsIgnoreCase("create") || arg3[0].equalsIgnoreCase("permission") || arg3[0].equalsIgnoreCase("delay") || arg3[0].equalsIgnoreCase("delete")) && u.isPlayerAdmin(p)) {
				String kit_name = arg3[1].toLowerCase();
				if(!kits.contains(kit_name) && !arg3[0].equalsIgnoreCase("create")) {
					m.message(p, "Kit not found!");
					return true;
				}
				if(arg3[0].equalsIgnoreCase("create")) {
					if(arg3.length>=2) {
						if(kits.contains(kit_name))
							kits.set(kit_name,null);
						kits.createSection(kit_name);
						if(arg3.length==3 && (arg3[2].equalsIgnoreCase("true") || arg3[2]=="1"))
							kits.set(kit_name+".permission", true);
						int i = 0;
						for(ItemStack is : p.getInventory().getContents()) {
							if(is!=null && is.getType()!=Material.AIR) {
								kits.set(kit_name+".items."+i,is);
								i++;
							}
						}
						m.message(p, "&fKit &e"+kit_name+"&f created/edited!");
					} else {
						m.message(p, "Usage: /kit create <name> [needsPermission]");
					}
				} else if(arg3[0].equalsIgnoreCase("delete")) {
					kits.set(kit_name,null);
					m.message(p, "&fKit successfuly deleted");
				} else if(arg3[0].equalsIgnoreCase("delay")) {
					if(arg3.length>=3) {							
						kits.set(kit_name+".delay",u.parseTimeFromString(arg3[2]));
						m.message(p, "&fDelay for "+kit_name+" set to "+u.parseTimeFormat(arg3[2]));
					} else
						m.message(p, "Usage: /kit delay <name> <time_string>");
				} else {
					if(arg3.length>=3) {
						boolean perm = arg3[2].equalsIgnoreCase("true") || arg3[2]=="1";
						kits.set(kit_name+".permission",perm);
						m.message(p, "&fPermission requirement for "+kit_name+" set to "+perm);
					} else
						m.message(p, "Usage: /kit delay <name> <needsPermission>");
				}
				Main.SaveKitStorage();
			} else {
				String kit_name = arg3[0].toLowerCase();
				if(kits.contains(kit_name)) {
					if(kits.contains(kit_name+".permission") && !p.hasPermission("core.kit."+kit_name) && !u.isPlayerAdminSilent(p)) {
						m.message(p, "You don't have permission to use this kit!");
					} else {
						ConfigurationSection delays = Main.GetKitDelayStorage();
						String delay_k = p.getUniqueId().toString()+"."+kit_name;
						if(kits.contains(kit_name+".delay") && delays.contains(delay_k) && delays.getLong(delay_k)>System.currentTimeMillis()) {
							m.message(p, "You need to wait "+u.parseTimeFormat((delays.getLong(delay_k)-System.currentTimeMillis())/1000));
							return true;
						}
						for(String i : kits.getConfigurationSection(kit_name+".items").getKeys(false)) {
							p.getInventory().addItem(kits.getItemStack(kit_name+".items."+i));
						}
						p.updateInventory();
						if(kits.contains(kit_name+".delay") && !u.isPlayerAdminSilent(p)) {
							delays.set(p.getUniqueId().toString()+"."+kit_name, System.currentTimeMillis()+kits.getLong(kit_name+".delay")*1000);
							Main.SaveKitDelayStorage();
						}
						m.message(p, "&fRedeemed kit &e"+kit_name);
					}
				} else {
					m.message(p, "That kit doesn't exist");
				}
			}
		}
		return true;
	}
	
}
