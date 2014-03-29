package net.mcshockwave.ZTD;

import java.util.ArrayList;
import java.util.List;

import net.mcshockwave.MCS.MCShockwave;
import net.mcshockwave.MCS.SQLTable;
import net.mcshockwave.ZTD.Games.Games;
import net.mcshockwave.ZTD.Games.Tower;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LevelManager {
	
	public static boolean isInTable(Player p) {
		return SQLTable.Zombiez.has("Username", p.getName());
	}
	
	public static void addToTable(Player p) {
		SQLTable.Zombiez.add("Username", p.getName());
	}

	public static int getLevel(Player p) {
		return SQLTable.Zombiez.getInt("Username", p.getName(), "Level");
	}

	public static void setLevel(Player p, int l) {
		SQLTable.Zombiez.set("Level", l + "", "Username", p.getName());
		MCShockwave.updateTab(p);
	}
	
	public static int getCredits(Player p) {
		return SQLTable.Zombiez.getInt("Username", p.getName(), "Credits");
	}
	
	public static void addCredits(Player p, int c) {
		int cr = getCredits(p);
		cr += c;
		setCredits(p, cr);
	}

	public static void setCredits(Player p, int l) {
		SQLTable.Zombiez.set("Credits", l + "", "Username", p.getName());
		MCShockwave.updateTab(p);
	}

	public static int getXP(Player p) {
		return SQLTable.Zombiez.getInt("Username", p.getName(), "XP");
	}
	
	public static void upgXP(Player p, int x) {
		boolean ru = LevelManager.addXP(p, x);
		if (ru) {
			int lev = LevelManager.getLevel(p);
			List<Tower> nt = LevelManager.getUnlockedTowers(lev);

			p.sendMessage(ChatColor.DARK_GRAY + "§l=======================");
			p.sendMessage(ChatColor.GOLD + "You Ranked Up! New Level: " + lev);
			if (nt.size() > 0) {
				for (Tower t : nt) {
					p.sendMessage(ChatColor.GRAY + "New Tower: " + t.name().replace('_', ' '));
				}
			}
			if (lev == 10) {
				p.sendMessage(ChatColor.YELLOW + "Unlocked MEDIUM difficulty!");
			}
			if (lev == 25) {
				p.sendMessage(ChatColor.RED + "Unlocked HARD difficulty!");
			}
			p.sendMessage(ChatColor.DARK_GRAY + "§l=======================");

			p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
		}

		MCShockwave.updateTab(p);
	}

	public static boolean addXP(Player p, int x) {
		boolean ret = false;
		int xp = getXP(p);
		int lv = getLevel(p);
		xp += x;
		if (xp >= getXPForLevel(lv)) {
			xp -= getXPForLevel(lv);
			setLevel(p, lv + 1);
			ret = true;
			int crH = SQLTable.Zombiez.getInt("Username", p.getName(), "Credits");
			crH += 1;
			SQLTable.Zombiez.set("Credits", crH + "", "Username", p.getName());
			MCShockwave.updateTab(p);
		}
		setXP(p, xp);
		return ret;
	}

	public static void setXP(Player p, int x) {
		SQLTable.Zombiez.set("XP", x + "", "Username", p.getName());
	}
	
	public static int getXPForLevel(int lev) {
		return (lev * 75) + 200;
	}
	
	public static boolean canPlayDif(int dif, int lev) {
		if (dif == 1) {
			return true;
		} else if (dif == 2 && lev >= 10) {
			return true;
		} else if (dif == 3 && lev >= 25) {
			return true;
		}
		return false;
	}
	
	public static boolean canPlayGame(Games g, int lev) {
		return canPlayDif(g.getDifficulty(), lev);
	}
	
	public static boolean canUseTower(Tower t, int lev) {
		return t.levReq <= lev;
	}
	
	public static List<Tower> getUnlockedTowers(int lev) {
		ArrayList<Tower> ret = new ArrayList<Tower>();
		
		for (Tower t : Tower.values()) {
			if (t.levReq == lev) {
				ret.add(t);
			}
		}
		
		return ret;
	}

}
