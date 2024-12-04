package me.marc3308.patenplugin.einzuweisender;

import me.marc3308.patenplugin.Patenplugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import static me.marc3308.patenplugin.Patenplugin.Parteninfo;
import static me.marc3308.patenplugin.Patenplugin.einweiserlist;

public class clickblockev implements Listener {


    @EventHandler
    public void onclick(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        //check if player rightclicks the right block
        if (!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if (!p.getInventory().getItemInOffHand().getType().equals(Material.AIR)) return;
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;

        //check if the cklickt block is the block for the parte
        int x = Patenplugin.getcon(2).getInt("Patenblock" + ".x");
        int y = Patenplugin.getcon(2).getInt("Patenblock" + ".y");
        int z = Patenplugin.getcon(2).getInt("Patenblock" + ".z");


        Location rightblock = new Location(p.getWorld(), x, y, z);

        if (!e.getClickedBlock().getLocation().equals(rightblock))return;


        //check if player has already clickt the block
        if (p.getPersistentDataContainer().has(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING))return;
        p.getPersistentDataContainer().set(new NamespacedKey(Patenplugin.getPlugin(), "einzuweisen"), PersistentDataType.STRING, "nono");

        einweiserlist.add(p);
        Parteninfo();

    }
}
