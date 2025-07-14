package com.aegis;

import com.aegis.commands.EcoCommand;
import com.aegis.commands.JobsCommand;
import com.aegis.commands.MissionsCommand;
import com.aegis.commands.ShopCommand;
import com.aegis.economy.EconomyManager;
import com.aegis.jobs.JobsManager;
import com.aegis.missions.MissionsManager;
import com.aegis.shop.ShopManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;

public class AegisPlugin extends JavaPlugin {
    private static AegisPlugin instance;
    private EconomyManager economyManager;
    private JobsManager jobsManager;
    private MissionsManager missionsManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        economyManager = new EconomyManager(getConfig().getDouble("starting-balance", 0));
        jobsManager = new JobsManager();
        missionsManager = new MissionsManager();
        shopManager = new ShopManager(this);
        Bukkit.getPluginManager().registerEvents(shopManager, this);
        Bukkit.getPluginManager().registerEvents(economyManager, this);
        Bukkit.getPluginManager().registerEvents(jobsManager, this);
        Bukkit.getPluginManager().registerEvents(missionsManager, this);

        getCommand("eco").setExecutor(new EcoCommand(this));
        getCommand("jobs").setExecutor(new JobsCommand(this));
        getCommand("missions").setExecutor(new MissionsCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));

        getLogger().info("Aegis enabled: Sistema de comercio, trabajos y misiones");
    }

    @Override
    public void onDisable() {
        getLogger().info("Aegis deshabilitado");
    }

    public static AegisPlugin getInstance() {
        return instance;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public JobsManager getJobsManager() {
        return jobsManager;
    }

    public MissionsManager getMissionsManager() {
        return missionsManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }
}
