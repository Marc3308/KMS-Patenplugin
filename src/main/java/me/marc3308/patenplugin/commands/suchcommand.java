package me.marc3308.patenplugin.commands;

import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class suchcommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //checks if the command sender is a parte
        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(!p.hasPermission("parte"))return false;




        //creat the inventory
        Inventory Loginventar= Bukkit.createInventory(p,54,"                 §lLog");


        //creat the allways components
        ItemStack suche=new ItemStack(Material.ANVIL);
        ItemMeta suche_meta=suche.getItemMeta();
        suche_meta.setDisplayName("Suche");
        ArrayList<String> suche_lore=new ArrayList<>();
        suche_lore.add("Klicke hier um nach bestimmten Spielern zu suchen");
        suche_meta.setLore(suche_lore);
        suche.setItemMeta(suche_meta);

        ItemStack vorpfeil=new ItemStack(Material.ARROW);
        ItemMeta vorpfeil_meta=vorpfeil.getItemMeta();
        vorpfeil_meta.setDisplayName("2");
        vorpfeil.setItemMeta(vorpfeil_meta);

        ItemStack buch=new ItemStack(Material.BOOK);
        ItemMeta buch_meta= buch.getItemMeta();
        buch_meta.setDisplayName(ChatColor.GRAY+"§lSeite: "+1);
        buch.setItemMeta(buch_meta);


        Loginventar.setItem(51,vorpfeil);
        Loginventar.setItem(49,buch);
        Loginventar.setItem(47,suche);

        //lock how manny heads there are
        int heads=howmutchheads();

        if(heads==0){
            p.sendMessage("Es gibt noch keine Einträge");
            return false;
        }

        //create the heads
        int a=0;

        for(int i=heads;i>0;i--){

            ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
            SkullMeta skull=(SkullMeta) head.getItemMeta();

            ArrayList<String> skull_lore=new ArrayList<>();
            skull_lore.add(Patenplugin.getcon(1).getString(i+".date"));
            skull_lore.add("Eingewiesen von: "+Patenplugin.getcon(1).getString(i+".einweiser"));
            skull.setDisplayName(Patenplugin.getcon(1).getString(i+".name"));
            skull.setOwner(Patenplugin.getcon(1).getString(i+".name"));
            skull.setLore(skull_lore);
            head.setItemMeta(skull);

            if(args.length==1){
                String name=args[0];
                String adwa=Patenplugin.getcon(1).getString(i+".name");

                if(name.equalsIgnoreCase(adwa)){
                    a++;
                    Loginventar.setItem(Loginventar.firstEmpty(), head);
                }
            } else {
                a++;
                Loginventar.setItem(Loginventar.firstEmpty(), head);
            }

            if(a>=45)break;

        }


        p.openInventory(Loginventar);
        return false;
    }

    public static int howmutchheads(){

        if(Patenplugin.getcon(1).get("1"+".name")==null){

            return 0;

        } else {
            boolean test=true;

            int i=0;

            while (test){
                i++;
                if(Patenplugin.getcon(1).get(String.valueOf(i)+".name")==null){
                    return (i-1);
                }


            }
        }
        return 0;
    }


}
