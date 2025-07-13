package com.aegis.commands;

import com.aegis.AegisPlugin;
import com.aegis.jobs.JobsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommand implements CommandExecutor {
    private final JobsManager jobs;

    public JobsCommand(AegisPlugin plugin) {
        this.jobs = plugin.getJobsManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo jugadores.");
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            jobs.joinJob(player, args[1]);
            player.sendMessage("Unido al trabajo " + args[1]);
            return true;
        }
        player.sendMessage("Tu trabajo actual: " + jobs.getJob(player));
        return true;
    }
}
