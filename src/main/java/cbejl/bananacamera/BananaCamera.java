package cbejl.bananacamera;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class BananaCamera extends JavaPlugin {

    private static BananaCamera instance;
    private static File configf, locationsf, messagesf;
    public static FileConfiguration config, locations, messages;
    private static List<Map<?, ?>> locationsList;

    @Override
    public void onEnable() {
        instance = this;
        createFiles();
        getCommand("bcamera").setExecutor(new CameraCommand());
        getCommand("bcamera").setTabCompleter(new CameraCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public void createFiles() {
        locationsf = new File(getDataFolder(), "locations.yml");
        configf = new File(getDataFolder(), "config.yml");
        messagesf = new File(getDataFolder(), "messages.yml");

        if (!locationsf.exists()) {
            locationsf.getParentFile().mkdirs();
            saveResource("locations.yml", false);
        }
        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!messagesf.exists()) {
            messagesf.getParentFile().mkdirs();
            saveResource("messages.yml", false);
        }

        config = new YamlConfiguration();
        locations = new YamlConfiguration();
        messages = new YamlConfiguration();

        try {
            config.load(configf);
            locations.load(locationsf);
            messages.load(messagesf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        locationsList = locations.getMapList("locations");
    }

    public static FileConfiguration getMessages() {
        return messages;
    }

    public static BananaCamera getInstance() {
        return instance;
    }

    public static List<Map<?,?>> getLocationsList() {
        return locationsList;
    }
}
