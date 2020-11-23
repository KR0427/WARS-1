package keizaiya.wars.process;

import keizaiya.wars.WARS;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.Province;
import keizaiya.wars.object.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class menu {

    public static Inventory getMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "メニュー");
        ItemStack stack;
        ItemMeta meta;
        List<String> lore = new ArrayList<>();
        WPlayer player1 = Playerpro.getPlayer(player);
        if (player1 != null) {
            Country country = Countrypro.getCountry(player1.getCountry());

            stack = new ItemStack(Material.PLAYER_HEAD);
            meta = stack.getItemMeta();
            meta.setDisplayName("Player Info");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "playerinfo");
            stack.setItemMeta(meta);
            SkullMeta metas = (SkullMeta) stack.getItemMeta();
            metas.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            stack.setItemMeta(metas);
            inv.setItem(1, stack);

            stack = new ItemStack(Material.PAPER);
            meta = stack.getItemMeta();
            meta.setDisplayName("Country");
            lore.clear();
            if(country != null){
                lore.add("§f国家: " + country.getName());
            }else {
                lore.add("§f国家に参加していません。");
            }
            meta.setLore(lore);
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "country");
            stack.setItemMeta(meta);
            inv.setItem(2, stack);

            stack = new ItemStack(Material.BIRCH_SIGN);
            meta = stack.getItemMeta();
            meta.setDisplayName("Help");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "help");
            stack.setItemMeta(meta);
            inv.setItem(4, stack);

            stack = new ItemStack(Material.GOLD_BLOCK);
            meta = stack.getItemMeta();
            meta.setDisplayName("Chatmode Country");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "CMCNT");
            stack.setItemMeta(meta);
            inv.setItem(6, stack);

            stack = new ItemStack(Material.DIAMOND_BLOCK);
            meta = stack.getItemMeta();
            meta.setDisplayName("Chatmode ALL");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "CMALL");
            stack.setItemMeta(meta);
            inv.setItem(7, stack);

            Provinepro.checkprovince(player,player.getLocation(),null);
            Province provine = Provinepro.getProvince(player.getChunk());
            if(provine != null){
                stack = new ItemStack(Material.GRASS_BLOCK);
                meta = stack.getItemMeta();
                meta.setDisplayName("Province Info");
                lore.clear();
                lore.add("§fposition: " + provine.gettag());
                String data = "無し";
                Country country1 = Countrypro.getCountry(provine.getOwner());
                if(country1 != null){
                    data = country1.getName();
                }
                lore.add("§f所持国: " + data);
                lore.add("§f地方名: " + provine.getName());
                meta.setLore(lore);
                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "PROI");
                stack.setItemMeta(meta);
                //inv.setItem(3, stack);
            }

            return inv;
        }else {
            return null;
        }
    }

    public static Inventory getPl(Player player , Integer num) {
        if(num < 0){ num = 0;}
        Inventory inv = Bukkit.createInventory(null, 54, "メニュー");
        List<String> lore = new ArrayList<>();
        WPlayer player1 = Playerpro.getPlayer(player);
        ItemStack stack;
        ItemMeta meta;
        if (player1 != null) {
            Country country = Countrypro.getCountry(player1.getCountry());

            stack = new ItemStack(Material.PLAYER_HEAD);
            meta = stack.getItemMeta();
            meta.setDisplayName("Player Info");
            if(country != null) {
                lore.add("Country: " + country.getName());
            }else {
                lore.add("Country: 無所属");
            }
            meta.setLore(lore);
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "playerinfo");
            stack.setItemMeta(meta);
            SkullMeta metas = (SkullMeta) stack.getItemMeta();
            metas.setOwningPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
            stack.setItemMeta(metas);
            inv.setItem(0, stack);

            stack = new ItemStack(Material.IRON_SWORD);
            meta = stack.getItemMeta();
            meta.setDisplayName("次へ");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "PLS");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "num"), PersistentDataType.INTEGER, num-+1);
            stack.setItemMeta(meta);
            inv.setItem(53,stack);

            stack = new ItemStack(Material.SHIELD);
            meta = stack.getItemMeta();
            meta.setDisplayName("戻る");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "PLS");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "num"), PersistentDataType.INTEGER, num-1);
            stack.setItemMeta(meta);
            inv.setItem(52,stack);

            stack = new ItemStack(Material.BOOK);
            meta = stack.getItemMeta();
            meta.setDisplayName("メニュー");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "menu");
            stack.setItemMeta(meta);
            inv.setItem(51,stack);

            Integer i = 0;
            Integer posi = 0;
            for(WPlayer player11 : Playerpro.getAllPlayer()){
                if(posi >= 44){
                    break;
                }else{
                    if((num*45 -1) < i){
                        if(player11 != null) {
                            stack = new ItemStack(Material.PLAYER_HEAD);
                            meta = stack.getItemMeta();
                            meta.setDisplayName(player11.getName());
                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "menu"), PersistentDataType.STRING, "playerinfo");
                            Country CD = Countrypro.getCountry(player11.getCountry());
                            lore.clear();
                            if(CD != null) {
                                lore.add("Country: " + CD.getName());
                            }else {
                                lore.add("Country: 無所属");
                            }
                            meta.setLore(lore);
                            stack.setItemMeta(meta);
                            metas = (SkullMeta) stack.getItemMeta();
                            metas.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(player11.getUuid())));
                            stack.setItemMeta(metas);
                            inv.setItem(posi, stack);
                            posi++;
                        }
                    }
                }
                i++;
            }
        }
        return inv;
    }

    public static boolean menuClickInventory(InventoryClickEvent e){
        ItemStack stack = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        WPlayer player1 = Playerpro.getPlayer(player);
        if(stack != null){
            String data = "";
            try {
                data = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "menu")
                        , PersistentDataType.STRING);
            }catch (Exception a){
                return false;
            }
            if(data != null){
                if(data.equalsIgnoreCase("country")){
                    if(Countrypro.getCountry(player1.getCountry()) != null){
                        e.getWhoClicked().openInventory(ActSystem.getInventory(2,player));
                    }else {
                        e.getWhoClicked().closeInventory();
                        tellraw.Sendsystemmessage(player,"国家に所属してください。");
                    }
                }else if(data.equalsIgnoreCase("CMCNT")){
                    if(Countrypro.getCountry(player1.getCountry()) != null) {
                        player1.setChatmode(1);
                        Playerpro.setPlayer(player1);
                        tellraw.Sendsystemmessage(player,"チャットモードをCountryに変更しました。");
                    }else{
                        tellraw.Sendsystemmessage(player,"国家に所属していません。");
                    }
                }else if(data.equalsIgnoreCase("CMALL")){
                    player1.setChatmode(0);
                    Playerpro.setPlayer(player1);
                    tellraw.Sendsystemmessage(player,"チャットモードをALLに変更しました。");
                }else if(data.equalsIgnoreCase("PLS")){
                    Integer datas = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "num")
                            , PersistentDataType.INTEGER);
                    if(datas != null){
                        player.openInventory(getPl(player,datas));
                    }
                }else if(data.equalsIgnoreCase("playerinfo")){
                    //player.openInventory(getPl(player,0));
                }else if(data.equalsIgnoreCase("menu")){
                    player.openInventory(getMenu(player));
                }
                e.setCancelled(true);
            }
        }
        return true;
    }

}
