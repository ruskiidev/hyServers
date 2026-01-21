package me.ruski;

import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.server.core.io.ServerManager;
import lombok.Getter;
import lombok.Setter;
import me.ruski.models.Server;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


@Getter
@Setter
public class ServersManager {

    private String currentServerName;
    private Server currentServer;
    private Server[] servers;
    private PluginConfiguration configuration;
    public static final ArrayCodec<Server> SERVERS_CODEC = new ArrayCodec<>(Server.SERVER_CODEC, Server[]::new);
    public static String ipOrDomainRegex = "^(" +
            "((\\d{1,3}\\.){3}\\d{1,3})" +
            "|" +
            "([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}" +
            ")$";


    public ServersManager() {

        this.configuration = null;
        this.currentServerName = null;
        this.servers = null;
        this.currentServer = null;
    }

    private boolean validateActualServer() {
        for (Server s: servers) {
            if (s.getName().equals(this.currentServerName)
                    && s.getIpAddress().matches(ServersManager.ipOrDomainRegex)) {
                currentServer = s;
                return true;
            }
        }

        return false;
    }

    private boolean validateServerAddress(Server server) throws SocketException {
        if (ServerManager.get().getLocalOrPublicAddress() != null) {
            InetSocketAddress serverRealAddress = ServerManager.get().getLocalOrPublicAddress();

            Logger.getGlobal().log(Level.WARNING, "Server ip : " + serverRealAddress.getAddress().getHostAddress() + ":" + serverRealAddress.getPort());
            Logger.getGlobal().log(Level.WARNING, "Current Server : " + currentServer.getIpAddress() + ":" + currentServer.getPort());
            return serverRealAddress.getAddress().getHostAddress().equals(currentServer.getIpAddress()) &&
                    serverRealAddress.getPort() == currentServer.getPort();
        }

        return false;
    }


    public void init() {

        this.configuration = Plugin.getInstance().getPluginConfig().get();
        this.servers = this.configuration.getServers();
        this.currentServerName = this.configuration.getCurrentServer();

        if (!validateActualServer()) {
            throw new IllegalStateException("CurrentServer not found on config.servers");
        } else {

            // validating server port and server address
            try {
                if (!validateServerAddress(currentServer)) {
                    throw new IllegalStateException("CurrentServer ip:port not matching on config.servers ( use ipv4 address - (127.0.0.1/loopback addresses are not allowed))");
                }

            } catch (SocketException e) {
                throw new RuntimeException(e);
            }


        }





    }

}
