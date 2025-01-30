package me.marc3308.patenplugin.moderator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import static me.marc3308.patenplugin.Patenplugin.plugin;

public class modkommentardommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))return true;
        Player p = (Player) sender;
        if(args.length<2)return false;
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return false;
        if(Bukkit.getPlayer(args[0])==null){
            p.sendMessage(ChatColor.RED+"Dieser Spieler existiert nicht oder ist nicht online");
            return false;
        }
        Player comentierter=Bukkit.getPlayer(args[0]);
        String newname=comentierter.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin, "commentare"), PersistentDataType.STRING,"")+";"+args[1];
        for(int i=3;i<args.length;i++)newname+=" "+args[i];
        newname+=";~"+p.getName();
        comentierter.getPersistentDataContainer().set(new NamespacedKey(plugin, "commentare"), PersistentDataType.STRING,newname);
        moderatorevents.openplayerinv(p,comentierter);

        return false;
    }
}
