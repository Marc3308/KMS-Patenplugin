package me.marc3308.patenplugin.parte;

import me.marc3308.patenplugin.Patenplugin;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

import static me.marc3308.patenplugin.Patenplugin.*;

public class leaveevent implements Listener {

    @EventHandler
    public void onleft(PlayerQuitEvent e){

        Player p=e.getPlayer();

        if(!p.hasPermission("parte"))return;
        patenliste.remove(p);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return;
        inventorymanager.restorinv(p);
        p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));

        UUID p2uuid =UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING));
        Player einzuweisender = Bukkit.getPlayer(p2uuid);

        //checkt if einzuweisender is on
        if(einzuweisender==null)return;

        einzuweisender.sendMessage(ChatColor.GREEN+"Dein Pate ist leider offline gegangen, bitte gedulde dich kurz.");
        //add einzuweisenden
        einweiserlist.add(einzuweisender);
        Parteninfo();

        einzuweisender.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"einzuweisen"), PersistentDataType.STRING,"nono");

    }

    @EventHandler
    public void onjoin(PlayerJoinEvent e){

        Player p=e.getPlayer();
        if(!p.hasPermission("parte"))return;
        patenliste.add(p);
        einweiserlist.forEach(einw -> {
            TextComponent yes= new TextComponent(net.md_5.bungee.api.ChatColor.YELLOW+"[Teleportieren]");
            String command="/patentp "+einw.getName();
            yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
            p.sendMessage(net.md_5.bungee.api.ChatColor.DARK_GREEN+" ist bereit für eine Einweisung!"+yes);
        });
    }
}
