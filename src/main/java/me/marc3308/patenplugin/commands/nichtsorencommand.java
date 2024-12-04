package me.marc3308.patenplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.marc3308.patenplugin.Patenplugin.patenliste;

public class nichtsorencommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //checks if the command sender is a parte
        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(!p.hasPermission("parte"))return false;

        if(patenliste.contains(p)){
            patenliste.remove(p);
            p.sendMessage(ChatColor.DARK_GREEN+"Dein Status ist nun: "+ChatColor.RED+"Abwesend");
        } else {
            patenliste.add(p);
            p.sendMessage(ChatColor.DARK_GREEN+"Dein Status ist nun: "+ChatColor.GREEN+"Anwesend");
        }
        return false;
    }
}
