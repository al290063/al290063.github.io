package com.aegis.missions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public class MissionsManager {
    private final Map<UUID, Set<Integer>> completed = new HashMap<>();

    public void claimMission(Player player, int id) {
        completed.computeIfAbsent(player.getUniqueId(), k -> new HashSet<>()).add(id);
    }

    public boolean isCompleted(Player player, int id) {
        return completed.getOrDefault(player.getUniqueId(), Set.of()).contains(id);
    }
}
