package me.marc3308.patenplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class patenbearbeitungcommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player))return false;
        Player p=(Player) sender;
        if(args.length<2){
            System.out.println(args.length);
            p.sendMessage(ChatColor.RED+"/patenübersicht <Name> <Arbeit>");
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set parteleitung false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set parte false");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+"permission set juniorparte false");
        p.sendMessage(ChatColor.DARK_GREEN+"Der Benutzer: "+ChatColor.GREEN+args[0]+ChatColor.DARK_RED+" ist nun ein: "+ChatColor.GREEN+args[1]);

        switch (args[1]){
            case "parteleitung":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set parteleitung true");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set parte true");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set juniorparte true");
                break;
            case "parte":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set parte true");
                break;
            case "juniorparte":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"lp user "+args[0]+" permission set juniorparte true");
                break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {
        ArrayList<String> list =new ArrayList<>();

        try {
            if(args.length == 0)return list;
            if(args.length == 1)Bukkit.getOnlinePlayers().forEach(p -> list.add(p.getName()));
            if(args.length == 2){
                list.add("parteleitung");
                list.add("parte");
                list.add("juniorparte");
                list.add("kündigen");
            }
            if(args.length>2)return list;

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
