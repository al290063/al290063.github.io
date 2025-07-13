package com.aegis.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

public class JobsManager {
    private final Map<UUID, String> playerJobs = new HashMap<>();

    public void joinJob(Player player, String job) {
        playerJobs.put(player.getUniqueId(), job);
    }

    public String getJob(Player player) {
        return playerJobs.getOrDefault(player.getUniqueId(), "None");
    }
}
