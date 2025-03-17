package me.marc3308.patenplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.marc3308.patenplugin.commands.*;
import me.marc3308.patenplugin.moderator.moderationsmoduscommand;
import me.marc3308.patenplugin.moderator.moderatorevents;
import me.marc3308.patenplugin.moderator.modkommentardommand;
import me.marc3308.patenplugin.parte.*;
import me.marc3308.patenplugin.einzuweisender.clickblockev;
import me.marc3308.patenplugin.einzuweisender.joinleaveevent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static me.marc3308.patenplugin.Patenplugin.plugin;

public final class Patenplugin extends JavaPlugin implements Listener {


    public static ArrayList<Player> einweiserlist=new ArrayList<>();
    public static ArrayList<Player> patenliste=new ArrayList<>();

    public static HashMap<Integer, FileConfiguration> conlist=new HashMap<>();

    public static Patenplugin plugin;
    @Override
    public void onEnable() {

        plugin = this;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                    @Override
                    public void run() {
                        File file = new File("plugins/KMS Plugins/Patenplugin","List.yml");
                        FileConfiguration con1= YamlConfiguration.loadConfiguration(file);
                        conlist.put(1,con1);
                        file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
                        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);
                        conlist.put(2,con2);
                        file = new File("plugins/KMS Plugins/Patenplugin","Checkliste.yml");
                        FileConfiguration con3= YamlConfiguration.loadConfiguration(file);
                        conlist.put(3,con3);

                        Parteninfo();
                    }
        },0,5*60*20); //alle 5min nachricht das er eingewiesen werden will

        //patenzeug
        Bukkit.getPluginManager().registerEvents(new guis(),this);
        Bukkit.getPluginManager().registerEvents(new leaveevent(),this);
        Bukkit.getPluginManager().registerEvents(new hotbaritems(),this);
        Bukkit.getPluginManager().registerEvents(new clickblockev(),this);
        Bukkit.getPluginManager().registerEvents(new joinleaveevent(),this);
        Bukkit.getPluginManager().registerEvents(new invdropundpickupevent(),this);

        getCommand("nichtstören").setExecutor(new nichtsorencommand());
        getCommand("patentp").setExecutor(new tpcommand());
        getCommand("patenlog").setExecutor(new suchcommand());
        getCommand("changename").setExecutor(new changenamecommand());
        getCommand("patenwatch").setExecutor(new watchcommand());
        getCommand("patenübersicht").setExecutor(new patenbearbeitungcommand());

        //modzeug
        Bukkit.getPluginManager().registerEvents(new moderatorevents(),this);
        getCommand("moderationsmodus").setExecutor(new moderationsmoduscommand());
        getCommand("kommentar").setExecutor(new modkommentardommand());

        File file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        //creat list that diskribes plugin
        List<String> a=new ArrayList<>();
        a.add("This yml is for the locations for the tps für die Paten");
        a.add("Patenblock ist der block für die neuen Spieler auf den sie klicken müssen um einen Paten zu rufen");
        a.add("x,y und z stehen für die Koodrinaten der Blöcke und locations");
        a.add("Um eine neue location anzulegen schreibe bitte die Nummer um eins weiter und benutze dann x,y und z wie beim Spawn Beispiel");
        a.add("bitte gib auch einen Namen, eine Beschreibung und den Block der den punkt representiert ein");

        //checks if there is a standart
        if(con.get("Patenblock"+".descripion")==null){


            con.set("Patenblock"+".descripion","Der Punkt an dem neue Spieler einen Paten rufen können");
            con.set("Patenblock"+".x",0.0);
            con.set("Patenblock"+".y",0.0);
            con.set("Patenblock"+".z",0.0);
            con.set("Patenblock"+".Yaw",0);

            con.set("Firstlogtp"+".x",0.0);
            con.set("Firstlogtp"+".y",0.0);
            con.set("Firstlogtp"+".z",0.0);

            con.setComments("Patenblock",a);

            con.set("location"+".1"+".name","Spawn");
            con.set("location"+".1"+".descripion","Der World Spawn");
            con.set("location"+".1"+".Block","DIAMOND");
            con.set("location"+".1"+".x",100);
            con.set("location"+".1"+".y",100);
            con.set("location"+".1"+".z",100);
        }

        //save the file
        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        file = new File("plugins/KMS Plugins/Patenplugin","Checkliste.yml");
        con= YamlConfiguration.loadConfiguration(file);
        //creat list that diskribes plugin
        a.clear();
        a.add("This yml is for the Checklist for the Parten");
        a.add("Wenn man einen neuen Punkt hinzufügen will, dann einfach numerisch weiter führen und den Text eingeben");

        //checks if there is a standart
        if(con.get("1")==null)con.set("1","Hier steht was du tun sollst");

        //save the file
        try {
            con.save(file);
        } catch (IOException i) {
            i.printStackTrace();
        }

        file = new File("plugins/KMS Plugins/Patenplugin","List.yml");
        FileConfiguration con1= YamlConfiguration.loadConfiguration(file);
        conlist.put(1,con1);
        file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);
        conlist.put(2,con2);
        file = new File("plugins/KMS Plugins/Patenplugin","Checkliste.yml");
        FileConfiguration con3= YamlConfiguration.loadConfiguration(file);
        conlist.put(3,con3);
    }


    @Override
    public void onDisable() {
        //leute aus dem modus rausschmeißen
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN)){
                p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"beobachtermodus"));
                p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"modmode"));
                p.setInvulnerable(false);
                p.setGameMode(GameMode.SURVIVAL);
                p.setAllowFlight(false);
                p.setFlying(false);
                inventorymanager.restorinv(p);
                p.removePotionEffect(PotionEffectType.GLOWING);
            }
            if(p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"), PersistentDataType.STRING)){
                inventorymanager.restorinv(p);
                p.setInvisible(false);
                p.setInvulnerable(false);
                p.getPersistentDataContainer().remove(new NamespacedKey(Patenplugin.getPlugin(),"partenmodus"));
            }
        });

        System.out.println("Patenplugin is working");
        System.out.println("Createt by Marc3308");
    }

    public static Patenplugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getcon(Integer num){
        return conlist.get(num);
    }

    public static void Parteninfo(){

        einweiserlist.forEach(p -> {

            if(!patenliste.isEmpty()){
                p.sendMessage(ChatColor.GREEN+"Momentan sind "+ChatColor.GOLD+patenliste.size()+ChatColor.GREEN+" Paten online");
                p.sendMessage(ChatColor.GREEN+"Bitte habe noch einen kleinen Moment Geduld!");
            } else {
                p.sendMessage(ChatColor.GREEN+"Momentan sind leider keine Paten online.");
                p.sendMessage(ChatColor.GREEN+"Bitte gedulde dich noch einen Moment ");
                p.sendMessage(ChatColor.GREEN+"oder mache einen Termin in deinem Ticket aus.");
            }

            patenliste.forEach(parte -> {
                TextComponent yes= new TextComponent(ChatColor.GREEN+p.getName()+ ChatColor.DARK_GREEN +" ist bereit für eine Einweisung! "+ChatColor.YELLOW+"[Teleportieren]");
                String command="/patentp "+p.getName();
                yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,command));
                parte.sendMessage(yes);
            });
        });
    }
}
