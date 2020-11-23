package keizaiya.wars.process;

import com.google.gson.Gson;
import keizaiya.wars.WARS;
import keizaiya.wars.file.Serverdata;
import keizaiya.wars.file.Yamlfile;
import keizaiya.wars.object.ConnectionIndo;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.Facility;
import keizaiya.wars.object.Province;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.util.*;

public class Countrypro {
    public static Map<String , Country> CountryMap = new HashMap<>();
    public static Gson gson = new Gson();

    public static void loadfile(){
        CountryMap.clear();
        if(checkfile()){
            File file = new File(WARS.config.getString("Filename") + "/Country.yml");
            YamlConfiguration yml = Yamlfile.loadyaml(file);
            for(String key : yml.getKeys(false)){
                Country country = gson.fromJson(yml.getString(key),Country.class);
                CountryMap.put(country.getTag(),country);
            }
        }
    }
    public static void savefile(){
        if(checkfile()){
            System.out.println("Saved Countryfile");
            File file = new File(WARS.config.getString("Filename") + "/Country.yml");
            YamlConfiguration yml = new YamlConfiguration();
            for(String key : CountryMap.keySet()){
                Country country = CountryMap.get(key);
                String data = gson.toJson(country,Country.class);
                yml.set(country.getTag(),data);
            }
            Yamlfile.Saveyaml(file,yml);
        }
    }
    public static boolean checkfile(){
        File file = new File(WARS.config.getString("Filename") + "/Country.yml");
        if(file.exists()){
            return true;
        }else {
            Yamlfile.Saveyaml(file,new YamlConfiguration());
            return true;
        }
    }
    public static void setCountry(Country country){
        if(country != null) {
            CountryMap.put(country.getTag(),country);
            sendLog.sendconsole("Value",country.toString());
            savefile();
        }
    }
    public static Country getCountry(String tag){
        if(CountryMap.containsKey(tag)){
            Country country = CountryMap.get(tag);
            if(country.getEnd()){
                return null;
            }else {
                return country;
            }
        }else {
            return null;
        }
    }
    public static Country getCountryN(String name){
        for(String key : CountryMap.keySet()){
            Country country = CountryMap.get(key);
            if(country.getName().equals(name)){
                return country;
            }
        }
        return null;
    }
    public static Country getCountryNi(String name){
        for(String key : CountryMap.keySet()){
            Country country = CountryMap.get(key);
            if(country.getNickname().equals(name)){
                return country;
            }
        }
        return null;
    }
    public static String getnewtag(){
        Integer num = 0;
        while(true){
            System.out.println(num);
           if(CountryMap.containsKey("Cnt" + num) == false){
               return "Cnt" + num;
           }
           num++;
        }
    }

