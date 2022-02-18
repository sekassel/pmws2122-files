package de.uniks.pmws2122.chat.client.util;

import kong.unirest.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class ResourceManager {
    private static Path configFilePath = Path.of("./config.json");

    static {
        // If the config file does not exist, create it and fill it with the default content with the "JsonUtil"
        if (!Files.exists(configFilePath)) {
            try {
                Files.createFile(configFilePath);
                Files.writeString(configFilePath, JsonUtil.createDefaultConfig());
            } catch (Exception e) {
                System.err.println("Can not create config file");
                e.printStackTrace();
            }
        }
    }

    public static JSONObject loadConfig() {
        // Try to load the content from the config file and parse it
        // If not possible return an empty json object
        try {
            return JsonUtil.parse(Files.readString(configFilePath)).getObject();
        } catch (Exception e) {
            System.err.println("Can not read from config file");
            return new JSONObject();
        }
    }

    public static void saveConfig(String config) {
        // Try to write the config string to the config file, if not possible print an error
        try {
            Files.writeString(configFilePath, config);
        } catch (Exception e) {
            System.err.println("Can not save config");
            e.printStackTrace();
        }
    }
}
