package me.marc3308.patenplugin.einzuweisender;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.afkmanager.AFKManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class joinleaveevent implements Listener {

    File file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
    FileConfiguration con= YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void onjoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        System.out.println(p.getPlayerTime());

        if (!p.getPersistentDataContainer().has(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)
                || !p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING).equals("???")){

            p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"firstjoin"), PersistentDataType.STRING,"yes");
            Location loc=new Location(p.getWorld(),con.getDouble("Firstlogtp"+".x"),con.getDouble("Firstlogtp"+".y"),con.getDouble("Firstlogtp"+".z"));
            loc.setYaw(con.getInt("Firstlogtp"+".Yaw"));

            p.teleport(loc);

            new BukkitRunnable(){
                @Override
                public void run() {
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING)){
                        p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"firstjoin"));
                        p.setInvulnerable(false);
                        cancel();
                    }

                    p.setInvulnerable(true);

                }
            }.runTaskTimer(Patenplugin.getPlugin(),0,20);

        }



        //check if player needs parte
        if (!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING))return;

        //resetzt claiming
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING, "nono");
        p.sendMessage(ChatColor.GREEN + "Ein Pate wurde informiert");

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
                        if (!parte.hasPermission("parte")) {
                            net.md_5.bungee.api.chat.TextComponent c = new net.md_5.bungee.api.chat.TextComponent(ChatColor.GREEN + p.getName() + org.bukkit.ChatColor.DARK_GREEN + " ist bereit für eine Einweisung!");
                            String command = "/patentp " + p.getName();
                            c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
                            parte.spigot().sendMessage(c);
                        }
                    }
                }
            }
        }.runTaskTimer(Patenplugin.getPlugin(),0,1*20L);

        //get a parte
        for (Player parte : Bukkit.getServer().getOnlinePlayers()) {
            if (!parte.hasPermission("parte")){
                net.md_5.bungee.api.chat.TextComponent c = new net.md_5.bungee.api.chat.TextComponent(ChatColor.GREEN + p.getName() + org.bukkit.ChatColor.DARK_GREEN + " ist bereit für eine Einweisung!");
                String command = "/patentp " + p.getName();
                c.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
                parte.spigot().sendMessage(c);
            }
        }
    }

    @EventHandler
    public void onleave(PlayerQuitEvent e){

        Player p = e.getPlayer();

        if (!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING))return;

        for (Player parte : Bukkit.getServer().getOnlinePlayers()) {
            if (!parte.hasPermission("parte")) {
                parte.sendMessage(ChatColor.DARK_GREEN+p.getName()+ ChatColor.RED+" ist offline und nun nicht mehr zur Einweisung verfügbar");
            }
        }
    }
}
