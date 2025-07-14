package com.aegis.jobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Tracks player jobs and exposes a list of available jobs.
 */
public class JobsManager {
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
}
