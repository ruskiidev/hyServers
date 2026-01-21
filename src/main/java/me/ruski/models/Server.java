package me.ruski.models;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.math.vector.Transform;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Server {

    private String name;
    private String ipAddress;
    private int port;
    private boolean isLobby;
    private String spawnWorld;
    private Transform spawnPosition;
    private boolean forceSpawnOnEnter;

    public static final BuilderCodec<Server> SERVER_CODEC = BuilderCodec.<Server>builder(Server.class, Server::new)
            .append(new KeyedCodec<>("Name", BuilderCodec.STRING),
                    (setting, value) -> setting.name = value,
                    setting -> setting.name ).add()
            .append(new KeyedCodec<>("IpAddress",BuilderCodec.STRING),
                    (setting, value) -> setting.ipAddress = value,
                    setting -> setting.ipAddress).add()
            .append(new KeyedCodec<>("Port", BuilderCodec.INTEGER),
                    (setting, value) -> setting.port = value,
                    setting -> setting.port).add()
            .append(new KeyedCodec<>("IsLobby", BuilderCodec.BOOLEAN),
                    (setting, value) -> setting.isLobby = value,
                    (setting) -> setting.isLobby).add()
            .append(new KeyedCodec<>("ForceSpawnOnEnter", BuilderCodec.BOOLEAN),
                    (setting, value) -> setting.forceSpawnOnEnter = value,
                    (setting) -> setting.forceSpawnOnEnter).add()
            .append(new KeyedCodec<>("SpawnWorld", BuilderCodec.STRING),
                    (setting, value) -> setting.spawnWorld = value,
                    setting -> setting.spawnWorld
                    ).add()
            .append(new KeyedCodec<>("SpawnPosition", Transform.CODEC),
                    (setting, value) -> setting.spawnPosition = value,
                    setting -> setting.spawnPosition
            ).add().build();


    public Server() {}

    public Server(String name, String ip, int port, boolean isLobby, String spawnWorld, Transform spawnPosition, boolean forceSpawnOnEnter) {
        this.name = name;
        this.spawnWorld = spawnWorld;
        this.isLobby = isLobby;
        this.ipAddress = ip;
        this.port = port;
        this.spawnPosition = spawnPosition;
        this.forceSpawnOnEnter = forceSpawnOnEnter;
    }

}
