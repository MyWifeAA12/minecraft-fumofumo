package net.caradr42.fumofumo;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import de.tr7zw.nbtapi.*;
import java.util.ArrayList;
import java.util.List;

public class fumoevents implements Listener {

    @EventHandler
    public static void blockPlace(BlockPlaceEvent event) {
        ItemStack rblock = event.getItemInHand().clone();
        ItemStack block2 = event.getItemInHand().clone();
        ItemStack block = event.getItemInHand();
        Player player = event.getPlayer();
        if (player.isSneaking()) {
            if (event.getBlock().getType().equals(Material.PLAYER_HEAD)) {
                //event.getBlock().setType(Material.AIR);
                event.setCancelled(true);

                if (block2.getAmount() > 1) block.setAmount(block2.getAmount()-1);
                else{player.getInventory().setItemInHand(null);}

                ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(event.getBlock().getLocation().add(0.5, 0, 0.5), EntityType.ARMOR_STAND);
                Location loc1 = armorstand.getLocation().clone();
                loc1.setDirection(player.getLocation().getDirection().multiply(-1));
                loc1.setY(armorstand.getLocation().getY() - 0.25);

                armorstand.teleport(loc1);

                armorstand.setGravity(false);


                rblock.setAmount(1);


                armorstand.setHelmet(rblock);
                armorstand.setCustomName(block.getItemMeta().getDisplayName());
                armorstand.setCustomNameVisible(true);
                armorstand.setArms(true);
                armorstand.setBasePlate(false);
                armorstand.setSmall(true);
                armorstand.setLeftLegPose(new EulerAngle(4.83456, 6.19592, 0));
                armorstand.setRightLegPose(new EulerAngle(4.83456, 0.0872665, 0));
                armorstand.setLeftArmPose(new EulerAngle(0, 0, 5.93412));
                armorstand.setRightArmPose(new EulerAngle(0, 0, 0.349066));
                armorstand.setInvulnerable(true);

                NBTItem nbti = new NBTItem(rblock);
                List<String> lore = rblock.getItemMeta().getLore();
                if(nbti.getBoolean("isfumo") == true);
                {
                    armorstand.setItemInHand(nbti.getItemStack("hand"));
                    armorstand.setChestplate(nbti.getItemStack("chest"));
                    armorstand.setLeggings(nbti.getItemStack("leg"));
                    armorstand.setBoots(nbti.getItemStack("boots"));

                }
            }
        }
    }

    @EventHandler
    public static void fumomisplace(BlockPlaceEvent event) {

        ItemStack block = event.getItemInHand();
        Player player = event.getPlayer();
        NBTItem nbti = new NBTItem(block);
        if(nbti.getBoolean("isfumo").equals(true) && !(player.isSneaking())){
            event.setCancelled(true);
            player.sendMessage("§4Please undress the fumo to put the head down normally");
        }
        else if(nbti.getBoolean("isfumo").equals(false)) {
            NBTTileEntity head = new NBTTileEntity(event.getBlockPlaced().getState());
            head.setString("Name", nbti.getString("Name"));
        }
    }


    @EventHandler
    public static void throwfumo(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if(item == null){return;}

        else if(item.getType().equals(Material.PLAYER_HEAD) && player.isSneaking())
        {
            if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

                ArmorStand armorstand = (ArmorStand) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ARMOR_STAND);
                Vector direction = player.getEyeLocation().getDirection();
                ItemStack item2 = item.clone();
                item.setAmount(1);
                //armorstand.getEquipment().setHelmet(item);
                armorstand.setHelmet(item);
                armorstand.setCustomName(item.getItemMeta().getDisplayName());
                armorstand.setCustomNameVisible(true);
                armorstand.setArms(true);
                armorstand.setBasePlate(false);
                armorstand.setSmall(true);
                armorstand.setLeftLegPose(new EulerAngle(4.83456, 6.19592, 0));
                armorstand.setRightLegPose(new EulerAngle(4.83456, 0.0872665, 0));
                armorstand.setLeftArmPose(new EulerAngle(0, 0, 5.93412));
                armorstand.setRightArmPose(new EulerAngle(0, 0, 0.349066));

                armorstand.setInvulnerable(true);

                List<String> lore = item.getItemMeta().getLore();
                NBTItem nbti = new NBTItem(item);

                if(nbti.getBoolean("isfumo")) {

                    armorstand.setItemInHand(nbti.getItemStack("hand"));
                    armorstand.setChestplate(nbti.getItemStack("chest"));
                    armorstand.setLeggings(nbti.getItemStack("leg"));
                    armorstand.setBoots(nbti.getItemStack("boots"));
                }

                double xV = direction.getX() * 0.75;
                double yV = direction.getY() * 0.75;
                double zV = direction.getZ() * 0.75;
                Vector velocity = new Vector(xV, yV, zV);
                armorstand.setVelocity(velocity);

                if (item2.getAmount() > 1) item.setAmount(item2.getAmount()-1);
                else{player.getInventory().setItemInHand(null);}
            }
        }


    }
    @EventHandler
    public static void removefumo(PlayerArmorStandManipulateEvent event) {
        ArmorStand armorstand = event.getRightClicked();
        Player player = event.getPlayer();
        if (armorstand.isSmall()==true) {
            if (event.getArmorStandItem().getType().equals(Material.PLAYER_HEAD)) {
                ItemStack head = armorstand.getHelmet().clone();
                ItemStack hand = armorstand.getItemInHand();
                ItemStack chest = armorstand.getChestplate();
                ItemStack legs = armorstand.getLeggings();
                ItemStack boots = armorstand.getBoots();

                if(event.getSlot().equals(EquipmentSlot.HEAD)){
                    event.setCancelled(true);
                    ItemStack air = new ItemStack(Material.AIR,1);
                    NBTItem nbti = new NBTItem(head);
                    ItemMeta meta = head.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    if(!(hand.equals(air) && chest.equals(air) && legs.equals(air) && boots.equals(air))) {

                        lore.add("This is a fumo!");
                        meta.setLore(lore);
                        head.setItemMeta(meta);
                        nbti.setBoolean("isfumo",true);
                    }
                    else{

                        lore.add(null);
                        meta.setLore(lore);
                        head.setItemMeta(meta);
                        nbti.setBoolean("isfumo",false);

                    }


                    nbti.setItemStack("hand",hand);
                    nbti.setItemStack("chest",chest);
                    nbti.setItemStack("leg",legs);
                    nbti.setItemStack("boots",boots);
                    head = nbti.getItem();


                    player.getInventory().addItem(head);
                    armorstand.remove();

                }

                /*if (event.getSlot().equals(EquipmentSlot.HEAD)) {
                    event.setCancelled(true);
                    player.getInventory().addItem(head);
                    player.getInventory().addItem(hand);
                    player.getInventory().addItem(chest);
                    player.getInventory().addItem(leg);
                    player.getInventory().addItem(boots);
                    armorstand.remove();
                }*/


            }
        }
    }

}
