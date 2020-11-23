package keizaiya.wars.process;

import com.google.gson.Gson;
import keizaiya.wars.WARS;
import keizaiya.wars.file.Yamlfile;
import keizaiya.wars.object.WPlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playerpro {
    public static Map<String , WPlayer> PlayerMap = new HashMap<>();
    public static Gson gson = new Gson();

    public static void loadfile(){
        PlayerMap.clear();
        if(checkfile()){
            File file = new File(WARS.config.getString("Filename") + "/Players.yml");
            YamlConfiguration yml = Yamlfile.loadyaml(file);
            for(String key : yml.getKeys(false)){
                WPlayer player =  gson.fromJson(yml.getString(key),WPlayer.class);
                PlayerMap.put(player.getUuid(),player);
            }
        }
    }
    public static void savefile(){
        if(checkfile()){
            System.out.println("saved Playerfile");
            File file = new File(WARS.config.getString("Filename") + "/Players.yml");
            YamlConfiguration yml = new YamlConfiguration();
            for(String key : PlayerMap.keySet()){
                WPlayer player = PlayerMap.get(key);
                String data = gson.toJson(player);
                yml.set(player.getUuid(), data);
            }
            Yamlfile.Saveyaml(file,yml);
        }
    }
    public static boolean checkfile(){
        File file = new File(WARS.config.getString("Filename") + "/Players.yml");
        if(file.exists()){
            return true;
        }else {
            Yamlfile.Saveyaml(file,new YamlConfiguration());
            return true;
        }
    }
    public static void setPlayer(WPlayer player){
        if(player != null) {
            PlayerMap.put(player.getUuid(),player);
            sendLog.sendconsole("Value",player.toString());
            savefile();
        }
    }
    public static WPlayer getPlayer(Player player){
        loadfile();
        if(player != null){
            if(PlayerMap.containsKey(player.getUniqueId().toString())){
                return PlayerMap.get(player.getUniqueId().toString());
            }
        }
        return null;
    }
    public static WPlayer getPlayer(String UUID){
        if(PlayerMap.containsKey(UUID)){
            return PlayerMap.get(UUID);
        }
        return null;
    }
    public static WPlayer getPlayerN(String name){
        for(String key : PlayerMap.keySet()){
            WPlayer player = PlayerMap.get(key);
            if(player.getName().equalsIgnoreCase(name)){
                return player;
            }
        }
        return null;
    }
    public static void removeCountry(String tag){
        for(String key : PlayerMap.keySet()){
            WPlayer player = PlayerMap.get(key);
            if(player.getCountry().equalsIgnoreCase(tag)){
                player.setCountry("null");
                setPlayer(player);
            }
        }
    }

    public static List<WPlayer> getAllPlayer(){
        loadfile();
        List<WPlayer> list = new ArrayList<>();
        for(String key : PlayerMap.keySet()){
            WPlayer player = PlayerMap.get(key);
            if(player != null){
                list.add(player);
            }
        }
        return list;
    }

    public static List<String> chengeUN(List<String> target){
        List<String> data = new ArrayList<>();
        if(target != null){
            for(String uuid : target){
                WPlayer player = getPlayer(uuid);
                if(player != null){
                    data.add(player.getName());
                }
            }
            return data;
        }
        return null;
    }
}
