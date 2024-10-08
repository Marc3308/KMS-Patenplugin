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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static me.marc3308.patenplugin.commands.suchcommand.howmutchheads;

public class guis implements Listener {

    @EventHandler
    public void oninv(InventoryClickEvent e){

        Player p=(Player) e.getWhoClicked();

        //log
        if(e.getView().getTitle().equalsIgnoreCase("                 §lLog")){

            e.setCancelled(true);

            ItemStack item=e.getCurrentItem();
            if(item==null)return;

            if(item.getType().equals(Material.ANVIL)){
                p.closeInventory();
                Inventory anvilinv=Bukkit.createInventory(null, InventoryType.ANVIL,"Suche:");

                ItemStack anv=new ItemStack(Material.NAME_TAG);
                ItemMeta anv_meta=anv.getItemMeta();
                anv_meta.setDisplayName("Bitte Name eingeben");
                anv.setItemMeta(anv_meta);
                anvilinv.setItem(0,anv);
                p.openInventory(anvilinv);
                return;
            }

            if(item.getType().equals(Material.BOOK)){
                logseiten(1,e,p);
                return;
            }
            if(!item.getType().equals(Material.ARROW))return;

            if(e.getSlot()==47){

                e.getView().getItem(47).getItemMeta().setDisplayName(Integer.toString(Integer.parseInt(e.getView().getItem(47).getItemMeta().getDisplayName())-1));
                e.getView().getItem(51).getItemMeta().setDisplayName(Integer.toString(Integer.parseInt(e.getView().getItem(51).getItemMeta().getDisplayName())-1));

                logseiten(Integer.parseInt(e.getView().getItem(47).getItemMeta().getDisplayName()),e,p);
            } else {


                if(e.getView().getItem(47).getType().equals(Material.ANVIL)){
                    e.getView().getItem(47).getItemMeta().setDisplayName("2");
                } else {
                    e.getView().getItem(47).getItemMeta().setDisplayName(Integer.toString(Integer.parseInt(e.getView().getItem(47).getItemMeta().getDisplayName())+1));
                }

                e.getView().getItem(51).getItemMeta().setDisplayName(Integer.toString(Integer.parseInt(e.getView().getItem(51).getItemMeta().getDisplayName())+1));

                logseiten(Integer.parseInt(e.getView().getItem(51).getItemMeta().getDisplayName()),e,p);
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

    public void logseiten(Integer seiten, InventoryClickEvent e, Player p){

        Inventory Loginventar= Bukkit.createInventory(p,54,"                 §lLog");

        //creat the allways components
        ItemStack suche=new ItemStack(Material.ANVIL);
        ItemMeta suche_meta=suche.getItemMeta();
        suche_meta.setDisplayName("Suche");
        ArrayList<String> suche_lore=new ArrayList<>();
        suche_lore.add("Klicke hier um nach bestimmten Spielern zu suchen");
        suche_meta.setLore(suche_lore);
        suche.setItemMeta(suche_meta);

        ItemStack backpfeil=new ItemStack(Material.ARROW);
        ItemMeta backpfeil_meta=backpfeil.getItemMeta();

        ItemStack vorpfeil=new ItemStack(Material.ARROW);
        ItemMeta vorpfeil_meta=vorpfeil.getItemMeta();

        ItemStack buch=new ItemStack(Material.BOOK);
        ItemMeta buch_meta= buch.getItemMeta();
        buch_meta.setDisplayName(ChatColor.GRAY+"§lSeite: "+Integer.toString(seiten));

        if(seiten==1){
            vorpfeil_meta.setDisplayName("2");
        } else if(seiten==2){
            backpfeil_meta.setDisplayName("1");
            vorpfeil_meta.setDisplayName("3");
        }else {
            backpfeil_meta.setDisplayName(Integer.toString(seiten-1));
            vorpfeil_meta.setDisplayName(Integer.toString(seiten+1));
        }

        //lock how manny heads there are
        int heads=howmutchheads();
        int a=0;

        for(int i=heads-(45*(seiten-1));i>0;i--){

            a++;
            ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
            SkullMeta skull=(SkullMeta) head.getItemMeta();

            ArrayList<String> skull_lore=new ArrayList<>();
            skull_lore.add(Patenplugin.getcon(1).getString(i+".date"));
            skull_lore.add("Eingewiesen von: "+Patenplugin.getcon(1).getString(i+".einweiser"));
            skull.setDisplayName(Patenplugin.getcon(1).getString(i+".name"));
            skull.setOwner(Patenplugin.getcon(1).getString(i+".name"));
            skull.setLore(skull_lore);
            head.setItemMeta(skull);

            Loginventar.setItem(Loginventar.firstEmpty(), head);

            if(a>=45)break;

        }

        if(seiten==100){

            //mein character wollte es so
            buch.setType(Material.ENCHANTED_GOLDEN_APPLE);
            buch_meta.setDisplayName(ChatColor.GOLD+"§n§oAiwendil, Lord and Savior");

            ArrayList<String> buch_lore=new ArrayList<>();
            buch_lore.add(ChatColor.GRAY+"Streichel mich!");
            buch_meta.setLore(buch_lore);

        }

        buch.setItemMeta(buch_meta);
        backpfeil.setItemMeta(backpfeil_meta);
        vorpfeil.setItemMeta(vorpfeil_meta);

        Loginventar.setItem(51,vorpfeil);
        Loginventar.setItem(49,buch);

        if(seiten==1){
            Loginventar.setItem(47,suche);
        } else {
            Loginventar.setItem(47,backpfeil);
        }

        p.closeInventory();
        p.openInventory(Loginventar);

    }

}
