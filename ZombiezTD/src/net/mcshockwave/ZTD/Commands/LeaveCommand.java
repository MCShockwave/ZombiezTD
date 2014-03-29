package net.mcshockwave.ZTD.Commands;

import net.mcshockwave.ZTD.Zombiez;
import net.mcshockwave.ZTD.Games.Games;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (Games.getGame(p) != null) {
				Games ga = Games.getGame(p);
				leaveGame(p);
				ga.sendToAll("Leave", p.getName() + " left the game");
				ga.onLeave(p);
				p.sendMessage(ChatColor.RED + "You left the game!");
			} else {
				p.sendMessage(ChatColor.RED + "You are not in a game!");
			}
		}
		return false;
	}
	
	public static void leaveGame(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		p.getInventory().clear();
		p.teleport(Zombiez.w.getSpawnLocation());
		p.setAllowFlight(false);
		for (Games g : Games.values()) {
			g.players.remove(p);
		}
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			if (p2 == p) continue;
			Games ga2 = Games.getGame(p2);
			if (ga2 != null) {
				p.hidePlayer(p2);
				p2.hidePlayer(p);
			} else {
				p.showPlayer(p2);
				p2.showPlayer(p);
			}
		}
	}

}
