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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import net.fallenkingdom.core.util.Messenger;
import net.fallenkingdom.core.util.Utils;

import javax.print.DocFlavor;

public class Help implements CommandCallable {

    private final Optional<Text> desc = Optional.of(Text.of("Displays command help."));
    private final Optional<Text> help = Optional.of(Text.of("Displays help"));
    private final Text usage = Text.of("/help <page>");
    private ArrayList<String> commands = Main.getMain().commands;
    private int perPage = 6;

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

        String[] args = arguments.split(" ");

        if(args.length == 1) {
            int page;
            try{
                page = Integer.parseInt(args[0]);
            } catch(NumberFormatException e) {
                msg.sendAction("&cYou did not specify a valid page!");
                return u.success;
            }
            
            if(page > 0 && page <= getTotalPages()) {
                getPages(p, page);
            } else {
                msg.sendAction("&cYou did not specify a valid page!");
            }
            
        } else {
            msg.sendAction("&cInvalid usage! Use /help [page]");
            return u.success;
        }

        return u.success;
    }


    private int getTotalPages() {
        int totalPages = (int) Math.ceil((double)commands.size() / perPage);
        return totalPages;
    }

    private void getPages(Player p, int page) {
        Messenger msg = new Messenger(p);
        int displayPage = page;
        page--;

        int lowerBound = perPage * page;
        int upperBound;

        if (lowerBound + perPage > commands.size()) {
            upperBound = commands.size();
        } else {
            upperBound = lowerBound + perPage;
        }

        List<String> showCommands = new ArrayList<>();
        for(int i = lowerBound; i < upperBound; i++) {
            showCommands.add(commands.get(i));
        }

        p.sendMessage(msg.iCanHasColor("\n&eCommands &f[&ePage &6" + displayPage + "&e/&6" + getTotalPages() + "&f]"));
        for(String cmd : showCommands) {
            p.sendMessage(msg.iCanHasColor("&a- &8" + cmd));
        }
        p.sendMessage(Text.of(" "));

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
        return source.hasPermission("core.help");
    }

}

