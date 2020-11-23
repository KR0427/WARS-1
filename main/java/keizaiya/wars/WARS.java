package keizaiya.wars;

import keizaiya.wars.command.*;
import keizaiya.wars.file.Serverdata;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.WPlayer;
import keizaiya.wars.process.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class WARS extends JavaPlugin implements Listener {

    public static Plugin plugin;
    public static FileConfiguration config;
    public static Class clname;
    public static Thread tuusins = null;

    @Override
    public void onEnable() {
        getCommand("country").setTabCompleter(new Countrytab());
        getCommand("admin").setTabCompleter(new Admintab());
        getCommand("chatmode").setTabCompleter(new chatmodetab());
        System.out.println("WARS Plugin v 1.0.0");
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this,this);
        config = getConfig();
        clname = getClass();
        plugin = getPlugin(this.getClass());
        Authorpro.allStart();
        Simpletab.startTPS();
        Simpletab.startTAB();
        Countrypro.startActcheck();
        Serverdata.checkfile();


        CConfig.loadfile();
        if(CConfig.TCPconfig.getBoolean("enable")) {
            tuusins = Connectionpro.startcom();
            tuusins.start();
        }else {
            System.out.println("サーバー通信がオフです");
        }
        Recipe.addingreripe();
        chengewoad.checkfile();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Connectionpro.stop = true;
        tuusins.stop();
        if(Connectionpro.kidou){
            Connectionpro.sendserver("server&close@");
        }
        if(Connectionpro.socket != null){
            try {
                Connectionpro.socket.close();
                System.out.println("closed sucsess");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Countrycommand.onCountrycommnand(sender,cmd,commandLabel,args);
        AuthorCommand.onCountrycommnand(sender,cmd,commandLabel,args);
        Admincommand.onCountrycommnand(sender,cmd,commandLabel,args);
        if(cmd.getName().equalsIgnoreCase("test")){
            sender.sendMessage("test Command active");
            if(sender instanceof Player) {
                if(args.length == 1){
                    System.out.println("yeah");
                    Country country = Countrypro.getCountry(args[0]);
                    if(country != null){
                        Countrypro.getAllFacility(country,(Player) sender);
                    }else {
                        tellraw.Sendsystemmessage((Player)sender,"タグが不正です");
                    }
                }
            }else {
                Countrypro.ActAddAll();
            }
        }
        return true;
    }

    @EventHandler
    public void onjoin(PlayerJoinEvent event){
        System.out.println("ok");
        WPlayer wplayer = Playerpro.getPlayer(event.getPlayer());
        Player player = event.getPlayer();
        System.out.println(wplayer != null);
        if(wplayer != null){
            System.out.println(wplayer);
        }else {
            WPlayer target = new WPlayer(player);
            Playerpro.setPlayer(target);
        }
    }

    @EventHandler
    public void click(PlayerInteractEvent e){
        Authorpro.deleteitem(e);
        if(e.getClickedBlock() != null) {
            if (Provinepro.checkprovince(e.getPlayer(), e.getClickedBlock().getLocation() , e.getClickedBlock().getType())) {
                e.setCancelled(true);
            }
            ActSystem.ActSystemClick(e);
        }
        if(e.getItem() != null) {
            if (e.getItem().getType().equals(Material.STICK)) {
                if (e.getPlayer().isSneaking() && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                    e.getPlayer().openInventory(menu.getMenu(e.getPlayer()));
                }
            }
        }
    }
    @EventHandler
    public void Eclick(PlayerInteractEntityEvent e){
        if(Provinepro.checkprovince(e.getPlayer(),e.getRightClicked().getLocation(), null)){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void Eclick2(EntityDamageByEntityEvent e){

        if(e.getDamager() instanceof Player){
            if(Provinepro.checkprovince((Player) e.getDamager(),e.getEntity().getLocation(),null)){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent e){
        ActSystem.ActSystemClickInventory(e);
        menu.menuClickInventory(e);
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        chat.chat(e);
    }

    @EventHandler
    public void kickkorobka(PlayerKickEvent event){
        System.out.println(event.getPlayer().getUniqueId().toString());
        if(event.getPlayer().getUniqueId().toString().contains("c09517d3-08e7-459e-adcc-116c435ae53e")){
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendTitle("korobka kicked by Server^^","",0,60,20);
            }
        }
    }


}
