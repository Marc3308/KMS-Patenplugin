package me.marc3308.patenplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class utility {
    private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    private static ArrayList<UUID> playerskin = new ArrayList<>();
    private static ArrayList<String> skinlist = new ArrayList<>() {{
        add("https://textures.minecraft.net/texture/741356bfd2e7abcf9bf8898877b36c6b485c55260b3b0d071a147e5641505acb");
    }};

    public static void sendchanchepack(Player p) {

        if (playerskin.contains(p.getUniqueId())) {
            resetSkin(p);
            playerskin.remove(p.getUniqueId());
            return;
        }
        playerskin.add(p.getUniqueId());
        changeSkin(p, skinlist.getFirst());

    }

    private static void changeSkin(Player player, String skinUrl) {
        // Create a new game profile with the custom skin
        WrappedGameProfile gameProfile = WrappedGameProfile.fromPlayer(player);
        gameProfile.getProperties().put("textures", new WrappedSignedProperty("textures", skinUrl, "signature"));

        // Create the PLAYER_INFO packet
        PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        // Create PlayerInfoData
        PlayerInfoData playerInfoData = new PlayerInfoData(
                gameProfile,
                player.getPing(),
                EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                WrappedChatComponent.fromText(player.getName())
        );

        // Write PlayerInfoData to the packet
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        // Send the packet to all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            protocolManager.sendServerPacket(onlinePlayer, packet);
        }
    }

    private static void resetSkin(Player player) {
        // Reset the game profile to the default skin
        WrappedGameProfile gameProfile = WrappedGameProfile.fromPlayer(player);
        gameProfile.getProperties().removeAll("textures");

        // Create the PLAYER_INFO packet
        PacketContainer packet = protocolManager.createPacket(com.comphenix.protocol.PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        // Create PlayerInfoData
        PlayerInfoData playerInfoData = new PlayerInfoData(
                gameProfile,
                player.getPing(),
                EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()),
                WrappedChatComponent.fromText(player.getName())
        );

        // Write PlayerInfoData to the packet
        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        // Send the packet to all online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            protocolManager.sendServerPacket(onlinePlayer, packet);
        }
    }
}
