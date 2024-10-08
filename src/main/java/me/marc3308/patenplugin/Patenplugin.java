package me.marc3308.patenplugin;

import me.marc3308.patenplugin.commands.changenamecommand;
import me.marc3308.patenplugin.commands.suchcommand;
import me.marc3308.patenplugin.parte.*;
import me.marc3308.patenplugin.einzuweisender.clickblockev;
import me.marc3308.patenplugin.einzuweisender.joinleaveevent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class Patenplugin extends JavaPlugin implements Listener {

    public static Patenplugin plugin;
    @Override
    public void onEnable() {

        plugin = this;

        //todo einer checkliste damit er weiß was er alles machen muss
        //todo suchleiste geht noch net


        Bukkit.getPluginManager().registerEvents(new guis(),this);
        Bukkit.getPluginManager().registerEvents(new leaveevent(),this);
        Bukkit.getPluginManager().registerEvents(new hotbaritems(),this);
        Bukkit.getPluginManager().registerEvents(new clickblockev(),this);
        Bukkit.getPluginManager().registerEvents(new joinleaveevent(),this);
        Bukkit.getPluginManager().registerEvents(new invdropundpickupevent(),this);

        getCommand("patentp").setExecutor(new tpcommand());
        getCommand("patenlog").setExecutor(new suchcommand());
        getCommand("changename").setExecutor(new changenamecommand());


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

    }


    @Override
    public void onDisable() {
        System.out.println("Patenplugin is working");
        System.out.println("Createt by Marc3308");
    }

    public static Patenplugin getPlugin() {
        return plugin;
    }

    public static FileConfiguration getcon(Integer num){

        File file = new File("plugins/KMS Plugins/Patenplugin","List.yml");
        FileConfiguration con= YamlConfiguration.loadConfiguration(file);

        file = new File("plugins/KMS Plugins/Patenplugin","Locations.yml");
        FileConfiguration con2= YamlConfiguration.loadConfiguration(file);

        file = new File("plugins/KMS Plugins/Patenplugin","Checkliste.yml");
        FileConfiguration con3= YamlConfiguration.loadConfiguration(file);

        return num==1 ? con : num==2 ? con2 : con3;

    }
}
