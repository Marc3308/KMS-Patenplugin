package me.marc3308.patenplugin.parte;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class inventorymanager {

    private static final HashMap<UUID,PlayerData> savedata=new HashMap<>();

    public static void saveinv(Player p){

        //save in construckt class4
        PlayerData playerData=new PlayerData();

        //get all the invos
        playerData.loc = p.getLocation();
        playerData.inv = p.getInventory().getContents();
        playerData.armor = p.getInventory().getArmorContents();

        savedata.put(p.getUniqueId(),playerData);

        p.getInventory().clear();

    }

    public static void restorinv(Player p){

        PlayerData pdata=savedata.get(p.getUniqueId());

        p.teleport(pdata.getLoc());
        p.getInventory().setContents(pdata.getInv());
        p.getInventory().setArmorContents(pdata.getArmor());

    }


    private static class PlayerData {
        private ItemStack[] inv;
        private ItemStack[] armor;
        private Location loc;

        public Location getLoc() {
            return loc;
        }
        public ItemStack[] getArmor() {
            return armor;
        }
        public ItemStack[] getInv() {
            return inv;
        }
    }
}
