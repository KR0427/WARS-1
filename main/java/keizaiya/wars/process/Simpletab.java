package keizaiya.wars.process;

import keizaiya.wars.WARS;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Simpletab {

    private static long use;
    private static long max;
    private static int tps;
    private static float Ntps;
    private static long time2;
    private static float data;
    private static List<Integer> tick = new ArrayList<Integer>();
    private static List<String> member = new ArrayList<String>();
    private static Map<String,Object> Botdata = new HashMap<String,Object>();
    private static long time;
    private static String Tcolor;
    private static String Header;
    private static String Footer;
    private static String Header2;
    private static String Footer2;
    private static String Mdef;
    private static String TPS;
    private static long ms;
    private static int tps2;
    private static Long gendo;
    private static Long gendo2;

    public static void startTAB(){
        Header = "";
        Footer = "";
        gendo = System.currentTimeMillis();
        gendo2 = System.currentTimeMillis();

        for(int i = 0; i < WARS.config.getList("Header").size(); i++) {
            Header = Header + WARS.config.getList("Header").get(i);
            if (i + 1 < WARS.config.getList("Header").size()) {
                Header = Header + "\n";
            }
        }
        for(int i = 0; i < WARS.config.getList("Footer").size(); i++) {
            Footer = Footer + WARS.config.getList("Footer").get(i);
            if (i + 1 < WARS.config.getList("Footer").size()) {
                Footer = Footer + "\n";
            }
        }


        new BukkitRunnable() {

            @Override
            public void run() {
                if (getServer().getOnlinePlayers() == null) {
                    return;
                } else {
                    use = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
                    max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
                    Mdef = "Memory: " + use + " MB / " + max + "MB";
                    if (WARS.config.getBoolean("Tcolor", true) == true) {
                        if (Ntps >= 18) {
                            Tcolor = WARS.config.getString("Hi", "&a");
                        }
                        if ((Ntps >= 15) && (Ntps < 18)) {
                            Tcolor = WARS.config.getString("Half", "&e");
                        }
                        if (Ntps < 15) {
                            Tcolor = WARS.config.getString("low", "&c");
                        }
                    } else {
                        Tcolor = "";
                    }
                    TPS = Tcolor + Ntps;
                    for (Player player : getServer().getOnlinePlayers()) {
                        Header2 = Header.replace("%Mmax%", String.valueOf(max))
                                .replace("%Muse%",String.valueOf(use))
                                .replace("%Mdef%",Mdef)
                                .replace("%TPS%",TPS)
                                .replace("%ping%",String.valueOf(getPing(player)))
                                .replace("%player%",player.getDisplayName())
                                .replace("&" , "§");
                        Footer2 = Footer.replace("%Mmax%", String.valueOf(max))
                                .replace("%Muse%",String.valueOf(use))
                                .replace("%Mdef%",Mdef)
                                .replace("%TPS%",TPS)
                                .replace("%ping%",String.valueOf(getPing(player)))
                                .replace("%player%",player.getDisplayName())
                                .replace("&" , "§");
                        Boolean Keizaiyas = WARS.config.getBoolean("WARS",false);
                        if(Keizaiyas) {
                            WPlayer player1 = Playerpro.getPlayer(player);
                            if(player1 != null) {
                                Country country = Countrypro.getCountry(player1.getCountry());
                                String name = "";
                                if (country != null) {
                                    name = "§f" + country.getName();
                                }else {
                                    name = "放浪者";
                                }
                                String chatmode = "";
                                if(player1.getChatmode() == 0){
                                    chatmode = "全体チャット";
                                }else if(player1.getChatmode() == 1){
                                    chatmode = "国家チャット";
                                }else if(player1.getChatmode() == 100){
                                    chatmode = "Admin";
                                }
                                Header2 = Header2.replace("%country%", name)
                                        .replace("%chat%", chatmode);
                                Footer2 = Footer2.replace("%country%", name)
                                        .replace("%chat%", chatmode);
                            }
                        }else{
                            Header2 = Header2.replace("%country%" , "")
                                    .replace("%chat%","");
                            Footer2 = Footer2.replace("%country%" , "")
                                    .replace("%chat%","");
                        }
                        player.setPlayerListHeaderFooter(Header2, Footer2);
                    }


                }
            }
        }.runTaskTimer(WARS.plugin, 0, WARS.config.getInt("updatetick", 2));
    }

    public static void startTPS(){
        time = System.currentTimeMillis();
        time2 = System.currentTimeMillis();
        Ntps = 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (time2 != 0) {
                    time = System.currentTimeMillis();
                    ms = time - time2;
                    if(ms <= 0){ ms = 4;}
                    tps2 = 1000/ (int)ms;
                    if (tps2 > 20){tps2 = 20;}
                    tick.add(tps2);
                }
                if (tick.size() == WARS.config.getInt("TPSrate", 10)) {
                    data = 0;
                    for (float tps : tick) {
                        data = data + tps;
                    }
                    Ntps = data / WARS.config.getInt("TPSrate", 10);
                    tick.clear();
                    if (Ntps > 20) {
                        Ntps = 20;
                    }
                }

                if (WARS.config.getBoolean("Log",true) == true) {
                    if((Ntps < 15) & time > gendo){
                        Calendar cal = Calendar.getInstance();
                        String Message = cal.get(Calendar.HOUR_OF_DAY) +":"+ cal.get(Calendar.MINUTE) +":"+ cal.get(Calendar.SECOND) + "[" + Ntps + "]\n";
                        gendo = time + 60000;
                        for (Player player : getServer().getOnlinePlayers()) {
                            Message = Message + "[" + player.getName() + "] X:" + player.getLocation().getX() + " Y:"
                                    + player.getLocation().getY() + " Z:" + player.getLocation().getZ() + "\n";
                        }
                        Message = Message + "\n\n";
                        String date = cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + "";
                        File file = new File(WARS.config.getString("Filename") + "/Log/" + date);
                        if (file.exists() == false){
                            if(file.mkdirs() == true){
                                System.out.println("mkdirssucsess");
                            }else{ System.out.println("mkdirsmiss"); }
                        }
                        file = new File( WARS.config.getString("Filename") + "/Log/"+ date + "/"+ cal.get(Calendar.DATE) + ".txt");
                        try{
                            FileWriter filewriter = new FileWriter(file,true);
                            filewriter.write(Message);
                            filewriter.close();
                        }catch(IOException e){
                            System.out.println(e);
                        }
                    }
                }
                time2 = System.currentTimeMillis();
            }
        }.runTaskTimer(WARS.plugin, 0, 1);
    }

    public static int getPing(Player player) {
        try {
            String a = Bukkit.getServer().getClass().getPackage().getName().substring(23);
            Class<?> b = Class.forName("org.bukkit.craftbukkit." + a + ".entity.CraftPlayer");
            Object c = b.getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            int d = ((Integer)c.getClass().getDeclaredField("ping").get(c)).intValue();
            return d;
        } catch (Exception e) {
            return -1;
        }
    }

    public static float getNtps() {
        return Ntps;
    }

    public static List<String> getMember() {
        List<String> yeah = new ArrayList<>();
        for(Player data : Bukkit.getOnlinePlayers()){
            yeah.add(data.getDisplayName());
        }
        return yeah;
    }
}
