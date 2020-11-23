package keizaiya.wars.process;

import keizaiya.wars.WARS;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.Province;
import keizaiya.wars.object.WAR;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Banner;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Wartime {

    public static Map<String , WAR> warlist = new HashMap<>();

    public static void Addwar(Country country , Country target , String Provincetag){
        if(country != null && target != null && Provincetag != null){
            WAR war = new WAR(country.getTag(),target.getTag(),Provincetag);
            warlist.put(country.getTag(),war);
        }
    }

    public static WAR getWAR(String tag){
        if(warlist.containsKey(tag)){
            return warlist.get(tag);
        }else {
            return null;
        }
    }

    public static WAR getWARD(String tag){
        for(String key : warlist.keySet()){
            WAR target = warlist.get(key);
            if(target != null){
                if(target.getDefense().equalsIgnoreCase(tag)){
                    return target;
                }
            }
        }
        return null;
    }

    public static void setWAR(String tag , WAR war){
        warlist.put(tag , war);
    }

    public static void remove(String tag){
        if(warlist.containsKey(tag)){
            warlist.remove(tag);
        }
    }

    public static boolean checkwar(String tag){
        return wardata.containsKey(tag);
    }

    public static boolean end(String tag){
        if(wardata.containsKey(tag)){
            List<Boolean> data = wardata.get(tag);
            for(Boolean yeah : data){
                if(yeah){
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public static Map<String , List<Boolean>> wardata = new HashMap<>();

    public static boolean startwar(WAR war){
        if(war != null){
            if(war.getDFAL() != null && war.getDFBL() != null && war.getProvince() != null){
                Province province = Provinepro.getProvince(war.getProvince());
                wardata.put(war.getAttaker(),new ArrayList<>(Arrays.asList(false,false)));
                if(province != null){
                    System.out.println("StartWar");
                    chat.sendAll(null,"会戦しました。");
                    new BukkitRunnable(){
                        Integer limittime = WARS.config.getInt("wartime",1) * 60;
                        Location A = war.getDFAL();
                        Location B = war.getDFBL();
                        Location AF = war.getDFAL().add(0,1,0);
                        Location BF = war.getDFBL().add(0,1,0);
                        String tag = war.getAttaker();
                        WAR wars = war;
                        Integer time = 0;
                        Boolean AB = false;
                        Boolean BB = false;
                        Integer timer = 0;
                        @Override
                        public void run() {
                            if(AF.getBlock().getState() instanceof Banner) {
                                timer = 0;
                                AB = false;
                                if( AB == false) {
                                    A.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, A.getX(), A.getY() + 5, A.getZ(), 20, 0, 50, 0,0.00001);
                                }
                            }else{
                                if(AB == false){
                                    System.out.println("FlagA");
                                    AB = true;
                                }
                            }
                            if(BF.getBlock().getState() instanceof Banner) {
                                timer = 0;
                                BB = false;
                                if( BB == false) {
                                    B.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, B.getX(), B.getY() + 5, B.getZ(), 20, 0, 50, 0,0.00001);
                                }
                            }else {
                                if(BB == false){
                                    System.out.println("FlagB");
                                    BB = true;
                                }
                            }
                            if((AB && BB)){
                                if(timer == 0) {
                                    chat.sendAll(null, "旗がすべて壊されました。");
                                    timer++;
                                }else{
                                    timer++;
                                    if(timer >= 60){
                                        timer = 0;
                                    }
                                }
                            }
                            if(end(tag) || time >= limittime){
                                //end
                                if(wardata.get(tag) != null) {
                                    String data = "";
                                    if ((AB && BB) || wardata.get(tag).get(1)) {
                                        System.out.println("攻撃側の勝利");
                                        data = "攻撃側の勝利";
                                    } else if (time >= limittime || wardata.get(tag).get(0)) {
                                        System.out.println("防衛の勝利");
                                        data = "防衛側の勝利";
                                    }
                                    chat.sendAll(null,data);
                                }
                                System.out.println("EndWar");
                                wardata.remove(tag);
                                remove(tag);
                                A.getBlock().setType(Material.AIR);
                                B.getBlock().setType(Material.AIR);
                                cancel();
                            }
                            time++;
                            System.out.println(time);
                        }
                    }.runTaskTimer(WARS.plugin,0,20);
                    return true;
                }
            }
        }
        return false;
    }
}
