package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class hotbaritems implements Listener {

    @EventHandler
    public void onclick(PlayerInteractEvent e){

        Player p=e.getPlayer();

        //check if spieler is nen parte und in einer einweißung
        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;



        UUID p2uuid =UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING));
        Player einzuweisender = Bukkit.getPlayer(p2uuid);

        Material hand=p.getInventory().getItemInMainHand().getType();

        switch (hand){
            case COMPASS:
                e.setCancelled(true);
                Inventory Platzinv=Bukkit.createInventory(p,54,ChatColor.GREEN+"Wichtige Plätze:");

                for(int i=0;i<25;i++){

                    String num="."+(i+1);

                    if(Patenplugin.getcon(2).get("location"+num+".name")==null)break;
                    if(Patenplugin.getcon(2).get("location"+num+".Block")==null)break;
                    if(Patenplugin.getcon(2).get("location"+num+".descripion")==null)break;

                    ItemStack loc=new ItemStack(Material.valueOf(Patenplugin.getcon(2).getString("location"+"."+num+".Block")));
                    ItemMeta loc_meta=loc.getItemMeta();
                    loc_meta.setDisplayName(Patenplugin.getcon(2).getString("location"+"."+num+".name"));
                    ArrayList<String> lore_liste=new ArrayList<>();
                    lore_liste.add(Patenplugin.getcon(2).getString("location"+"."+num+".descripion"));
                    loc_meta.setLore(lore_liste);
                    loc.setItemMeta(loc_meta);

                    Platzinv.setItem(i,loc);
                }



                p.openInventory(Platzinv);

                break;

            case BONE:
                if(einzuweisender==null){
                    p.sendMessage(ChatColor.DARK_GREEN+" Der Spieler ist leider nicht mehr online");
                    return;
                }

                einzuweisender.teleport(p.getLocation());

                break;
            case RED_CONCRETE_POWDER:
                e.setCancelled(true);
                Inventory Abschlusinv=Bukkit.createInventory(p,27,"Abschluss");

                ItemStack glass=new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
                ItemMeta glas_meta= glass.getItemMeta();
                glas_meta.setDisplayName(" ");
                glass.setItemMeta(glas_meta);

                ItemStack red=new ItemStack(Material.RED_CONCRETE_POWDER);
                ItemMeta red_meta= red.getItemMeta();
                red_meta.setDisplayName(ChatColor.RED+"NICHT ABSCHLIESSEN");
                red.setItemMeta(red_meta);

                ItemStack greed=new ItemStack(Material.GREEN_CONCRETE_POWDER);
                ItemMeta gre_meta=greed.getItemMeta();
                gre_meta.setDisplayName(ChatColor.GREEN+"ABSCHLIESSEN");
                greed.setItemMeta(gre_meta);


                for(int i=0;i<27;i++){
                    Abschlusinv.setItem(i,glass);
                }
                Abschlusinv.setItem(11,greed);
                Abschlusinv.setItem(15,red);

                p.openInventory(Abschlusinv);
                break;
            case PAPER:



                ItemStack book =new ItemStack(Material.WRITTEN_BOOK);
                BookMeta book_meta=(BookMeta) book.getItemMeta();

                book_meta.setTitle(ChatColor.GREEN+"Checkliste");
                book_meta.setAuthor("Marc3308");
                book_meta.setTitle("Marc3308");

                List<String> bb=new ArrayList<>();

                for(int i=0;i<25;i++){

                    String num=String.valueOf(i+1);

                    if(Patenplugin.getcon(3).get(num)==null)break;

                    String b=Patenplugin.getcon(3).getString(num);


                    bb.add(b);

                    book_meta.setPages(bb);

                }

                book.setItemMeta(book_meta);

                p.openBook(book);





                break;
            case COOKED_BEEF:

                p.getWorld().dropItemNaturally(p.getLocation(),new ItemStack(Material.BREAD,1));

                break;
            case BOOKSHELF:
                e.setCancelled(true);
                Bukkit.getServer().dispatchCommand(p,"patenlog");
                String command="/patenlog "+p.getName();
                break;
        }


    }
}
