package net.fallenkingdom.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import net.fallenkingdom.core.util.*;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.ProviderRegistration;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;

import net.fallenkingdom.core.commands.*;
import net.fallenkingdom.core.commands.home.*;
import net.fallenkingdom.core.commands.kit.*;
import net.fallenkingdom.core.commands.teleportation.*;
import net.fallenkingdom.core.events.*;
import net.fallenkingdom.core.util.config.BackStorage;
import net.fallenkingdom.core.util.config.HomeStorage;
import net.fallenkingdom.core.util.config.KitStorage;
import net.fallenkingdom.core.util.config.MainConfig;
import net.fallenkingdom.core.util.config.WarpStorage;
import net.luckperms.api.LuckPerms;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.world.Location;

@Plugin(id = "fallenkingdomcore", name = "FallenKingdomCore", version = "1.5", authors = "8qBIT, Elipse458")
public class Main {
	
	static Main me;
	public boolean restart = false;
	public int restart_timer = 600;
	public ArrayList<String> commands = new ArrayList<>();
	public ArrayList<Integer> tpsCollection = new ArrayList<Integer>();
	
	@Inject
    private Logger logger;
	public VanishManager vanishManager;
	public LuckPerms LuckpermsApi;
	
	
	@Inject
	@DefaultConfig(sharedRoot = false)
	private File rootDir;

	@Listener
	public void onPreInit(GamePreInitializationEvent e) {
		me = this;
		
		vanishManager = new VanishManager();
		
		// Load databases
		
		MainConfig.init(rootDir);
		MainConfig.load();
		BackStorage.init(rootDir);
		BackStorage.load();
		HomeStorage.init(rootDir);
		HomeStorage.load();
		WarpStorage.init(rootDir);
		WarpStorage.load();
		KitStorage.init(rootDir);
		KitStorage.load();
		TPAManager.awaiting = new ArrayList<TPA>();

		// Register stuff
		
		try {
			registerCommands();
			registerEvents();
			TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(KitStorage.Kit.class), new KitStorage.KitSerializer());
		} catch(Exception e1) {
			this.getLogger().error(e1.toString());
		}
		
		Optional<ProviderRegistration<LuckPerms>> provider = Sponge.getServiceManager().getRegistration(LuckPerms.class);
		if (provider.isPresent()) {
		    LuckpermsApi = provider.get().getProvider();
		}
	}

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    	
        logger.info("The Core Is now fully loaded.");
		Announcer();
		AutoRestart ar = new AutoRestart(true, false);
		TickCollection();
		//addHelpEntry();
    }
    
    private void registerCommands() {
    	CommandManager cmdService = Sponge.getCommandManager();
    	cmdService.register(me, new RandomTeleport(), "rtp");
    	cmdService.register(me, new Back(), "back");
    	cmdService.register(me, new GameMode(), "gamemode", "gm");
    	cmdService.register(me, new Flight(), "fly", "flight");
    	cmdService.register(me, new Speed(), "speed");
    	cmdService.register(me, new TeleportPosition(), "tppos");
    	cmdService.register(me, new TestCommand(), "test");
    	cmdService.register(me, new Home(), "home");
    	cmdService.register(me, new SetHome(), "sethome");
    	cmdService.register(me, new DelHome(), "delhome");
    	cmdService.register(me, new Teleport(), "tpa");
    	cmdService.register(me, new TeleportHere(), "tpahere");
    	cmdService.register(me, new TeleportAccept(), "tpaccept", "tpyes");
    	cmdService.register(me, new TeleportDeny(), "tpdeny", "tpno");
    	cmdService.register(me, new Warp(), "warp");
    	cmdService.register(me, new SetWarp(), "setwarp");
    	cmdService.register(me, new DelWarp(), "delwarp");
    	cmdService.register(me, new Vanish(), "vanish", "v");
    	cmdService.register(me, new Kit(), "kit");
    	cmdService.register(me, new CreateKit(), "createkit");
    	cmdService.register(me, new KickAll(), "kickall");
    	cmdService.register(me, new Heal(), "heal");
    	cmdService.register(me, new Feed(), "feed");
    	cmdService.register(me, new Spawn(), "spawn");
		cmdService.register(me, new Discord(), "discord");
		cmdService.register(me, new Trash(), "trash", "trash_can");
		cmdService.register(me, new Restart(), "restart", "reboot");
		cmdService.register(me, new TicksPerSecond(), "tps", "lag", "lagmonitor");
		//cmdService.register(me, new Help(), "help");
    }
    
    private void registerEvents() {
    	EventManager evtService = Sponge.getEventManager();
    	evtService.registerListeners(me, new PlayerJoin());
    	evtService.registerListeners(me, new Chat());
    	//evtService.registerListeners(me, new VanishEvents());
    }

    private void Announcer() {
		Task task = Task.builder().execute(() -> {

			try{
				MainConfig.getConfig().getNode("announcment").getString().equals(null);
			} catch(NullPointerException e) {
				try {
					MainConfig.getConfig().getNode("announcment").setValue(TypeToken.of(String.class),
							"&eThank you for playing on &6&lF&e&lallen &6&lK&e&lingdom&e, make sure to join our discord server: &fhttps://discord.gg/zhrW4zC");
				} catch (ObjectMappingException ex) {
					getLogger().error("Unable to set default announcment!");
					return;
				}
				MainConfig.save();
			}

			Sponge.getServer().getBroadcastChannel().send(Messenger.iCanHasColor("\n" + MainConfig.getConfig().getNode("announcment").getString() + "\n"));
		}).async().interval(10, TimeUnit.MINUTES).submit(this);
	}

	private void TickCollection() {

		// Collect server tick every 10 secs.

		Task task = Task.builder().execute(() -> {
			if(tpsCollection.size() > 5) {
				tpsCollection.add((int) Sponge.getServer().getTicksPerSecond());
				tpsCollection.remove(6);
			} else {
				tpsCollection.add((int) Sponge.getServer().getTicksPerSecond());
			}
		}).async().interval(10, TimeUnit.SECONDS).submit(this);
	}

	@Deprecated
	private void addHelpEntry() {
		String[] cmds = new String[] {"sethome <name>", "delhome <name>","home <name>", "back", "tpa <player>", "tpaccept", "tpdeny", "rtp", "spawn", "kit <name>"};
		for (String cmd : cmds) {
			commands.add(cmd);
		}
	}
    
    public static Main getMain() {
		return me;
	}
    
    public Logger getLogger()
	{
		return me.logger;
	}
    
}
