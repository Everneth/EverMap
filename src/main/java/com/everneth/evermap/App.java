package com.everneth.evermap;

import java.io.File;

import com.everneth.evermap.commands.Commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import co.aikar.idb.DB;
import co.aikar.idb.Database;
import co.aikar.idb.DatabaseOptions;
import co.aikar.idb.PooledDatabaseOptions;

/**
 * Class: App, Author: Lilahamstern (@Lilahamstern) Purpose: Plugins main class,
 *
 */

public class App extends JavaPlugin {
    private static App plugin;
    private Commands commands = new Commands();
    FileConfiguration config = getConfig();
    String configPath = getDataFolder() + System.getProperty("file.separator") + "config.yml";
    File configFile = new File(configPath);

    @Override
    public void onEnable() {
        plugin = this;

        if (!configFile.exists()) {
            this.saveDefaultConfig();
        }

        databaseInit();

        commands.init(plugin);
        getLogger().info("EverMap is ready!");
    }

    private void databaseInit() {
        try {
            DatabaseOptions dboptions = DatabaseOptions.builder().mysql(config.getString("dbuser"),
                    config.getString("dbpass"), config.getString("dbname"), config.getString("dbhost")).build();
            Database db = PooledDatabaseOptions.builder().options(dboptions).createHikariDatabase();
            DB.setGlobalDatabase(db);
        } catch (Exception e) {
            getLogger().warning(e.getMessage());
            // getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    @Override
    public void onDisable() {
        DB.close();
        getLogger().info("EverMap is stopped!");
    }

    public static App getPlugin() {
        return plugin;
    }

}
