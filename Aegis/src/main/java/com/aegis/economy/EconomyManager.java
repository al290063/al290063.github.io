package com.aegis.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Basic in-memory economy with configurable starting balance.
 */
public class EconomyManager {
    private final Map<UUID, Double> balances = new HashMap<>();
    private final double startingBalance;

    public EconomyManager(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public double getBalance(Player player) {
        return balances.getOrDefault(player.getUniqueId(), startingBalance);
    }

    public void deposit(Player player, double amount) {
        balances.put(player.getUniqueId(), getBalance(player) + amount);
    }

    public boolean withdraw(Player player, double amount) {
        double balance = getBalance(player);
        if (balance < amount) return false;
        balances.put(player.getUniqueId(), balance - amount);
        return true;
    }
}
