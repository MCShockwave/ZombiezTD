package net.mcshockwave.ZTD.Commands;

import net.mcshockwave.MCS.SQLTable;
import net.mcshockwave.MCS.SQLTable.Rank;
import net.mcshockwave.ZTD.Zombiez;
import net.mcshockwave.ZTD.Games.Games;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZTDCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (SQLTable.hasRank(p.getName(), Rank.JR_MOD)) {
				if (args.length > 0) {
					if (args[0].equalsIgnoreCase("setspawn")) {
						Location l = p.getLocation();
						Zombiez.w.setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
						p.sendMessage(ChatColor.RED + "Spawn set.");
					}
					
					if (args[0].equalsIgnoreCase("cheat")) {
						if (Games.getGame(p) != null) {
							Games.getGame(p).addMoney(1000000);
						}
					}
				}
			}
		}
		return false;
	}

}
