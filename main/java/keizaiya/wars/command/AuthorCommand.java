package keizaiya.wars.command;

import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.Countrypro;
import keizaiya.wars.process.Playerpro;
import keizaiya.wars.process.menu;
import keizaiya.wars.process.tellraw;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class AuthorCommand {
    public static void onCountrycommnand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Boolean tomy = null;
        Player player = null;
        String plname = "null";
        WPlayer senders = null;
        if(sender instanceof Player){
            tomy = true;
            player = (Player) sender;
            plname = player.getUniqueId().toString();
            senders = Playerpro.getPlayer(player);
        }else {
            tomy = false;
            System.out.println(sender.getName());
        }
        if(cmd.getName().equalsIgnoreCase("menu")){
            if(player != null){
                Inventory inv = menu.getMenu(player);
                player.openInventory(inv);
            }
        }else if(cmd.getName().equalsIgnoreCase("chatmode")){
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("all")){
                    senders.setChatmode(0);
                    Playerpro.setPlayer(senders);
                    tellraw.Sendsystemmessage(player,"チャットモードをALLに変更しました。");
                }else if(args[0].equalsIgnoreCase("country")){
                    if(Countrypro.getCountry(senders.getCountry()) != null) {
                        senders.setChatmode(1);
                        Playerpro.setPlayer(senders);
                        tellraw.Sendsystemmessage(player,"チャットモードをCountryに変更しました。");
                    }else{
                        tellraw.Sendsystemmessage(player,"国家に所属していません。");
                    }
                }
            }
        }
    }
}
