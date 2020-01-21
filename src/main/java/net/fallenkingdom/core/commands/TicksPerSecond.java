package net.fallenkingdom.core.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import net.fallenkingdom.core.Main;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.AutoRestart;
import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;

public class TicksPerSecond implements CommandCallable {

    private final Optional<Text> desc = Optional.of(Text.of("Get current and average TPS over 1 minute."));
    private final Optional<Text> help = Optional.of(Text.of("Get TPS display."));
    private final Text usage = Text.of("/tps");

    @Override
    public CommandResult process(CommandSource source, String arguments) throws CommandException {
        if(!(source instanceof Player)) {
            return Utils.success;
        }

        Player p = (Player) source;
        Utils u = new Utils(p);
        Messenger msg = new Messenger(p);

        String[] args = arguments.split(" ");

        double avgTicks = calculateAverage(Main.getMain().tpsCollection);

        p.sendMessage(msg.iCanHasColor("\n &6&lF&e&lallen &6&lK&e&lingdom &f-> &a&lTicks per second monitor.\n" +
                " &8- &eAverage last minute&f: &a" + calculateAverage(Main.getMain().tpsCollection) + "\n" +
                " &8- &eCurrent&f: &a" + Sponge.getServer().getTicksPerSecond() + "\n"));

        if(avgTicks > 15.00 && p.getConnection().getLatency() > 100) {
            p.sendMessage(msg.iCanHasColor("&eIs this server lagging? &a&lNo.\n &a&lSolution&f -> &cYour ping is above 100 please check your internet connection."));
        } else {
            p.sendMessage(msg.iCanHasColor("&eIs this server lagging? &c&lPossibly."));
        }

        return u.success;
    }

    private double calculateAverage(ArrayList<Integer> marks) {
        Integer sum = 0;
        if(!marks.isEmpty()) {
            for (Integer mark : marks) {
                sum += mark;
            }
            return sum.doubleValue() / marks.size();
        }
        return sum;
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
        return source.hasPermission("core.tps");
    }

}

