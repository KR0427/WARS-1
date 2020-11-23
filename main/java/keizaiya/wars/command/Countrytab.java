package keizaiya.wars.command;

import keizaiya.wars.object.Country;
import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.Countrypro;
import keizaiya.wars.process.Playerpro;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Countrytab implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> cmdlist = new ArrayList<>();
        if(sender instanceof Player){
            Player player = (Player) sender;
            WPlayer target = Playerpro.getPlayer(player);
            if(args.length == 1){
                if("create".contains(args[0])){
                    cmdlist.add("create");
                }if("invite".contains(args[0])){
                    cmdlist.add("invite");
                }if("request".contains(args[0])){
                    cmdlist.add("request");
                }if("remove".contains(args[0])){
                    cmdlist.add("remove");
                }if("edit".contains(args[0])){
                    cmdlist.add("edit");
                }if("province".contains(args[0])){
                    cmdlist.add("province");
                }if("facility".contains(args[0])){
                    cmdlist.add("facility");
                }
                return cmdlist;
            }else if(args.length == 2){
                if("invite".contains(args[0])){
                    for(WPlayer player1 : Playerpro.getAllPlayer()){
                        Country country = Countrypro.getCountry(player1.getCountry());
                        if(country != null){
                        }else {
                            cmdlist.add(player1.getName());
                        }
                    }
                }else if("request".contains(args[0])){
                    if(Countrypro.getCountry(target.getCountry()) != null){
                        cmdlist.add("reply");
                    }else{
                        for (Country country : Countrypro.getAllCountry()){
                            cmdlist.add(country.getName());
                        }
                    }
                }else if("remove".contains(args[0])){
                    Country country = Countrypro.getCountry(target.getCountry());
                    if(country != null){
                        if(country.getHead().equalsIgnoreCase(target.getUuid()) || country.getReader().contains(target.getUuid())){
                            for(String ter : country.getMember()){
                                cmdlist.add(ter);
                            }
                            cmdlist.remove(target.getUuid());
                            cmdlist = Playerpro.chengeUN(cmdlist);
                        }else{
                            cmdlist.add(target.getUuid());
                            cmdlist = Playerpro.chengeUN(cmdlist);
                        }
                    }
                }else if("edit".contains(args[0])){
                    if("name".contains(args[1])){
                        cmdlist.add("name");
                    }if("nickname".contains(args[1])){
                        cmdlist.add("nickname");
                    }if("promote".contains(args[1])){
                        cmdlist.add("promote");
                    }if("delete".contains(args[1])){
                        cmdlist.add("delete");
                    }if("settp".contains(args[1])){
                        cmdlist.add("settp");
                    }if("assistant".contains(args[1])){
                        cmdlist.add("assistant");
                    }if("actEnable".contains(args[1])){
                        cmdlist.add("actEnable");
                    }
                }
                return cmdlist;
            }else if(args.length == 3){
                if("edit".contains(args[0])){
                    if("promote".contains(args[1])){
                        Country country = Countrypro.getCountry(target.getCountry());
                        if(country != null){
                            if(country.getHead().equalsIgnoreCase(target.getUuid())){
                                for(String ter : country.getMember()){
                                    cmdlist.add(ter);
                                }
                                cmdlist.remove(target.getUuid());
                                cmdlist = Playerpro.chengeUN(cmdlist);
                            }
                        }
                    }if("assistant".contains(args[1]) || "actEnable".contains(args[1])){
                        if("add".contains(args[2])){
                            cmdlist.add("add");
                        }if("remove".contains(args[2])){
                            cmdlist.add("remove");
                        }if("list".contains(args[2])){
                            cmdlist.add("list");
                        }
                    }
                }
            }else if(args.length == 4){
                if("edit".contains(args[0])){
                    if("assistant".contains(args[1])){
                        Country country = Countrypro.getCountry(target.getCountry());
                        if(country != null){
                            if(country.getHead().equalsIgnoreCase(target.getUuid()) || country.getReader().contains(target.getUuid())){
                                for(String ter : country.getMember()){
                                    cmdlist.add(ter);
                                }
                                cmdlist.remove(target.getUuid());
                                cmdlist = Playerpro.chengeUN(cmdlist);
                            }
                        }
                    }if("actEnable".contains(args[1])){
                        Country country = Countrypro.getCountry(target.getCountry());
                        if(country != null){
                            if(country.getHead().equalsIgnoreCase(target.getUuid())){
                                for(String ter : country.getMember()){
                                    cmdlist.add(ter);
                                }
                                cmdlist.remove(target.getUuid());
                                cmdlist = Playerpro.chengeUN(cmdlist);
                            }
                        }
                    }
                }
            }
        }
        return cmdlist;
    }
}
