# 🌐 Hytale MultiServer Connector (Base Version)

> **⚠️ PROJECT STATUS: BASE / IN DEVELOPMENT**
> This system is a **base structure** for connecting Hytale servers. It is currently functional, but the code requires improvements, refactoring, and optimization before being used in a massive production environment.

## 📖 Description
This mod allows for the management and connection between multiple Hytale server instances. It manages the network identity to allow player flow between servers using the native connection API.

**Requirement:** This component must be installed in the `mods` folder of **ALL** servers that make up your network.

---

## ⚙️ Parameter Explanation (`config.json`)

The file consists of a list of all available servers (`Servers`) and the identification of the local server (`CurrentServer`).

### 1. `Servers` (Network List)
Defines the network topology. This list must be identical on all servers.

| Parameter | Type | Description |
| :--- | :---: | :--- |
| **`Name`** | `String` | Unique server name (e.g., `"Lobby"`, `"AdventureMode"`). |
| **`IpAddress`** | `String` | **CRITICAL:** The host server IP address.<br>❌ **DO NOT USE:** `127.0.0.1` or `localhost`.<br>✅ **USE:** The local network IPv4 (e.g., `192.168.1.X`) or the Public IP if remote. |
| **`Port`** | `Int` | The Hytale server connection port (e.g., `5520`). |
| **`IsLobby`** | `Bool` | `true`: Marks this server as the main entry point.<br>`false`: Standard game server. |
| **`ForceSpawnOnEnter`**| `Bool` | `true`: Forces the avatar to spawn at `SpawnPosition` upon connection.<br>`false`: Respects the player's last saved position. |
| **`SpawnWorld`** | `String` | Identifier of the destination world or instance. |
| **`SpawnPosition`** | `Object` | Vector coordinates `X`, `Y`, `Z` for the spawn. |

### 2. `CurrentServer` (Identity & Connection)
| Parameter | Type | Description |
| :--- | :---: | :--- |
| **`CurrentServer`** | `String` | Defines **who this instance is**.<br>⚠️ Must match **exactly** one of the `Name`s listed above. Its function is critical to send players to said server by executing the native Hytale method:<br>`PlayerSetupConnectEvent.referToServer(@Nonnull final String host, final int port, @Nullable byte[] data)` |

---

## 📝 Configuration Example

```json
{
  "Servers": [
    {
      "Name": "Lobby",
      "IpAddress": "192.168.1.19",
      "Port": 5520,
      "IsLobby": true,
      "ForceSpawnOnEnter": true,
      "SpawnWorld": "lobby_world",
      "SpawnPosition": { "X": 0.0, "Y": 2.0, "Z": 0.0 }
    },
    {
      "Name": "Survival",
      "IpAddress": "192.168.1.19",
      "Port": 3500,
      "IsLobby": false,
      "ForceSpawnOnEnter": false,
      "SpawnWorld": "orbis_default",
      "SpawnPosition": { "X": 100.0, "Y": 65.0, "Z": 100.0 }
    }
  ],
  "CurrentServer": "Survival"
}