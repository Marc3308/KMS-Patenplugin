package me.marc3308.patenplugin.commands;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class suchcommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //checks if the command sender is a parte
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        openlog(p,"Neuste zuerst",1);

        return false;
    }

    public static void openlog(Player p,String sucher,int Seitenzahl){

        //creat the inventory
        Inventory Loginventar = Bukkit.createInventory(p, 54, "                 §lLog");

        //creat the allways components
        ItemStack suche = new ItemStack(Material.ANVIL);
        ItemMeta suche_meta = suche.getItemMeta();
        suche_meta.setDisplayName(sucher);
        ArrayList<String> suche_lore = new ArrayList<>();
        suche_lore.add("Klicke hier um den Such Algorytmus zu ändern");
        suche_meta.setLore(suche_lore);
        suche.setItemMeta(suche_meta);

        ItemStack vorpfeil = new ItemStack(Material.ARROW);
        ItemMeta vorpfeil_meta = vorpfeil.getItemMeta();
        vorpfeil_meta.setDisplayName(String.valueOf((Seitenzahl+1)));
        vorpfeil.setItemMeta(vorpfeil_meta);

        ItemStack buch = new ItemStack(Material.BOOK);
        ItemMeta buch_meta = buch.getItemMeta();
        buch_meta.setDisplayName(ChatColor.GRAY + "§lSeite: 1");
        buch.setItemMeta(buch_meta);


        Loginventar.setItem(51, vorpfeil);
        Loginventar.setItem(49, buch);
        Loginventar.setItem(47, suche);

        //lock how manny heads there are
        ArrayList<SpielerKopf> heads=new ArrayList<>();
        FileConfiguration con=Patenplugin.getcon(1);


        int i = 1;
        while (con.getString(String.valueOf(i) + ".name") != null){
            heads.add(new SpielerKopf(con.getString(i + ".einweiser"),con.getString(i + ".date"),con.getString(i + ".name")));
            i++;
        }

        //sorter
        switch (sucher){
            case "Neuste zuerst":
                Collections.reverse(heads);
                break;
            case "Pate":
                heads.sort(Comparator.comparing(SpielerKopf::getEinzuweisender, String.CASE_INSENSITIVE_ORDER));
                break;
            case "Spieler":
                heads.sort(Comparator.comparing(SpielerKopf::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            case "Älteste zuerst":
                break;
            default:
        }


        for (SpielerKopf it : heads) {
            if (heads.indexOf(it) >= 44 * (Seitenzahl-1) && heads.indexOf(it) <= 44 * Seitenzahl) {
                ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
                SkullMeta skull = (SkullMeta) head.getItemMeta();

                ArrayList<String> skull_lore = new ArrayList<>();
                skull_lore.add(it.getDate());
                skull_lore.add("Eingewiesen von: " + it.getEinzuweisender());
                skull.setDisplayName(it.getName());

                if(Bukkit.getPlayer(it.getName())==null){
                    String base64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU3NzAwMDk2YjVhMmE4NzM4NmQ2MjA1YjRkZGNjMTRmZDMzY2YyNjkzNjJmYTY4OTM0OTk0MzFjZTc3YmY5In19fQ==";

                    // Create a PlayerProfile with a random UUID and apply the base64 texture
                    PlayerProfile profile = getServer().createProfile(UUID.randomUUID(), "CustomHead");
                    profile.getProperties().add(new ProfileProperty("textures", base64));

                    // Set the profile to the skull meta
                    skull.setPlayerProfile(profile);
                } else {
                    skull.setOwner(it.getName());
                }
                skull.setLore(skull_lore);
                head.setItemMeta(skull);
                Loginventar.setItem(Loginventar.firstEmpty(),head);
            }
        }
        p.openInventory(Loginventar);
    }
}

class SpielerKopf{
    private String Einzuweisender;
    private String Date;
    private String Name;

    public SpielerKopf(String Einzuweisender, String Date, String Name){
        this.Name=Name;
        this.Date=Date;
        this.Einzuweisender=Einzuweisender;
    }

    public String getName() {
        return Name;
    }

    public String getDate() {
        return Date;
    }

    public String getEinzuweisender() {
        return Einzuweisender;
    }
}
