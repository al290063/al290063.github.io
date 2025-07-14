package com.aegis.missions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
 * Manages 1000 simple missions for players to claim.
 */
public class MissionsManager implements Listener {
    private final Map<UUID, Set<Integer>> completed = new HashMap<>();
    private final List<String> missionNames = new ArrayList<>();

    public MissionsManager() {
        for (int i = 1; i <= 1000; i++) {
            missionNames.add("Misión #" + i);
        }
    }

    public String getMissionName(int id) {
        if (id < 1 || id > missionNames.size()) return "";
        return missionNames.get(id - 1);
    }

    public void claimMission(Player player, int id) {
        completed.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>()).add(id);
    }

    public boolean isCompleted(Player player, int id) {
        return completed.getOrDefault(player.getUniqueId(), Set.of()).contains(id);
    }

    public void openMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "Misiones");
        for (int i = 1; i <= 27 && i <= missionNames.size(); i++) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(missionNames.get(i - 1));
            if (isCompleted(player, i)) {
                meta.setLore(List.of("Completada"));
            }
            item.setItemMeta(meta);
            inv.setItem(i - 1, item);
        }
        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!event.getView().getTitle().equals("Misiones")) return;
        event.setCancelled(true);
        int slot = event.getSlot() + 1;
        if (slot >= 1 && slot <= missionNames.size()) {
            if (isCompleted(player, slot)) {
                player.sendMessage("Misión ya completada.");
            } else {
                claimMission(player, slot);
                player.sendMessage("Misión " + slot + " reclamada.");
            }
            openMenu(player);
        }
    }
}
