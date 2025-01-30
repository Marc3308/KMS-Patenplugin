package me.marc3308.patenplugin.moderator;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.parte.inventorymanager;
import me.marc3308.patenplugin.utility;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.ArrayList;

import static me.marc3308.patenplugin.Patenplugin.plugin;
import static me.marc3308.patenplugin.utility.*;

public class moderationsmoduscommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player p && p.hasPermission("mod")))return true;

        if(p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN)){
            p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"beobachtermodus"));
            p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"modmode"));
            for (Player o : Bukkit.getOnlinePlayers())o.showPlayer(plugin,p);
            p.setInvulnerable(false);
            p.setGameMode(GameMode.SURVIVAL);
            p.setAllowFlight(false);
            p.setFlying(false);
            inventorymanager.restorinv(p);
            p.removePotionEffect(PotionEffectType.GLOWING);
            //utility.sendpack(p);
            // Packet camera
            PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
            cameraPacket.getIntegers().write(0, p.getEntityId());
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, cameraPacket);
            p.sendMessage(ChatColor.RED+"Modmodus beendet");
            return true;
        }

        //save the inv
        inventorymanager.saveinv(p);
        //utility.sendpack(p);

        //set the mode
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"modmode"), PersistentDataType.BOOLEAN,true);
        p.setInvulnerable(true);
        Bukkit.getOnlinePlayers().forEach(o -> {
            if(!o.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN))o.hidePlayer(plugin,p);
            if(o.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN))p.showPlayer(plugin,o);
        });
        p.setGameMode(GameMode.ADVENTURE);
        p.setAllowFlight(true);
        p.setFlying(true);

        //Stock der weißheit, gibt info über nen block
        p.getInventory().setItem(0,getItem(Material.STICK,ChatColor.BOLD+""+ChatColor.BLUE+"Block Information",new ArrayList<>(){{
            add("Rechtsklicken um Informationen über den Block zu erhalten");
            add("~Powerd by Coreprotect");
        }},true));
        //Beobachter
        p.getInventory().setItem(1,getItem(Material.COMPASS,ChatColor.BOLD+""+ChatColor.DARK_GREEN+"Spieler Beobachten",new ArrayList<>(){{
            add("Rechtsklicken um den Spieler zu beobachten");
        }},false));
        //Blockedit
        p.getInventory().setItem(2,getItem(Material.VAULT,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Block Menü",new ArrayList<>(){{
            add("Clicken um das Blockmenü zu öffnen");
        }},false));

        //clear
        p.getInventory().setItem(6,getItem(Material.BRUSH,ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Clear inventar",new ArrayList<>(),false));
        //Vansih
        p.getInventory().setItem(7,getItem(Material.BARRIER,ChatColor.BOLD+""+ChatColor.GREEN+"Vanish ausschalten",new ArrayList<>(),false));
        //Modmodus
        p.getInventory().setItem(8,getItem(Material.RED_CONCRETE,ChatColor.BOLD+""+ChatColor.RED+"Modmodus Beenden",new ArrayList<>(),false));

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
