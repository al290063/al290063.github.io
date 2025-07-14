package com.aegis.shop;

import com.aegis.AegisPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;

/**
 * Simple interactive shop backed by items defined in config.yml.
 */
public class ShopManager implements Listener {
    private final AegisPlugin plugin;
    private final List<ShopEntry> entries = new ArrayList<>();

    public ShopManager(AegisPlugin plugin) {
        this.plugin = plugin;

        Map<String, Object> section = plugin.getConfig().getConfigurationSection("shop-items").getValues(false);
        for (Map.Entry<String, Object> e : section.entrySet()) {
            Material mat = Material.matchMaterial(e.getKey());
            if (mat != null) {
                double price = Double.parseDouble(e.getValue().toString());
                entries.add(new ShopEntry(mat, price));
            }
        }
    }

    public void openShop(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Aegis Shop");
        for (int i = 0; i < entries.size() && i < 27; i++) {
            ShopEntry entry = entries.get(i);
            ItemStack item = new ItemStack(entry.material());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Comprar " + entry.material().name());
            meta.setLore(List.of("Precio: " + entry.price()));
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getView().getTitle().equals("Aegis Shop")) return;
        event.setCancelled(true);

        int slot = event.getSlot();
        if (slot < 0 || slot >= entries.size()) return;
        ShopEntry entry = entries.get(slot);

        double balance = plugin.getEconomyManager().getBalance(player);
        if (balance < entry.price()) {
            player.sendMessage("Fondos insuficientes.");
            return;
        }
        plugin.getEconomyManager().withdraw(player, entry.price());
        player.getInventory().addItem(new ItemStack(entry.material()));
        player.sendMessage("Compraste " + entry.material().name() + " por " + entry.price());
    }
}
