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
        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            StringBuilder sb = new StringBuilder("Misiones 1-10:\n");
            for (int i = 1; i <= 10; i++) {
                sb.append(i).append(". ").append(missions.getMissionName(i)).append("\n");
            }
            player.sendMessage(sb.toString());
            return true;
        }

        if (args.length == 0) {
            missions.openMenu(player);
            return true;
        }

        player.sendMessage("Uso: /missions [list|claim <id>]");
        return true;
    }
}
