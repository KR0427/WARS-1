package keizaiya.wars.process;

import keizaiya.wars.WARS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipe {
    public static void addingreripe(){
        for(org.bukkit.inventory.Recipe data : Bukkit.getRecipesFor(new ItemStack(Material.TNT))){
            if(data instanceof ShapedRecipe){
                ShapedRecipe target = (ShapedRecipe) data;
                System.out.println(target.getKey());
                Bukkit.removeRecipe(target.getKey());
            }
        }
        for(org.bukkit.inventory.Recipe data : Bukkit.getRecipesFor(new ItemStack(Material.END_CRYSTAL))){
            if(data instanceof ShapedRecipe){
                ShapedRecipe target = (ShapedRecipe) data;
                System.out.println(target.getKey());
                Bukkit.removeRecipe(target.getKey());
            }
        }
        NamespacedKey key = new NamespacedKey(WARS.plugin,"TNT");
        ShapedRecipe recipe = new ShapedRecipe(key,new ItemStack(Material.TNT));
        recipe.shape("CCC","CBC","CCC");
        recipe.setIngredient('C',Material.GUNPOWDER);
        recipe.setIngredient('B',Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(recipe);
    }
}
