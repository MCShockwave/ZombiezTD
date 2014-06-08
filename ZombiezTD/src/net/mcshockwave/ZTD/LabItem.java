package net.mcshockwave.ZTD;

import net.mcshockwave.MCS.SQLTable;

import org.bukkit.entity.Player;

public enum LabItem {

	Popping_Power(
		new int[] { 2, 3, 4, 7, 8, 10, 16, 28 },
		new double[] { 2, 4, 6, 8, 10, 12, 15, 20 },
		"PopPow",
		"Increases damage by %p%"),
	Attack_Speed(
		new int[] { 2, 3, 4, 5, 10, 12, 18, 25 },
		new double[] { 2, 3, 4, 5, 7, 9, 12, 15 },
		"AtRate",
		"Increases attack rate by %p%"),
	Starting_Cash(
		new int[] { 1, 2, 4, 6, 10, 12, 20, 25 },
		new double[] { 10, 20, 40, 60, 90, 120, 160, 200 },
		"StartCash",
		"Start with $%p more cash"),
	Starting_Lives(
		new int[] { 1, 2, 4, 6, 10, 12, 20, 25 },
		new double[] { 10, 20, 40, 60, 90, 120, 160, 200 },
		"StartLives",
		"Increases lives by %p"),
	Sell_Price(
		new int[] { 2, 2, 5, 6, 8, 12, 20, 25 },
		new double[] { 82, 83, 85, 87, 89, 91, 93, 95 },
		"SellPrice",
		"Get %p% sales when selling towers",
		80),
	Big_Bloon_Sabotage(
		new int[] { 2, 3, 4, 6, 8, 10, 12, 15, 20 },
		new double[] { 2, 4, 6, 8, 10, 12, 15, 20 },
		"BBSabo",
		"MOAB+ zombies enter the game with %p% less health"),
	Hotter_Cooldown(
		new int[] { 1, 2, 3, 5, 7, 10, 17, 35 },
		new double[] { 2, 4, 6, 8, 10, 12, 15, 20 },
		"HotCool",
		"Abilities charge %p% faster");

	public int[]	costs;
	public double[]	per;
	public String	sql, desc;
	public int		defPer	= 0;

	LabItem(int[] costs, double[] per, String sql, String desc) {
		this.costs = costs;
		this.per = per;
		this.sql = sql;
		this.desc = desc;
	}

	LabItem(int[] costs, double[] per, String sql, String desc, int defPer) {
		this.costs = costs;
		this.per = per;
		this.sql = sql;
		this.desc = desc;
		this.defPer = defPer;
	}

	public boolean canGetNext(Player p) {
		int lvlat = SQLTable.Zombiez.getInt("Username", p.getName(), sql);
		int cost = getUpgradeCost(lvlat + 1);
		return LevelManager.getCredits(p) >= cost;
	}

	public boolean hasUpgrade(Player p, int lvl) {
		int lvlhas = SQLTable.Zombiez.getInt("Username", p.getName(), sql);
		return lvlhas >= lvl;
	}

	public void setUpgrade(Player p, int lvl) {
		SQLTable.Zombiez.set(sql, lvl + "", "Username", p.getName());
	}

	public int getUpgradeCost(int lvl) {
		if (costs.length > lvl - 1) {
			return costs[lvl - 1];
		}
		return -1;
	}

	public int getUpgradeCost(Player p) {
		return getUpgradeCost(SQLTable.Zombiez.getInt("Username", p.getName(), sql) + 1);
	}

	public boolean isAnotherLevel(Player p) {
		int lvl = SQLTable.Zombiez.getInt("Username", p.getName(), sql);
		return costs.length > lvl;
	}

	public double getPercent(Player p) {
		int lvl = SQLTable.Zombiez.getInt("Username", p.getName(), sql);
		return getPercent(lvl);
	}

	public double getPercent(int lvl) {
		if (lvl == 0) {
			return defPer;
		} else if (lvl >= per.length) {
			return per[per.length - 1];
		} else {
			return per[lvl - 1];
		}
	}

	public double calc(double ent, Player p) {
		double per = getPercent(p);

		return ent * per;
	}

}
