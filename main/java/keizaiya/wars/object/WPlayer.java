package keizaiya.wars.object;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WPlayer {
    private String uuid;
    private String name;
    private Integer chatmode;
    private String country;

    public WPlayer(String uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        chatmode = 0;
        country ="null";
    }

    public WPlayer(Player player){
        this.uuid = player.getUniqueId().toString();
        this.name = player.getName();
        chatmode = 0;
        country = "null";
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getChatmode() {
        return chatmode;
    }
    public void setChatmode(Integer chatmode) {
        this.chatmode = chatmode;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getValue(){
        String message = "UUID: " + uuid + ",name: " + name + ",chatmode: " + chatmode + ",country: " + country;
        return message;
    }
    public Player getPlayer(){
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getUniqueId().toString().equalsIgnoreCase(uuid)){
                return player;
            }else if(player.getName().equalsIgnoreCase(name)){
                return player;
            }
        }
        return null;
    }
}
