package me.marc3308.patenplugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class utility {
    private static HashMap<UUID,String[]> playerskin = new HashMap<>();
    private static ArrayList<String[]> skinlist = new ArrayList<>(){{
        add(new String[]{"ewogICJ0aW1lc3RhbXAiIDogMTczNzUwNjkxNjY5OSwKICAicHJvZmlsZUlkIiA6ICIzZmM3ZmRmOTM5NjM0YzQxOTExOTliYTNmN2NjM2ZlZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJZZWxlaGEiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTEyNjEyYTBhZDBjZTZlNDVhYjhmZTc4YTQ5ODEwNWQ3YWIzOTQyNDZkODlkZDgzMjU0NDI3NTIwYTRmMmM0YyIKICAgIH0KICB9Cn0="
                ,"iw/wB+FxQKjyy2hUX/dGw07qQPGD4qWW8pZGL0WuTpJYaf6jyiyo+mC8ANShg5bi1Zko5iT4YoCp21EpLnOeTcd0vpPnaOT8mDd9vjeHLoDCBz8V18MuEuVPDfFgXXcr3AVtkkv6wNX9Pkm8spSxekat3m2+f3hXCOWq4H6Xx6XBQgL//Cw1HsfdzGZB4sl+YI8SAVrdwQ4/4xvhMqaC4QNrzgCMZQSQ74bJwroRqnVI2U8hn4k035Ge/mrxijnULCb8EIeJcBI2pEg3FMDp92H7SXUwUH0uQd0rrg9jbT+hPBO8PT4WbLC5xS9d6Jlixrz3GUpxSVngCHREq5dxXF3KT32WWL3ZrtvRTb3h2B0z9/4+tVC4jpIlaCf4JtbOewH6V6MyTU926cNf4KKExfEegwZvKJ5JoLM8B7T/38Yg8bFYe61JCVTBio+uUFsBPfeB2kH6TchYptwjKDyBoHNOR+xbegxHMKM7LJGcjfhS0ZwP2LY4+s9VzzcucHMBo36CAkxIthfKiElm9A6OhkANUi5QcJzJ6gX35Q46Qu4KAZq0bTEMNPJFJblob+z3NzWvOt6XNSRiEpws6omI8K29wv4euyR3ds3EHCZSuns/hNKMNg23gCks1rSN/TlbJERW+edcobbHin2x7GYsivuB5eICId67t1ezKWd1WsU="
        });
    }};

    public static void sendpack(Player p){
        UUID uuid = p.getUniqueId();

        if(1==1)return;

        if (!playerskin.containsKey(uuid)) {
            // Save the player's original skin
            String[] skinData = getSkinData(p);
            if (skinData == null) {
                p.sendMessage("Unable to retrieve your original skin data.");
            }
            playerskin.put(uuid, skinData);

            // Change to the new skin
            changePlayerSkin(p, skinlist.getFirst()[0], skinlist.getFirst()[1]);
            p.sendMessage("Your skin has been changed!");
        } else {
            // Revert to the original skin
            String[] originalSkin = playerskin.remove(uuid);
            changePlayerSkin(p, originalSkin[0], originalSkin[1]);
            p.sendMessage("Your skin has been reverted to the original!");
        }
    }

    private static void changePlayerSkin(Player player, String value, String signature) {
        try {

            for (Player p : Bukkit.getOnlinePlayers()){

                PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
                PacketContainer revpack = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO_REMOVE);
                revpack.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
                revpack.getPlayerInfoDataLists().write(0, Collections.singletonList(new PlayerInfoData(WrappedGameProfile.fromPlayer(player),
                        player.getPing(),
                        EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                        WrappedChatComponent.fromText(player.getName()))));
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, revpack);


                // Get the current WrappedGameProfile
                WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
                // Add or modify the "textures" property for the skin
                profile.getProperties().clear();
                profile.getProperties().put("textures", new WrappedSignedProperty(
                        "textures",
                        value, // Replace with actual Base64 value
                        signature // Replace with actual signature
                ));

                packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
                // create a new player info data
                PlayerInfoData new_playerinfo = new PlayerInfoData(
                        profile,
                        player.getPing(),
                        EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                        WrappedChatComponent.fromText(player.getName()));

                // write it into packet
                packet.getPlayerInfoDataLists().write(0, Collections.singletonList(new_playerinfo)); //1 -> 0
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);


            }

        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions for debugging
        }

//        try {
//            PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
//
//            // Get the current WrappedGameProfile
//            WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
//
//            // Add or modify the "textures" property for the skin
//            profile.getProperties().clear();
//            profile.getProperties().put("textures", new WrappedSignedProperty(
//                    "textures",
//                    value, // Replace with actual Base64 value
//                    signature // Replace with actual signature
//            ));
//
//            packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
//            // create a new player info data
//            PlayerInfoData new_playerinfo = new PlayerInfoData(profile,
//                    player.getPing(),
//                    EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
//                    WrappedChatComponent.fromText(player.getName()));
//
//            // write it into packet
//            packet.getPlayerInfoDataLists().write(0, Collections.singletonList(new_playerinfo)); //1 -> 0
//
//            // Send ADD_PLAYER packet to all players
//            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//                ProtocolLibrary.getProtocolManager().sendServerPacket(onlinePlayer, packet);
//            }
//
//            // Refresh the player by hiding and showing them
//            player.hidePlayer(Patenplugin.getPlugin(), player); // Hide the player temporarily
//            Bukkit.getScheduler().runTaskLater(Patenplugin.getPlugin(), () -> player.showPlayer(Patenplugin.getPlugin(), player), 10L); // Show the player again after a short delay
//
//        } catch (Exception e) {
//            e.printStackTrace(); // Log any exceptions for debugging
//        }
    }

    private static String[] getSkinData(Player player) {
        try {
            WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);

            // Get the textures property (skin data)
            if (profile.getProperties().containsKey("textures")) {
                for (WrappedSignedProperty property : profile.getProperties().get("textures")) {
                    String value = property.getValue();
                    String signature = property.getSignature();
                    return new String[]{value, signature};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
