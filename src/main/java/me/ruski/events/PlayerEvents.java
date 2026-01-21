package me.ruski.events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.ruski.Plugin;
import me.ruski.PluginConfiguration;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import me.ruski.models.Server;

import java.util.Arrays;

public class PlayerEvents {

    protected static PluginConfiguration cfg = Plugin.getInstance().getPluginConfig().get();
    protected static Server currentServer = Arrays.stream(cfg.getServers()).filter(s -> s.getName().equals(cfg.getCurrentServer())).findFirst().orElse(null);

    public static void onReady(PlayerReadyEvent event) {
        Ref<EntityStore> playerRef = event.getPlayerRef();
        Store<EntityStore> playerStore = playerRef.getStore();
        Player p = playerStore.getComponent(playerRef, Player.getComponentType());
        if (p == null) {return;}
        if (p.getWorld() == null) {return;}
        if (currentServer == null) {return;}

        if (currentServer.isForceSpawnOnEnter() || currentServer.isLobby()) {
            p.getWorld().execute(() -> {
                Teleport teleport = new Teleport(
                        currentServer.getSpawnPosition().getPosition(),
                        new Vector3f(0, 0, 0)  // (pitch, yaw, roll)
                );
                playerStore.addComponent(playerRef, Teleport.getComponentType(), teleport);
            });
        }

    }

    public static void onConnected(PlayerConnectEvent event) {
        World world = Universe.get().getWorld(currentServer.getSpawnWorld());
        if (world == null) {return;}
        if (currentServer == null) {return;}

        if (currentServer.isLobby() | currentServer.isForceSpawnOnEnter()) {
            event.setWorld(world);
        }
    }

}
