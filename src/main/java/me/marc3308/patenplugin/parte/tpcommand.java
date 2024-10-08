package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.afkmanager.AFKManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class tpcommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        //checks if the command sender is a parte
        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(!p.hasPermission("parte"))return false;
        if(args.length==0)return false;

        Player p2= Bukkit.getPlayer(args[0]);

        //check if parte betreut schon einen spieler
        if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING)) {
            p.sendMessage(ChatColor.RED+"Bitte weise erst deine Person ein");
            return false;
        }



        //checks if the einzuweisende is on and not already in company of a parte
        if(p2==null) {
            p.sendMessage("Dieser Spieler ist gerade offline");
            return false;
        }
        if(!p2.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING)){
            p.sendMessage("Diesen Spieler muss man nicht mehr einweisen");
            return false;
        }
        if(p2.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING)!="nono" && p2.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING)!=null){

            UUID p3uuid =UUID.fromString(p2.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING));
            Player p3 = Bukkit.getPlayer(p3uuid);

            p.sendMessage(ChatColor.DARK_GREEN+ "Tut mir leid, aber "+ ChatColor.GREEN+ p3.getName() +ChatColor.DARK_GREEN+" kümmert sich schon um "+ChatColor.GREEN+ p2.getName());
            return false;
        }

        //remove player von afk manager
        AFKManager.playerleave(p);

        //saving the inv and clearing it for parten modus
        inventorymanager.saveinv(p);

        //sends the parte to the einzuweisendem
        Location a=p2.getLocation();
        p.teleport(a);
        p2.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING,p.getUniqueId().toString());
        p2.sendMessage(ChatColor.GREEN+p.getName()+ChatColor.DARK_GREEN+" betreut dich jetzt");


        //parten modus
        p.setInvulnerable(true);
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING,p2.getUniqueId().toString());


        //Inventory components

        //teleproter mit dem man sich zu den punkten tpn kann
        ItemStack Commpas=new ItemStack(Material.COMPASS);
        ItemMeta Commpas_meta=Commpas.getItemMeta();
        Commpas_meta.setDisplayName(ChatColor.BLUE+"Teleporter");
        Commpas_meta.addEnchant(Enchantment.MENDING,1,false );
        Commpas.setItemMeta(Commpas_meta);

        //Log
        ItemStack liste=new ItemStack(Material.BOOKSHELF);
        ItemMeta liste_meta=liste.getItemMeta();
        liste_meta.setDisplayName(ChatColor.GRAY+"Audit-Log");
        liste.setItemMeta(liste_meta);

        //teleproter mit dem er den wanderer zu sich tpn kann
        ItemStack Bone=new ItemStack(Material.BONE);
        ItemMeta Bone_meta=Bone.getItemMeta();
        Bone_meta.setDisplayName("Pfeife");
        Bone.setItemMeta(Bone_meta);

        //the liste
        ItemStack plat=new ItemStack(Material.PAPER);
        ItemMeta plat_meta=Bone.getItemMeta();
        plat_meta.setDisplayName("Checkliste");
        plat.setItemMeta(plat_meta);

        //essendrops
        ItemStack Steak=new ItemStack(Material.COOKED_BEEF);
        ItemMeta Steak_meta=Steak.getItemMeta();
        Steak_meta.setDisplayName("Essen");
        Steak.setItemMeta(Steak_meta);

        //item zum abschliesen der einweißung
        ItemStack abschluss=new ItemStack(Material.RED_CONCRETE_POWDER);
        ItemMeta abschluss_meta=abschluss.getItemMeta();
        abschluss_meta.setDisplayName(ChatColor.RED+"Abschließen");
        abschluss.setItemMeta(abschluss_meta);

        p.getInventory().setItem(0,Commpas);
        p.getInventory().setItem(1,Bone);
        p.getInventory().setItem(2,plat);
        p.getInventory().setItem(3,Steak);
        p.getInventory().setItem(7,liste);
        p.getInventory().setItem(8,abschluss);



        return true;
    }
}
