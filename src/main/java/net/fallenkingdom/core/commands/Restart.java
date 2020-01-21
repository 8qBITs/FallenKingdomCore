package net.fallenkingdom.core.commands;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.fallenkingdom.core.util.AutoRestart;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;

public class Restart implements CommandCallable {

    private final Optional<Text> desc = Optional.of(Text.of("Restarts the server."));
    private final Optional<Text> help = Optional.of(Text.of("Restarts the server without automatic scheduler."));
    private final Text usage = Text.of("/restart");

    @Override
    public CommandResult process(CommandSource source, String arguments) throws CommandException {
        if(!(source instanceof Player)) {
            return Utils.success;
        }

        Player p = (Player) source;
        Utils u = new Utils(p);
        Messenger msg = new Messenger(p);

        if(!(testPermission(source))) {
            msg.sendFullTitle("&cUh oh what now?", "&eYou don't have permission to use this!");
            return u.success;
        }

        AutoRestart ar = new AutoRestart(true, true);
        msg.sendAction("&aSuccess&e, AutoRestart sequence has been initiated.");
        return u.success;
    }

    @Override
    public Optional<Text> getHelp(CommandSource source) {
        // TODO Auto-generated method stub
        return help;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource source) {
        // TODO Auto-generated method stub
        return desc;
    }

    @Override
    public List<String> getSuggestions(CommandSource arg0, String arg1, Location<World> arg2) throws CommandException {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

    @Override
    public Text getUsage(CommandSource source) {
        // TODO Auto-generated method stub
        return usage;
    }

    @Override
    public boolean testPermission(CommandSource source) {
        // TODO Auto-generated method stub
        return source.hasPermission("core.admin.heal");
    }

}

