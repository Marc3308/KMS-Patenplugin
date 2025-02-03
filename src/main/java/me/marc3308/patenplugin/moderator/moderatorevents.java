package me.marc3308.patenplugin.moderator;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.marc3308.patenplugin.Patenplugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
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
import static org.bukkit.Bukkit.getConsoleSender;
import static org.bukkit.Bukkit.getServer;

public class moderatorevents implements Listener {

    private static ArrayList<Material> blockliste=new ArrayList<>();

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
                openmodinv(p,"Online",1);
                break;
            case VAULT:
                openblockinf(p);
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
                        for (int i = 0; i < 5; i++)p.getWorld().spawnParticle(Particle.DUST, p.getLocation(), 10, 0.5, i%2+0.5, 0.5,  new Particle.DustOptions(Color.fromBGR(146,110,224), 1.0f));
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
                if(!e.getAction().isLeftClick())openblockinf(p);
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
                if(!e.getAction().isLeftClick())openblockinf(p);
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
                if(!e.getAction().isLeftClick())openblockinf(p);
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
                if(!e.getAction().isLeftClick())openblockinf(p);
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                Block block = p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)
                        .getHitBlock().getRelative(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlockFace());
                if(e.getAction().isLeftClick()){
                    fillblocks(block.getLocation(),block,"water");
                }
                break;
            case LAVA_BUCKET:
                if(!e.getAction().isLeftClick())openblockinf(p);
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                Block block2 = p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)
                        .getHitBlock().getRelative(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlockFace());
                if(e.getAction().isLeftClick()){
                    fillblocks(block2.getLocation(),block2,"lava");
                }
                break;
            case SPONGE:
                if(!e.getAction().isLeftClick())openblockinf(p);
                if(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS)==null)return;
                if((p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.WATER)
                        || p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getType().equals(Material.LAVA)) && e.getAction().isLeftClick()){
                    fillblocks(p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock().getLocation()
                            ,p.getWorld().rayTraceBlocks(p.getEyeLocation(),p.getEyeLocation().getDirection(),4, FluidCollisionMode.ALWAYS).getHitBlock(),"del");
                }
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
        if(e.getClickedInventory()!=null && e.getClickedInventory().getHolder()!=null && e.getClickedInventory().getHolder().equals(p) && e.getSlot()<9){
            e.setCancelled(true);
            switch (e.getSlot()){
                case 1:
                    openmodinv(p,"Online",1);
                    break;
                case 8:
                    Bukkit.dispatchCommand(p,"moderationsmodus");
                    break;
                case 6:
                    ItemStack it=p.getInventory().getItem(2);
                    p.getInventory().clear();
                    p.getInventory().setItem(2,it);
                    starterkit(p);
                    break;
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

        if(e.getView().getTitle().equalsIgnoreCase("Block Menü")){
            e.setCancelled(true);
            if(e.getCurrentItem()==null)return;
            if(e.getSlot()<9)return;
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
                }
                return;
            }

            Player beobachtet=Bukkit.getPlayer(e.getView().getTitle().split(" >")[1].replace(" ",""));
            switch (e.getCurrentItem().getType()) {
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
                                p.teleport(beobachtet.getLocation().add(0,5,0));
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
            }

            openplayerinv(p,beobachtet);
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

    private void openblockinf(Player p){
        //Blockedit
        p.getInventory().setItem(2,getItem(Material.VAULT,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Block Menü",new ArrayList<>(){{
            add("Clicken um das Blockmenü zu öffnen");
        }},false));

        Inventory inv= Bukkit.createInventory(p,45,"Block Menü");

        inv.setItem(11,getItem(Material.WATER_BUCKET,ChatColor.BOLD+""+ChatColor.BLUE+"Wasser Setzen",new ArrayList<>(){{
            add(ChatColor.BLUE+"Linksklicken um den Wasserblock zu setzen [20 Blöcke]");
            add("");
            add(ChatColor.BLUE+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));
        inv.setItem(13,getItem(Material.BUCKET,ChatColor.BOLD+""+ChatColor.DARK_GRAY+"Wasser Temporer entfernen",new ArrayList<>(){{
            add(ChatColor.DARK_GRAY+"Linksklicken um den Wasser temporär zu entfernen");
            add("");
            add(ChatColor.DARK_GRAY+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));
        inv.setItem(15,getItem(Material.LAVA_BUCKET,ChatColor.BOLD+""+ChatColor.DARK_RED+"Lava Setzen",new ArrayList<>(){{
            add(ChatColor.DARK_RED+"Linksklicken um den Lava Block zu setzen [20 Blöcke]");
            add("");
            add(ChatColor.DARK_RED+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));

        inv.setItem(23,getItem(Material.WOODEN_HOE,ChatColor.BOLD+""+ChatColor.GRAY+"Block Temporer entfernen",new ArrayList<>(){{
            add(ChatColor.GRAY+"Linksklicken um den Block temporär zu entfernen");
            add(ChatColor.GRAY+"[Ausgenommen: Türen, Truhen, Betten oder ähnliches]");
            add("");
            add(ChatColor.GRAY+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));
        inv.setItem(21,getItem(Material.NETHERITE_HOE,ChatColor.BOLD+""+ChatColor.RED+"Block entfernen",new ArrayList<>(){{
            add(ChatColor.RED+"Linksklicken um den Block zu entfernen");
            add("");
            add(ChatColor.RED+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));
        inv.setItem(31,getItem(Material.SPONGE,ChatColor.BOLD+""+ChatColor.YELLOW+"Wasser entfernen",new ArrayList<>(){{
            add(ChatColor.YELLOW+"Linksklicken um Wasser zu entfernen [20 Blöcke]");
            add("");
            add(ChatColor.YELLOW+"Rechtsklicken um das Blockmenü zu öffnen");
        }},false));

        p.openInventory(inv);
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

}
