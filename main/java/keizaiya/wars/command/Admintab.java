package keizaiya.wars.command;

import keizaiya.wars.object.Country;
import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.Countrypro;
import keizaiya.wars.process.Playerpro;
import keizaiya.wars.process.Provinepro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Admintab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmdlist = new ArrayList<>();
        if(sender instanceof Player) {
            Player player = (Player) sender;
            WPlayer target = Playerpro.getPlayer(player);
            if (args.length >= 1) {
                if("war".contains(args[0])){
                    if(args.length == 1) {
                        cmdlist.add("war");
                    }else if(args.length >= 2){
                        if("declaration".contains(args[1])){
                            if(args.length == 2) {
                                cmdlist.add("declaration");
                            }else if(args.length == 3 || args.length == 4){
                                for (Country country : Countrypro.getAllCountry()){
                                    cmdlist.add(country.getName());
                                }
                            }else if(args.length == 5){
                                for(String data : Provinepro.ProvinceMap.keySet()){
                                    cmdlist.add(data);
                                }
                            }
                        }if("setflag".contains(args[1])){
                            if(args.length == 2) {
                                cmdlist.add("setflag");
                            }else if(args.length == 3){
                                for (Country country : Countrypro.getAllCountry()){
                                    cmdlist.add(country.getName());
                                }
                            }else if(args.length == 4){
                                cmdlist.add("A");
                                cmdlist.add("B");
                            }
                        }if("start".contains(args[1])){
                            if(args.length == 2) {
                                cmdlist.add("start");
                            }else if(args.length == 3){
                                for (Country country : Countrypro.getAllCountry()){
                                    cmdlist.add(country.getName());
                                }
                            }
                        }if("end".contains(args[1])){
                            if(args.length == 2) {
                                cmdlist.add("end");
                            }else if(args.length == 3){
                                for (Country country : Countrypro.getAllCountry()){
                                    cmdlist.add(country.getName());
                                }
                            }else if(args.length == 4){
                                cmdlist.add("A");
                                cmdlist.add("D");
                            }
                        }
                    }
                }if("list".contains(args[0])){
                    if(args.length == 1) {
                        cmdlist.add("list");
                    }
                }if("country".contains(args[0])){
                    if(args.length == 1) {
                        cmdlist.add("country");
                    }else if(args.length >= 2){
                        if("setPro".contains(args[1])){
                            if(args.length == 2){
                                cmdlist.add("setPro");
                            }else if(args.length == 3){
                                for (Country country : Countrypro.getAllCountry()){
                                    cmdlist.add(country.getName());
                                }
                            }else if(args.length == 4){
                                for(String data : Provinepro.ProvinceMap.keySet()){
                                    cmdlist.add(data);
                                }
                            }
                        }
                    }
                }
            }else{

            }
        }
        return cmdlist;
    }
}
