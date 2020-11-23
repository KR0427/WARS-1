package keizaiya.wars.file;

import keizaiya.wars.WARS;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Serverdata {
    public static Integer nowDay = 0;
    public static List<String> Admin = new ArrayList<>();

    public static void loadfile(){
        if(checkfile()){
            File file = new File(WARS.config.getString("Filename") + "/Server.yml");
            YamlConfiguration yml = Yamlfile.loadyaml(file);
            nowDay = yml.getInt("day");
            Admin = yml.getStringList("admin");
        }
    }
    public static void savefile(){
        if(checkfile()){
            System.out.println("Saved Serverfile");
            File file = new File(WARS.config.getString("Filename") + "/Server.yml");
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("day",nowDay);
            yml.set("admin",Admin);
            Yamlfile.Saveyaml(file,yml);
        }
    }
    public static boolean checkfile(){
        File file = new File(WARS.config.getString("Filename") + "/Server.yml");
        if(file.exists()){
            return true;
        }else {
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("day",0);
            yml.set("admin",new ArrayList<>());
            Yamlfile.Saveyaml(file,yml);
            return true;
        }
    }

    public static Integer getNowDay() {
        loadfile();
        return nowDay;
    }

    public static void setNowDay(Integer nowDay) {
        Serverdata.nowDay = nowDay;
    }

    public static List<String> getAdmin() {
        return Admin;
    }

    public static void setAdmin(List<String> admin) {
        Admin = admin;
    }
}
