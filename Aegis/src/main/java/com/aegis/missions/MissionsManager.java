package com.aegis.missions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Manages 1000 simple missions for players to claim.
 */
public class MissionsManager {
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
}
