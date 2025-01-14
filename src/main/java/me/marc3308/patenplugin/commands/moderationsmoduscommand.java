package me.marc3308.patenplugin.commands;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.parte.inventorymanager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static me.marc3308.patenplugin.Patenplugin.plugin;

public class moderationsmoduscommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player p && p.hasPermission("mod")
                && !p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN)))return true;

        //save the inv
        inventorymanager.saveinv(p);

        //set the mode
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"modmode"), PersistentDataType.BOOLEAN,true);
        p.setInvulnerable(true);
        p.setInvisible(true);
        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true); //todo watch that it not clash with flight

        //Stock der weißheit, gibt info über nen block
        p.getInventory().setItem(0,getItem(Material.STICK,ChatColor.BLUE+"Block Information",new ArrayList<>(),true));
        //Beobachter
        p.getInventory().setItem(1,getItem(Material.COMPASS,"Spieler Beobachten",new ArrayList<>(),false));
        //Zerstören
        p.getInventory().setItem(2,getItem(Material.NETHERITE_HOE,"Block Zerstören",new ArrayList<>(),false));
        //Bukkit
        p.getInventory().setItem(3,getItem(Material.BUCKET,"Flüssigkeit kapput",new ArrayList<>(),false));
        //Vansih
        p.getInventory().setItem(7,getItem(Material.BARRIER,ChatColor.GREEN+"Vanish ausschalten",new ArrayList<>(),false));
        //Modmodus
        p.getInventory().setItem(8,getItem(Material.RED_CONCRETE,"Modmodus beenden",new ArrayList<>(),false));

        return true;
    }

    public static ItemStack getItem(Material material ,String name,ArrayList<String> beschreibung,boolean mending) {
        ItemStack item=new ItemStack(material);
        ItemMeta meta=item.getItemMeta();
        meta.setDisplayName(name);
        if(mending)meta.addEnchant(Enchantment.MENDING,1,false);
        meta.setLore(beschreibung);
        item.setItemMeta(meta);

        return item;
    }
}
