package me.qbit.core.events;

import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;

import me.qbit.core.utils.messenger;
import me.qbit.core.utils.util;

public class vehicleExit implements Listener {
	util u = new util();
	messenger m = new messenger();
	
	@EventHandler
	public void onVehicleExit(VehicleExitEvent e) {
		Player p = (Player)e.getExited();
		if(e.getVehicle().getType()==EntityType.HORSE) {
			e.getVehicle().teleport(p);
		}
	}

}
