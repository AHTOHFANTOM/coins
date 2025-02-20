package org.example.ecosub;

public class TransactionManager {
    // Метод для пополнения баланса
    public static void deposit(PlayerData playerData, int amount) {
        playerData.setBalance(playerData.getBalance() + amount);
        playerData.addTransaction(new Transaction("deposit", amount, System.currentTimeMillis())); // Добавляем временную метку
        PlayerDataManager.savePlayerData(playerData);
        System.out.println("Депозит успешен: +" + amount + " монет. Текущий баланс: " + playerData.getBalance());
    }

    // Метод для снятия средств
    public static void withdraw(PlayerData playerData, int amount) {
        if (playerData.getBalance() >= amount) {
            playerData.setBalance(playerData.getBalance() - amount);
            playerData.addTransaction(new Transaction("withdraw", amount, System.currentTimeMillis())); // Добавляем временную метку
            PlayerDataManager.savePlayerData(playerData);
            System.out.println("Снятие успешно: -" + amount + " монет. Текущий баланс: " + playerData.getBalance());
        } else {
            System.out.println("Недостаточно средств! Текущий баланс: " + playerData.getBalance());
        }
    }
}