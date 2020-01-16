package net.fallenkingdom.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.*;
import org.spongepowered.api.scheduler.Task;

import net.fallenkingdom.core.Main;

public class VanishManager {
	
	private List<Player> vanished;
	
	public VanishManager() {
		vanished = new ArrayList<Player>();
		Task.builder().async().execute(new Runnable() {
			@Override
			public void run() {
				vanished.forEach((p)->{
					new Messenger(p).sendAction("&aYou are vanished");
				});
			}
		}).interval(1, TimeUnit.SECONDS).submit(Main.getMain());
	}
	
	public boolean IsVanished(Player p) {
		return vanished.contains(p);
	}
	
	public void ToggleVanish(Player p) {
		boolean toggle = !vanished.contains(p);
		if(toggle)
			vanished.add(p);
		else
			vanished.remove(p);
		
		p.setSleepingIgnored(toggle);
		p.offer(Keys.INVISIBLE, toggle);
		p.offer(Keys.VANISH, toggle);
		p.offer(Keys.VANISH_IGNORES_COLLISION, toggle);
		p.offer(Keys.VANISH_PREVENTS_TARGETING, toggle);
		
		new Messenger(p).sendTitle("&6POOF! You are now "+(toggle ? "in" : "")+"visible");
	}

}
