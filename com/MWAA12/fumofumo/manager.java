package com.MWAA12.fumofumo;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class manager {

    public static ItemStack cirno;

    public static void init(){
        createcirno();


    }

    private static void createcirno(){
        Material type = Material.matchMaterial("PLAYER_HEAD");


        ItemStack item = new ItemStack(type,1);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner("unorigin");
        meta.setDisplayName("§bCirno Fumo");
        List<String> lore = new ArrayList<>();
        lore.add("This is a cirno fumo!");
        lore.add("She is the strongest fairy!");
        meta.setLore(lore);
        item.setItemMeta(meta);

        cirno = item;

    }

    private static void createfumo(){




    }
}
