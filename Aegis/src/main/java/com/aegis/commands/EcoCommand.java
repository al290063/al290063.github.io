package com.aegis.commands;

import com.aegis.AegisPlugin;
import com.aegis.economy.EconomyManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {
    private final EconomyManager economy;

    public EcoCommand(AegisPlugin plugin) {
        this.economy = plugin.getEconomyManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo jugadores.");
            return true;
        }
        if (args.length == 0 || args[0].equalsIgnoreCase("balance")) {
            player.sendMessage("Balance: " + economy.getBalance(player));
            return true;
        }
        if (args[0].equalsIgnoreCase("deposit") && args.length == 2) {
            try {
                double amount = Double.parseDouble(args[1]);
                economy.deposit(player, amount);
                player.sendMessage("Deposited " + amount);
            } catch (NumberFormatException e) {
                player.sendMessage("Cantidad inválida");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("withdraw") && args.length == 2) {
            try {
                double amount = Double.parseDouble(args[1]);
                if (economy.withdraw(player, amount)) {
                    player.sendMessage("Withdrawn " + amount);
                } else {
                    player.sendMessage("Fondos insuficientes.");
                }
            } catch (NumberFormatException e) {
                player.sendMessage("Cantidad inválida");
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("pay") && args.length == 3) {
            Player target = player.getServer().getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("Jugador no encontrado.");
                return true;
            }
            try {
                double amount = Double.parseDouble(args[2]);
                if (economy.withdraw(player, amount)) {
                    economy.deposit(target, amount);
                    player.sendMessage("Pagaste " + amount + " a " + target.getName());
                    target.sendMessage(player.getName() + " te pagó " + amount);
                } else {
                    player.sendMessage("Fondos insuficientes.");
                }
            } catch (NumberFormatException e) {
                player.sendMessage("Cantidad inválida");
            }
            return true;
        }
        player.sendMessage("/eco [balance|deposit|withdraw|pay]");
        return true;
    }
}
