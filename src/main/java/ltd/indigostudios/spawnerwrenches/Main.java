package ltd.indigostudios.spawnerwrenches;

import ltd.indigostudios.spawnerwrenches.api.Language;
import ltd.indigostudios.spawnerwrenches.commands.BaseCommand;
import ltd.indigostudios.spawnerwrenches.commands.GiveSpawnerCommand;
import ltd.indigostudios.spawnerwrenches.commands.GiveWrenchCommand;
import ltd.indigostudios.spawnerwrenches.commands.SpawnerCommand;
import ltd.indigostudios.spawnerwrenches.commands.SpawnerWrenchesCommand;
import ltd.indigostudios.spawnerwrenches.listeners.BlockPlaceListener;
import ltd.indigostudios.spawnerwrenches.listeners.PlayerInteractListener;
import ltd.indigostudios.spawnerwrenches.listeners.PrepareAnvilListener;
import ltd.indigostudios.spawnerwrenches.utils.YML;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    // TODO: Toggle for sounds/particles?

    public void onEnable() {
        instance = this;

        loadConfig(new YML());

        // Commands
        registerCommands(
                new SpawnerWrenchesCommand()
                        .setName("spawnerwrenches")
                        .setDescription("Base command for spawner wrenches")
                        .setPermission("spawnerwrenches.admin"),
                new SpawnerCommand()
                        .setName("spawner")
                        .setDescription("Change a spawners type")
                        .setPermission("spawnerwrenches.setspawner"),
                new GiveWrenchCommand()
                        .setName("givewrench")
                        .setDescription("Gives a player a spawner wrench")
                        .setPermission("spawnerwrenches.admin"),
                new GiveSpawnerCommand()
                        .setName("givespawner")
                        .setDescription("Gives a player a spawner")
                        .setPermission("spawnerwrenches.admin")
        );

        // Listeners
        registerListeners(
                new BlockPlaceListener(),
                new PrepareAnvilListener(),
                new PlayerInteractListener()
        );
    }

    private void registerCommands(BaseCommand... commands) {
        for (BaseCommand baseCommand : commands) {
            PluginCommand cmd = getCommand(baseCommand.getName());
            if (cmd != null) {
                cmd.setTabCompleter(baseCommand.getTabCompleter());
                cmd.setDescription(baseCommand.getDescription());
                cmd.setPermission(baseCommand.getPermission());
                cmd.setPermissionMessage(baseCommand.getPermissionMessage());
                cmd.setExecutor(baseCommand);
            }
        }
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    public YML loadConfig(YML yml) {
        yml.setup();
        Language.setConfiguration(getConfig());

        return yml;
    }

    public static Main getInstance() { return instance; }
}
