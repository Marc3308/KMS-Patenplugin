package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

public class invdropundpickupevent implements Listener {

    @EventHandler
    public void ondrop(PlayerDropItemEvent e){

        Player p=e.getPlayer();

        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;
        e.setCancelled(true);

    }

    @EventHandler
    public void onpickup(PlayerPickupItemEvent e){

        Player p=e.getPlayer();

        if(!p.hasPermission("parte"))return;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;
        e.setCancelled(true);

    }
}
