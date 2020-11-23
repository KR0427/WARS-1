package keizaiya.wars.command;

import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.Playerpro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class chatmodetab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmdlist = new ArrayList<>();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            WPlayer target = Playerpro.getPlayer(player);
            if (args.length == 1) {
                cmdlist.add("all");
                cmdlist.add("country");
            }
        }
        return cmdlist;
    }
}
