package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.afkmanager.AFKManager;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class leaveevent implements Listener {

    @EventHandler
    public void onleft(PlayerQuitEvent e){

        Player p=e.getPlayer();

        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;
        inventorymanager.restorinv(p);

        UUID p2uuid =UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING));
        Player einzuweisender = Bukkit.getPlayer(p2uuid);

        //checkt if einzuweisender is on
        if(einzuweisender==null){
            p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));
            return;
        }

        einzuweisender.sendMessage(ChatColor.GREEN+"Dein Pate ist leider offline gegangen, bitte gedulde dich kurz. Weitere Paten wurden informiert");

        einzuweisender.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING,"nono");

        //remove player von afk manager
        AFKManager.playerJoin(einzuweisender);

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


                        net.md_5.bungee.api.chat.TextComponent c = new net.md_5.bungee.api.chat.TextComponent(net.md_5.bungee.api.ChatColor.GREEN + p.getName() + org.bukkit.ChatColor.DARK_GREEN + " ist bereit für eine Einweisung!");
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

            net.md_5.bungee.api.chat.TextComponent c= new net.md_5.bungee.api.chat.TextComponent(net.md_5.bungee.api.ChatColor.GREEN+einzuweisender.getName()+ org.bukkit.ChatColor.DARK_GREEN+" ist bereit für eine Einweisung!");
            String command="/patentp "+einzuweisender.getName();
            c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
            parte.spigot().sendMessage(c);

        }

    }

    @EventHandler
    public void onjoin(PlayerJoinEvent e){

        Player p=e.getPlayer();

        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;

        UUID p2uuid =UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING));
        Player einzuweisender = Bukkit.getPlayer(p2uuid);

        //checkt if einzuweisender is on
        if(einzuweisender==null){
            return;
        }


        //send the messege spachel for him
        net.md_5.bungee.api.chat.TextComponent c= new net.md_5.bungee.api.chat.TextComponent(net.md_5.bungee.api.ChatColor.GREEN+einzuweisender.getName()+ org.bukkit.ChatColor.DARK_GREEN+" ist bereit für eine Einweisung!");
        String command="/patentp "+einzuweisender.getName();
        c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
        p.spigot().sendMessage(c);

        p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));

    }
}
