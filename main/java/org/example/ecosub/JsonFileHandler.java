package org.example.ecosub;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonFileHandler {
    private static final Map<String, Long> lastModifiedMap = new HashMap<>();

    // Чтение данных из файла в формате JSON
    public static String readFile(File file) throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }
    }

    // Запись данных в файл в формате JSON
    public static void writeToFile(File file, String jsonContent) throws IOException {
        // Создаем родительские директории, если их нет
        file.getParentFile().mkdirs();

        // Записываем данные в файл с кодировкой UTF-8
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(jsonContent);
        }

    }

    // Проверка, был ли файл изменен
    public static boolean isModified(File file) {
        long lastModified = file.lastModified();
        String path = file.getAbsolutePath();

        if (!lastModifiedMap.containsKey(path) || lastModifiedMap.get(path) != lastModified) {
            lastModifiedMap.put(path, lastModified);
            return true;
        }
        return false;
    }

    // Сериализация объекта PlayerData в JSON
    public static String serializePlayerData(PlayerData playerData) {
        StringBuilder json = new StringBuilder("{");
        json.append("\"username\":\"").append(playerData.getUsername()).append("\",");
        json.append("\"balance\":").append(playerData.getBalance()).append(",");
        json.append("\"transactions\":[");
        for (int i = 0; i < playerData.getTransactions().size(); i++) {
            if (i > 0) {
                json.append(",");
            }
            json.append(serializeTransaction(playerData.getTransactions().get(i)));
        }
        json.append("]}");
        return json.toString();
    }

    public static PlayerData deserializePlayerData(String jsonContent) {
        Map<String, String> data = deserialize(jsonContent);
        String username = data.get("username");
        int balance = Integer.parseInt(data.get("balance"));
        PlayerData playerData = new PlayerData(username);
        playerData.setBalance(balance);

        // Исправленный код для транзакций
        String transactionsStr = jsonContent.substring(jsonContent.indexOf("[") + 1, jsonContent.lastIndexOf("]"));
        if (!transactionsStr.isEmpty()) {
            String[] transactions = transactionsStr.split("\\s*},\\s*\\{"); // Учитываем пробелы
            for (String transactionStr : transactions) {
                transactionStr = transactionStr.replaceAll("[{}]", "").trim();
                if (!transactionStr.isEmpty()) {
                    playerData.addTransaction(deserializeTransaction("{" + transactionStr + "}"));
                }
            }
        }
        return playerData;
    }

    // Сериализация объекта Transaction в JSON
    private static String serializeTransaction(Transaction transaction) {
        return String.format(
                "{\"type\":\"%s\",\"amount\":%d,\"timestamp\":%d}",
                transaction.getType(), transaction.getAmount(), transaction.getTimestamp()
        );
    }

    // Десериализация JSON в объект Transaction
    private static Transaction deserializeTransaction(String jsonContent) {
        Map<String, String> data = deserialize(jsonContent);
        String type = data.get("type");
        int amount = Integer.parseInt(data.get("amount"));
        long timestamp = Long.parseLong(data.get("timestamp"));
        return new Transaction(type, amount, timestamp);
    }

    private static Map<String, String> deserialize(String jsonContent) {
        Map<String, String> result = new HashMap<>();
        // Удаляем все пробелы и переносы строк
        String cleanJson = jsonContent.replaceAll("\\s+", "");
        String[] entries = cleanJson.split("[{},]");

        for (String entry : entries) {
            if (entry.isEmpty()) continue;
            String[] keyValue = entry.split(":", 2); // Разделяем только по первому ":"
            if (keyValue.length == 2) {
                result.put(keyValue[0].replaceAll("\"", ""), keyValue[1].replaceAll("\"", ""));
            }
        }
        return result;
    }
}