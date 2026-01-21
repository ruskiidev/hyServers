package me.ruski;

import com.hypixel.hytale.server.core.event.events.player.PlayerConnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import lombok.Getter;
import lombok.Setter;
import me.ruski.commands.LobbyCommand;
import me.ruski.events.PlayerEvents;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

@Getter
@Setter
public class Plugin extends JavaPlugin {

    @Getter
    public static Plugin instance;

    private final ServersManager serverManager;
    private final Config<PluginConfiguration> pluginConfig;

    public Plugin(@NonNullDecl JavaPluginInit init) {
        super(init);
        this.pluginConfig = this.withConfig("Config", PluginConfiguration.CODEC);
        this.serverManager = new ServersManager();
        instance = this;
    }

    @Override
    protected void setup() {
        getEventRegistry().registerGlobal(PlayerReadyEvent.class, PlayerEvents::onReady);
        getEventRegistry().registerGlobal(PlayerConnectEvent.class, PlayerEvents::onConnected);
        getCommandRegistry().registerCommand(new LobbyCommand());
    }

    @Override
    protected void start() {
        this.pluginConfig.save();
        serverManager.init();
    }

    @Override
    protected void shutdown() {
        this.pluginConfig.save();
    }
}
