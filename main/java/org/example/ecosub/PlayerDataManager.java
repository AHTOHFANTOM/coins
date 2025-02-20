package org.example.ecosub;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {
    private static final String DATA_FOLDER = "mods/ecosub/playerdata/";
    private static final Map<String, PlayerData> loadedPlayers = new HashMap<>();

    // Сохранение данных игрока в файл
    public static void savePlayerData(PlayerData playerData) {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, playerData.getUsername() + ".json");
        try {
            String jsonContent = JsonFileHandler.serializePlayerData(playerData);
            JsonFileHandler.writeToFile(file, jsonContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerData loadPlayerData(String username) {
        // Проверяем, не загружены ли уже данные
        if (loadedPlayers.containsKey(username)) {
            return loadedPlayers.get(username);
        }

        File file = new File(DATA_FOLDER + username + ".json");
        System.out.println("Путь к файлу данных игрока: " + file.getAbsolutePath());

        if (file.exists()) {
            try {
                String jsonContent = JsonFileHandler.readFile(file);
                PlayerData data = JsonFileHandler.deserializePlayerData(jsonContent);
                loadedPlayers.put(username, data); // Кэшируем загруженные данные
                return data;
            } catch (IOException e) {
                System.out.println("Ошибка чтения файла: " + e.getMessage());
            }
        }

        System.out.println("Файл не существует, создаем новые данные для игрока: " + username);
        PlayerData newData = new PlayerData(username);
        loadedPlayers.put(username, newData); // Кэшируем новые данные
        return newData;
    }


}