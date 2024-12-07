package me.marc3308.patenplugin.commands;

import me.marc3308.patenplugin.Patenplugin;
import me.marc3308.patenplugin.parte.inventorymanager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static me.marc3308.patenplugin.Patenplugin.plugin;

public class watchcommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(!p.hasPermission("juniorparte"))return false;
        if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING)){
            p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));
            return false;
        }
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING,"watching");
        if(args.length<1)return false;
        if(Bukkit.getPlayer(args[0])==null)return false;
        Player parte=Bukkit.getPlayer(args[0]);
        if(!parte.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))return false;
        inventorymanager.saveinv(p);
        p.setInvisible(true);
        p.setInvulnerable(true);
        p.teleport(parte);

        p.sendMessage(ChatColor.DARK_GREEN+"Du Beobachtest nun: "+ChatColor.GREEN+parte.getName());

        new BukkitRunnable(){
            @Override
            public void run() {
                if(!p.isOnline() || !parte.isOnline()
                        || !p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING)
                        || !parte.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING)){
                    p.sendMessage(ChatColor.RED+"Deine Beobachtung ist nun Zuende");
                    p.setInvisible(false);
                    p.setInvulnerable(false);
                    inventorymanager.restorinv(p);
                    cancel();
                    return;
                }
                if(p.getLocation().distance(parte.getLocation())>10)p.teleport(parte);
            }
        }.runTaskTimer(plugin,0,20);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        ArrayList<String> list =new ArrayList<>();

        try {
            if(args.length == 0)return list;
            if(args.length == 1)for (Player p : Bukkit.getOnlinePlayers())if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING))list.add(p.getName());
            if(args.length >1)return list;
            //autocompetion
            ArrayList<String> commpleteList = new ArrayList<>();
            String currentarg = args[args.length-1].toLowerCase();
            for (String s : list){
                if(s==null)return list;
                String s1 =s.toLowerCase();
                if(s1.startsWith(currentarg)){
                    commpleteList.add(s);
                }
            }

            return commpleteList;
        } catch (CommandException e){
            return list;
        }
    }
}
