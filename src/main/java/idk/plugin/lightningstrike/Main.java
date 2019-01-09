package idk.plugin.lightningstrike;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.network.protocol.AddEntityPacket;
import cn.nukkit.plugin.PluginBase;

public class Main extends PluginBase implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        strikeLightning(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        strikeLightning(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        strikeLightning(e.getEntity());
    }

    public void strikeLightning(Player p) {
        AddEntityPacket pk = new AddEntityPacket();
        pk.entityUniqueId = cn.nukkit.entity.Entity.entityCount++;
        pk.entityRuntimeId = cn.nukkit.entity.Entity.entityCount++;
        pk.type = 93;
        pk.x = (float) p.getX();
        pk.y = (float) p.getY();
        pk.z = (float) p.getZ();
        pk.speedX = 0.0f;
        pk.speedY = 0.0f;
        pk.speedZ = 0.0f;
        pk.yaw = (float) p.getYaw();
        pk.pitch = (float) p.getPitch();

        for (Player pl : p.getLevel().getPlayers().values()) {
            try {
                Class.forName("cn.nukkit.utils.EntityUtils");
                pk.protocol = pl.protocol;
            } catch (Exception e) {}
            pl.dataPacket(pk);
        }
    }
}
