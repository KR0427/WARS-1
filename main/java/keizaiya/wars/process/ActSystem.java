package keizaiya.wars.process;

import com.google.gson.Gson;
import keizaiya.wars.WARS;
import keizaiya.wars.file.Serverdata;
import keizaiya.wars.object.Country;
import keizaiya.wars.object.Facility;
import keizaiya.wars.object.Province;
import keizaiya.wars.object.WPlayer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ActSystem {
    public static Inventory getInventory(Integer type , Player player){
        Integer size = 9;
        String title = "null";
        ItemStack stack = null;
        ItemMeta meta = null;
        List<String> lore = new ArrayList<>();
        WPlayer player1 = Playerpro.getPlayer(player);
        if(player1 != null) {
            Country country = Countrypro.getCountry(player1.getCountry());
            Map<Integer, ItemStack> inventory = new HashMap<>();
            if(type.equals(1)) {
                System.out.println(type);
                //特殊行動
                size = 27;
                title = "特殊行動";
                if (country != null) {
                    stack = new ItemStack(Material.EMERALD);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("保有アクト");
                    lore.clear();
                    lore.add("value: " + country.getAct().toString());
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "OA");
                    stack.setItemMeta(meta);
                    inventory.put(8, stack);

                    stack = new ItemStack(Material.BOOK);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("メニューに戻る");
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu");
                    stack.setItemMeta(meta);
                    inventory.put(26, stack);

                    stack = new ItemStack(Material.PAPER);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("アクトメニューに戻る");
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu2");
                    stack.setItemMeta(meta);
                    inventory.put(17, stack);

                    stack = new ItemStack(Material.ENDER_PEARL);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("トランスポート");
                    meta.setLore(new ArrayList<>(Arrays.asList("アクトを2消費でテレポートできます。")));
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "TP");
                    stack.setItemMeta(meta);
                    inventory.put(1, stack);

                    stack = new ItemStack(Material.LIGHT_GRAY_SHULKER_BOX);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("大量輸送");
                    meta.setLore(new ArrayList<>(Arrays.asList("アクトを5消費でシュルカーを1つ入手します")));
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "AC");
                    stack.setItemMeta(meta);
                    inventory.put(3, stack);

                    stack = new ItemStack(Material.IRON_PICKAXE);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("妨害工作");
                    meta.setLore(new ArrayList<>(Arrays.asList("アクトを10消費で保護を無効化します")));
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "AAA");
                    stack.setItemMeta(meta);
                    inventory.put(5, stack);

                }
            }else if(type.equals(2)) {
                System.out.println(type);
                size = 27;
                title = "アクトシステム";
                if (country != null) {
                    stack = new ItemStack(Material.EMERALD);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("保有アクト");
                    lore.clear();
                    lore.add("value: " + country.getAct().toString());
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "OA");
                    stack.setItemMeta(meta);
                    //inventory.put(0, stack);

                    stack = new ItemStack(Material.IRON_PICKAXE);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("総産出予定アクト");
                    lore.clear();
                    lore.add("value: " + Countrypro.getAllActs(country));
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "GDA");
                    stack.setItemMeta(meta);
                    //inventory.put(9, stack);

                    stack = new ItemStack(Material.DARK_OAK_SAPLING);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("累計アクト");
                    lore.clear();
                    lore.add("value: " + country.getCumact());
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CA");
                    stack.setItemMeta(meta);
                    //inventory.put(18, stack);

                    stack = new ItemStack(Material.COMPASS);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("実効支配権");
                    lore.clear();
                    lore.add("value: " + country.getEffective());
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "ER");
                    stack.setItemMeta(meta);
                    //inventory.put(19, stack);

                    stack = new ItemStack(Material.BRICK);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("設備管理,プロヴィンス管理");
                    lore.clear();
                    lore.add("設備関連の操作をします。");
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EM");
                    stack.setItemMeta(meta);
                    //inventory.put(12, stack);

                    stack = new ItemStack(Material.FEATHER);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("特殊行動");
                    lore.clear();
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "SA");
                    stack.setItemMeta(meta);
                    //inventory.put(13, stack);

                    stack = new ItemStack(Material.MOJANG_BANNER_PATTERN);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("実効支配");
                    lore.clear();
                    Provinepro.checkprovince(player, player.getLocation(),null);
                    Province provine = Provinepro.getProvince(player.getChunk());
                    if (provine != null) {
                        lore.add("position: " + provine.gettag());
                        String data = "無し";
                        Country country1 = Countrypro.getCountry(provine.getOwner());
                        if (country1 != null) {
                            data = country1.getName();
                        }
                        lore.add("所持国: " + data);
                        if (country1 != null || country.getEffective() < 1) {
                            lore.add("不可能");
                        } else {
                            lore.add("可能");
                        }
                    }
                    meta.setLore(lore);
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EC");
                    stack.setItemMeta(meta);
                    //inventory.put(14, stack);

                    stack = new ItemStack(Material.BOOK);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("メニューに戻る");
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu");
                    stack.setItemMeta(meta);
                    inventory.put(26, stack);

                    stack = new ItemStack(Material.ENCHANTED_BOOK);
                    meta = stack.getItemMeta();
                    meta.setDisplayName("国家情報");
                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CI");
                    lore.clear();
                    lore.add("§f国名: " + country.getName());
                    lore.add("§f略称: " + country.getNickname());
                    lore.add("§f===== メンバー =====");
                    for (String target : country.getMember()) {
                        String data = "§f";
                        WPlayer target2 = Playerpro.getPlayer(target);
                        if (target2 != null) {
                            if (target2.getUuid().equalsIgnoreCase(player1.getUuid())) {
                                data = data + "§6§l" + target2.getName() + "§r§f";
                            } else {
                                data = data + target2.getName();
                            }
                            if (country.getHead().equals(target2.getUuid())) {
                                data = data + " §e[HED]§f";
                            }
                            if (country.getReader().contains(target2.getUuid())) {
                                data = data + " §3[AST]§f";
                            }
                            if (country.getActPa().contains(target2.getUuid())) {
                                data = data + " §c[ACT]§f";
                            }
                            lore.add(data);
                        }
                    }
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    inventory.put(8, stack);

                }

            }else if(type >= 1000 && type < 2000) {
                if (country != null) {
                    Integer Product = 0;
                    if (type == 1000) {
                        size = 18;
                        title = "国家カテゴリー " + country.getName();
                        stack = new ItemStack(Material.NETHER_STAR);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("国家の象徴");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "L1");
                        stack.setItemMeta(meta);
                        inventory.put(1, stack);

                        stack = new ItemStack(Material.IRON_HORSE_ARMOR);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("軍事拠点");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "S1");
                        stack.setItemMeta(meta);
                        inventory.put(3, stack);

                        stack = new ItemStack(Material.GOLD_INGOT);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("経済拠点");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "R1");
                        stack.setItemMeta(meta);
                        inventory.put(5, stack);

                        stack = new ItemStack(Material.BOOK);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("メニューに戻る");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu");
                        stack.setItemMeta(meta);
                        inventory.put(7, stack);

                        stack = new ItemStack(Material.PAPER);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("アクトメニューに戻る");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu2");
                        stack.setItemMeta(meta);
                        inventory.put(16, stack);

                        if (country.getProvince() != null) {
                            if (country.getProvince().size() >= 1) {
                                stack = new ItemStack(Material.STONE_SWORD);
                                meta = stack.getItemMeta();
                                meta.setDisplayName("次へ");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "NEXT");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Page"), PersistentDataType.INTEGER, 1001);
                                stack.setItemMeta(meta);
                                inventory.put(17, stack);
                            }
                        }

                        Map<Integer, String> data = country.getFacility();
                        Gson gson = new Gson();
                        lore.clear();
                        if (data.containsKey(0)) {
                            stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("有効");
                            Facility facility = gson.fromJson(data.get(0), Facility.class);
                            if (facility != null) {
                                lore.add("§fname: " + facility.getName());
                                lore.add("§fLocate: " + facility.getLocationS());
                            }
                            Product++;
                        } else {
                            stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("無効");
                            lore.add("null");
                        }
                        meta.setLore(lore);
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "L2");
                        stack.setItemMeta(meta);
                        inventory.put(10, stack);

                        lore.clear();
                        if (data.containsKey(1)) {
                            stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("有効");
                            Facility facility = gson.fromJson(data.get(1), Facility.class);
                            if (facility != null) {
                                lore.add("§fname: " + facility.getName());
                                lore.add("§fLocate: " + facility.getLocationS());
                            }
                            Product++;
                        } else {
                            stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("無効");
                            lore.add("null");
                        }
                        meta.setLore(lore);
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "S2");
                        stack.setItemMeta(meta);
                        inventory.put(12, stack);

                        lore.clear();
                        if (data.containsKey(2)) {
                            stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("有効");
                            Facility facility = gson.fromJson(data.get(2), Facility.class);
                            if (facility != null) {
                                lore.add("§fname: " + facility.getName());
                                lore.add("§fLocate: " + facility.getLocationS());
                            }
                            Product++;
                        } else {
                            stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("無効");
                            lore.add("null");
                        }
                        meta.setLore(lore);
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "R2");
                        stack.setItemMeta(meta);
                        inventory.put(14, stack);

                        stack = new ItemStack(Material.IRON_PICKAXE);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("産出予定アクト");
                        lore.clear();
                        lore.add("§fvalue: " + Product);
                        meta.setLore(lore);
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "AA");
                        stack.setItemMeta(meta);
                        inventory.put(8, stack);
                    } else if (type > 1000) {
                        Integer i = type - 1000;
                        if (country.getProvince() != null) {
                            if (i <= country.getProvince().size()) {
                                Province province = Provinepro.getProvince(country.getProvince().get(i - 1));
                                if (province != null) {
                                    size = 18;
                                    title = "市街カテゴリー " + province.getName();
                                    stack = new ItemStack(Material.WRITABLE_BOOK);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("役所");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CL1");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Pronum"), PersistentDataType.INTEGER, i);
                                    stack.setItemMeta(meta);
                                    inventory.put(1, stack);

                                    stack = new ItemStack(Material.IRON_HORSE_ARMOR);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("軍事拠点");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CS1");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Pronum"), PersistentDataType.INTEGER, i);
                                    stack.setItemMeta(meta);
                                    inventory.put(3, stack);

                                    stack = new ItemStack(Material.GOLD_INGOT);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("経済拠点");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CR1");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Pronum"), PersistentDataType.INTEGER, i);
                                    stack.setItemMeta(meta);
                                    inventory.put(5, stack);

                                    stack = new ItemStack(Material.OAK_SIGN);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("名前変更");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "PNC");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Protag"), PersistentDataType.STRING, province.gettag());
                                    stack.setItemMeta(meta);
                                    inventory.put(0, stack);

                                    stack = new ItemStack(Material.BOOK);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("メニューに戻る");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu");
                                    stack.setItemMeta(meta);
                                    inventory.put(7, stack);

                                    stack = new ItemStack(Material.GRASS_BLOCK);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("Province Info");
                                    lore.clear();
                                    lore.add("§fTAG: " + province.gettag());
                                    lore.add("§fName: " + province.getName());
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "PPI");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Pro"), PersistentDataType.STRING, province.gettag());
                                    stack.setItemMeta(meta);
                                    inventory.put(9, stack);

                                    if (country.getProvince().size() >= i + 1) {
                                        stack = new ItemStack(Material.STONE_SWORD);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("次へ");
                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "NEXT");
                                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Page"), PersistentDataType.INTEGER, i + 1001);
                                        stack.setItemMeta(meta);
                                        inventory.put(17, stack);
                                    }

                                    stack = new ItemStack(Material.SHIELD);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("戻る");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "RETURN");
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Page"), PersistentDataType.INTEGER, i + 999);
                                    stack.setItemMeta(meta);
                                    inventory.put(16, stack);

                                    Map<Integer, String> data = province.getFacility();
                                    Gson gson = new Gson();
                                    lore.clear();
                                    if (data.containsKey(0)) {
                                        stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("有効");
                                        Facility facility = gson.fromJson(data.get(0), Facility.class);
                                        if (facility != null) {
                                            lore.add("§fname: " + facility.getName());
                                            lore.add("§fLocate: " + facility.getLocationS());
                                        }
                                        Product++;
                                    } else {
                                        stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("無効");
                                        lore.add("null");
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CL2");
                                    stack.setItemMeta(meta);
                                    inventory.put(10, stack);

                                    lore.clear();
                                    if (data.containsKey(1)) {
                                        stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("有効");
                                        Facility facility = gson.fromJson(data.get(1), Facility.class);
                                        if (facility != null) {
                                            lore.add("§fname: " + facility.getName());
                                            lore.add("§fLocate: " + facility.getLocationS());
                                        }
                                        Product++;
                                    } else {
                                        stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("無効");
                                        lore.add("null");
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CS2");
                                    stack.setItemMeta(meta);
                                    inventory.put(12, stack);

                                    lore.clear();
                                    if (data.containsKey(2)) {
                                        stack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("有効");
                                        Facility facility = gson.fromJson(data.get(2), Facility.class);
                                        if (facility != null) {
                                            lore.add("§fname: " + facility.getName());
                                            lore.add("§fLocate: " + facility.getLocationS());
                                        }
                                        Product++;
                                    } else {
                                        stack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
                                        meta = stack.getItemMeta();
                                        meta.setDisplayName("無効");
                                        lore.add("null");
                                    }
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "CR2");
                                    stack.setItemMeta(meta);
                                    inventory.put(14, stack);

                                    stack = new ItemStack(Material.IRON_PICKAXE);
                                    meta = stack.getItemMeta();
                                    meta.setDisplayName("産出予定アクト");
                                    lore.clear();
                                    lore.add("§fvalue: " + Product);
                                    meta.setLore(lore);
                                    meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "AA");
                                    stack.setItemMeta(meta);
                                    inventory.put(8, stack);
                                }
                            }
                        }
                    }
                }
            }
            Inventory inv = Bukkit.createInventory(null, size, title);
            for (Integer i : inventory.keySet()) {
                if (i <= size - 1) {
                    inv.setItem(i, inventory.get(i));
                }
            }
            return inv;
        }
        return null;
    }

    public static Inventory getProvinceAndFacility(Integer type , Player player , Integer num , String data){
        Integer size = 9;
        String title = "null";
        ItemStack stack = null;
        ItemMeta meta = null;
        List<String> lore = new ArrayList<>();
        Map<Integer, ItemStack> inventory = new HashMap<>();
        WPlayer player1 = Playerpro.getPlayer(player);
        Gson gson = new Gson();
        if(player1 != null) {
            Country country = Countrypro.getCountry(player1.getCountry());
            if (type == 0) {
                //Province
                Province province = Provinepro.getProvince(data);
                if(province != null) {
                    if (province.getOwner().equalsIgnoreCase(country.getTag())) {
                        stack = new ItemStack(Material.OAK_SIGN);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("名前変更");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EP2");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                        stack.setItemMeta(meta);
                        inventory.put(2, stack);

                        stack = new ItemStack(Material.MAGMA_BLOCK);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("解放");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EP3");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                        stack.setItemMeta(meta);
                        inventory.put(4, stack);
                    }else {
                        Country target = Countrypro.getCountry(province.getOwner());
                        if(target != null) {
                        }else {
                            stack = new ItemStack(Material.IRON_SHOVEL);
                            meta = stack.getItemMeta();
                            meta.setDisplayName("セット");
                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EP1");
                            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                            stack.setItemMeta(meta);
                            inventory.put(0, stack);
                        }
                    }
                }

            } else if (type == 1) {
                //Facility
                Map<Integer,String> list = country.getFacility();
                String name = "";
                if(num == 0){
                    name = "国家の象徴 , 役所";
                } else if(num == 1){
                    name = "軍事拠点";
                }else if(num == 2){
                    name = "経済拠点";
                }else {
                    return player.getInventory();
                }
                title = "施設編集 " + name;
                if(data != null){
                    Province province = Provinepro.getProvince(data);
                    if(province != null) {
                        if(province.getOwner().equalsIgnoreCase(country.getTag())) {
                            list.clear();
                            list = province.getFacility();
                            Facility facility = gson.fromJson(list.get(num), Facility.class);
                            if (facility != null) {
                                stack = new ItemStack(Material.IRON_SHOVEL);
                                meta = stack.getItemMeta();
                                meta.setDisplayName("セット");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF1");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                                stack.setItemMeta(meta);
                                inventory.put(0, stack);

                                stack = new ItemStack(Material.OAK_SIGN);
                                meta = stack.getItemMeta();
                                meta.setDisplayName("名前変更");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF2");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                                stack.setItemMeta(meta);
                                inventory.put(2, stack);

                                stack = new ItemStack(Material.MAGMA_BLOCK);
                                meta = stack.getItemMeta();
                                meta.setDisplayName("消去");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF3");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                                stack.setItemMeta(meta);
                                inventory.put(4, stack);
                            } else {
                                stack = new ItemStack(Material.IRON_SHOVEL);
                                meta = stack.getItemMeta();
                                meta.setDisplayName("セット");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF1");
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, data);
                                meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                                stack.setItemMeta(meta);
                                inventory.put(0, stack);
                            }
                        }
                    }
                }else{
                    Facility facility = gson.fromJson(list.get(num),Facility.class);
                    if(facility != null){
                        stack = new ItemStack(Material.IRON_SHOVEL);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("セット");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF1");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, "CNT");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                        stack.setItemMeta(meta);
                        inventory.put(0,stack);

                        stack = new ItemStack(Material.OAK_SIGN);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("名前変更");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF2");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, "CNT");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                        stack.setItemMeta(meta);
                        inventory.put(2,stack);

                        stack = new ItemStack(Material.MAGMA_BLOCK);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("消去");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF3");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, "CNT");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                        stack.setItemMeta(meta);
                        inventory.put(4,stack);
                    }else {
                        stack = new ItemStack(Material.IRON_SHOVEL);
                        meta = stack.getItemMeta();
                        meta.setDisplayName("セット");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "EF1");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Type"), PersistentDataType.STRING, "CNT");
                        meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Num"), PersistentDataType.INTEGER, num);
                        stack.setItemMeta(meta);
                        inventory.put(0,stack);
                    }
                }
            }
            stack = new ItemStack(Material.BOOK);
            meta = stack.getItemMeta();
            meta.setDisplayName("メニューに戻る");
            meta.getPersistentDataContainer().set(new NamespacedKey(WARS.plugin, "Act"), PersistentDataType.STRING, "menu");
            stack.setItemMeta(meta);
            inventory.put(8, stack);
        }
        Inventory inv = Bukkit.createInventory(null, size, title);
        for (Integer i : inventory.keySet()) {
            if (i <= size - 1) {
                inv.setItem(i, inventory.get(i));
            }
        }
        return inv;
    }

    public static boolean ActSystemClickInventory(InventoryClickEvent e){
        ItemStack stack = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        WPlayer senders = Playerpro.getPlayer(player);
        if(stack != null && player != null){
            String data = "";
            try {
                data = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Act")
                        , PersistentDataType.STRING);
            }catch (Exception a){
                return false;
            }
            if(data != null){
                System.out.println(data);
                if(data.equalsIgnoreCase("menu")){
                    player.openInventory(menu.getMenu(player));
                }else if(data.equalsIgnoreCase("EM")){
                    player.openInventory(getInventory(1000,player));
                }else if(data.equalsIgnoreCase("NEXT") || data.equalsIgnoreCase("RETURN")){
                    Integer page = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Page")
                            , PersistentDataType.INTEGER);
                    if(page != null){
                        player.openInventory(getInventory(page,player));
                    }
                }else if(data.equalsIgnoreCase("PNC")){
                    String tag = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Protag")
                            , PersistentDataType.STRING);
                    if(tag != null){
                        e.getWhoClicked().closeInventory();
                        tellraw.sendtellraw(player,tellraw.gettellrawtosuggest("クリックすることでコマンドをコピーできます","/country province edit name " + tag + " "));
                    }
                }else if(data.equalsIgnoreCase("menu2")){
                    player.openInventory(getInventory(2,player));
                }else if(data.equalsIgnoreCase("EC")){
                    tellraw.sendtellraw(player, tellraw.gettellrawtocommand("実行支配する場合はクリックしてください。","/country province add nowposition"));
                }else if(data.equalsIgnoreCase("SA")){
                    player.openInventory(getInventory(1,player));
                }else if(data.equalsIgnoreCase("TP")){
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if(country != null){
                        if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid()) || country.getActPa().contains(senders.getUuid())) {
                            if(country.getAct() >= 2){
                                Location location = country.getLocation();
                                country.setAct(country.getAct() -2);
                                Countrypro.setCountry(country);
                                player.teleport(location);
                                e.getWhoClicked().closeInventory();
                                tellraw.Sendsystemmessage(player,"テレポートしました。");
                            }else {
                                tellraw.Sendsystemmessage(player,"アクトがありません。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"あなたには権限がありません。");
                        }
                    }else {
                        tellraw.Sendsystemmessage(player, "国家に所属していないと使うことができません。");
                    }
                }else if(data.equalsIgnoreCase("AC")){
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if(country != null){
                        if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid()) || country.getActPa().contains(senders.getUuid())) {
                            if(country.getAct() >= 5){
                                country.setAct(country.getAct() - 5);
                                Countrypro.setCountry(country);
                                player.getInventory().addItem(new ItemStack(Material.SHULKER_BOX));
                                e.getWhoClicked().closeInventory();
                                tellraw.Sendsystemmessage(player,"シュルカーボックスを追加しました。");
                            }else {
                                tellraw.Sendsystemmessage(player,"アクトがありません。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"あなたには権限がありません。");
                        }
                    }else {
                        tellraw.Sendsystemmessage(player, "国家に所属していないと使うことができません。");
                    }
                }else if(data.equalsIgnoreCase("AAA")){
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if(country != null){
                        if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid()) || country.getActPa().contains(senders.getUuid())) {
                            if(country.getAct() >= 10){
                                country.setAct(country.getAct() - 10);
                                Countrypro.setCountry(country);
                                TileAttack(senders.getUuid());
                                e.getWhoClicked().closeInventory();
                                tellraw.Sendsystemmessage(player,"10秒間保護を無効化を開始しました。");
                            }else {
                                tellraw.Sendsystemmessage(player,"アクトがありません。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"あなたには権限がありません。");
                        }
                    }else {
                        tellraw.Sendsystemmessage(player, "国家に所属していないと使うことができません。");
                    }
                } else if(data.equalsIgnoreCase("EF1")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    Integer num = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Num")
                            , PersistentDataType.INTEGER);
                    if(type != null && num != null){
                        if(type.equalsIgnoreCase("CNT")){
                            String name = "";
                            if(num == 0){
                                name = "国家の象徴";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            Bukkit.getServer().dispatchCommand(player,"country facility set " + name);
                        }else{
                            String name = "";
                            if(num == 0){
                                name = "役所";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            Bukkit.getServer().dispatchCommand(player,"country province facility " + type + " set " + name);
                        }
                    }
                }else if(data.equalsIgnoreCase("EF2")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    Integer num = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Num")
                            , PersistentDataType.INTEGER);
                    if(type != null && num != null){
                        if(type.equalsIgnoreCase("CNT")){
                            String name = "";
                            if(num == 0){
                                name = "国家の象徴";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            tellraw.sendtellraw(player,tellraw.gettellrawtosuggest("実行する場合はこちらをクリックしてコマンドをコピーしてください。","/country facility edit name " + name));
                        }else{
                            String name = "";
                            if(num == 0){
                                name = "役所";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            tellraw.sendtellraw(player,tellraw.gettellrawtosuggest("実行する場合はこちらをクリックしてコマンドをコピーしてください。","/country province facility " + type + " edit name " + name));
                        }
                    }
                }else if(data.equalsIgnoreCase("EF3")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    Integer num = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Num")
                            , PersistentDataType.INTEGER);
                    if(type != null && num != null){
                        if(type.equalsIgnoreCase("CNT")){
                            String name = "";
                            if(num == 0){
                                name = "国家の象徴";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            Bukkit.getServer().dispatchCommand(player,"country facility remove " + name);
                        }else{
                            String name = "";
                            if(num == 0){
                                name = "役所";
                            }else if(num == 1){
                                name = "軍事拠点";
                            }else if(num == 2){
                                name = "経済拠点";
                            }
                            Bukkit.getServer().dispatchCommand(player,"country province facility " + type + " remove " + name);
                        }
                    }
                }else if(data.equalsIgnoreCase("EP1")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    Bukkit.getServer().dispatchCommand(player,"country province add " + type);
                }else if(data.equalsIgnoreCase("EP2")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    tellraw.sendtellraw(player,tellraw.gettellrawtosuggest("実行する場合はこちらをクリックしてコマンドをコピーしてください。","/country province edit name " + type + " "));
                }else if(data.equalsIgnoreCase("EP3")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Type")
                            , PersistentDataType.STRING);
                    Bukkit.getServer().dispatchCommand(player,"country province remove " + type);
                }else if(data.equalsIgnoreCase("L1")){
                    player.openInventory(getProvinceAndFacility(1,player,0,null));
                }else if(data.equalsIgnoreCase("S1")){
                    player.openInventory(getProvinceAndFacility(1,player,1,null));
                }else if(data.equalsIgnoreCase("R1")){
                    player.openInventory(getProvinceAndFacility(1,player,2,null));
                }else if(data.equalsIgnoreCase("CL1")) {
                    Integer type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Pronum")
                            , PersistentDataType.INTEGER);
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if (country != null) {
                        Province province = Provinepro.getProvince(country.getProvince().get(type - 1));
                        if(province != null){
                            player.openInventory(getProvinceAndFacility(1,player,0,province.gettag()));
                        }
                    }
                }else if(data.equalsIgnoreCase("CS1")) {
                    Integer type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Pronum")
                            , PersistentDataType.INTEGER);
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if (country != null) {
                        Province province = Provinepro.getProvince(country.getProvince().get(type - 1));
                        if(province != null){
                            player.openInventory(getProvinceAndFacility(1,player,1,province.gettag()));
                        }
                    }
                }else if(data.equalsIgnoreCase("CR1")) {
                    Integer type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Pronum")
                            , PersistentDataType.INTEGER);
                    Country country = Countrypro.getCountry(senders.getCountry());
                    if (country != null) {
                        Province province = Provinepro.getProvince(country.getProvince().get(type - 1));
                        if(province != null){
                            player.openInventory(getProvinceAndFacility(1,player,2,province.gettag()));
                        }
                    }
                }else if(data.equalsIgnoreCase("PPI")){
                    String type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "Pro")
                            , PersistentDataType.STRING);
                    player.openInventory(getProvinceAndFacility(0,player,0,type));
                }
                e.setCancelled(true);
            }
        }
        return true;
    }

    public static boolean ActSystemClick(PlayerInteractEvent e){
        ItemStack stack = e.getItem();
        Player player = (Player) e.getPlayer();
        WPlayer senders = Playerpro.getPlayer(player);
        if(stack != null) {
            String data = "";
            try {
                data = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "ActItem")
                        , PersistentDataType.STRING);
            } catch (Exception a) {
                return false;
            }
            if (data != null) {
                if(data.equalsIgnoreCase("FAS")){
                    Country country = null;
                    Integer type = null;
                    try {
                        country = Countrypro.getCountry(stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "FAC")
                                , PersistentDataType.STRING));
                        type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "FAT")
                                , PersistentDataType.INTEGER);
                    }catch (Exception b){
                        return false;
                    }
                    if(country != null && type != null && senders != null){
                        try {
                            e.getClickedBlock().getState();
                        }catch (Exception d){
                            return false;
                        }
                        if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                            Gson gson = new Gson();
                            Facility facility = new Facility(e.getClickedBlock().getLocation(), "null", type);
                            Map<Integer, String> yeah = country.getFacility();
                            yeah.put(type, gson.toJson(facility, Facility.class));
                            if(Serverdata.getNowDay() >= yeah.size()) {
                                country.setFacility(yeah);
                                Countrypro.setCountry(country);
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                                tellraw.Sendsystemmessage(player, "設定しました。");
                                e.setCancelled(true);
                            }else{
                                System.out.println(Serverdata.getNowDay());
                                tellraw.Sendsystemmessage(player,"本日は占領できません。");
                            }
                        }

                    }
                }else if(data.equalsIgnoreCase("FASP")){
                    Province province = null;
                    Integer type = null;
                    Country country = null;
                    try {
                        province = Provinepro.getProvince(stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "FAC")
                                , PersistentDataType.STRING));
                        type = stack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(WARS.plugin, "FAT")
                                , PersistentDataType.INTEGER);
                        if(province != null) {
                            country = Countrypro.getCountry(province.getOwner());
                        }
                    }catch (Exception b){
                        return false;
                    }
                    if(province != null && type != null && senders != null && country != null){
                        try {
                            e.getClickedBlock().getState();
                        }catch (Exception d){
                            return false;
                        }
                        if(country.getHead().equalsIgnoreCase(senders.getUuid()) || country.getReader().contains(senders.getUuid())) {
                            if(province.gettag().equalsIgnoreCase( Provinepro.getProvincetag(player.getChunk()))) {
                                Gson gson = new Gson();
                                Facility facility = new Facility(e.getClickedBlock().getLocation(), "null", type);
                                Map<Integer, String> yeah = province.getFacility();
                                yeah.put(type, gson.toJson(facility, Facility.class));
                                province.setFacility(yeah);
                                Provinepro.setProvince(province);
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                                tellraw.Sendsystemmessage(player, "設定しました。");
                                e.setCancelled(true);
                            }else{
                                tellraw.Sendsystemmessage(player,"指定したプロヴィンスの範囲外です。");
                            }
                        }else {
                            tellraw.Sendsystemmessage(player,"元首又は権限持ちでなければこのコマンドを実行することができません。");
                        }
                    }
                }
            }
            if(stack.getType().equals(Material.STICK)){
                if(player.isSneaking() && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))){
                    player.openInventory(menu.getMenu(player));
                }
            }
        }
        return false;
    }

    public static Map<String,BukkitTask> TileAttacks = new HashMap<>();

    public static void TileAttack(String uuid){
        if(TileAttacks.containsKey(uuid) == false) {
            BukkitTask task = new BukkitRunnable() {
                Integer timer = WARS.config.getInt("TileAttacklimit", 10);
                Integer now = 0;
                String Uuid = uuid;

                @Override
                public void run() {
                    Player player = null;
                    for(Player players : Bukkit.getOnlinePlayers()){
                        if(players.getUniqueId().toString().equalsIgnoreCase(Uuid)){
                            player = players;
                        }
                    }
                    if (timer <= now) {
                        if (player != null) {
                            tellraw.Sendsystemmessage(player, "無効化が解除されました。");
                            TileAttacks.remove(Uuid);
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                    "残り: " + 0 + " 秒"));
                            this.cancel();
                        }
                    }else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                                "残り: " + (timer - now) + " 秒"));
                    }
                    now++;
                }

            }.runTaskTimer(WARS.plugin, 0, 20);
            TileAttacks.put(uuid,task);
        }else {
            Player Sender = Bukkit.getPlayer(uuid);
            if(Sender != null){
                tellraw.Sendsystemmessage(Sender,"実行中です。");
            }
        }
    }

}
