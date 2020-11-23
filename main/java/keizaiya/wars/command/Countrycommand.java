package keizaiya.wars.command;

import com.google.gson.Gson;
import keizaiya.wars.WARS;
import keizaiya.wars.object.*;
import keizaiya.wars.process.*;
import keizaiya.wars.process.chat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static keizaiya.wars.process.Countrypro.getCountryN;

public class Countrycommand {
    public static void onCountrycommnand(CommandSender sender, Command cmd, String commandLabel, String[] args){
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
        if(cmd.getName().equalsIgnoreCase("country")){
            if(args.length == 0){

            }
            else if(args.length >= 0) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length == 3) {
                        if (player != null) {
                            WPlayer wPlayer = Playerpro.getPlayer(player);
                            if (Countrypro.getCountry(wPlayer.getCountry()) != null) {
                                tellraw.Sendsystemmessage(player,"すでに国家に所属しています。");
                            }else {
                                String tag = Countrypro.getnewtag();
                                Country country = new Country(args[1], args[2], plname, tag);
                                Countrypro.setCountry(country);
                                sendLog.sendconsole("newCountryCreate", player.getDisplayName());
                                sendLog.sendconsole("Value1", country.getvalue());
                                wPlayer.setCountry(tag);
                                Playerpro.setPlayer(wPlayer);
                                sendLog.sendconsole("Value2", wPlayer.getValue());
                                tellraw.Sendsystemmessage(player,"国家「 " + country.getName() + " 」を作成しました。");
                                Countrypro.sendCountrydata("create",country,country.getName());
                            }
                        } else {
                            String tag = Countrypro.getnewtag();
                            Country country = new Country(args[1], args[2], "admin", tag);
                            Countrypro.setCountry(country);
                        }
                    }
                } else if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length == 2) {
                        if (player != null) {
                            Country countrys = Countrypro.getCountry(senders.getCountry());
                            if (countrys != null) {
                                if (countrys.getHead().equalsIgnoreCase(senders.getUuid()) || countrys.getReader().contains(senders.getUuid())) {
                                    WPlayer object = Playerpro.getPlayerN(args[1]);
                                    if(object != null) {
                                        Country country = Countrypro.getCountry(object.getCountry());
                                        if (country != null) {
                                            tellraw.Sendsystemmessage(player, "指定したプレイヤーは国家に所属しています。");
                                        } else {
                                            if (object.getPlayer() != null) {
                                                InvitePlayer(countrys.getTag(), object.getUuid(), player.getUniqueId().toString());
                                                tellraw.Sendsystemmessage(player, "招待しました。");
                                                //国招待処理
                                            }else {
                                                tellraw.Sendsystemmessage(player,"情報が入手できませんでした。");
                                            }
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"指定したプレイヤーは存在しません。");
                                    }
                                } else {
                                    tellraw.Sendsystemmessage(player, "あなたには権限がありません。");
                                }
                            } else {
                                tellraw.Sendsystemmessage(player, "あなたは国家に所属しておりません。");
                            }
                        } else {
                            sender.sendMessage("Player Only Command");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("accept")){
                    if (args.length == 1) {
                        if (player != null) {
                            Country countrys = Countrypro.getCountry(senders.getCountry());
                            if(countrys != null){
                                tellraw.Sendsystemmessage(player,"国家に所属しているため入ることができません。");
                            }else {
                                if(InviteConfirm(player.getUniqueId().toString())){
                                }else {
                                    tellraw.Sendsystemmessage(player,"招待されていません。");
                                }
                            }
                        }
                    }
                }else if(args[0].equalsIgnoreCase("request")){
                    if(args.length >= 2){
                        if(player != null) {
                            if(args[1].equalsIgnoreCase("reply")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null){
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                        if (args.length >= 3){
                                            if(args[2].equalsIgnoreCase("list")){
                                                player.sendMessage("リクエストリスト\nクリックすることで承認できます。");
                                                for(String data : country.getRequest()){
                                                    WPlayer target = Playerpro.getPlayer(data);
                                                    if(target != null){
                                                        tellraw.sendtellraw(player, tellraw.gettellrawtocommand(target.getName(),"/country request reply add " + target.getName()));
                                                    }
                                                }
                                            }else if(args[2].equalsIgnoreCase("add")){
                                                if(args.length == 4){
                                                    WPlayer player1 = Playerpro.getPlayerN(args[3]);
                                                    if(player1 != null){
                                                        Country target = Countrypro.getCountry(player1.getCountry());
                                                        if(target != null) {
                                                            tellraw.Sendsystemmessage(player,"指定したプレイヤーは国家に所属しています。");
                                                        }else {
                                                            if (country.getRequest().contains(player1.getUuid())) {
                                                                country.addUser(player1.getUuid());
                                                                Countrypro.setCountry(country);
                                                                player1.setCountry(country.getTag());
                                                                Playerpro.setPlayer(player1);
                                                                Countrypro.removeRequest(player1.getUuid());
                                                                player.sendMessage("承認しました。");
                                                                Countrypro.sendCountrydata("addmember",country,player1.getName());
                                                            }else {
                                                                tellraw.Sendsystemmessage(player,"指定したプレイヤーはリストに載っていません。");
                                                            }
                                                        }
                                                    }else {
                                                        tellraw.Sendsystemmessage(player,"指定したプレイヤーは存在しません。");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }else {
                                Country country = getCountryN(args[1]);
                                Country target = Countrypro.getCountry(senders.getCountry());
                                if (target == null) {
                                    if (country != null) {
                                        Countrypro.setRequest(senders.getUuid(), country.getName());
                                        tellraw.Sendsystemmessage(player, "リクエストを送信しました。");
                                    } else {
                                        tellraw.Sendsystemmessage(player, "指定した国家は存在していません。");
                                    }
                                } else {
                                    tellraw.Sendsystemmessage(player, "国家に所属しているため、リクエストを送信できません。");
                                }
                            }
                        }else {
                            sender.sendMessage("プレイヤーしか扱えません。");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("remove")){
                    if(args.length >= 2) {
                        if (senders != null) {
                            Country country = Countrypro.getCountry(senders.getCountry());
                            if(country != null) {
                                WPlayer target = Playerpro.getPlayerN(args[1]);
                                if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                    if (target != null) {
                                        if(target == senders){
                                            tellraw.Sendsystemmessage(player,"リーダーまたは元首では離脱することができません。");
                                        }else {
                                            if(country.getMember().contains(target.getUuid())) {
                                                if (args.length == 2) {
                                                    if (country.getHead().equalsIgnoreCase(target.getUuid())) {
                                                        player.sendMessage("あなたはこの操作が行えません。");
                                                    } else {
                                                        tellraw.sendtellraw(player, tellraw.gettellrawtocommand("離脱させるにはこのメッセージをクリックしてください。", "/Country remove " + args[1] + " confirm"));
                                                    }
                                                } else if (args.length == 3) {
                                                    if(args[2].equalsIgnoreCase("confirm")) {
                                                        if (country.getHead().equalsIgnoreCase(target.getUuid())) {
                                                            player.sendMessage("不可能です。wikiよんで");
                                                        } else {
                                                            country.removeUser(target.getUuid());
                                                            Countrypro.setCountry(country);
                                                            target.setCountry("null");
                                                            Playerpro.setPlayer(target);
                                                            tellraw.Sendsystemmessage(player,"離脱させました。");
                                                            Countrypro.sendCountrydata("removemember",country,target.getName());
                                                        }
                                                    }
                                                }
                                            }else {
                                                tellraw.Sendsystemmessage(player,"指定したプレイヤーは国家に参加していません。");
                                            }
                                        }
                                    }
                                }else {
                                    if (target != null) {
                                        if (target.equals(senders)) {
                                            if (args.length == 2) {
                                                tellraw.sendtellraw(player, tellraw.gettellrawtocommand("離脱するにはこのメッセージをクリックしてください。", "/Country remove " + args[1] + " confirm"));
                                            } else if (args.length == 3) {
                                                if (args[2].equalsIgnoreCase("confirm")) {
                                                    if(country.removeUser(senders.getUuid())){
                                                        System.out.println("remove ok");
                                                    }else { System.out.println("error");}
                                                    Countrypro.setCountry(country);
                                                    senders.setCountry("null");
                                                    Playerpro.setPlayer(senders);
                                                    tellraw.Sendsystemmessage(player,"離脱しました。");
                                                    Countrypro.sendCountrydata("removemember",country,senders.getName());
                                                }
                                            } else {
                                                tellraw.Sendsystemmessage(player, "yeah");
                                            }
                                        } else {
                                            tellraw.Sendsystemmessage(player, "権限を持っていません。");
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"プレイヤーがみつかりません");
                                    }
                                }
                            }
                        }
                    }
                }else if(args[0].equalsIgnoreCase("edit")){
                    if(args.length >= 2){
                        if(player != null) {
                            if (args[1].equalsIgnoreCase("name")) {
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null){
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())){
                                        country.setName(args[2]);
                                        Countrypro.setCountry(country);
                                        Countrypro.sendCountrydata("changename",country,country.getName());
                                        tellraw.Sendsystemmessage(player,"変更しました。");
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首しか変更することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に参加していません。");
                                }
                            }else if(args[1].equalsIgnoreCase("nickname")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null) {
                                    if (country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                        Integer yeah = Countrypro.checkhan(args[2]);
                                        String nickname = args[2].substring(0, yeah);
                                        country.setNickname(nickname);
                                        Countrypro.setCountry(country);
                                        tellraw.Sendsystemmessage(player,"変更しました。");
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首しか変更することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に参加していません。");
                                }
                            }else if(args[1].equalsIgnoreCase("promote")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null) {
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid())){
                                        WPlayer target = Playerpro.getPlayerN(args[2]);
                                        if(target != null) {
                                            if (country.getMember().contains(target.getUuid())) {
                                                if(args.length == 3){
                                                    tellraw.sendtellraw(player, tellraw.gettellrawtocommand("実行するにはこのメッセージをクリックしてください。", "/Country edit promote " + args[2] + " confirm"));
                                                }else if(args.length == 4){
                                                    if(args[3].equalsIgnoreCase("confirm")){
                                                        country.setHead(target.getUuid());
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,"変更しました。");
                                                        Countrypro.sendCountrydata("changehead",country,target.getName());
                                                    }else {
                                                        tellraw.Sendsystemmessage(player,"コマンドが違っています。");
                                                    }
                                                }
                                            }else {
                                                tellraw.Sendsystemmessage(player,"国家のメンバーではありません。");
                                            }
                                        }else {
                                            tellraw.Sendsystemmessage(player,"指定したプレイヤーはいません。");
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首しかこのコマンドを実行することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                                }
                            }else if(args[1].equalsIgnoreCase("delete")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null) {
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid())){
                                        if(args.length == 3){
                                            if(args[2].equalsIgnoreCase("confirm")){
                                                Playerpro.removeCountry(country.getTag());
                                                country.setEnd(true);
                                                Countrypro.setCountry(country);
                                                tellraw.Sendsystemmessage(player,"国家を消去しました。");
                                                Countrypro.sendCountrydata("deleteCountry",country,country.getName());
                                            }else {
                                                tellraw.Sendsystemmessage(player,"コマンドが間違っています。");
                                            }
                                        }else {
                                            tellraw.sendtellraw(player,tellraw.gettellrawtosuggest("本当に消去する場合はこちらをクリックしてください。","/country edit delete confirm"));
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首でなければこのコマンドを実行することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                                }
                            }else if(args[1].equalsIgnoreCase("settp")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null) {
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                        if(args.length == 2){
                                            country.setLocation(player.getLocation());
                                            Countrypro.setCountry(country);
                                            tellraw.Sendsystemmessage(player,"設定しました。");
                                        }else {
                                            player.sendMessage(country.getLocation().toString());
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                                }
                            }else if(args[1].equalsIgnoreCase("assistant")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null){
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())){
                                        if(args.length == 4){
                                            WPlayer target = Playerpro.getPlayerN(args[3]);
                                            if(target != null) {
                                                if(country.getMember().contains(target.getUuid())) {
                                                    if (args[2].equalsIgnoreCase("add")) {
                                                        country.addreader(target.getUuid());
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,target.getName() + " に権限をあたえました。");
                                                    } else if (args[2].equalsIgnoreCase("remove")) {
                                                        country.removereader(target.getUuid());
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,target.getName() + " の権限を剥奪しました。");
                                                    }
                                                }else {
                                                    tellraw.Sendsystemmessage(player,"指定したプレイヤーは国家に所属していません。");
                                                }
                                            }else {
                                                tellraw.Sendsystemmessage(player,"指定したプレイヤーは存在しません。");
                                            }
                                        }else {
                                            if(args.length == 3){
                                                if(args[2].equalsIgnoreCase("list")){
                                                    String data = "========= 権限リスト ========\n";
                                                    for(String target : country.getReader()){
                                                        WPlayer target2 = Playerpro.getPlayer(target);
                                                        if(target2 != null){
                                                            data = data + target2.getName() + "\n";
                                                        }
                                                    }
                                                    tellraw.Sendsystemmessage(player,data);
                                                }
                                            }
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                                }
                            }else if(args[1].equalsIgnoreCase("actEnable")){
                                Country country = Countrypro.getCountry(senders.getCountry());
                                if(country != null){
                                    if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())){
                                        if(args.length == 4){
                                            WPlayer target = Playerpro.getPlayerN(args[3]);
                                            if(target != null) {
                                                if(country.getMember().contains(target.getUuid())) {
                                                    if (args[2].equalsIgnoreCase("add")) {
                                                        country.addActPa(target.getUuid());
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,target.getName() + " にアクト権限をあたえました。");
                                                    } else if (args[2].equalsIgnoreCase("remove")) {
                                                        country.removeActPa(target.getUuid());
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,target.getName() + " のアクト権限を剥奪しました。");
                                                    }
                                                }else {
                                                    tellraw.Sendsystemmessage(player,"指定したプレイヤーは国家に所属していません。");
                                                }
                                            }else {
                                                tellraw.Sendsystemmessage(player,"指定したプレイヤーは存在しません。");
                                            }
                                        }else {
                                            if(args.length == 3){
                                                if(args[2].equalsIgnoreCase("list")){
                                                    String data = "========= アクト権限リスト ========\n";
                                                    for(String target : country.getActPa()){
                                                        WPlayer target2 = Playerpro.getPlayer(target);
                                                        if(target2 != null){
                                                            data = data + target2.getName() + "\n";
                                                        }
                                                    }
                                                    tellraw.Sendsystemmessage(player,data);
                                                }
                                            }
                                        }
                                    }else {
                                        tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                                }
                            }
                        }else {
                            sender.sendMessage("プレイヤーしか実行することができません。");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("province")) {
                    if (player != null) {
                        Provinepro.checkprovince(player,player.getLocation(),null);
                        Country country = Countrypro.getCountry(senders.getCountry());
                        if(country != null){
                            if(args.length >= 3){
                                if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                    if (args[1].equalsIgnoreCase("add")) {
                                        Province province = null;
                                        if(args[2].equalsIgnoreCase("nowposition")) {
                                            province = Provinepro.getProvince(player.getChunk());
                                            if(province != null){
                                            }else {
                                                tellraw.Sendsystemmessage(player,"そのTAGは無効です。");
                                                return;
                                            }
                                        }else {
                                            province = Provinepro.getProvince(args[2]);
                                            if(province != null){
                                            }else {
                                                tellraw.Sendsystemmessage(player,"そのTAGは無効です。");
                                                return;
                                            }
                                        }
                                        if(country.getEffective() >= 1){
                                            if(province != null){
                                                if(Countrypro.getCountry(province.getOwner()) != null){
                                                    tellraw.Sendsystemmessage(player,"すでにここは占領されています。");
                                                }else {
                                                    if(Countrypro.checkProvince(country,province)) {
                                                        province.setFacility(new HashMap<>());
                                                        province.setOwner(country.getTag());
                                                        Provinepro.setProvince(province);
                                                        List<String> list = country.getProvince();
                                                        list.add(province.gettag());
                                                        country.setProvince(list);
                                                        country.setEffective(country.getEffective() - 1);
                                                        Countrypro.setCountry(country);
                                                        chat.sendCountry(country, province.gettag() + "を占領しました。");
                                                    }else{
                                                        tellraw.Sendsystemmessage(player,"隣り合う領土でしか占領することができません。");
                                                    }
                                                }
                                            }
                                        }else {
                                            tellraw.Sendsystemmessage(player,"実効支配権がたりません。");
                                        }
                                    }else if(args[1].equalsIgnoreCase("edit")){
                                        if(args[2].equalsIgnoreCase("name")){
                                            if(args.length == 5){
                                                Province province = Provinepro.getProvince(args[3]);
                                                if(province != null){
                                                    province.setName(args[4]);
                                                    Provinepro.setProvince(province);
                                                    tellraw.Sendsystemmessage(player,"地方名を設定しました。");
                                                }
                                            }
                                        }
                                    }else if(args[1].equalsIgnoreCase("remove")){
                                        Province province = Provinepro.getProvince(args[2]);
                                        if(province != null) {
                                            if (args.length == 4) {
                                                if(args[3].equalsIgnoreCase("confirm")){
                                                    if(country.getProvince().contains(province.gettag())) {
                                                        province.setOwner("tomy");
                                                        province.setFacility( new HashMap<>());
                                                        Provinepro.setProvince(province);
                                                        List<String> data = country.getProvince();
                                                        data.remove(province.gettag());
                                                        country.setProvince(data);
                                                        Countrypro.setCountry(country);
                                                        tellraw.Sendsystemmessage(player,"領土を開放しました。");
                                                    }
                                                }
                                            } else {
                                                tellraw.sendtellraw(player, tellraw.gettellrawtosuggest("本当に開放する場合はこのコマンドをコピーして実行してください。", "/country province remove " + args[2] + " confirm"));
                                            }
                                        }else {

                                        }
                                    }else if(args[1].equalsIgnoreCase("facility")){
                                        if (args.length >= 5) {
                                            Province province = Provinepro.getProvince(args[2]);
                                            if (province != null) {
                                                if(province.getOwner().equalsIgnoreCase(country.getTag())) {
                                                    if (args[3].equalsIgnoreCase("set")) {
                                                        ItemStack stack = new ItemStack(Material.STICK);
                                                        ItemMeta meta = stack.getItemMeta();
                                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "ActItem"), PersistentDataType.STRING, "FASP");
                                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAC"), PersistentDataType.STRING, province.gettag());
                                                        if (args[4].equalsIgnoreCase("役所")) {
                                                            meta.setDisplayName("役所設定棒");
                                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 0);
                                                        } else if (args[4].equalsIgnoreCase("軍事拠点")) {
                                                            meta.setDisplayName("軍事拠点設定棒");
                                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 1);
                                                        } else if (args[4].equalsIgnoreCase("経済拠点")) {
                                                            meta.setDisplayName("経済拠点設定棒");
                                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 2);
                                                        }
                                                        List<String> lore = new ArrayList<>();
                                                        lore.add("クリックすると施設を設定することができます");
                                                        meta.setLore(lore);
                                                        stack.setItemMeta(meta);
                                                        player.getInventory().addItem(stack);
                                                        tellraw.Sendsystemmessage(player, "設定棒を付与しました。");
                                                    } else if (args[3].equalsIgnoreCase("remove")) {
                                                        Map<Integer, String> data = province.getFacility();
                                                        Integer yeah = null;
                                                        if (args[4].equalsIgnoreCase("役所")) {
                                                            yeah = 0;
                                                        } else if (args[4].equalsIgnoreCase("軍事拠点")) {
                                                            yeah = 1;
                                                        } else if (args[3].equalsIgnoreCase("経済拠点")) {
                                                            yeah = 2;
                                                        }
                                                        if (yeah != null) {
                                                            data.remove(yeah);
                                                            province.setFacility(data);
                                                            Provinepro.setProvince(province);
                                                            tellraw.Sendsystemmessage(player, "消去しました。");
                                                        }
                                                    } else if (args[3].equalsIgnoreCase("edit")) {
                                                        if (args.length >= 7) {
                                                            if (args[4].equalsIgnoreCase("name")) {
                                                                Map<Integer, String> data = province.getFacility();
                                                                Gson gson = new Gson();
                                                                if (args[5].equalsIgnoreCase("役所") && data.containsKey(0)) {
                                                                    Facility facility = gson.fromJson(data.get(0), Facility.class);
                                                                    if (facility != null) {
                                                                        facility.setName(args[6]);
                                                                        data.put(0, gson.toJson(facility, Facility.class));
                                                                    }
                                                                } else if (args[5].equalsIgnoreCase("軍事拠点") && data.containsKey(1)) {
                                                                    Facility facility = gson.fromJson(data.get(1), Facility.class);
                                                                    if (facility != null) {
                                                                        facility.setName(args[6]);
                                                                        data.put(1, gson.toJson(facility, Facility.class));
                                                                    }
                                                                } else if (args[5].equalsIgnoreCase("経済拠点") && data.containsKey(2)) {
                                                                    Facility facility = gson.fromJson(data.get(2), Facility.class);
                                                                    if (facility != null) {
                                                                        facility.setName(args[6]);
                                                                        data.put(2, gson.toJson(facility, Facility.class));
                                                                    }
                                                                }
                                                                province.setFacility(data);
                                                                Provinepro.setProvince(province);
                                                                tellraw.Sendsystemmessage(player,"設定しました。");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else {
                                    tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                                }
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                        }
                    }else {

                    }
                }else if(args[0].equalsIgnoreCase("facility")){
                    if(player != null) {
                        Country country = Countrypro.getCountry(senders.getCountry());
                        if(country != null) {
                            if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                if (args.length >= 3) {
                                    if (args[1].equalsIgnoreCase("set")) {
                                        ItemStack stack = new ItemStack(Material.STICK);
                                        ItemMeta meta = stack.getItemMeta();
                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "ActItem"), PersistentDataType.STRING, "FAS");
                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAC"), PersistentDataType.STRING, country.getTag());
                                        if (args[2].equalsIgnoreCase("国家の象徴")) {
                                            meta.setDisplayName("国家の象徴設定棒");
                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 0);
                                        } else if (args[2].equalsIgnoreCase("軍事拠点")) {
                                            meta.setDisplayName("軍事拠点設定棒");
                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 1);
                                        } else if (args[2].equalsIgnoreCase("経済拠点")) {
                                            meta.setDisplayName("経済拠点設定棒");
                                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "FAT"), PersistentDataType.INTEGER, 2);
                                        }
                                        List<String> lore = new ArrayList<>();
                                        lore.add("クリックすると施設を設定することができます");
                                        meta.setLore(lore);
                                        stack.setItemMeta(meta);
                                        player.getInventory().addItem(stack);
                                        tellraw.Sendsystemmessage(player, "設定棒を付与しました。");
                                    } else if (args[1].equalsIgnoreCase("remove")) {
                                        Map<Integer,String> data = country.getFacility();
                                        Integer yeah = null;
                                        if (args[2].equalsIgnoreCase("国家の象徴")) {
                                            yeah = 0;
                                        } else if (args[2].equalsIgnoreCase("軍事拠点")) {
                                            yeah = 1;
                                        } else if (args[2].equalsIgnoreCase("経済拠点")) {
                                            yeah = 2;
                                        }
                                        if(yeah != null){
                                            data.remove(yeah);
                                            country.setFacility(data);
                                            Countrypro.setCountry(country);
                                            tellraw.Sendsystemmessage(player,"消去しました。");
                                        }
                                    } else if (args[1].equalsIgnoreCase("edit")) {
                                        if (args.length >= 5) {
                                            if (args[2].equalsIgnoreCase("name")) {
                                                Map<Integer,String> data = country.getFacility();
                                                Gson gson = new Gson();
                                                if (args[3].equalsIgnoreCase("国家の象徴") && data.containsKey(0)) {
                                                    Facility facility = gson.fromJson(data.get(0),Facility.class);
                                                    if(facility != null){
                                                        facility.setName(args[4]);
                                                        data.put(0,gson.toJson(facility,Facility.class));
                                                    }
                                                } else if (args[3].equalsIgnoreCase("軍事拠点") && data.containsKey(1)) {
                                                    Facility facility = gson.fromJson(data.get(1),Facility.class);
                                                    if(facility != null){
                                                        facility.setName(args[4]);
                                                        data.put(1,gson.toJson(facility,Facility.class));
                                                    }
                                                } else if (args[3].equalsIgnoreCase("経済拠点") && data.containsKey(2)) {
                                                    Facility facility = gson.fromJson(data.get(2),Facility.class);
                                                    if(facility != null){
                                                        facility.setName(args[4]);
                                                        data.put(2,gson.toJson(facility,Facility.class));
                                                    }
                                                }
                                                country.setFacility(data);
                                                Countrypro.setCountry(country);
                                                tellraw.Sendsystemmessage(player,"設定しました。");
                                            }
                                        }
                                    }
                                }
                            }else {
                                tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("war")){
                    if(player != null) {
                        Country country = Countrypro.getCountry(senders.getCountry());
                        if (country != null) {
                            if (country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                                if (args.length >= 3) {
                                    if(args[1].equalsIgnoreCase("declaration")){
                                        if(args.length == 4) {
                                            Country target = Countrypro.getCountryN(args[2]);
                                            Province province = Provinepro.getProvince(args[3]);
                                            if(target != null && province != null){
                                                if(target.getProvince().contains(province.gettag())){
                                                    Wartime.Addwar(country,target,province.gettag());
                                                    System.out.println(country.getTag());
                                                    tellraw.Sendsystemmessage(player, target.getName()+ "に宣戦布告しました。");
                                                    player.sendMessage(country.getTag());
                                                }
                                            }
                                        }
                                    }else if(args[1].equalsIgnoreCase("setflag")){
                                        if(args.length == 4){
                                            WAR war = Wartime.getWAR(args[2]);
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
                                                }
                                            }
                                        }
                                    }else if(args[1].equalsIgnoreCase("start")){
                                        WAR war = Wartime.getWAR(args[2]);
                                        if(war != null){
                                            if(Wartime.startwar(war)){
                                                System.out.println("会戦開始");
                                            }else {
                                                System.out.println("エラー");
                                            }
                                        }
                                    }
                                }
                            }else {
                                tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"国家に所属していないと実行できません。");
                        }
                    }
                }else if(args[0].equalsIgnoreCase("tp")){
                    if(args.length == 4){
                        if(player != null) {
                            Country country = Countrypro.getCountry(senders.getCountry());
                            if(country != null){
                                Location location = country.getLocation();
                                if(location != null){
                                    player.teleport(location);
                                }else {
                                    player.sendMessage("TPポイントが設定されていません。");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static Map<String,BukkitTask> Invitelist = new HashMap<>();
    public static Map<String,Boolean> InviteBoolen = new HashMap<>();

    public static void InvitePlayer(String tag , String uuid , String sender){
        if(Invitelist.containsKey(tag) == false) {
            BukkitTask task = new BukkitRunnable() {
                Integer timer = WARS.config.getInt("invitelimit", 60);
                Integer now = 0;
                String Tag = tag;
                String Uuid = uuid;
                String Sender = sender;
                Boolean Send = true;
                String Cnname = Countrypro.getCountry(tag).getName();

                @Override
                public void run() {
                    Player target = null;
                    Player sender = null;
                    for(Player player : Bukkit.getOnlinePlayers()){
                        if(player.getUniqueId().toString().equalsIgnoreCase(Uuid)){
                            target = player;
                        }else if(player.getUniqueId().toString().equalsIgnoreCase(Sender)){
                            sender = player;
                        }
                    }
                    if (target != null) {
                        if(Send){
                            tellraw.sendtellraw(target,tellraw.gettellrawtocommand(Cnname + "から招待がきてます。参加する場合はクリックしてください。","/country accept"));
                            Send = false;
                        }
                        //時間経過
                    }
                    if(InviteBoolen.containsKey(Uuid)){
                        if(InviteBoolen.get(Uuid)){
                            Country country = Countrypro.getCountry(Tag);
                            country.addUser(Uuid);
                            Countrypro.setCountry(country);
                            WPlayer player = Playerpro.getPlayer(Uuid);
                            player.setCountry(country.getTag());
                            Playerpro.setPlayer(player);
                            if(target != null) {
                                tellraw.Sendsystemmessage(target, country.getName() + " に参加しました。");
                            }
                            if(sender != null) {
                                tellraw.Sendsystemmessage(sender, target.getDisplayName() + "が国家に参加しました。");
                            }
                            Inviteremove(Uuid);
                            Countrypro.sendCountrydata("addmember",country,player.getName());
                            cancel();
                        }
                    }
                    if (timer <= now) {
                        if (sender != null) {
                            tellraw.Sendsystemmessage(sender, "招待がキャンセルされました。");
                        }
                        Inviteremove(Uuid);
                        cancel();
                    }
                    now++;
                }

            }.runTaskTimer(WARS.plugin, 0, 20);
            Invitelist.put(uuid,task);
            InviteBoolen.put(uuid,false);
        }else {
            Player Sender = Bukkit.getPlayer(sender);
            if(Sender != null){
                tellraw.Sendsystemmessage(Sender,"すでに招待されています。時間を空けてから再実行してください。");
            }
        }
    }

    public static Boolean InviteConfirm(String uuid){
        if(Invitelist.containsKey(uuid) && InviteBoolen.containsKey(uuid)){
            InviteBoolen.put(uuid,true);
            return true;
        }else {
            return false;
        }
    }
    public static void Inviteremove(String uuid){
        if(Invitelist.containsKey(uuid)){
            Invitelist.get(uuid).cancel();
        }
        Invitelist.remove(uuid);
        InviteBoolen.remove(uuid);
    }

    public static void sendList(List<String> data){
        String message = "";
        for(String yeah : data){
            message = message + yeah + " ; ";
        }
        System.out.println(message);
    }

}
