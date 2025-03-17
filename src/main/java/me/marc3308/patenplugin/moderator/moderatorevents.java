package me.marc3308.patenplugin.moderator;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.patenplugin.Patenplugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static me.marc3308.patenplugin.Patenplugin.plugin;
import static me.marc3308.patenplugin.moderator.moderationsmoduscommand.getItem;
import static me.marc3308.patenplugin.moderator.moderationsmoduscommand.starterkit;
import static org.bukkit.Bukkit.*;

public class moderatorevents implements Listener {

    private static ArrayList<Material> blockliste=new ArrayList<>();
    private static ArrayList<Material> erzliste=new ArrayList<>();
    private static ArrayList<Material> grunderz=new ArrayList<>();


    public moderatorevents(){
        blockliste.add(Material.CHEST);
        blockliste.add(Material.CHEST_MINECART);
        blockliste.add(Material.ENDER_CHEST);
        blockliste.add(Material.TRAPPED_CHEST);
        blockliste.add(Material.ANVIL);
        blockliste.add(Material.CHIPPED_ANVIL);
        blockliste.add(Material.DAMAGED_ANVIL);
        blockliste.add(Material.ENCHANTING_TABLE);
        blockliste.add(Material.BARREL);
        blockliste.add(Material.FURNACE);
        blockliste.add(Material.FURNACE_MINECART);
        blockliste.add(Material.BLAST_FURNACE);
        blockliste.add(Material.SMOKER);
        blockliste.add(Material.PLAYER_WALL_HEAD);
        blockliste.add(Material.PLAYER_HEAD);
        blockliste.add(Material.BLACK_BED);
        blockliste.add(Material.BLUE_BED);
        blockliste.add(Material.BROWN_BED);
        blockliste.add(Material.GREEN_BED);
        blockliste.add(Material.CYAN_BED);
        blockliste.add(Material.GRAY_BED);
        blockliste.add(Material.LIGHT_BLUE_BED);
        blockliste.add(Material.LIGHT_GRAY_BED);
        blockliste.add(Material.LIME_BED);
        blockliste.add(Material.MAGENTA_BED);
        blockliste.add(Material.ORANGE_BED);
        blockliste.add(Material.PINK_BED);
        blockliste.add(Material.PURPLE_BED);
        blockliste.add(Material.RED_BED);
        blockliste.add(Material.WHITE_BED);
        blockliste.add(Material.YELLOW_BED);

        blockliste.add(Material.ACACIA_DOOR);
        blockliste.add(Material.DARK_OAK_DOOR);
        blockliste.add(Material.BIRCH_DOOR);
        blockliste.add(Material.JUNGLE_DOOR);
        blockliste.add(Material.BAMBOO_DOOR);
        blockliste.add(Material.IRON_DOOR);
        blockliste.add(Material.OAK_DOOR);
        blockliste.add(Material.SPRUCE_DOOR);
        blockliste.add(Material.CRIMSON_DOOR);
        blockliste.add(Material.WARPED_DOOR);
        blockliste.add(Material.COPPER_DOOR);
        blockliste.add(Material.EXPOSED_COPPER_DOOR);
        blockliste.add(Material.WEATHERED_COPPER_DOOR);
        blockliste.add(Material.OXIDIZED_COPPER_DOOR);
        blockliste.add(Material.WAXED_COPPER_DOOR);
        blockliste.add(Material.WAXED_EXPOSED_COPPER_DOOR);
        blockliste.add(Material.WAXED_WEATHERED_COPPER_DOOR);
        blockliste.add(Material.WAXED_OXIDIZED_COPPER_DOOR);

        grunderz.add(Material.COAL_ORE);
        erzliste.add(Material.DEEPSLATE_COAL_ORE);
        erzliste.add(Material.COAL_BLOCK);

        grunderz.add(Material.COPPER_ORE);
        erzliste.add(Material.DEEPSLATE_COPPER_ORE);
        erzliste.add(Material.COPPER_BLOCK);

        grunderz.add(Material.IRON_ORE);
        erzliste.add(Material.DEEPSLATE_IRON_ORE);
        erzliste.add(Material.IRON_BLOCK);

        grunderz.add(Material.GOLD_ORE);
        erzliste.add(Material.DEEPSLATE_GOLD_ORE);
        erzliste.add(Material.GOLD_BLOCK);

        grunderz.add(Material.LAPIS_ORE);
        erzliste.add(Material.DEEPSLATE_LAPIS_ORE);
        erzliste.add(Material.LAPIS_BLOCK);

        grunderz.add(Material.REDSTONE_ORE);
        erzliste.add(Material.DEEPSLATE_REDSTONE_ORE);
        erzliste.add(Material.REDSTONE_BLOCK);

        grunderz.add(Material.EMERALD_ORE);
        erzliste.add(Material.DEEPSLATE_EMERALD_ORE);
        erzliste.add(Material.EMERALD_BLOCK);

        grunderz.add(Material.DIAMOND_ORE);
        erzliste.add(Material.DEEPSLATE_DIAMOND_ORE);
        erzliste.add(Material.DIAMOND_BLOCK);

        grunderz.add(Material.NETHER_QUARTZ_ORE);
        grunderz.add(Material.ANCIENT_DEBRIS);
        erzliste.add(Material.NETHERITE_BLOCK);
        erzliste.add(Material.NETHER_GOLD_ORE);

        grunderz.add(Material.BEDROCK);
    }

