package keizaiya.wars.process;

import keizaiya.wars.WARS;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Authorpro {

    public static boolean checkfile(){
        File file = new File(WARS.config.getString("Filename","WARS"));
        if(file.exists() == false){
            if(file.mkdirs() == true){ System.out.println("MainFile Create Sucsess");
            return true;
            }else{
                System.out.println("MainFile Create miss");
                return false;
            }
        }else {
            return true;
        }
    }
    public static void allStart(){
        Countrypro.loadfile();
        Playerpro.loadfile();
        Provinepro.loadfile();
    }
    public static void saveall(){
        Countrypro.savefile();
        Playerpro.savefile();
        Provinepro.savefile();
    }

    public static void deleteitem(PlayerInteractEvent e){
        List<Material> list = new ArrayList<>();
        for(String data : WARS.config.getStringList("DERETEITEM")){
            Material material = Material.getMaterial(data);
            if(material != null){
                list.add(material);
            }
        }
        Player player = e.getPlayer();
        for(int i = 0 ; i < player.getInventory().getSize() ; i++){
            ItemStack stack = player.getInventory().getItem(i);
            if(stack != null){
                if(list.contains(stack.getType())){
                    player.getInventory().setItem(i,null);
                }
            }
        }
    }

    public static boolean notBreak(Material target){
        List<Material> list = new ArrayList<>();
        for(String data : WARS.config.getStringList("NotBreak")){
            Material material = Material.getMaterial(data);
            if(material != null){
                list.add(material);
            }
        }
        return list.contains(target);
    }

}
