package keizaiya.wars.file;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Yamlfile {
    public static YamlConfiguration loadyaml(File file){
        if(file.exists() == true){
            YamlConfiguration yml = new YamlConfiguration();
            try {
                yml.load(file);
                return yml;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            System.out.println(file + "ファイルがありません。");
            return null;
        }
    }

    public static boolean Saveyaml(File file, YamlConfiguration yml){
        try {
            yml.save(file);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
