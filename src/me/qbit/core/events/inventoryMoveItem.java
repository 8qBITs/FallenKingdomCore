package me.qbit.core.events;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.block.CraftFurnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import me.qbit.core.Main;
import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class inventoryMoveItem implements Listener {
	messenger m = new messenger();
	util u = new util();
    @EventHandler
    public void onInventoryMoveItem(InventoryClickEvent event){
    	Player p = (Player)event.getWhoClicked();
	    if(event.getInventory().getType().equals(InventoryType.FURNACE) && event.getRawSlot() == 0){
	    	FurnaceInventory inv = (FurnaceInventory)event.getInventory();
	    	ItemStack old = inv.getSmelting();
	    	BukkitScheduler sch = Bukkit.getScheduler();
	        sch.runTaskLater(Main.getMain(), new Runnable() {
	            @SuppressWarnings("deprecation")
				public void run() {
	            	FurnaceInventory inv = ((CraftFurnace)event.getInventory().getHolder()).getSnapshotInventory();
	            	if(old==null && inv.getSmelting()==null && inv.getViewers().size()<1 && !p.getOpenInventory().getType().equals(InventoryType.FURNACE)) {
	            		m.message(p, "&eSpace Heaters + Furnaces are buggy right now, you can have your item back.");
	            		p.getInventory().addItem(event.getCurrentItem());
	            		p.updateInventory();
	            	}
	            }
	        }, 2L);
	    }
    }
}
