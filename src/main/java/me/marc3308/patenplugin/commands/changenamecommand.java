package me.marc3308.patenplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class changenamecommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(p.hasPermission("parte") && !p.getPersistentDataContainer().has(new NamespacedKey("patenplugin","partenmodus"), PersistentDataType.STRING))return false;

        Player pp= Bukkit.getPlayer(UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey("patenplugin","partenmodus"), PersistentDataType.STRING)));

        String newname=args[0];
        for(int i=1;i<args.length;i++)newname+=" "+args[i];
        pp.getPersistentDataContainer().set(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING,newname);
        p.sendMessage(ChatColor.DARK_GREEN+p.getName()+" neuer name ist nun: "+ChatColor.GREEN+newname);
        pp.setPlayerListName(ChatColor.GOLD+newname);
        return false;
    }
}
