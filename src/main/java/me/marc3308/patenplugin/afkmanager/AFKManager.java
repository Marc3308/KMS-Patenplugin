package me.marc3308.patenplugin.afkmanager;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class AFKManager {

    // AFKTIME has to be MS because of the calculation below (timesincemove)
    private static final long AFKTIME= 180000L; // 180.000 / 1000 = 180 sek / 60 = 3 Min.

    private static final HashMap<Player, Long> lastMovement=new HashMap<>();

    public static void playerJoin(Player player){
        lastMovement.put(player,System.currentTimeMillis());
    }

    public static void playerleave(Player player){
        lastMovement.remove(player);
    }

    public static long getAfktime(Player player){
        return lastMovement.get(player);
    }

    public static boolean isafk(Player player){

        if(lastMovement.get(player)==null) return false;

        long timesincemove = System.currentTimeMillis() - lastMovement.get(player);

        return timesincemove >= AFKTIME;
    }

}
