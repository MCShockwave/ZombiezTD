package net.mcshockwave.ZTD.Commands;

import net.mcshockwave.ZTD.Games.Games;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			Games g = Games.getGame(p);
			if (g != null && g.voting) {
				g.onVote(p);
			}
		}
		return false;
	}

}
