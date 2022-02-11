package de.uniks.pmws2122.icp.util;

import kong.unirest.json.JSONObject;

import java.nio.file.Path;

public class ResourceManager {
    private static Path configFilePath = Path.of("./config.json");

    static {
        // TODO: If the config file does not exist, create it and fill it with the default content with the "JsonUtil"
    }

    public static JSONObject loadConfig() {
        // TODO: Try to load the content from the config file and parse it
        // TODO: If not possible return an empty json object
    }

    public static void saveConfig(String config) {
        // TODO: Try to write the config string to the config file, if not possible print an error
    }
}
