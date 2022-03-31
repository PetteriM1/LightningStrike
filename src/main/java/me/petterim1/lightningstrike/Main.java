package me.petterim1.lightningstrike;

import cn.nukkit.Player;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

    private boolean onJoin;
    private boolean onQuit;
    private boolean onDeath;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        onJoin = getConfig().getBoolean("onJoin", true);
        onQuit = getConfig().getBoolean("onQuit", true);
        onDeath = getConfig().getBoolean("onDeath", true);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        if (onJoin) strikeLightning(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        if (onQuit) strikeLightning(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDeath(PlayerDeathEvent e) {
        if (onDeath) strikeLightning(e.getEntity());
    }

    private static void strikeLightning(Player p) {
        long id = cn.nukkit.entity.Entity.entityCount++;

        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = id;
        pk.entityRuntimeId = id;
        pk.type = EntityLightning.NETWORK_ID;
        pk.x = (float) p.getX();
        pk.y = (float) p.getY();
        pk.z = (float) p.getZ();
        pk.speedX = 0.0f;
        pk.speedY = 0.0f;
        pk.speedZ = 0.0f;
        pk.yaw = (float) p.getYaw();
        pk.pitch = (float) p.getPitch();

        for (Player pl : p.getLevel().getPlayers().values()) {
            pl.dataPacket(pk);
        }
    }
}
