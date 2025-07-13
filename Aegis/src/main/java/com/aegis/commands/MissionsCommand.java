package com.aegis.commands;

import com.aegis.AegisPlugin;
import com.aegis.missions.MissionsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MissionsCommand implements CommandExecutor {
    private final MissionsManager missions;

    public MissionsCommand(AegisPlugin plugin) {
        this.missions = plugin.getMissionsManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Solo jugadores.");
            return true;
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("claim")) {
            try {
                int id = Integer.parseInt(args[1]);
                if (missions.isCompleted(player, id)) {
                    player.sendMessage("Misión ya completada.");
                } else {
                    missions.claimMission(player, id);
                    player.sendMessage("Misión " + id + " reclamada.");
                }
            } catch (NumberFormatException e) {
                player.sendMessage("ID inválido");
            }
            return true;
        }
        player.sendMessage("Uso: /missions claim <id>");
        return true;
    }
}
