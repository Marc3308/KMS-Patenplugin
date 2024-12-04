package me.marc3308.patenplugin.einzuweisender;

import me.marc3308.patenplugin.Patenplugin;
import net.md_5.bungee.api.ChatColor;
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

import static me.marc3308.patenplugin.Patenplugin.Parteninfo;
import static me.marc3308.patenplugin.Patenplugin.einweiserlist;

public class joinleaveevent implements Listener {   

    File file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
    FileConfiguration con= YamlConfiguration.loadConfiguration(file);

    @EventHandler
    public void onjoin(PlayerJoinEvent e) {

        Player p = e.getPlayer();

        if (!p.getPersistentDataContainer().has(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING)
                || p.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING).equals("???")){

            Location loc=new Location(p.getWorld(),con.getDouble("Firstlogtp"+".x"),con.getDouble("Firstlogtp"+".y"),con.getDouble("Firstlogtp"+".z"));
            loc.setYaw(con.getInt("Firstlogtp"+".Yaw"));

            p.teleport(loc);

            new BukkitRunnable(){
                @Override
                public void run() {
                    if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING)){
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
        einweiserlist.add(p);
        Parteninfo();
    }

    @EventHandler
    public void onleave(PlayerQuitEvent e){

        Player p = e.getPlayer();
        if(einweiserlist.contains(p))einweiserlist.remove(p);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING))return;
        Bukkit.getServer().getOnlinePlayers().stream().filter(pp -> pp.hasPermission("parte")).forEach(parte ->{
            parte.sendMessage(ChatColor.DARK_GREEN+p.getName()+ ChatColor.RED+" ist offline und nun nicht mehr zur Einweisung verfügbar");
        });
    }
}
