package net.fallenkingdom.core.util;

import java.util.List;
import java.util.Optional;
import org.spongepowered.api.entity.living.player.Player;

public class TPAManager {
	
	public static List<TPA> awaiting;
	
	public static void RequestTpa(Player sender, Player receiver) {
		awaiting.add(new TPA(sender, receiver));
	}
	
	public static void RequestTpa(Player sender, Player receiver, boolean here) {
		awaiting.add(new TPA(sender, receiver, here));
	}
	
	public static void AcceptTpa(Player receiver) {
		Optional<TPA> res = awaiting.stream().filter(v -> v.receiver==receiver).findFirst();
		if(res.isPresent()) {
			res.get().Accept();
		} else {
			new Messenger(receiver).sendSubTitle("&cNo pending tpa requests");
		}
	}
	
	public static void DenyTpa(Player receiver) {
		Optional<TPA> res = awaiting.stream().filter(v -> v.receiver==receiver).findFirst();
		if(res.isPresent()) {
			res.get().Deny();
		} else {
			new Messenger(receiver).sendSubTitle("&cNo pending tpa requests");
		}
	}
}