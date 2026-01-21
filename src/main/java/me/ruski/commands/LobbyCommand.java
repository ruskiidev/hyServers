package me.ruski.commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.ruski.Plugin;
import me.ruski.models.Server;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class LobbyCommand extends AbstractPlayerCommand {

    public LobbyCommand() {
        super("lobby", "go to lobby server");
    }

    public Server getLobbyServer() {
        for (Server s: Plugin.getInstance().getPluginConfig().get().getServers()) {
            if (s.isLobby()) {
                return s;
            }
        }
        return null;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = store.getComponent(ref, Player.getComponentType()); // also a component
        Server lobbyServer = getLobbyServer();

        if (player != null && lobbyServer != null) {
            if (!Plugin.getInstance().getServerManager().getCurrentServer().getName().equals(lobbyServer.getName())) {
                playerRef.referToServer(lobbyServer.getIpAddress(), lobbyServer.getPort());
            } else {
                playerRef.sendMessage(Message.raw("You`re already in lobby."));
            }
        }

    }
}
