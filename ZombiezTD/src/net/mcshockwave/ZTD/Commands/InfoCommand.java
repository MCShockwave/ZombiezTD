package net.mcshockwave.ZTD.Commands;

import net.mcshockwave.MCS.SQLTable;
import net.mcshockwave.ZTD.LevelManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			p.sendMessage(ChatColor.DARK_GRAY + "§l=======================");
			p.sendMessage(ChatColor.GOLD + "Info for " + p.getName() + ":");
			p.sendMessage(ChatColor.GRAY + "Level: " + LevelManager.getLevel(p));
			p.sendMessage(ChatColor.GOLD + "XP: " + LevelManager.getXP(p) + " / "
					+ LevelManager.getXPForLevel(LevelManager.getLevel(p)));
			p.sendMessage(ChatColor.GRAY + "Credits: " + SQLTable.Zombiez.get("Username", p.getName(), "Credits"));
			p.sendMessage(ChatColor.DARK_GRAY + "§l=======================");
		}
		return false;
	}

}
