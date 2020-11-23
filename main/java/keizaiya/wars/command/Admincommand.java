package keizaiya.wars.command;

import keizaiya.wars.object.Country;
import keizaiya.wars.object.Province;
import keizaiya.wars.object.WAR;
import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.Countrypro;
import keizaiya.wars.process.Provinepro;
import keizaiya.wars.process.Wartime;
import keizaiya.wars.process.tellraw;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Admincommand {
    public static void onCountrycommnand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Boolean tomy = null;
        Player player = null;
        String plname = "null";
        WPlayer senders = null;
        if(cmd.getName().equalsIgnoreCase("admin")) {
            if (sender instanceof Player) {
                player = (Player) sender;
                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("war")){
                        if(args.length >= 3){
                            if(args[1].equalsIgnoreCase("declaration")){
                                if(args.length == 5) {
                                    Country country = Countrypro.getCountryN(args[2]);
                                    Country target = Countrypro.getCountryN(args[3]);
                                    Province province = Provinepro.getProvince(args[4]);
                                    if(country != null && target != null && province != null){
                                        if(target.getProvince().contains(province.gettag()) || true){
                                            Wartime.Addwar(country,target,province.gettag());
                                            System.out.println(country.getTag());
                                            tellraw.Sendsystemmessage(player,"設定しました。");
                                        }else{
                                            tellraw.Sendsystemmessage(player,"ターゲットが選択したプロヴィンスをもっていません。");
                                        }
                                    }else{
                                        tellraw.Sendsystemmessage(player,"国家名が不明か又はプロヴィンスが不正です。");
                                    }
                                }
                            }else if(args[1].equalsIgnoreCase("setflag")){
                                if(args.length == 4){
                                    Country country = Countrypro.getCountryN(args[2]);
                                    WAR war = null;
                                    if(country != null) {
                                        war = Wartime.getWAR(country.getTag());
                                    }else{
                                        tellraw.Sendsystemmessage(player,"戦争データの読み込みに失敗しました。");
                                        return;
                                    }
                                    if(war != null) {
                                        if(war.getProvince().equalsIgnoreCase(Provinepro.getProvincetag(player.getChunk()))){
                                            Location lo = new Location(null,0,0,0);
                                            if (args[3].equalsIgnoreCase("A")) {
                                                if(war.getDFAL().equals(lo) == false){
                                                    System.out.println(war.getDFAL());
                                                    war.getDFAL().getBlock().setType(Material.AIR);
                                                }
                                                war.setDFA(player.getLocation());
                                                player.getLocation().getBlock().setType(Material.BEDROCK);
                                            }else if(args[3].equalsIgnoreCase("B")){
                                                if(war.getDFBL().equals(lo) == false){
                                                    war.getDFBL().getBlock().setType(Material.AIR);
                                                }
                                                war.setDFB(player.getLocation());
                                                player.getLocation().getBlock().setType(Material.BEDROCK);
                                            }
                                            Wartime.setWAR(args[2],war);
                                            tellraw.Sendsystemmessage(player,"設定しました");
                                        }else{
                                            tellraw.Sendsystemmessage(player,"指定した範囲外のプロヴィンスを選択しています。");
                                        }
                                    }
                                }
                            }else if(args[1].equalsIgnoreCase("start")){
                                Country country = Countrypro.getCountryN(args[2]);
                                WAR war = null;
                                if(country != null) {
                                    war = Wartime.getWAR(country.getTag());
                                }else{ return; }
                                if(war != null){
                                    if(Wartime.startwar(war)){
                                        System.out.println("会戦開始");
                                    }else {
                                        System.out.println("エラー");
                                    }
                                }
                            }else if(args[1].equalsIgnoreCase("end")){
                                if(args.length == 4) {
                                    Country country = Countrypro.getCountryN(args[2]);
                                    WAR war = null;
                                    if (country != null) {
                                        war = Wartime.getWAR(country.getTag());
                                    } else {
                                        return;
                                    }
                                    if (war != null) {
                                        if(args[3].equalsIgnoreCase("A")) {
                                            Wartime.wardata.put(country.getTag(), new ArrayList<>(Arrays.asList(true, false)));
                                        }else if(args[3].equalsIgnoreCase("D")){
                                            Wartime.wardata.put(country.getTag(), new ArrayList<>(Arrays.asList(false,true)));
                                        }
                                    }
                                }
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("list")){
                        String data = "";
                        for(Country country : Countrypro.getAllCountry()){
                            if(country != null) {
                                if(country.getName() != null && country.getTag() != null) {
                                    data = data + country.getName() + " : " + country.getTag() + "\n";
                                }
                            }
                        }
                        player.sendMessage(data);
                    }else if(args[0].equalsIgnoreCase("country")){
                        if(args.length >= 2){
                            if(args[1].equalsIgnoreCase("setPro")){
                                if(args.length == 4){
                                    Country country = Countrypro.getCountryN(args[2]);
                                    Province province = Provinepro.getProvince(args[3]);
                                    if(country != null && province != null){
                                        Country target = Countrypro.getCountry(province.getOwner());
                                        if(target != null){
                                            target.getProvince().remove(province.gettag());
                                            Countrypro.setCountry(target);
                                        }
                                        province.setOwner(country.getTag());
                                        Provinepro.setProvince(province);
                                        country.getProvince().add(province.gettag());
                                        Countrypro.setCountry(country);
                                        tellraw.Sendsystemmessage(player,"設定しました。");
                                    }else{
                                        tellraw.Sendsystemmessage(player,"国家名かプロヴィンスタグが不正です。");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
