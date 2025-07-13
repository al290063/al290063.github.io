package com.aegis.commands;

import com.aegis.AegisPlugin;
import com.aegis.shop.ShopManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {
    private final ShopManager shop;

    public ShopCommand(AegisPlugin plugin) {
        this.shop = plugin.getShopManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo jugadores.");
            return true;
        }
        shop.openShop(player);
        return true;
    }
}
