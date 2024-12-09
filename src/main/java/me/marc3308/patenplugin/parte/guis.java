package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static me.marc3308.patenplugin.commands.suchcommand.openlog;

public class guis implements Listener {

    @EventHandler
    public void oninv(InventoryClickEvent e){

        Player p=(Player) e.getWhoClicked();

        //log
        if(e.getView().getTitle().equalsIgnoreCase("                 §lLog")){

            e.setCancelled(true);

            ItemStack item=e.getCurrentItem();
            if(item==null)return;

            switch (item.getType()){
                case ANVIL:
                    String sucher=e.getInventory().getItem(47).getItemMeta().getDisplayName();
                    sucher = sucher.equals("Neuste zuerst") ? "Älteste zuerst"
                            : sucher.equals("Älteste zuerst") ? "Spieler"
                            : sucher.equals("Spieler") ? "Pate" : "Neuste zuerst";
                    openlog(p,sucher,Integer.valueOf(e.getInventory().getItem(51).getItemMeta().getDisplayName())-1);
                    break;
                case BOOK:
                    openlog(p,e.getInventory().getItem(47).getItemMeta().getDisplayName(),1);
                    break;
                case ARROW:
                    openlog(p,e.getInventory().getItem(47).getItemMeta().getDisplayName()
                            ,Integer.valueOf(e.getInventory().getItem(51).getItemMeta().getDisplayName()));
                    break;
            }
        }

        //suche
        if(e.getView().getTitle().equalsIgnoreCase("Suche:")){

            if(e.getInventory().getType().equals(InventoryType.ANVIL)){

                p.closeInventory();
                p.sendMessage("This is work in progress");

            }

        }

        if(e.getView().getTitle().equalsIgnoreCase("GrundRassen")){


            e.setCancelled(true);

            if(e.getCurrentItem() == null)return;
            if(!e.getCurrentItem().getType().equals(Material.PAPER))return;

            p.getWorld().dropItemNaturally(p.getLocation(),e.getCurrentItem());

            p.closeInventory();

        }

        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;
        e.setCancelled(true);

        File file = new File("plugins/KMS Plugins/Patenplugin","List.yml");
        FileConfiguration listelsite= YamlConfiguration.loadConfiguration(file);

        //abschluss gui
        if (e.getView().getTitle().equalsIgnoreCase("Abschluss")){

            ItemStack off = p.getInventory().getItemInOffHand();
            ItemStack item = e.getCurrentItem();
            if (item == null) return;


            switch (e.getCurrentItem().getType()){
                case RED_CONCRETE_POWDER:
                    p.getInventory().setItemInOffHand(off);
                    p.updateInventory();
                    p.closeInventory();
                    break;
                case GREEN_CONCRETE_POWDER:
                    p.sendMessage("Du bist nun fertig mit der Einweisung");
                    p.closeInventory();

                    UUID p3uuid =UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING));
                    Player einzuweisender = Bukkit.getPlayer(p3uuid);

                    if(einzuweisender!=null){
                        einzuweisender.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"));
                        einzuweisender.sendMessage("Deine Einweisung ist nun zu Ende");
                    }

                    p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));
                    inventorymanager.restorinv(p);
                    p.setInvulnerable(false);
                    p.setInvisible(false);

                    //creat list that diskribes plugin
                    List<String> a=new ArrayList<>();
                    a.add("This yml is for the list for the Parten");
                    a.add("Hier sieht man, wann wer und von wem er eingewiesen wurde");

                    Date Listendate=new Date();

                    //checks if there is a standart
                    if(listelsite.get("1"+".name")==null){

                        listelsite.set("1"+".name",einzuweisender.getName().toString());
                        listelsite.set("1"+".einweiser",p.getName().toString());
                        listelsite.set("1"+".date",Listendate.toString());

                        listelsite.setComments("Patenblock",a);

                    } else {
                        boolean test=true;

                        int i=0;

                        while (test){
                            i++;
                            if(listelsite.get(String.valueOf(i)+".name")==null){

                                listelsite.set(String.valueOf(i)+".name",einzuweisender.getName().toString());
                                listelsite.set(String.valueOf(i)+".einweiser",p.getName().toString());
                                listelsite.set(String.valueOf(i)+".date",Listendate.toString());
                                test=false;
                                break;
                            }


                        }
                    }

                    //save the file
                    try {
                        listelsite.save(file);
                    } catch (IOException i) {
                        i.printStackTrace();
                    }
                    break;
            }

            p.getInventory().setItemInOffHand(off);
            p.updateInventory();
        }

        if (e.getView().getTitle().equalsIgnoreCase(ChatColor.GREEN+"Wichtige Plätze:")){

            ItemStack off = p.getInventory().getItemInOffHand();
            ItemStack item = e.getCurrentItem();
            if (item == null) return;


            int zahl=e.getSlot()+1;
            String num="."+zahl;

            if(!Patenplugin.getcon(2).getString("location"+"."+num+".Block").equals(e.getCurrentItem().getType().toString()))return;

            //check if the cklickt block is the block for the parte
            double x=Patenplugin.getcon(2).getDouble("location"+"."+num+".x");
            double y=Patenplugin.getcon(2).getDouble("location"+"."+num+".y");
            double z=Patenplugin.getcon(2).getDouble("location"+"."+num+".z");

            Location rightblock=new Location(p.getWorld(),x,y,z);

            p.teleport(rightblock);

            p.closeInventory();
            p.getInventory().setItemInOffHand(off);
            p.updateInventory();
        }

    }

}
