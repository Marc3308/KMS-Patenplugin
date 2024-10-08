package me.marc3308.patenplugin.einzuweisender;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.afkmanager.AFKManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class clickblockev implements Listener {


    @EventHandler
    public void onclick(PlayerInteractEvent e){

        Player p= e.getPlayer();

        //check if player rightclicks the right block
        if(!p.getInventory().getItemInMainHand().getType().equals(Material.AIR))return;
        if(!p.getInventory().getItemInOffHand().getType().equals(Material.AIR))return;
        if(!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)))return;

        //check if the cklickt block is the block for the parte
        int x=Patenplugin.getcon(2).getInt("Patenblock"+".x");
        int y=Patenplugin.getcon(2).getInt("Patenblock"+".y");
        int z=Patenplugin.getcon(2).getInt("Patenblock"+".z");


        Location rightblock=new Location(p.getWorld(),x,y,z);

        if(!e.getClickedBlock().getLocation().equals(rightblock))return;


        //check if player has already clickt the block
        if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING))return;


        int onparten =0;
        for (Player pp : Bukkit.getOnlinePlayers())if(pp.hasPermission("parte"))onparten++;
        if(onparten>0){
            p.sendMessage(ChatColor.GREEN+"Momentan sind "+ChatColor.GOLD+onparten+ChatColor.GREEN+" Paten online");
            p.sendMessage(ChatColor.GREEN+"Bitte habe noch einen kleinen Moment Geduld!");
        } else {
            p.sendMessage(ChatColor.GREEN+"Momentan sind leider keine Paten online.");
            p.sendMessage(ChatColor.GREEN+"Bitte gedulde dich noch einen Moment ");
            p.sendMessage(ChatColor.GREEN+"oder mache einen Termin in deinem Ticket aus. ");
        }

        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING,"nono");

        //every 5min the parten will be informt
        AFKManager.playerJoin(p);

        new BukkitRunnable(){

            @Override
            public void run() {

                if(Bukkit.getPlayerExact(p.getName())==null || !p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING)){
                    AFKManager.playerleave(p);
                    cancel();
                }

                if(AFKManager.isafk(p)){

                    AFKManager.playerleave(p);
                    AFKManager.playerJoin(p);

                    for (Player parte : Bukkit.getServer().getOnlinePlayers()) {
                        if (!parte.hasPermission("parte")) return;


                        net.md_5.bungee.api.chat.TextComponent c = new net.md_5.bungee.api.chat.TextComponent(ChatColor.GREEN + p.getName() + org.bukkit.ChatColor.DARK_GREEN + " ist bereit für eine Einweisung!");
                        String command = "/patentp " + p.getName();
                        c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
                        parte.spigot().sendMessage(c);


                    }
                }
            }
        }.runTaskTimer(Patenplugin.getPlugin(),0,1*20L);

        //messege the parten
        for(Player parte : Bukkit.getServer().getOnlinePlayers()){
            if(!parte.hasPermission("parte"))return;

            net.md_5.bungee.api.chat.TextComponent c= new net.md_5.bungee.api.chat.TextComponent(ChatColor.GREEN+p.getName()+ org.bukkit.ChatColor.DARK_GREEN+" ist bereit für eine Einweisung!");
            String command="/patentp "+p.getName();
            c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
            parte.spigot().sendMessage(c);

        }
    }
}
