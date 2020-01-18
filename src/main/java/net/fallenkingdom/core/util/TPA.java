package net.fallenkingdom.core.util;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import com.flowpowered.math.vector.Vector3d;

import net.fallenkingdom.core.Main;
import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.TPAManager;

public class TPA {
	public Player sender, receiver;
	public boolean here = false;
	public TPA(Player sender, Player receiver) {
		this.sender = sender;
		this.receiver = receiver;
		receiver.sendMessages(Messenger.iCanHasColor("&8[&cCORE&8] &6"+sender.getName()+"&f requested to teleport &cto you&f! Write &6/tpaccept&f to confirm or &6/tpdeny&f to deny"));
		StartLifetime();
	}
	public TPA(Player sender, Player receiver, boolean here) {
		this.sender = sender;
		this.receiver = receiver;
		this.here = true;
		receiver.sendMessages(Messenger.iCanHasColor("&8[&cCORE&8] &6"+sender.getName()+"&f requested &cyou&f to teleport &cto them&f! Write &6/tpaccept&f to confirm or &6/tpdeny&f to deny"));
		StartLifetime();
	}
	private void StartLifetime() {
		Task.builder().execute(new TPALifetime(this)).async().delay(120, TimeUnit.SECONDS).name("TPA Expire").submit(Main.getMain());
	}
	public void Accept() {
		Task.builder().execute(new TPAcceptTimer(this)).interval(1, TimeUnit.SECONDS).name("TPA Accepted").submit(Main.getMain());
		TPAManager.awaiting.remove(this);
		new Messenger(sender).sendSubTitle("&6Tpa request accepted");
	}
	public void Deny() {
		new Messenger(receiver).sendAction("&aTpa request denied");
		new Messenger(sender).sendAction("&cTpa request was denied");
		TPAManager.awaiting.remove(this);
	}
}

class TPAcceptTimer implements Consumer<Task> {
    private int time = 6;
    private Vector3d curloc;
    private TPA tpa;
    
    public TPAcceptTimer(TPA tpa) {
    	this.tpa = tpa;
    	this.curloc = (this.tpa.here ? this.tpa.receiver : this.tpa.sender).getPosition();
    }
    
    @Override
    public void accept(Task task) {
    	time--;
    	Player p1 = tpa.here ? tpa.receiver : tpa.sender, p2 = tpa.here ? tpa.sender : tpa.receiver;
        Messenger msg1 = new Messenger(p1);
        msg1.sendAction("&6Tpa accepted. Do not move ["+time+"]");
		if(curloc.distance(p1.getPosition())>.5) {
			msg1.sendAction("&cTeleportation cancelled because of movement");
		}
		if(time<=0) {
			new Utils(p1).setBackLocation(p1.getLocation());
			p1.setLocation(p2.getLocation());
		}
		if(time<=0 || curloc.distance(p1.getPosition())>.5)		
			task.cancel();
    }
}
class TPALifetime implements Consumer<Task> {
	private TPA tpa;
	public TPALifetime(TPA tpa) {
		this.tpa = tpa;
	}
	@Override
	public void accept(Task task) {
		Optional<TPA> res = TPAManager.awaiting.stream().filter(v -> v==tpa).findFirst();
		if(res.isPresent()) {
			tpa.sender.sendMessages(Messenger.iCanHasColor("&8[&cCORE&8] &fYour tpa request has expired"));
			TPAManager.awaiting.remove(res.get());
		}
	}
}