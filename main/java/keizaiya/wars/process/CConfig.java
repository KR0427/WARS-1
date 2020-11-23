package keizaiya.wars.process;

import keizaiya.wars.WARS;
import keizaiya.wars.file.Yamlfile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CConfig {

    public static YamlConfiguration TCPconfig = new YamlConfiguration();

    public static void checkfile(){
        File file = new File(WARS.config.getString("Filename") + "/TCPconfig.yml");
        if(file.exists() == false){
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("ServerAdd", "kura-LiserCraudia0505.f5.si");
            yml.set("Serverpoat",42732);
            yml.set("enable",true);
            yml.set("account","test2");
            yml.set("pass","test");
            yml.set("bot",new ArrayList<>(Arrays.asList("discord_BOT_for_POTATO_WARS","test2")));
            Yamlfile.Saveyaml(file,yml);
        }
    }

    public static void loadfile(){
        checkfile();
        File file = new File(WARS.config.getString("Filename") +"/TCPconfig.yml");
        TCPconfig = Yamlfile.loadyaml(file);
    }

}
