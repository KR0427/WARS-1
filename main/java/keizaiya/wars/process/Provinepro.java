package keizaiya.wars.process;

import com.google.gson.Gson;
import keizaiya.wars.WARS;
import keizaiya.wars.file.Yamlfile;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.Province;
import keizaiya.wars.object.WAR;
import keizaiya.wars.object.WPlayer;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Provinepro {
    public static Map<String , Province> ProvinceMap = new HashMap<>();
    public static Gson gson = new Gson();

    public static void loadfile(){
        ProvinceMap.clear();
        if(checkfile()){
            File file = new File(WARS.config.getString("Filename") + "/Plovince.yml");
            YamlConfiguration yml = Yamlfile.loadyaml(file);
            for(String key : yml.getKeys(false)){
                Province province = gson.fromJson(yml.getString(key),Province.class);
                ProvinceMap.put(province.gettag(),province);
            }
        }
    }
    public static void savefile(){
        if(checkfile()){
            File file = new File(WARS.config.getString("Filename") + "/Plovince.yml");
            YamlConfiguration yml = new YamlConfiguration();
            for(String key : ProvinceMap.keySet()){
                Province province = ProvinceMap.get(key);
                String data = gson.toJson(province);
                yml.set(province.gettag(),data);
            }
            Yamlfile.Saveyaml(file,yml);
        }
    }
    public static boolean checkfile(){
        File file = new File(WARS.config.getString("Filename") + "/Plovince.yml");
        if(file.exists()){
            return true;
        }else {
            Yamlfile.Saveyaml(file,new YamlConfiguration());
            return true;
        }
    }

    public static void setProvince(Province province){
        if(province != null) {
            ProvinceMap.put(province.gettag(),province);
            sendLog.sendconsole("Value",province.toString());
            savefile();
        }
    }
    public static Province getProvince(String tag){
        loadfile();
        if(tag != null){
            if(ProvinceMap.containsKey(tag)){
                return ProvinceMap.get(tag);
            }
        }
        return null;
    }
    public static Province getProvince(Chunk chunk){
        if(chunk != null) {
            String tag = getProvincetag(chunk);
            return getProvince(tag);
        }else {
            return null;
        }
    }
    public static String getProvincetag(Chunk chunk){
        if(chunk != null) {
            Integer X = 0;
            Integer Z = 0;
            Integer data = WARS.config.getInt("chanksize", 8);
            System.out.println((chunk.getX() + WARS.config.getInt("chankX", 0)) / WARS.config.getInt("chanksize", 8));
            if(chunk.getX() >= 0) {
                X = (chunk.getX() + WARS.config.getInt("chankX", 0)) / data;
            }else {
                X = (chunk.getX() + WARS.config.getInt("chankX", 0) + 1) / data -1;
            }
            if(chunk.getZ() >= 0) {
                Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0)) / data;
            }else{
                Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0) + 1) / data -1;
            }
            String tag = X + "," + Z;
            return tag;
        }else {
            return null;
        }
    }

    public static boolean checkprovince(Player player , Location location , Material Blocktype){
        Chunk chunk;
        if(location != null) {
            chunk = location.getChunk();
        }else {
            chunk = player.getChunk();
        }
        Integer X = 0;
        Integer Z = 0;
        Integer datas = WARS.config.getInt("chanksize", 8);
        if(chunk.getX() >= 0) {
            X = (chunk.getX() + WARS.config.getInt("chankX", 0)) / datas;
        }else {
            X = (chunk.getX() + WARS.config.getInt("chankX", 0) + 1) / datas -1;
        }
        if(chunk.getZ() >= 0) {
            Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0)) / datas;
        }else{
            Z = (chunk.getZ() + WARS.config.getInt("chankZ", 0) + 1) / datas -1;
        }
        String tag = X + "," + Z;
        loadfile();
        Province province = getProvince(tag);
        if(province != null){
            Country country = Countrypro.getCountry(province.getOwner());
            if(country != null){
                WPlayer player1 = Playerpro.getPlayer(player);
                if(player1 != null){
                    if(country.getTag().equalsIgnoreCase(player1.getCountry())){
                        return false;
                    }else {
                        if(ActSystem.TileAttacks.containsKey(player1.getUuid())){
                            if(Blocktype != null) {
                                if (Authorpro.notBreak(Blocktype)){
                                    return true;
                                }else {
                                    if(player.getInventory().getItemInMainHand() != null){
                                        Material data = player.getInventory().getItemInMainHand().getType();
                                        if(Authorpro.notBreak(data)){
                                            return true;
                                        }
                                    }if(player.getInventory().getItemInOffHand() != null){
                                        Material data = player.getInventory().getItemInOffHand().getType();
                                        if(Authorpro.notBreak(data)){
                                            return true;
                                        }
                                    }
                                }
                            }
                            return false;
                        }
                        WAR war = Wartime.getWARD(country.getTag());
                        if(war != null){
                            if(province.gettag().equalsIgnoreCase(war.getProvince())){
                                return false;
                            }
                        }
                    }
                }
            }else {
                return false;
            }
        }else {
            Province province2 = new Province(X,Z,"null");
            setProvince(province2);
            savefile();
            return false;
        }
        return true;
    }
}
