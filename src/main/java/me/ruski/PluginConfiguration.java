package me.ruski;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.math.vector.Transform;
import lombok.Getter;
import lombok.Setter;
import me.ruski.models.Server;

@Getter
@Setter
public class PluginConfiguration {

    public static final BuilderCodec<PluginConfiguration> CODEC = BuilderCodec.builder(
            PluginConfiguration.class, PluginConfiguration::new
    ).append(
            new KeyedCodec<>("Servers", ServersManager.SERVERS_CODEC),
            ((pluginConfiguration, servers) -> pluginConfiguration.servers = servers),
            (pluginConfiguration -> pluginConfiguration.servers)
    ).add().append(
            new KeyedCodec<>("CurrentServer", Codec.STRING),
            ((pluginConfiguration, server) -> pluginConfiguration.currentServer = server),
            (pluginConfiguration -> pluginConfiguration.currentServer)
    ).add().build();

    private String currentServer = "example";
    private Server[] servers = { new Server("example", "127.0.0.1", 25500, true, "lobby", new Transform(0, 2, 0), false) };
}