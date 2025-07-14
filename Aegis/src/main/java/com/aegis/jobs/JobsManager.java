package com.aegis.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Tracks player jobs and exposes a list of available jobs.
 */
public class JobsManager implements Listener {
    private final Map<UUID, String> playerJobs = new HashMap<>();
    private final List<String> availableJobs = new ArrayList<>();

    public JobsManager() {
        availableJobs.add("Miner");
        availableJobs.add("Farmer");
        availableJobs.add("Hunter");
    }

    public List<String> getAvailableJobs() {
        return availableJobs;
    }

    public void joinJob(Player player, String job) {
        if (availableJobs.contains(job)) {
            playerJobs.put(player.getUniqueId(), job);
        }
    }

    public String getJob(Player player) {
        return playerJobs.getOrDefault(player.getUniqueId(), "None");
    }

    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9, "Trabajos");
        for (int i = 0; i < availableJobs.size() && i < 9; i++) {
            String job = availableJobs.get(i);
            ItemStack item = new ItemStack(Material.BOOK);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(job);
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getView().getTitle().equals("Trabajos")) return;
        event.setCancelled(true);
        int slot = event.getSlot();
        if (slot >= 0 && slot < availableJobs.size()) {
            String job = availableJobs.get(slot);
            joinJob(player, job);
            player.sendMessage("Unido al trabajo " + job);
            player.closeInventory();
        }
    }
}
