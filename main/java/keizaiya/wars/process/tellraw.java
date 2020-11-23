package keizaiya.wars.process;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class tellraw {
    public static String gettellrawtocommand(String message , String command){
        return " {\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + command + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§3Click here\"}}";
    }

    public static String gettellrawtoopenurl(String message , String URL){
        return " {\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"" + URL + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§3Open URL\"}}";
    }

    public static String gettellrawtosuggest(String message , String suggest){
        return " {\"text\":\"" + message + "\",\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"" + suggest + "\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"§3get Command\"}}";
    }

    public static void sendtellraw(Player player, String json){
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"tellraw " + player.getDisplayName() + json);
    }
    public static void Sendsystemmessage(Player player , String message){
        player.sendMessage("§7[System] " + message);
    }

}
