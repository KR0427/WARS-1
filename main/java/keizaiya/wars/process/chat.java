package keizaiya.wars.process;

import keizaiya.wars.WARS;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class chat {
    public static void sendAll(Player player , String message){
        if(message != null) {
                for(Player player1 : Bukkit.getOnlinePlayers()){
                    tellraw.Sendsystemmessage(player1,message);
                }
        }
    }
    public static void sendCountry(Country country, String message){
        if(message != null){
            if(country != null){
                for(Player player1 : Bukkit.getOnlinePlayers()){
                    WPlayer player11 = Playerpro.getPlayer(player1);
                    if(player11 != null){
                        Country country1 = Countrypro.getCountry(player11.getCountry());
                        if(country1 != null){
                            if(country1.getTag().equalsIgnoreCase(country.getTag())){
                                player1.sendMessage(message);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void chat(AsyncPlayerChatEvent e) {
        WPlayer player2 = Playerpro.getPlayer(e.getPlayer());
        Integer chatmode = player2.getChatmode();
        String messagemot = e.getMessage();
        Boolean noneIME = true;
        Player sender = e.getPlayer();
        if (e.getMessage().startsWith("!")) {
            chatmode = 0;
            messagemot = messagemot.substring(1, messagemot.length());
        }
        if (messagemot.startsWith("#")) {
            noneIME = false;
            messagemot = messagemot.substring(1, messagemot.length());
            if (messagemot.startsWith("!")) {
                chatmode = 0;
                messagemot = messagemot.substring(1, messagemot.length());
            }
        }
        String messagemot2 = chengewoad.chengeword(messagemot);
        String result = "";
        Integer iti = 0;
        Integer iti2 = 0;
        String data = messagemot2;
        if(noneIME) {
            for (int i = 0; i <= messagemot2.length(); i++) {
                if (data.contains("&")) {
                    iti = data.indexOf("&");
                    if(iti != 0){
                        result = result + googleIME.convByGoogleIME(japanese.conv(data.substring(0,iti)));
                    }
                    if (data.length() >= iti + 2) {
                        if (data.substring(iti + 2, data.length()).contains("&")) {
                            iti2 = data.substring(iti + 2).indexOf("&");
                            result = result + data.substring(iti, iti + 2) + googleIME.convByGoogleIME(japanese.conv(data.substring(iti + 2, iti2 + 2)));
                            data = data.substring(iti2 + 2, data.length());
                        } else {
                            result = result + data.substring(iti, iti + 2) + googleIME.convByGoogleIME(japanese.conv(data.substring(iti + 2, data.length())));
                            break;
                        }
                    } else {
                        result = result + googleIME.convByGoogleIME(japanese.conv(data));
                        break;
                    }
                } else {
                    result = result + googleIME.convByGoogleIME(japanese.conv(data));
                    break;
                }
            }
        }
        System.out.println(result);
        String Countryname = "";
        String tag = null;
        Country country = Countrypro.getCountry(player2.getCountry());
        System.out.println(chatmode);
        if (country != null) {
            Countryname = "§e" + country.getNickname() + "§8";
        }else {
            player2.setChatmode(0);
            Playerpro.setPlayer(player2);
            chatmode = 0;
            Countryname = "§e放浪者§8";
        }
        if( chatmode == 100){
            Countryname = "§cAdmin§8";
        }

        String message2 = "";
        Boolean nihongoin = false;
        for(String tango : nihongo){
            if(e.getMessage().contains(tango)){
                nihongoin = true;
                break;
            }
        }
        if(nihongoin){ message2 = messagemot2;}
        if(noneIME) {
            if (nihongoin == false) {
                message2 = result + " §8(" + messagemot + "§8)";
            }
        }else{ message2 = messagemot; }

        if(chatmode == 0){
            String role = "";
            String message = "§8[§bAll§8][§7" + Countryname + "§8]§f" + role + "§f" + sender.getDisplayName() + "§b: §f" + message2;
            for(Player player : WARS.plugin.getServer().getOnlinePlayers()){
                player.sendMessage(message.replace("&","§"));
            }

            Log( "[" + "All" + "][" + e.getPlayer().getDisplayName() + "] " + message2);

        }else if(chatmode == 100){
            String message = "§8[§cAdmin§8] §f" + sender.getDisplayName() + "§b: §f" + message2;
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage(message.replace("&","§"));
            }

            Log( "[" + "Admin" + "][" + e.getPlayer().getDisplayName() + "] " + message2);

        }else if(chatmode > 0 && chatmode < 1000){
            String role = "";
            String message = "§8[§eCountry§8]§f" + role + "§f" + sender.getDisplayName() + "§b: §f" + message2;
            sendCountry(country,message.replace("&","§"));
            Log( "[" + "Country" + "][" + Countryname + "§8][§f" + e.getPlayer().getDisplayName() + "] " + message2);
        }else{
            e.getPlayer().sendMessage("§cチャットモードが不正な値になっています。");
            System.out.println(chatmode);
        }
        e.setCancelled(true);
    }

    public static List<String> nihongo = new ArrayList<>(Arrays.asList(
            "あ","い","う","え","お","か","き","く","け","こ",
            "さ","し","す","せ","そ","た","ち","つ","て","と",
            "な","に","ぬ","ね","の","は","ひ","ふ","へ","ほ",
            "ま","み","む","め","も","ら","り","る","れ","ろ",
            "や","ゆ","よ","わ","を", "ん",
            "ア","イ","ウ","エ","オ","カ","キ","ク","ケ","コ",
            "サ","シ","ス","セ","ソ","タ","チ","ツ","テ","ト",
            "ナ","ニ","ヌ","ネ","ノ","ハ","ヒ","フ","ヘ","ホ",
            "マ","ミ","ム","メ","モ","ラ","リ","ル","レ","ロ",
            "ヤ","ユ","ヨ","ワ","ヲ", "ン"
    ));

    public static void Log(String message){
        System.out.println(message);
        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + "";
        File file = new File("KeizaiyaMain/Log/"+ date);
        if (file.exists() == false){
            if(file.mkdirs() == true){
                System.out.println("mkdirssucsess");
            }else{ System.out.println("mkdirsmiss"); }
        }
        file = new File("KeizaiyaMain/Log/"+ date + "/"+ cal.get(Calendar.DATE) + ".txt");
        try{
            FileWriter filewriter = new FileWriter(file,true);
            filewriter.write(  cal.get(Calendar.HOUR_OF_DAY) +":"+ cal.get(Calendar.MINUTE) +":"+ cal.get(Calendar.SECOND) + ";"
                    + message + "\n");
            filewriter.close();
        }catch(IOException e){
            System.out.println(e);
        }
    }
}