    @EventHandler
    public void onpress(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN))e.setCancelled(true);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return;
        switch (p.getInventory().getItemInMainHand().getType()){
            case STICK:
                //todo try without rechte
                if(e.getAction().equals(Action.RIGHT_CLICK_AIR))Bukkit.dispatchCommand(p,"co i");
                break;
            case COMPASS:
                if(e.getAction().isLeftClick()){
                    Location saveloc=p.getLocation();
                    Location location = p.getLocation();
                    if(p.getPersistentDataContainer().has(new NamespacedKey(plugin,"xraymode"), PersistentDataType.LONG)){
                        location.add(location.getDirection().normalize().multiply(5)); // Move 5 block forward
                        p.teleport(location);
                    } else {
                        while (true){
                            location.add(location.getDirection().normalize().multiply(2)); // Move 2 block forward
                            if(location.distance(saveloc)>100 || location.getY()<-64){
                                p.sendMessage(ChatColor.RED+"Kein freier Platz gefunden");
                                return;
                            }
                            if(p.getWorld().getBlockAt(location).getType().equals(Material.AIR) && p.getWorld().getBlockAt(location.clone().add(0,1,0)).getType().equals(Material.AIR)){
                                p.teleport(location);
                                return;
                            }
                        }
                    }
                } else {
                    openmodinv(p,"Online",1);
                }
                break;
            case VAULT:
                openblockinf(p,"Start");
                break;
            case BARRIER:
                Bukkit.getOnlinePlayers().forEach(o -> o.showPlayer(plugin,p));
                p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,Integer.MAX_VALUE,1,false,false));
                starterkit(p);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(!p.isOnline() || !p.hasPotionEffect(PotionEffectType.GLOWING)){
                            cancel();
                            return;
                        }
                        // Particles
                        for (int i = 0; i < 5; i++)p.getWorld().spawnParticle(Particle.DUST, p.getLocation(), 10, 0.5, i%2+0.5, 0.5,  new Particle.DustOptions(Color.PURPLE,1.0f));
                    }
                }.runTaskTimer(plugin,0,10);
                break;
            case GLASS:
                Bukkit.getOnlinePlayers().forEach(o -> {
                    if(!o.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN))o.hidePlayer(plugin,p);
                });
                p.removePotionEffect(PotionEffectType.GLOWING);
                starterkit(p);
                break;
            case RED_CONCRETE:
                e.setCancelled(true);
                Bukkit.dispatchCommand(p,"moderationsmodus");
                break;
            case BRUSH:
                ItemStack it=p.getInventory().getItem(2);
                p.getInventory().clear();
                p.getInventory().setItem(2,it);
                starterkit(p);
                break;

            //Block edits

            case NETHERITE_HOE:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                if(e.getAction().isLeftClick()){
                    if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getBlockData() instanceof Waterlogged
                            && ((Waterlogged) p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getBlockData()).isWaterlogged()){
                        Waterlogged bp = (Waterlogged) p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getBlockData();
                        bp.setWaterlogged(false);
                        p.getWorld().setBlockData(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getLocation(),bp);
                    } else p.getWorld().setBlockData(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getLocation(),Bukkit.createBlockData(Material.AIR));
                }
                break;
            case WOODEN_HOE:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                if(e.getAction().isLeftClick()){
                    Block hitblock =p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock();
                    if(blockliste.contains(hitblock.getType())){
                        p.sendMessage(ChatColor.RED+"Dieser Block kann nicht temporär entfernt werden");
                        return;
                    }
                    p.sendMessage(ChatColor.DARK_GREEN+"Block temporär für 10 secunden entfernt: "+ChatColor.GREEN+hitblock.getType());
                    Location loc = hitblock.getLocation();
                    BlockData blockData = hitblock.getBlockData();
                    p.getWorld().setBlockData(loc, Bukkit.createBlockData(Material.AIR));
                    Bukkit.getScheduler().runTaskLater(Patenplugin.getPlugin(), () -> p.getWorld().setBlockData(loc,blockData), 20*10);
                }
                break;
            case BUCKET:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                if((p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.WATER)
                        || p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.LAVA)) && e.getAction().isLeftClick()){
                    p.sendMessage(ChatColor.DARK_GREEN+"Block temporär für 10 secunden entfernt: "+ChatColor.GREEN
                            +p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType());
                    fillblocks(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getLocation()
                            ,p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock(),"temp");
                }
                break;
            case WATER_BUCKET:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                Block block = p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)
                        .getHitBlock().getRelative(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlockFace());
                if(e.getAction().isLeftClick()){
                    fillblocks(block.getLocation(),block,"water");
                }
                break;
            case LAVA_BUCKET:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                Block block2 = p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)
                        .getHitBlock().getRelative(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlockFace());
                if(e.getAction().isLeftClick()){
                    fillblocks(block2.getLocation(),block2,"lava");
                }
                break;
            case SPONGE:
                if(!e.getAction().isLeftClick())openblockinf(p,"Start");
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                if((p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.WATER)
                        || p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.LAVA)) && e.getAction().isLeftClick()){
                    fillblocks(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getLocation()
                            ,p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock(),"del");
                }
                break;
            case SPYGLASS:
                p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"xraymode"));
                if(e.getAction().isLeftClick()){
                    Bukkit.getScheduler().runTaskLater(plugin, () -> xray(p),20*2);
                    return;
                }
                openblockinf(p,"Start");
                break;
        }
    }

    @EventHandler
    public void ondrop(PlayerDropItemEvent e){

        Player p=e.getPlayer();
        if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN))e.setCancelled(true);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);

    }

    @EventHandler
    public void onpickup(PlayerPickupItemEvent e){

        Player p=e.getPlayer();
        if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN))e.setCancelled(true);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return;
        e.setCancelled(true);

    }

    @EventHandler
    public void oninvcklick(InventoryClickEvent e){

        Player p= (Player) e.getWhoClicked();
        if(p.getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN) && ((e.getInventory().getHolder()!=null && e.getInventory().getHolder().equals(p)) || e.getInventory().getHolder()==null))e.setCancelled(true);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return;
        if(e.getClickedInventory()!=null && e.getClickedInventory().getHolder()!=null && e.getClickedInventory().getHolder().equals(p) && e.getSlotType().equals(InventoryType.SlotType.QUICKBAR)){

            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            //hotbar items
            switch (e.getSlot()){
                case 1:
                    openmodinv(p,"Online",1);
                    return;
                case 2:
                    p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"xraymode"));
                    if(e.getAction().equals(InventoryAction.PICKUP_ALL) && e.getCurrentItem().getType().equals(Material.SPYGLASS)){
                        Bukkit.getScheduler().runTaskLater(plugin, () -> xray(p),20*2);
                        return;
                    }
                    openblockinf(p,"Start");
                    return;
                case 6:
                    ItemStack it=p.getInventory().getItem(2);
                    p.getInventory().clear();
                    p.getInventory().setItem(2,it);
                    starterkit(p);
                    return;
                case 8:
                    Bukkit.dispatchCommand(p,"moderationsmodus");
                    return;
            }
        }

        if(e.getView().getTitle().equalsIgnoreCase("Modmodus")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            switch (e.getCurrentItem().getType()){
                case ARROW:
                    openmodinv(p,e.getInventory().getItem(47).getItemMeta().getDisplayName(),(Integer.valueOf(e.getCurrentItem().getItemMeta().getDisplayName())));
                    break;
                case BOOK:
                    openmodinv(p,e.getInventory().getItem(47).getItemMeta().getDisplayName(),1);
                    break;
                case ANVIL:
                    String sorter = e.getInventory().getItem(47).getItemMeta().getDisplayName();
                    sorter=sorter.equals("Online") ? "Offline"
                            :sorter.equals("Offline") ? "Jeder"
                            :sorter.equals("Jeder") ? "Verwarnt"
                            :sorter.equals("Verwarnt") ? "Gebannt"
                            :sorter.equals("Gebannt") ? "Whitelist"
                            :"Online";
                    openmodinv(p,sorter
                            ,(Integer.valueOf(e.getInventory().getItem(51).getItemMeta().getDisplayName())-1));
                    break;
                case PLAYER_HEAD:
                    openplayerinv(p,Bukkit.getOfflinePlayer(e.getCurrentItem().getItemMeta().getDisplayName()));
                    break;
            }
        }

        if(e.getView().getTitle().split(" >")[0].equals("Block Menü")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            switch (e.getView().getTitle()){
                case "Block Menü > Xray > Flüssigkeiten":
                    if(e.getCurrentItem().getType().equals(Material.ARROW)){
                        openblockinf(p,"Xray");
                        return;
                    }
                    switch (e.getSlot()){
                        case  11:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,Material.LAVA.toString().toLowerCase()), PersistentDataType.BOOLEAN,
                                    !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.LAVA.toString().toLowerCase()), PersistentDataType.BOOLEAN,true));
                            break;
                        case 15:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,Material.WATER.toString().toLowerCase()), PersistentDataType.BOOLEAN,
                                    !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.WATER.toString().toLowerCase()), PersistentDataType.BOOLEAN,true));
                            break;
                    }
                    openblockinf(p,"Xray > Flüssigkeiten");
                    return;
                case "Block Menü > Xray > Blocke":
                    if(e.getCurrentItem().getType().equals(Material.ARROW)){
                        openblockinf(p,"Xray");
                        return;
                    }
                    if(e.getCurrentItem().getType().equals(Material.GREEN_TERRACOTTA) || e.getCurrentItem().getType().equals(Material.RED_TERRACOTTA)){
                        boolean toggle = !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,e.getSlot()==20 ? Material.BEDROCK.toString().toLowerCase() : e.getSlot()==24 ? Material.ANCIENT_DEBRIS.toString().toLowerCase() :
                                e.getInventory().getItem(e.getSlot()-9).getType().toString().toLowerCase()), PersistentDataType.BOOLEAN,true);
                        //ore
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin, e.getSlot()==20 ? Material.BEDROCK.toString().toLowerCase() : e.getSlot()==24 ? Material.ANCIENT_DEBRIS.toString().toLowerCase() :
                                        e.getInventory().getItem(e.getSlot()-9).getType().toString().toLowerCase()), PersistentDataType.BOOLEAN,toggle);
                        //deepslate_ore
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin, e.getSlot()==20 ? Material.BEDROCK.toString().toLowerCase() : e.getSlot()==24 ? Material.ANCIENT_DEBRIS.toString().toLowerCase() :
                                "deepslate_"+e.getInventory().getItem(e.getSlot()-9).getType().toString().toLowerCase()), PersistentDataType.BOOLEAN,toggle);
                        //block
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin, e.getSlot()==20 ? Material.BEDROCK.toString().toLowerCase() : e.getSlot()==24 ? Material.ANCIENT_DEBRIS.toString().toLowerCase() :
                                e.getInventory().getItem(e.getSlot()-9).getType().toString().toLowerCase().split("_")[0]+"_block"), PersistentDataType.BOOLEAN,toggle);
                    }
                    openblockinf(p,"Xray > Blocke");
                    return;
                case "Block Menü > Xray":
                    switch (e.getSlot()){
                        case 11:
                            Material.valueOf(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"glassval"), PersistentDataType.STRING,"glass").toUpperCase());
                            String val=p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"glassval"), PersistentDataType.STRING,"glass");
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"glassval"), PersistentDataType.STRING,
                                    val.equals("glass") ? "gray_stained_glass" :
                                    val.equals("gray_stained_glass") ? "red_stained_glass" :
                                    val.equals("red_stained_glass") ? "green_stained_glass" :
                                    val.equals("green_stained_glass") ? "yellow_stained_glass" :
                                    val.equals("yellow_stained_glass") ? "white_stained_glass" : "glass");
                            openblockinf(p,"Xray");
                            return;
                        case 13:
                            openblockinf(p,"Xray > Blocke");
                            return;
                        case 15:
                            openblockinf(p,"Xray > Flüssigkeiten");
                            return;
                        case 20:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"showholes"), PersistentDataType.BOOLEAN,
                                    !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showholes"), PersistentDataType.BOOLEAN,false));
                            openblockinf(p,"Xray");
                            return;
                        case 22:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"showores"), PersistentDataType.BOOLEAN,
                                    !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showores"), PersistentDataType.BOOLEAN,true));
                            openblockinf(p,"Xray");
                            return;
                        case 24:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"showfluid"), PersistentDataType.BOOLEAN,
                                    !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showfluid"), PersistentDataType.BOOLEAN,false));
                            openblockinf(p,"Xray");
                            return;
                        case 30:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) ?
                                    p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50)-10 : p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50)-1);
                            if(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER)<0)p.getPersistentDataContainer().set(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,0);
                            openblockinf(p,"Xray");
                            return;
                        case 32:
                            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,e.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) ?
                                    p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50)+10 : p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50)+1);
                            if(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER)>200)p.getPersistentDataContainer().set(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,200);
                            openblockinf(p,"Xray");
                            return;
                        case 31:
                            if(e.getAction().equals(InventoryAction.PICKUP_ALL)){
                                p.getInventory().setItem(2,getItem(Material.SPYGLASS,ChatColor.BOLD+""+ChatColor.YELLOW+"X-ray",new ArrayList<>(){{
                                    add("");
                                    add(ChatColor.YELLOW+"Linksklicken um X-ray zu re-aktivieren");
                                    add(ChatColor.YELLOW+"Rechtsklicken um das Blockmenü zu öffnen und X-ray zu deaktivieren");
                                }},false));
                                xray(p);
                                p.closeInventory();
                            } else {
                                p.getPersistentDataContainer().set(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,
                                        !p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false));
                                openblockinf(p,"Xray");
                            }
                            return;
                    }
                    return;
                case "Block Menü":
                    openblockinf(p,e.getCurrentItem().getType().equals(Material.WATER_BUCKET)? "Wasser" : e.getCurrentItem().getType().equals(Material.DIAMOND_HOE) ? "Block" : "Xray");
                    return;
            }
            p.getInventory().setItem(2,e.getCurrentItem());
            p.closeInventory();
        }

        if(e.getView().getTitle().split(" >")[0].equals("Beobachtung")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(Bukkit.getPlayer(e.getView().getTitle().split(" >")[1].replace(" ",""))==null){
                if(e.getCurrentItem().getType().equals(Material.BARRIER)){
                    OfflinePlayer pp2 = Bukkit.getOfflinePlayer(e.getView().getTitle().split(" >")[1].replace(" ",""));
                    Bukkit.dispatchCommand(getConsoleSender(),(pp2.isBanned() ? "pardon " : "ban ")+pp2.getName());
                    openplayerinv(p,Bukkit.getOfflinePlayer(e.getView().getTitle().split(" >")[1].replace(" ","")));
                } else if(e.getCurrentItem().getType().equals(Material.ENDER_PEARL)){
                    p.teleport(Bukkit.getOfflinePlayer(e.getView().getTitle().split(" >")[1].replace(" ","")).getLocation());
                } else if (e.getCurrentItem().getType().equals(Material.ARROW)) {
                    openmodinv(p, "Online", 1);
                } else if(e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)){
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getView().getTitle().split(" >")[1].replace(" ","")),0,"Tage","Alle");
                }
                return;
            }

            Player beobachtet=Bukkit.getPlayer(e.getView().getTitle().split(" >")[1].replace(" ",""));


            switch (e.getCurrentItem().getType()) {
                case ARROW:
                    openmodinv(p, "Online", 1);
                    return;
                case BLUE_ICE:
                    if (beobachtet.getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN)) {
                        beobachtet.getPersistentDataContainer().remove(new NamespacedKey(plugin, "freeze"));
                        p.sendMessage(ChatColor.GREEN + "Spieler wurde entfreezed");
                    } else {
                        beobachtet.getPersistentDataContainer().set(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN, true);
                        p.sendMessage(ChatColor.RED + "Spieler wurde gefreezed");
                    }
                    break;
                case WHITE_CONCRETE:
                    beobachtet.getPersistentDataContainer().set(new NamespacedKey(plugin, "verwarnung"), PersistentDataType.INTEGER, 1);
                    break;
                case GREEN_CONCRETE:
                    beobachtet.getPersistentDataContainer().set(new NamespacedKey(plugin, "verwarnung"), PersistentDataType.INTEGER, 2);
                    break;
                case YELLOW_CONCRETE:
                    beobachtet.getPersistentDataContainer().set(new NamespacedKey(plugin, "verwarnung"), PersistentDataType.INTEGER, 3);
                    break;
                case RED_CONCRETE:
                    beobachtet.getPersistentDataContainer().remove(new NamespacedKey(plugin, "verwarnung"));
                    break;
                case BOOK:
                    if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
                        p.closeInventory();
                        p.sendMessage(Component.text("§aClick to write a command [ ; für zeilenumbruch!].")
                                .clickEvent(ClickEvent.suggestCommand("/kommentar " + beobachtet.getName() + " ")));
                        return;
                    } else if(e.getAction().equals(InventoryAction.PICKUP_HALF)){
                        beobachtet.getPersistentDataContainer().remove(new NamespacedKey(plugin, "commentare"));
                    }
                    break;
                case CHEST:
                    p.openInventory(beobachtet.getInventory());
                    return;
                case ENDER_CHEST:
                    p.openInventory(beobachtet.getEnderChest());
                    return;
                case SPYGLASS:
                    p.closeInventory();
                    Bukkit.getOnlinePlayers().forEach(o -> {
                        if(!o.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"),PersistentDataType.BOOLEAN))o.hidePlayer(plugin,p);
                    });
                    p.getInventory().setItem(7,getItem(Material.BARRIER,ChatColor.BOLD+""+ChatColor.GREEN+"Vanish ausschalten",new ArrayList<>(),false));
                    p.removePotionEffect(PotionEffectType.GLOWING);
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING)){
                        p.getPersistentDataContainer().set(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING,
                                p.getLocation().getX()+":"+p.getLocation().getY()+":"+p.getLocation().getZ()+":"+beobachtet.getName());
                    }
                    Bukkit.getOnlinePlayers().stream()
                            .filter(o -> o.getPersistentDataContainer().has(new NamespacedKey(plugin,"wirdbeobachtet"),PersistentDataType.STRING)
                            && o.getPersistentDataContainer().get(new NamespacedKey(plugin,"wirdbeobachtet"),PersistentDataType.STRING).equals(p.getUniqueId().toString()))
                            .forEach(o -> o.getPersistentDataContainer().remove(new NamespacedKey(plugin,"wirdbeobachtet")));
                    beobachtet.getPersistentDataContainer().set(new NamespacedKey(plugin,"wirdbeobachtet"), PersistentDataType.STRING, p.getUniqueId().toString());
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            if(!p.isOnline() || !p.getPersistentDataContainer().has(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING) || !beobachtet.isOnline()){
                                beobachtet.getPersistentDataContainer().remove(new NamespacedKey(plugin,"wirdbeobachtet"));
                                cancel();
                                return;
                            }
                            if(beobachtet.getLocation().distance(p.getLocation())>10){
                                p.setFlying(true);
                                p.sendBlockChange(p.getEyeLocation(),p.getEyeLocation().getBlock().getBlockData());
                                p.teleport(beobachtet.getLocation().add(0,5,0));
                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    p.sendBlockChange(p.getEyeLocation(),Material.AIR.createBlockData());
                                },20);
                                // Packet camera
                                PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
                                cameraPacket.getIntegers().write(0, beobachtet.getEntityId());
                                ProtocolLibrary.getProtocolManager().sendServerPacket(p, cameraPacket);
                            }
                        }
                    }.runTaskTimer(plugin,0,20);
                    // Packet camera
                    PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
                    cameraPacket.getIntegers().write(0, beobachtet.getEntityId());
                    Bukkit.getScheduler().runTaskLater(plugin, () -> ProtocolLibrary.getProtocolManager().sendServerPacket(p, cameraPacket), 10);
                    return;
                case ENDER_PEARL:
                    p.closeInventory();
                    p.teleport(beobachtet);
                    return;
                case BARRIER:
                    Bukkit.dispatchCommand(getConsoleSender(),(beobachtet.isBanned() ?"pardon " : "ban ") +beobachtet.getName());
                    break;
                case DIAMOND_PICKAXE:
                    openbreakblockinv(p,beobachtet,0,"Tage","Alle");
                    return;
            }
            openplayerinv(p,beobachtet);
        }

        if(e.getView().getTitle().equals("§lSpieler Erz-Tracker")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;

            //glass
            if(e.getCurrentItem().getType().equals(Material.GLASS)){
                String lore = e.getCurrentItem().getItemMeta().getLore().get(1);
                p.teleport(p.getLocation().set(Integer.valueOf(lore.split(":")[2].split(" ")[0]),Integer.valueOf(lore.split(":")[3].split(" ")[0]),Integer.valueOf(lore.split(":")[4].split(";")[0])));
                return;
            }

            //cklickitems
            switch (e.getSlot()){
                case 45:
                    openplayerinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()));
                    return;
                case 47:
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()),
                            Integer.valueOf(e.getInventory().getItem(47).getItemMeta().getLore().getFirst()),
                            e.getInventory().getItem(48).getItemMeta().getDisplayName(),
                            e.getInventory().getItem(50).getItemMeta().getDisplayName());
                    return;
                case 48:
                    String sorter2 = e.getInventory().getItem(48).getItemMeta().getDisplayName();
                    int Zeit = Integer.valueOf(e.getInventory().getItem(47).getItemMeta().getLore().getFirst())+1;
                    Zeit= sorter2.equals("Tage") ? Zeit*24 : sorter2.equals("Stunden") ? Zeit*60 : sorter2.equals("Minuten") ? Zeit/60/24/7 : Zeit*7;
                    sorter2=sorter2.equals("Tage") ? "Stunden" : sorter2.equals("Stunden") ? "Minuten" : sorter2.equals("Minuten") ? "Wochen" : "Tage";
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()),
                            Zeit,
                            sorter2,
                            e.getInventory().getItem(50).getItemMeta().getDisplayName());
                    return;
                case 49:
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()),
                            0,
                            e.getInventory().getItem(48).getItemMeta().getDisplayName(),
                            e.getInventory().getItem(50).getItemMeta().getDisplayName());
                    return;
                case 50:
                    String sorter = e.getInventory().getItem(50).getItemMeta().getDisplayName();
                    sorter=sorter.equals("Alle") ? grunderz.getFirst().toString() : sorter.equals("ANCIENT_DEBRIS") ? "Alle" : grunderz.get(grunderz.indexOf(Material.valueOf(sorter))+1).toString();
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()),
                            Integer.valueOf(e.getInventory().getItem(47).getItemMeta().getLore().getFirst())+1,
                            e.getInventory().getItem(48).getItemMeta().getDisplayName(),
                            sorter);
                    return;
                case 51:
                    openbreakblockinv(p,Bukkit.getOfflinePlayer(e.getInventory().getItem(53).getItemMeta().getDisplayName()),
                            Integer.valueOf(e.getInventory().getItem(51).getItemMeta().getLore().getFirst()),
                            e.getInventory().getItem(48).getItemMeta().getDisplayName(),
                            e.getInventory().getItem(50).getItemMeta().getDisplayName());
                    return;
            }

        }
    }

    @EventHandler
    public void onsneak(PlayerToggleSneakEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN)
                 || !p.getPersistentDataContainer().has(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING))return;
        String[] loc=p.getPersistentDataContainer().get(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING).split(":");
        p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"beobachtermodus"));
        p.teleport(p.getLocation().set(Double.valueOf(loc[0]),Double.valueOf(loc[1]),Double.valueOf(loc[2])));
        PacketContainer cameraPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.CAMERA);
        cameraPacket.getIntegers().write(0, p.getEntityId());
        ProtocolLibrary.getProtocolManager().sendServerPacket(p, cameraPacket);
    }

    @EventHandler
    public void onmove(PlayerMoveEvent e){
        if (e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN))e.setCancelled(true);
    }

    @EventHandler
    public void oninvopen(InventoryOpenEvent e){
        Player p = (Player) e.getPlayer();
        if(p.getPersistentDataContainer().has(new NamespacedKey(plugin, "freeze"), PersistentDataType.BOOLEAN))e.setCancelled(true);
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"wirdbeobachtet"), PersistentDataType.STRING))return;
        if(Bukkit.getPlayer(UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"wirdbeobachtet"), PersistentDataType.STRING)))==null)return;
        Player p2 = Bukkit.getPlayer(UUID.fromString(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"wirdbeobachtet"), PersistentDataType.STRING)));
        if(!p2.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN)
                || !p2.getPersistentDataContainer().has(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING))return;
        if(p2.equals(p))return;
        p2.openInventory(e.getInventory());
    }

    @EventHandler
    public void onleave(PlayerQuitEvent e){
        Player p=e.getPlayer();
        if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN))return;
        Bukkit.dispatchCommand(p,"moderationsmodus");
    }

    @EventHandler
    public void playerjoin(PlayerJoinEvent e){
        Bukkit.getOnlinePlayers().forEach(p -> {
            if(p.getPersistentDataContainer().has(new NamespacedKey(plugin,"modmode"), PersistentDataType.BOOLEAN)){
                e.getPlayer().hidePlayer(plugin,p);
            }
        });
    }

    @EventHandler
    public void playerbreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        if(!erzliste.contains(e.getBlock().getType()) && !grunderz.contains(e.getBlock().getType()))return;
        if(e.getBlock().getType().toString().toLowerCase().contains("block"))return;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:dd:MM:yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        String currentTime = dateFormat.format(new Date());
        //key dd.MM.yyyyore_oreTYPE
        //speichert HH:mm:ss:dd:MM:yyyy:x:y:z;
        p.getPersistentDataContainer().set(new NamespacedKey(plugin,currentTime.split(":")[3]+"."+currentTime.split(":")[4]+"."+currentTime.split(":")[5]+"ore_"+e.getBlock().getType().toString().toLowerCase()),PersistentDataType.STRING,
                p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,currentTime.split(":")[3]+"."+currentTime.split(":")[4]+"."+currentTime.split(":")[5]+"ore_"+e.getBlock().getType().toString().toLowerCase()),PersistentDataType.STRING,"")
                        +currentTime+":"+e.getBlock().getLocation().getBlockX()+":"+e.getBlock().getLocation().getBlockY()+":"+e.getBlock().getLocation().getBlockZ()+";");
    }

    public static void openplayerinv(Player p, OfflinePlayer beobachtet){

        Inventory inv= Bukkit.createInventory(p,45,"Beobachtung > "+beobachtet.getName());

        inv.setItem(11,getItem(Material.BLUE_ICE,ChatColor.BOLD+""+ChatColor.BLUE+"Freeze Toggle",new ArrayList<>(){{
            add(ChatColor.DARK_GREEN+"Zustand gerade: "+(beobachtet.getPersistentDataContainer().has(new NamespacedKey(plugin,"freeze"), PersistentDataType.BOOLEAN) ? ChatColor.RED+"gefreezed" : ChatColor.GREEN+"Nicht gefreezed"));
            add("");
            add(ChatColor.YELLOW+"Linksklicken um zu Toggeln");
        }},false));

        switch (beobachtet.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"verwarnung"), PersistentDataType.INTEGER,0)){
            case 0:
                inv.setItem(13,getItem(Material.WHITE_CONCRETE,ChatColor.BOLD+""+ChatColor.WHITE+"Keine Verwarnung",new ArrayList<>(){{
                    add("");
                    add(ChatColor.WHITE+"Linksklicken um zu Verwarnen");
                }},true));
                break;
            case 1:
                inv.setItem(13,getItem(Material.GREEN_CONCRETE,ChatColor.BOLD+""+ChatColor.GREEN+"Verwarnt Stufe: 1",new ArrayList<>(){{
                    add("");
                    add(ChatColor.GREEN+"Linksklicken um Verwarung zu erhöhen");
                }},false));
                break;
            case 2:
                inv.setItem(13,getItem(Material.YELLOW_CONCRETE,ChatColor.BOLD+""+ChatColor.YELLOW+"Verwarnt Stufe: 2",new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um Verwarung zu erhöhen");
                }},false));
                break;
            case 3:
                inv.setItem(13,getItem(Material.RED_CONCRETE,ChatColor.BOLD+""+ChatColor.RED+"Verwarnt Stufe: 3",new ArrayList<>(){{
                    add("");
                    add(ChatColor.RED+"Linksklicken um Verwarnung zu entfernen");
                }},false));
                break;
        }

        inv.setItem(15,getItem(Material.BOOK,ChatColor.BOLD+""+ChatColor.GRAY+"Kommentare zu dem Spieler",new ArrayList<>(){{
            if(beobachtet.getPersistentDataContainer().has(new NamespacedKey(plugin,"commentare"), PersistentDataType.STRING)){
                Arrays.stream(beobachtet.getPersistentDataContainer().get(new NamespacedKey(plugin, "commentare"), PersistentDataType.STRING).split(";")).forEach(this::add);
            }
            add("");
            add(ChatColor.YELLOW+"Linksklicken um Kommentar hinzuzufügen");
            add(ChatColor.DARK_RED+"Rechtsklick um Kommentare zu löschen");
        }},false));

        inv.setItem(21,getItem(Material.CHEST,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Inventar öffnen",new ArrayList<>(){{
            add("");
            add(ChatColor.DARK_GRAY+"Linksklicken um das Spieler Inventar zu öffnen");
        }},false));
        inv.setItem(23,getItem(Material.ENDER_CHEST,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"EnderChest öffnen",new ArrayList<>(){{
            add("");
            add(ChatColor.DARK_GRAY+"Linksklicken um die Spieler Enderchest zu öffnen");
        }},false));

        inv.setItem(29,getItem(Material.SPYGLASS,ChatColor.BOLD+""+ChatColor.LIGHT_PURPLE+"Spieler beobachten",new ArrayList<>(){{
            add("");
            add(ChatColor.LIGHT_PURPLE+"Linksklicken um den Spieler zu beobachten");
        }},false));

        inv.setItem(31,getItem(Material.ENDER_PEARL,ChatColor.BOLD+""+ChatColor.DARK_PURPLE+"Zum Spieler telepotieren",new ArrayList<>(){{
            add("");
            add(ChatColor.DARK_PURPLE+"Linksklicken um zu dem Spieler zu teleportieren");
        }},false));

        inv.setItem(33,getItem(Material.BARRIER,ChatColor.BOLD+""+ChatColor.RED+"Spieler bannen/entbannen",new ArrayList<>(){{
            add(beobachtet.isBanned() ? ChatColor.RED+"Spieler ist gebannt" : ChatColor.GREEN+"Spieler ist nicht gebannt");
            add("");
            add(ChatColor.RED+"Linksklicken um den Spieler zu bannen/entbannen");
        }},false));
        inv.setItem(36,getItem(Material.ARROW,ChatColor.BOLD+""+ChatColor.BLUE+"Zurück",new ArrayList<>(){{
            add("");
            add(ChatColor.BLUE+"Linksklicken um zurück in das Moderations menü zu gehen");
        }},false));
        inv.setItem(44,getItem(Material.DIAMOND_PICKAXE,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Erz Tracker",new ArrayList<>(){{
            add("");
            add(ChatColor.DARK_GRAY+"Linksklicken um das Erz Tracker zu öffnen");
        }},true));

        p.openInventory(inv);
    }

    private void openmodinv(Player p, String sucher, int Seite){
        Inventory inv= Bukkit.createInventory(p,54,"Modmodus");


        //creat the allways components
        ItemStack suche = new ItemStack(Material.ANVIL);
        ItemMeta suche_meta = suche.getItemMeta();
        suche_meta.setDisplayName(sucher);
        ArrayList<String> suche_lore = new ArrayList<>();
        suche_lore.add("Klicke hier um den Filter Algorytmus zu ändern");
        suche_meta.setLore(suche_lore);
        suche.setItemMeta(suche_meta);

        ItemStack vorpfeil = new ItemStack(Material.ARROW);
        ItemMeta vorpfeil_meta = vorpfeil.getItemMeta();
        vorpfeil_meta.setDisplayName(String.valueOf((Seite+1)));
        vorpfeil.setItemMeta(vorpfeil_meta);

        ItemStack buch = new ItemStack(Material.BOOK);
        ItemMeta buch_meta = buch.getItemMeta();
        buch_meta.setDisplayName(ChatColor.GRAY + "§lSeite: 1");
        buch.setItemMeta(buch_meta);


        inv.setItem(51, vorpfeil);
        inv.setItem(49, buch);
        inv.setItem(47, suche);

        ArrayList<OfflinePlayer> alltheplayers= new ArrayList<>();

        switch (sucher){
            case "Online":
                Bukkit.getOnlinePlayers().forEach(alltheplayers::add);
                break;
            case "Offline":
                Arrays.stream(Bukkit.getOfflinePlayers()).filter(offlinePlayer -> !offlinePlayer.isOnline()).forEach(alltheplayers::add);
                break;
            case "Verwarnt":
                Arrays.stream(Bukkit.getOfflinePlayers()).filter(o -> o.getPersistentDataContainer().has(new NamespacedKey(plugin,"verwarnung"), PersistentDataType.INTEGER)).forEach(alltheplayers::add);
                Collections.sort(alltheplayers, (a1,a2) -> Long.compare(a2.getPersistentDataContainer().get(new NamespacedKey(plugin,"verwarnung"), PersistentDataType.INTEGER)
                        ,a1.getPersistentDataContainer().get(new NamespacedKey(plugin,"verwarnung"), PersistentDataType.INTEGER)));
                break;
            case "Gebannt":
                Arrays.stream(Bukkit.getOfflinePlayers()).filter(o -> o.isBanned()).forEach(alltheplayers::add);
                break;
            case "Whitelist":
                Arrays.stream(Bukkit.getOfflinePlayers()).filter(OfflinePlayer::isWhitelisted).forEach(alltheplayers::add);
                break;
            default:
                Stream.of(Bukkit.getOfflinePlayers()).forEach(alltheplayers::add);
                break;
        }

        //sort name alphabetikly
        alltheplayers.sort(Comparator.comparing(OfflinePlayer::getName, String.CASE_INSENSITIVE_ORDER));

        for (OfflinePlayer sp : alltheplayers){
            if(alltheplayers.indexOf(sp)>=44*(Seite-1) && alltheplayers.indexOf(sp)<=44*Seite){

                //time
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                //Create head
                ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
                SkullMeta skull=(SkullMeta) head.getItemMeta();

                ArrayList<String> skull_lore=new ArrayList<>();
                skull_lore.add("--------Rp Infos--------");
                skull_lore.add("Rp Name: "+sp.getPersistentDataContainer().get(new NamespacedKey("klassensysteem", "secretname"), PersistentDataType.STRING));
                skull_lore.add("Verwarntstufe: "+(sp.getPersistentDataContainer().has(new NamespacedKey(plugin,"verwarnt"), PersistentDataType.INTEGER)
                        ? sp.getPersistentDataContainer().get(new NamespacedKey(plugin,"verwarnt"), PersistentDataType.INTEGER) : "0"));
                skull_lore.add("Gebannt: "+(sp.isBanned() ? "Ja" : "Nein"));
                skull_lore.add("Whitlistet: "+(sp.isWhitelisted() ? "Ja" : "Nein"));
                skull_lore.add("--------Timestamp--------");
                skull_lore.add("LastLogin: "+dateFormat.format(new Date(sp.getLastLogin())));
                skull_lore.add("Last Location: X:"+sp.getLocation().getBlockX()+" Y:"+sp.getLocation().getBlockY()+" Z:"+sp.getLocation().getBlockZ());
                if(sp.getPersistentDataContainer().has(new NamespacedKey(plugin,"commentare"), PersistentDataType.STRING)){
                    skull_lore.add("--------Komentare--------");
                    String[] commentare = sp.getPersistentDataContainer().get(new NamespacedKey(plugin, "commentare"), PersistentDataType.STRING).split(";");
                    for (String commentar : commentare) {
                        skull_lore.add(commentar);
                    }
                }
                skull_lore.add("");
                skull_lore.add(ChatColor.YELLOW+"Linksklick zum überwachen");
                skull.setDisplayName(sp.getName());
                if(Bukkit.getPlayer(sp.getName())==null){
                    String base64 = sp.isWhitelisted() && !sp.isBanned() ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU3NzAwMDk2YjVhMmE4NzM4NmQ2MjA1YjRkZGNjMTRmZDMzY2YyNjkzNjJmYTY4OTM0OTk0MzFjZTc3YmY5In19fQ=="
                            : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==";

                    // Create a PlayerProfile with a random UUID and apply the base64 texture
                    PlayerProfile profile = getServer().createProfile(UUID.randomUUID(), "CustomHead");
                    profile.getProperties().add(new ProfileProperty("textures", base64));

                    // Set the profile to the skull meta
                    skull.setPlayerProfile(profile);
                } else {
                    skull.setOwner(sp.getName());
                }
                skull.setLore(skull_lore);
                head.setItemMeta(skull);
                inv.setItem(inv.firstEmpty(),head);
            }
        }

        p.openInventory(inv);
    }

    private void openblockinf(Player p, String sucher){

        //Blockedit
        p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"xraymode"));
        p.getInventory().setItem(2,getItem(Material.VAULT,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Block Menü",new ArrayList<>(){{
            add("Clicken um das Blockmenü zu öffnen");
        }},false));

        Inventory inv= Bukkit.createInventory(p, sucher.equals("Xray") ? 45 : 27,sucher.equals("Start") ? "Block Menü" : "Block Menü > " +sucher);

        switch (sucher){
            case "Wasser":
                inv.setItem(10,getItem(Material.WATER_BUCKET,ChatColor.BOLD+""+ChatColor.BLUE+"Wasser Setzen",new ArrayList<>(){{
                    add(ChatColor.BLUE+"Linksklicken um den Wasserblock zu setzen [20 Blöcke]");
                    add("");
                    add(ChatColor.BLUE+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                inv.setItem(12,getItem(Material.BUCKET,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Wasser Temporer entfernen",new ArrayList<>(){{
                    add(ChatColor.DARK_GRAY+"Linksklicken um den Wasser temporär zu entfernen");
                    add("");
                    add(ChatColor.DARK_GRAY+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                inv.setItem(14,getItem(Material.LAVA_BUCKET,ChatColor.BOLD+""+ChatColor.DARK_RED+"Lava Setzen",new ArrayList<>(){{
                    add(ChatColor.DARK_RED+"Linksklicken um den Lava Block zu setzen [20 Blöcke]");
                    add("");
                    add(ChatColor.DARK_RED+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                inv.setItem(16,getItem(Material.SPONGE,ChatColor.BOLD+""+ChatColor.YELLOW+"Wasser entfernen",new ArrayList<>(){{
                    add(ChatColor.YELLOW+"Linksklicken um Wasser zu entfernen [20 Blöcke]");
                    add("");
                    add(ChatColor.YELLOW+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                break;
            case "Block":
                inv.setItem(12,getItem(Material.WOODEN_HOE,ChatColor.BOLD+""+ChatColor.GRAY+"Block Temporer entfernen",new ArrayList<>(){{
                    add(ChatColor.GRAY+"Linksklicken um den Block temporär zu entfernen");
                    add(ChatColor.GRAY+"[Ausgenommen: Türen, Truhen, Betten oder ähnliches]");
                    add("");
                    add(ChatColor.GRAY+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                inv.setItem(14,getItem(Material.NETHERITE_HOE,ChatColor.BOLD+""+ChatColor.RED+"Block entfernen",new ArrayList<>(){{
                    add(ChatColor.RED+"Linksklicken um den Block zu entfernen");
                    add("");
                    add(ChatColor.RED+"Rechtsklicken um das Blockmenü zu öffnen");
                }},false));
                break;
            case "Xray":
                boolean toggle1 = p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showholes"), PersistentDataType.BOOLEAN,false);
                inv.setItem(11,getItem(Material.valueOf(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"glassval"), PersistentDataType.STRING,"glass").toUpperCase()),ChatColor.BOLD+""+ChatColor.WHITE+"Höhlen anzeigen",new ArrayList<>(){{
                    add(ChatColor.WHITE+"Wenn AN sieht man alle höllen und");
                    add(ChatColor.WHITE+"spieler gegrabene tunnel mit glass umrandet");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um Glass-Farbe zu wechseln");
                }},false));
                inv.setItem(20,getItem(toggle1 ? Material.GREEN_TERRACOTTA : Material.RED_TERRACOTTA,ChatColor.BOLD+""+(toggle1 ? ChatColor.GREEN+"AN" : ChatColor.RED+"AUS"),new ArrayList<>(){{
                    add(toggle1 ? ChatColor.GREEN+"Momentan An" : ChatColor.RED+"Momentan Aus");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um zu toggeln");
                }},false));

                boolean toggle2 = p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showores"), PersistentDataType.BOOLEAN,true);
                inv.setItem(13,getItem(Material.DIAMOND_ORE,ChatColor.BOLD+""+ChatColor.BLUE+"Erze anzeigen",new ArrayList<>(){{
                    add(ChatColor.BLUE+"Wenn AN sieht man alle ausgewählten erze");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken erze zu toggeln");
                }},false));
                inv.setItem(22,getItem(toggle2 ? Material.GREEN_TERRACOTTA : Material.RED_TERRACOTTA,ChatColor.BOLD+""+(toggle2 ? ChatColor.GREEN+"AN" : ChatColor.RED+"AUS"),new ArrayList<>(){{
                    add(toggle2 ? ChatColor.GREEN+"Momentan An" : ChatColor.RED+"Momentan Aus");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um zu toggeln");
                }},false));

                boolean toggle3 = p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showfluid"), PersistentDataType.BOOLEAN,false);
                inv.setItem(15,getItem(Material.BUCKET,ChatColor.BOLD+""+ChatColor.GRAY+"Flüssigkeiten anzeigen",new ArrayList<>(){{
                    add(ChatColor.GRAY+"Wenn AN sieht man alle ausgewählten Flüssigkeiten");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um Flüssigkeiten zu toggeln");
                }},false));
                inv.setItem(24,getItem(toggle3 ? Material.GREEN_TERRACOTTA : Material.RED_TERRACOTTA,ChatColor.BOLD+""+(toggle3 ? ChatColor.GREEN+"AN" : ChatColor.RED+"AUS"),new ArrayList<>(){{
                    add(toggle3 ? ChatColor.GREEN+"Momentan An" : ChatColor.RED+"Momentan Aus");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um zu toggeln");
                }},false));

                int radius =  p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50);
                inv.setItem(31,getItem(Material.SPYGLASS,ChatColor.BOLD+""+ChatColor.YELLOW+"X-ray Aktivieren ["+radius+"]",new ArrayList<>(){{
                    add(ChatColor.RED+""+ChatColor.UNDERLINE+"Maximal 200 / Empfholen Max 100");
                    add(ChatColor.YELLOW+"Momentaner Radius: "+ChatColor.DARK_AQUA+radius);
                    add("");
                    add(ChatColor.YELLOW+"FullRender: "+ (p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false) ? ChatColor.GREEN+"AN" : ChatColor.RED+"AUS"));
                    add(ChatColor.AQUA+"Fullrender bedeutet von min bis max punkt [ "+ChatColor.DARK_AQUA+p.getWorld().getMaxHeight()+" ]");
                    add("");
                    add(ChatColor.YELLOW+"Rechtsklick um FullRender zu aktivieren");
                    add(ChatColor.YELLOW+"Linksklicken um X-ray zu aktivieren");
                }},false));
                inv.setItem(30,getItem(Material.RED_TERRACOTTA,ChatColor.BOLD+""+ChatColor.RED+"Range -1/-10 ["+ChatColor.DARK_AQUA+radius+ChatColor.RED+"]",new ArrayList<>(){{
                    add(ChatColor.RED+""+ChatColor.UNDERLINE+"Maximal 200 / Empfholen Max 100");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um die X-Ray range um 1 zu verringern");
                    add(ChatColor.YELLOW+"Shift + Linksklicken um die X-Ray range um 10 zu verringern");
                }},false));
                inv.setItem(32,getItem(Material.GREEN_TERRACOTTA,ChatColor.BOLD+""+ChatColor.GREEN+"Range +1/+10 ["+ChatColor.DARK_AQUA+radius+ChatColor.GREEN+"]",new ArrayList<>(){{
                    add(ChatColor.RED+""+ChatColor.UNDERLINE+"Maximal 200 / Empfholen Max 100");
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um die X-Ray range um 1 zu erhöhen");
                    add(ChatColor.YELLOW+"Shift + Linksklicken um die X-Ray range um 10 zu erhöhen");
                }},false));

                break;
            case "Xray > Flüssigkeiten":
                inv.setItem(10,getItem(Material.LAVA_BUCKET,Material.LAVA_BUCKET.toString(),new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um sichtbarkeit zu toggeln");
                }},false));
                inv.setItem(11,getyeno(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.LAVA.toString().toLowerCase()), PersistentDataType.BOOLEAN,true)));
                inv.setItem(16,getItem(Material.WATER_BUCKET,Material.WATER_BUCKET.toString(),new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um sichtbarkeit zu toggeln");
                }},false));
                inv.setItem(15,getyeno(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.WATER.toString().toLowerCase()), PersistentDataType.BOOLEAN,true)));
                inv.setItem(13,getItem(Material.ARROW,ChatColor.BOLD+""+ChatColor.BLUE+"Zurück",new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um zurück zu gehen");
                }},false));
                break;
            case "Xray > Blocke":
                for(Material mat : grunderz){
                    if(mat.equals(Material.BEDROCK) || mat.equals(Material.ANCIENT_DEBRIS))continue;
                    inv.setItem(grunderz.indexOf(mat),new ItemStack(mat));
                    inv.setItem(grunderz.indexOf(mat)+9,getyeno(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,mat.toString().toLowerCase()), PersistentDataType.BOOLEAN,true)));
                }
                inv.setItem(19,new ItemStack(Material.BEDROCK));
                inv.setItem(20,getyeno(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.BEDROCK.toString().toLowerCase()), PersistentDataType.BOOLEAN,true)));
                inv.setItem(25,new ItemStack(Material.ANCIENT_DEBRIS));
                inv.setItem(24,getyeno(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,Material.ANCIENT_DEBRIS.toString().toLowerCase()), PersistentDataType.BOOLEAN,true)));
                inv.setItem(22,getItem(Material.ARROW,ChatColor.BOLD+""+ChatColor.BLUE+"Zurück",new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um zurück zu gehen");
                }},false));
                break;
            default:
                inv.setItem(11,getItem(Material.WATER_BUCKET,ChatColor.BOLD+""+ChatColor.BLUE+"Wasser editieren",new ArrayList<>(){{
                    add("");
                    add(ChatColor.BLUE+"Linksklicken um das Wasser Menü zu öffnen");
                }},false));
                inv.setItem(13,getItem(Material.DIAMOND_HOE,ChatColor.BOLD+""+ChatColor.DARK_AQUA+"Blöcke editieren",new ArrayList<>(){{
                    add("");
                    add(ChatColor.DARK_AQUA+"Linksklicken um das Block Menü zu öffnen");
                }},false));
                inv.setItem(15,getItem(Material.SPYGLASS,ChatColor.BOLD+""+ChatColor.YELLOW+"X-ray editieren",new ArrayList<>(){{
                    add("");
                    add(ChatColor.YELLOW+"Linksklicken um das X-ray Menü zu öffnen");
                }},false));
                break;
        }

        p.openInventory(inv);
    }

    private void openbreakblockinv(Player p, OfflinePlayer of,int Zeit, String sorter,String erzsorter) {
        //create the inventory
        Inventory Erzinv= Bukkit.createInventory(p,54,"§lSpieler Erz-Tracker");

        //spieler
        ItemStack head=new ItemStack(Material.PLAYER_HEAD,1,(short) 3);
        SkullMeta skull=(SkullMeta) head.getItemMeta();
        skull.setDisplayName(of.getName());
        if(Bukkit.getPlayer(of.getName())==null){
            String base64 = of.isWhitelisted() ? "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU3NzAwMDk2YjVhMmE4NzM4NmQ2MjA1YjRkZGNjMTRmZDMzY2YyNjkzNjJmYTY4OTM0OTk0MzFjZTc3YmY5In19fQ=="
                    : "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmViNTg4YjIxYTZmOThhZDFmZjRlMDg1YzU1MmRjYjA1MGVmYzljYWI0MjdmNDYwNDhmMThmYzgwMzQ3NWY3In19fQ==";

            // Create a PlayerProfile with a random UUID and apply the base64 texture
            PlayerProfile profile = getServer().createProfile(UUID.randomUUID(), "CustomHead");
            profile.getProperties().add(new ProfileProperty("textures", base64));

            // Set the profile to the skull meta
            skull.setPlayerProfile(profile);
        } else {
            skull.setOwner(of.getName());
        }
        head.setItemMeta(skull);
        Erzinv.setItem(53,head);

        //click items
        Erzinv.setItem(51,getItem(Material.ARROW,ChatColor.BOLD+""+gettime(sorter,(Zeit-2))+" - "+gettime(sorter,(Zeit+4)),new ArrayList<>(){{
            add(String.valueOf(Zeit+1));
        }},false));
        Erzinv.setItem(49,getItem(Material.BOOK,ChatColor.BOLD+""+gettime(sorter,(Zeit-3))+" - "+gettime(sorter,(Zeit+3)),new ArrayList<>(){{
            add("");
            add(ChatColor.YELLOW+"Linkscklick für "+gettime(sorter,0));
        }},false));
        Erzinv.setItem(48,getItem(Material.ANVIL,sorter,new ArrayList<>(){{
            add("Mögliche Ansichten: "+ChatColor.ITALIC+"Wochen, Tage, Stunden, Minuten");
            add("Jede ansicht ermöglicht eine genauere Zeitangabe");
            add("");
            add(ChatColor.YELLOW+"Linkscklick um die Zeit-ansicht zu ändern");
        }},false));
        Erzinv.setItem(47,getItem(Material.ARROW,ChatColor.BOLD+""+gettime(sorter,(Zeit-4))+" - "+gettime(sorter,(Zeit+2)),new ArrayList<>(){{
            add(String.valueOf(Zeit-1));
        }},false));
        Erzinv.setItem(50,getItem(Material.ANVIL,erzsorter,new ArrayList<>(){{
            add("Mögliche Erze sind:");
            add("Alle, Coal_Ore, Iron_Ore,");
            add("Gold_Ore, Diamond_Ore, Emerald_Ore, Lapis_Ore,");
            add("Redstone_Ore, Quartz_Ore, Ancient_Debris");
            add("");
            add(ChatColor.YELLOW+"Linkscklick um die Erz-ansicht zu ändern");
        }},false));
        Erzinv.setItem(45,getItem(Material.BARRIER,ChatColor.RED+"§lZurück",new ArrayList<>(),false));

        //glass & erze
        for (int i = -3; i < 4; i++) {
            int finalI = i;

            //add the ores
            ArrayList<String> erzabbau = new ArrayList<>();
            for (int j = (sorter.equals("Wochen") ? -3 : 0 ); j< (sorter.equals("Wochen") ? 4 : 1 );j++){
                //woche dd.MM.yyyy muss ausgang nehmen dann -3 -> +3  Zeit + (i*7)+j
                //tage dd.MM.yyyy richtig
                //stunden HH dd.MM.yyyy
                //minuten mm:HH dd.MM.yyyy
                String finalSorter = gettime(sorter.equals("Wochen")
                        ? "Tage" : sorter, sorter.equals("Wochen") ? ((Zeit+i)*7)+j : Zeit+i);
                if(finalSorter.contains(" "))finalSorter=finalSorter.split(" ")[1];

                if(erzsorter.equals("Alle")) {
                    int finalJ = j;

                    String finalSorter1 = finalSorter;
                    grunderz.forEach(mat -> {
                        erzabbau.addAll(getValues(of,sorter.equals("Wochen") ? "Tage" : sorter,mat, finalSorter1,(sorter.equals("Wochen") ? ((Zeit+finalI)*7)+finalJ : Zeit + finalI)));
                    });
                    erzliste.forEach(mat -> erzabbau.addAll(getValues(of,sorter.equals("Wochen") ? "Tage" : sorter,mat,finalSorter1,(sorter.equals("Wochen") ? ((Zeit+finalI)*7)+finalJ : Zeit + finalI))));
                } else {
                    erzabbau.addAll(getValues(of,sorter.equals("Wochen") ? "Tage" : sorter,Material.valueOf(erzsorter.toUpperCase()),finalSorter,(sorter.equals("Wochen") ? ((Zeit+i)*7)+j : Zeit + i)));
                    if(erzsorter.toUpperCase().equals("NETHER_QUARTZ_ORE") || erzsorter.toUpperCase().equals("ANCIENT_DEBRIS") || erzsorter.toUpperCase().equals("BEDROCK"))continue;
                    erzabbau.addAll(getValues(of,sorter.equals("Wochen") ? "Tage" : sorter,Material.valueOf("DEEPSLATE_"+erzsorter.toUpperCase()),finalSorter,(sorter.equals("Wochen") ? ((Zeit+i)*7)+j : Zeit + i)));
                }
            }

            //add the erzblocks
            if(Zeit+i>0) {
                Erzinv.setItem(31+i,getItem(Material.WHITE_TERRACOTTA,ChatColor.BOLD+""+ChatColor.WHITE+"Zukunft",new ArrayList<>(),false));
            } else if(erzabbau.isEmpty()){
                Erzinv.setItem(31+i,getItem(Material.GRAY_TERRACOTTA,ChatColor.BOLD+""+ChatColor.GRAY+"Keine abgebauten erze",new ArrayList<>(),false));
            } else {
                //erz map to count them all
                HashMap<String,Integer> erz = new HashMap<>();
                erzabbau.forEach(s -> erz.put(s.split(";")[1],erz.containsKey(s.split(";")[1]) ? erz.get(s.split(";")[1])+1 : 1));

                //create the item
                ItemStack item = new ItemStack(erzsorter.equals("Alle") ? Material.IRON_PICKAXE : Material.valueOf(erzsorter.toUpperCase())){{
                    ItemMeta it = this.getItemMeta();
                    it.setDisplayName(ChatColor.YELLOW+
                            (sorter.equals("Wochen") ? gettime(sorter,(Zeit+finalI))+" - "+ gettime("Tage",((Zeit+finalI+1)*7)-1): gettime(sorter,(Zeit+finalI))));
                    it.lore(new ArrayList<Component>(){{
                        erz.forEach((s, integer) -> {
                            Material material = Material.valueOf(s);
                            Component itemName = Component.translatable(material.getItemTranslationKey()).color(NamedTextColor.YELLOW);
                            Component count = Component.text(": " + integer).color(NamedTextColor.WHITE);
                            Component finalComponent = itemName.append(count);
                            add(finalComponent);
                        });
                        add(Component.text(""));
                        add(Component.text("Gesamt: "+ChatColor.WHITE+erzabbau.size()));
                    }});
                    this.setItemMeta(it);
                }};
                //ab wann ein neues item erstellt wird, vl anpassen über zeit
                int stück = sorter.equals("Wochen") ? 500 : sorter.equals("Tage") ? 200 : sorter.equals("Stunden") ? 100 : 20;

                for (int j = 0; j < (erzabbau.size()/stück+(erzabbau.size()%stück>0 ? 1 : 0)); j++) {
                    if(31+i-(9*j)<0)break; //savty first
                    Erzinv.setItem(31+i-(9*j),item);
                }
            }

            //add the glass
            Erzinv.setItem(40+i,getItem(Material.GLASS,gettime(sorter,(Zeit+i)),new ArrayList<>(){{
                if(!erzabbau.isEmpty()){
                    String last=erzabbau.getLast();
                    add("Zuletzt abgebaut: "+last.split(":")[0]+":"+last.split(":")[1]+":"+last.split(":")[2]+" "
                            +last.split(":")[3]+"."+last.split(":")[4]+"."+last.split(":")[5]);
                    add("Position: X:"+last.split(":")[6]+" Y:"+last.split(":")[7]+" Z:"+last.split(":")[8].split(";")[0]);
                    add("Erz: "+last.split(":")[8].split(";")[1]);
                    add("");
                    add(ChatColor.YELLOW+"Linksklick zum Teleportieren");
                }
            }},false));

        }
        p.openInventory(Erzinv);
    }

    private void fillblocks(Location loc, Block block,String type){
        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        Material material = block.getType();

        queue.add(block);
        visited.add(block);

        while (!queue.isEmpty()) {
            Block current = queue.poll();

            // Set the block to air (remove the water)
            BlockData bl =current.getBlockData();

            if(current.getBlockData() instanceof Waterlogged){
                Waterlogged wt = (Waterlogged) current.getBlockData();
                wt.setWaterlogged(type.equals("water"));
                current.setBlockData(wt);
            }else current.setType(type.equals("water") ? Material.WATER : type.equals("lava") ? Material.LAVA : Material.AIR);

            if(type.equals("temp"))Bukkit.getScheduler().runTaskLater(plugin, () -> current.setBlockData(bl), 20*10);
            // Check all adjacent blocks (including diagonals)
            for (BlockFace face : BlockFace.values()) {
                Block adjacent = current.getRelative(face);

                // Skip if the block is already visited or is not water
                if (!visited.contains(adjacent)
                        && (adjacent.getType().equals(material) || (adjacent.getBlockData() instanceof Waterlogged))
                        && adjacent.getLocation().distance(loc) <= 20
                        && !((type.equals("water") || type.equals("lava")) && adjacent.getLocation().getBlockY()>loc.getBlockY())) {
                    visited.add(adjacent);
                    queue.add(adjacent);
                }
            }
        }
    }

    public static void xray(Player p){
        int radius =  p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"xrayrange"), PersistentDataType.INTEGER,50);

        //center
        Player center = p.getPersistentDataContainer().has(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING)
                ? Bukkit.getPlayer(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING).split(":")[3])!=null
                ? Bukkit.getPlayer(p.getPersistentDataContainer().get(new NamespacedKey(plugin,"beobachtermodus"), PersistentDataType.STRING).split(":")[3]) : p : p;

        if(p.getPersistentDataContainer().has(new NamespacedKey(plugin,"xraymode"), PersistentDataType.LONG)){
            if((new Date().getTime()-1000<=p.getPersistentDataContainer().get(new NamespacedKey(plugin,"xraymode"), PersistentDataType.LONG)))return;
            p.getPersistentDataContainer().remove(new NamespacedKey(plugin,"xraymode"));
            p.sendMessage(ChatColor.RED+"X-Ray Deaktiviert");
        } else {
            p.getPersistentDataContainer().set(new NamespacedKey(plugin,"xraymode"), PersistentDataType.LONG,new Date().getTime());
            p.sendMessage(ChatColor.GREEN+"X-Ray Aktiviert");
            //send packs
            Location loc = center.getLocation().getBlock().getLocation();

            for (int i = 0; i < radius; i++) {
                for (int j = 0; j < (p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false) ? p.getWorld().getMaxHeight()>300 ? 300 : p.getWorld().getMaxHeight() : radius); j++) {
                    for (int k = 0; k < radius; k++) {

                        Location loc2 = loc.clone().add(i - radius / 2, j - (p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false) ? p.getWorld().getMaxHeight()>300 ? 300 : p.getWorld().getMaxHeight() : radius)/2, k - radius / 2);

                        //check die listen
                        if(center.getWorld().getBlockAt(loc2).getType().isAir())continue;
                        if(center.getWorld().getHighestBlockAt(loc2).getY()+1<loc2.getY())continue;
                        if(blockliste.contains(center.getWorld().getBlockAt(loc2).getType()))continue;
                        if(!center.getWorld().getBlockAt(loc2).getType().isCollidable() && !center.getWorld().getBlockAt(loc2).getType().equals(Material.WATER) && !center.getWorld().getBlockAt(loc2).getType().equals(Material.LAVA)){
                            p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(),Material.AIR.createBlockData());
                            continue;
                        }
                        if(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showores"), PersistentDataType.BOOLEAN,true)
                                && (erzliste.contains(center.getWorld().getBlockAt(loc2).getType())
                                || grunderz.contains(center.getWorld().getBlockAt(loc2).getType()) && center.getWorld().getHighestBlockAt(loc2).getY()>=loc2.getY())
                                && p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,center.getWorld().getBlockAt(loc2).getType().toString().toLowerCase()), PersistentDataType.BOOLEAN,true)){
                            p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(), center.getWorld().getBlockAt(loc2).getBlockData());
                            continue;
                        }
                        if(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showfluid"), PersistentDataType.BOOLEAN,false)
                                && p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,center.getWorld().getBlockAt(loc2).getType().toString().toLowerCase()), PersistentDataType.BOOLEAN,true)
                                && (center.getWorld().getBlockAt(loc2).getType().equals(Material.WATER) || center.getWorld().getBlockAt(loc2).getType().equals(Material.LAVA)))continue;
                        if(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"showholes"), PersistentDataType.BOOLEAN,false) && center.getWorld().getBlockAt(loc2).getType().isSolid()){
                            if((loc2.getBlock().getRelative(BlockFace.UP).getType().isAir()
                                    || loc2.getBlock().getRelative(BlockFace.DOWN).getType().isAir()
                                    || loc2.getBlock().getRelative(BlockFace.NORTH).getType().isAir()
                                    || loc2.getBlock().getRelative(BlockFace.SOUTH).getType().isAir()
                                    || loc2.getBlock().getRelative(BlockFace.EAST).getType().isAir()
                                    || loc2.getBlock().getRelative(BlockFace.WEST).getType().isAir())){
                                if((center.getWorld().getHighestBlockAt(loc2).getY()==loc2.getY() && !loc2.getBlock().getRelative(BlockFace.DOWN).getType().isAir())
                                        || center.getWorld().getMinHeight()==loc2.getY()){
                                    p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(),Material.BARRIER.createBlockData());
                                    continue;
                                }
                                p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(),Material.valueOf(p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"glassval"), PersistentDataType.STRING,"glass").toUpperCase()).createBlockData());
                                continue;
                            }
                        }
                        p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(),Material.BARRIER.createBlockData());
                    }
                }
            }

            //wieder ausschalten
            new BukkitRunnable(){
                @Override
                public void run() {
                    if(!p.isOnline()){
                        cancel();
                        return;
                    }
                    if(!p.getPersistentDataContainer().has(new NamespacedKey(plugin,"xraymode"), PersistentDataType.LONG)){
                        if(p.getLocation().distance(loc)>300){
                            cancel();
                            return;
                        }
                        for (int i = 0; i < radius; i++) {
                            for (int j = 0; j < (p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false) ? p.getWorld().getMaxHeight()>300 ? 300 : p.getWorld().getMaxHeight() : radius); j++) {
                                for (int k = 0; k < radius; k++) {
                                    Location loc2 = loc.clone().add(i - radius / 2, j - (p.getPersistentDataContainer().getOrDefault(new NamespacedKey(plugin,"fullrender"), PersistentDataType.BOOLEAN,false) ? p.getWorld().getMaxHeight()>300 ? 300 : p.getWorld().getMaxHeight() : radius)/2, k - radius / 2);
                                    if(center.getWorld().getHighestBlockAt(loc2).getY()>=loc2.getY()){
                                        p.sendBlockChange(center.getWorld().getBlockAt(loc2).getLocation(), center.getWorld().getBlockAt(loc2).getBlockData());
                                    }
                                }
                            }
                        }
                        cancel();
                        return;
                    }
                }
            }.runTaskTimer(plugin,0,20);
        }
    }

    private ItemStack getyeno(boolean toggle){

        ItemStack item = new ItemStack(toggle ? Material.GREEN_TERRACOTTA : Material.RED_TERRACOTTA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(toggle ? ChatColor.GREEN+"AN" : ChatColor.RED+"AUS");
        meta.setLore(new ArrayList<>(){{
            add(toggle ? ChatColor.GREEN+"Momentan An" : ChatColor.RED+"Momentan Aus");
            add("");
            add(ChatColor.YELLOW+"Linksklicken um zu toggeln");
        }});
        item.setItemMeta(meta);

        return item;
    }

    private String gettime(String sorter, int Zeit){
        //chanchable specs time
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"));
        int zeiger = sorter.equals("Wochen") ? Calendar.WEEK_OF_YEAR
                : sorter.equals("Tage") ? Calendar.DAY_OF_YEAR
                : sorter.equals("Stunden") ? Calendar.HOUR_OF_DAY
                : Calendar.MINUTE;
        calendar.add(zeiger, Zeit);

        //chanching timeform
        SimpleDateFormat dateFormat = new SimpleDateFormat(sorter.equals("Wochen") ? "dd.MM.yyyy"
                : sorter.equals("Tage") ? "dd.MM.yyyy"
                : sorter.equals("Stunden") ? "HH dd.MM.yyyy"
                : "HH:mm dd.MM.yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
        return dateFormat.format(calendar.getTime());
    }

    private ArrayList<String> getValues(OfflinePlayer p, String sorter, Material mat,String namezeitsorter, int Zeit){
        //returns list of HH:mm:ss:dd:MM:yyyy:x:y:z:Material
        return new ArrayList<>(){{
            if(p.getPersistentDataContainer().has(new NamespacedKey(plugin, namezeitsorter+"ore_"+mat.toString().toLowerCase()), PersistentDataType.STRING)){
                Arrays.stream(p.getPersistentDataContainer().get(new NamespacedKey(plugin,namezeitsorter+"ore_"+mat.toString().toLowerCase()), PersistentDataType.STRING).split(";")).forEach(s ->{
                    if(sorter.equals("Tage"))this.add(s+";"+mat);
                    if(sorter.equals("Stunden") && s.split(":")[0].equals(gettime(sorter, Zeit).split(" ")[0]))this.add(s+";"+mat);
                    if(sorter.equals("Minuten") && s.split(":")[0].equals(gettime(sorter, Zeit).split(":")[0])
                            && s.split(":")[1].equals(gettime(sorter, Zeit).split(":")[1].split(" ")[0]))this.add(s+";"+mat);
                });
            }
        }};
    }
}