    public static Integer checkhan(String s) {
        Integer yeah = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 0xff61 && chars[i] <= 0xff9f) {
                yeah = yeah + 1;
            } else {
                yeah = yeah + 2;
            }
            if (yeah >= 16) {
                return i + 1;
            }
        }
        return s.length();
    }
    public static Boolean setRequest(String Uuid , String name){
        Country country2 = getCountryN(name);
        if(country2 != null) {
            for (String key : CountryMap.keySet()) {
                Country country = CountryMap.get(key);
                if (country.getRequest().contains(Uuid)) {
                    country.removeRequest(Uuid);
                    setCountry(country);
                    break;
                }
            }
            country2.addRequest(Uuid);
            setCountry(country2);
            return true;
        }
        return false;
    }
    public static Boolean removeRequest(String Uuid){
        for (String key : CountryMap.keySet()) {
            Country country = CountryMap.get(key);
            if (country.getRequest().contains(Uuid)) {
                country.removeRequest(Uuid);
                setCountry(country);
                return true;
            }
        }
        return false;
    }
    public static String getCountryJson(Country country){
        return gson.toJson(country,Country.class);
    }

    public static Integer getAllActs(Country country){
        Integer num = 0;
        if(country.getFacility() != null){
            for(Integer i : country.getFacility().keySet()){
                num++;
            }
        }
        if(country.getProvince() != null){
            for(String tag : country.getProvince()){
                Province province = Provinepro.getProvince(tag);
                if(province != null){
                    if(province.getFacility() != null){
                        for(Integer i : province.getFacility().keySet()){
                            num++;
                        }
                    }
                }
            }
        }
        return num;
    }

    public static void getAllFacility(Country country, Player player){
        if(country.getFacility() != null){
            player.sendMessage("国家カテゴリー");
            for(Integer i : country.getFacility().keySet()){
                Facility facility = gson.fromJson(country.getFacility().get(i),Facility.class);
                tellraw.sendtellraw(player,tellraw.gettellrawtocommand(" - " + facility.getName() + ": " + i ,"/tp @p " + facility.getLocationL().getBlockX() + " "+ facility.getLocationL().getBlockY() + " " + facility.getLocationL().getBlockZ()));
            }
        }
        if(country.getProvince() != null){
            player.sendMessage("市街カテゴリー");
            for(String tag : country.getProvince()){
                Province province = Provinepro.getProvince(tag);
                if(province != null){
                    player.sendMessage(province.getName()+ " : " +province.gettag());
                    if(province.getFacility() != null){
                        for(Integer i : province.getFacility().keySet()){
                            Facility facility = gson.fromJson(country.getFacility().get(i),Facility.class);
                            tellraw.sendtellraw(player,tellraw.gettellrawtocommand(" - " + facility.getName() + ": " + i ,"/tp @p " + facility.getLocationL().getBlockX() + " "+ facility.getLocationL().getBlockY() + " " + facility.getLocationL().getBlockZ()));
                        }
                    }
                }
            }
        }
    }

    public static void ActAddAll(){
        loadfile();
        for(String key : CountryMap.keySet()){
            Country country = getCountry(key);
            if(country != null){
                Integer data = getAllActs(country);
                country.setAct(country.getAct() + data);
                Integer Befour = country.getCumact();
                Integer After = country.getCumact() + data;
                List<Integer> Addpoint = new ArrayList<>(Arrays.asList(5,10,20,40));
                for(Integer i : Addpoint){
                    if(Befour < i && After >= i){
                        country.setEffective(country.getEffective() + 1);
                        break;
                    }
                }
                country.setCumact(After);
                setCountry(country);
            }
        }
    }

    public static void startActcheck(){
        new BukkitRunnable(){
            Integer data = Calendar.getInstance().getTime().getDay();
            @Override
            public void run() {
                Calendar cal = Calendar.getInstance();
                if(cal.getTime().getDay() != data){
                    data = cal.getTime().getDay();
                    ActAddAll();
                    Serverdata.loadfile();
                    System.out.println(Serverdata.getNowDay());
                    Serverdata.setNowDay(Serverdata.getNowDay() + 1);
                    Serverdata.savefile();
                }
            }
        }.runTaskTimer(WARS.plugin,0,300);
    }

    public static void sendCountrydata(String event , Country country , String data){
        Map<String , String> datas = new HashMap<>();
        datas.put("data",data);
        datas.put("country",gson.toJson(country));
        ConnectionIndo info = new ConnectionIndo(0,event,datas);
        Connectionpro.sendserver(Connectionpro.getBotsend(gson.toJson(info),"ALL"));
    }

    public static boolean checkProvince(Country country , Province target){
        if(country != null) {
            List<String> ActProvincelist = new ArrayList<>();
            Integer num = 0;
            for (String data : country.getProvince()) {
                Province province = Provinepro.getProvince(data);
                if(province != null){
                    ActProvincelist.add((province.getX() -1) + "," + (province.getZ()));
                    ActProvincelist.add((province.getX() +1) + "," + (province.getZ()));
                    ActProvincelist.add((province.getX()) + "," + (province.getZ() -1));
                    ActProvincelist.add((province.getX()) + "," + (province.getZ() +1));
                }
                num++;
            }
            if(num == 0){
                return true;
            }else{
                return ActProvincelist.contains(target.gettag());
            }
        }
        return false;
    }

    public static List<Country> getAllCountry(){
        List<Country> data = new ArrayList<>();
        for(String key : CountryMap.keySet()){
            data.add(CountryMap.get(key));
        }
        return data;
    }
}
