package net.mcshockwave.ZTD;

import net.mcshockwave.MCS.MCShockwave;
import net.mcshockwave.ZTD.Commands.InfoCommand;
import net.mcshockwave.ZTD.Commands.LeaveCommand;
import net.mcshockwave.ZTD.Commands.StartCommand;
import net.mcshockwave.ZTD.Commands.ZTDCommand;
import net.mcshockwave.ZTD.Games.Games;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;

public class Zombiez extends JavaPlugin {

	public static World		w;

	public static Zombiez	instance;

	public void onEnable() {
		w = Bukkit.getWorld("ZTD");

		MCShockwave.setMaxPlayers(50);

		instance = this;

		Bukkit.getPluginManager().registerEvents(new DefaultListener(this), this);

		getCommand("zom").setExecutor(new ZTDCommand());
		getCommand("leave").setExecutor(new LeaveCommand());
		getCommand("start").setExecutor(new StartCommand());
		getCommand("info").setExecutor(new InfoCommand());

		MCShockwave.chatEnabled = false;
		MCShockwave.mesJoin = "";
		MCShockwave.mesKick = "";
		MCShockwave.mesLeave = "";

		// for (Player p : Bukkit.getOnlinePlayers()) {
		// p.teleport(w.getSpawnLocation());
		// }

		for (Games ga : Games.values()) {
			ga.updateSign();
		}
	}

	public void onDisable() {
		for (Games ga : Games.values()) {
			ga.stop();
		}
		for (Entity e : w.getEntities()) {
			if (!e.getLocation().getChunk().isLoaded()) {
				e.getLocation().getChunk().load();
			}
			e.remove();
		}
	}

	public static boolean utilSameLoc(Location l, Location l2) {
		boolean sl = (l.getBlockX() == l2.getBlockX() && l.getBlockY() == l2.getBlockY() && l.getBlockZ() == l2
				.getBlockZ());
		return sl;
	}

}
