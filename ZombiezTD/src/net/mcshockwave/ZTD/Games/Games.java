package net.mcshockwave.ZTD.Games;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.mcshockwave.MCS.Utils.ItemMetaUtils;
import net.mcshockwave.ZTD.LabItem;
import net.mcshockwave.ZTD.LevelManager;
import net.mcshockwave.ZTD.Zombiez;
import net.mcshockwave.ZTD.Commands.LeaveCommand;
import net.mcshockwave.ZTD.Games.Tower.Upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

public enum Games {
	// Place, lobby, spawn, villager, zombie, signs...

	// EASY
	Desert(
		Material.SANDSTONE,
		new Location(Zombiez.w, 292, 113, -293),
		new Location(Zombiez.w, 283, 110, -297),
		new Location(Zombiez.w, 280, 113, -292),
		new Location(Zombiez.w, 290, 110, -293),
		new Location(Zombiez.w, -25, 102, 7)),
	Grasslands(
		Material.GRASS,
		new Location(Zombiez.w, 279, 130, -37),
		new Location(Zombiez.w, 275, 122, -30),
		new Location(Zombiez.w, 260, 118, 20),
		new Location(Zombiez.w, 279, 119, -37),
		new Location(Zombiez.w, -26, 102, 7)),
	Ice(
		Material.SNOW_BLOCK,
		new Location(Zombiez.w, 291, 117, 277),
		new Location(Zombiez.w, 295, 112, 283),
		new Location(Zombiez.w, 291, 106, 280),
		new Location(Zombiez.w, 291, 110, 279),
		new Location(Zombiez.w, -27, 102, 7)),
	Nether(
		Material.NETHERRACK,
		new Location(Zombiez.w, -240, 127, -223),
		new Location(Zombiez.w, -232, 117, -228),
		new Location(Zombiez.w, -204, 117, -228),
		new Location(Zombiez.w, -241, 114, -222),
		new Location(Zombiez.w, -28, 102, 7)),
	End(
		Material.OBSIDIAN,
		new Location(Zombiez.w, 15, 137, 287),
		new Location(Zombiez.w, 11, 125, 295),
		new Location(Zombiez.w, 44, 122, 295),
		new Location(Zombiez.w, 15, 119, 287),
		new Location(Zombiez.w, -29, 102, 7)),

	// MEDIUM
	Lab(
		Material.QUARTZ_BLOCK,
		new Location(Zombiez.w, 35, 112, -200, 180, 0),
		new Location(Zombiez.w, 35, 109, -218),
		new Location(Zombiez.w, 44, 107, -225),
		new Location(Zombiez.w, 35, 107, -202),
		new Location(Zombiez.w, -32, 102, 2)),
	Jail(
		Material.COAL_BLOCK,
		new Location(Zombiez.w, -164.5, 109, 17.5),
		new Location(Zombiez.w, -185, 112, 18),
		new Location(Zombiez.w, -222, 109, 9),
		new Location(Zombiez.w, -156, 109, 22),
		new Location(Zombiez.w, -32, 102, 1)),
	Library(
		Material.WOOD,
		new Location(Zombiez.w, -186, 110, 275, 180, 0),
		new Location(Zombiez.w, -200, 110, 267),
		new Location(Zombiez.w, -248, 105, 271),
		new Location(Zombiez.w, -182, 107, 271),
		new Location(Zombiez.w, -32, 102, 0)),
	Carnival(
		Material.SMOOTH_BRICK,
		new Location(Zombiez.w, 519.5, 111, 527.5),
		new Location(Zombiez.w, 508, 105, 522),
		new Location(Zombiez.w, 578, 103, 528),
		new Location(Zombiez.w, 503, 102, 526),
		new Location(Zombiez.w, -32, 102, -1)),
	Factory(
		Material.STONE,
		new Location(Zombiez.w, 534, 108, -450),
		new Location(Zombiez.w, 531, 116, -467),
		new Location(Zombiez.w, 510, 104, -451),
		new Location(Zombiez.w, 534, 104, -450),
		new Location(Zombiez.w, -32, 102, -2)),

	// HARD
	Apocalypse(
		Material.STONE,
		new Location(Zombiez.w, -486, 97, -491),
		new Location(Zombiez.w, -531, 92, -482),
		new Location(Zombiez.w, -509, 89, -455),
		new Location(Zombiez.w, -525, 90, -449),
		new Location(Zombiez.w, -29, 102, -7)),
	// Carrier(
	// Material.SMOOTH_BRICK,
	// new Location(Zombiez.w, 519, 111, 527),
	// new Location(Zombiez.w, 508, 105, 522),
	// new Location(Zombiez.w, 578, 103, 528),
	// new Location(Zombiez.w, 503, 102, 526),
	// new Location(Zombiez.w, -28, 102, -7)),
	Crossroads(
		Material.GRASS,
		new Location(Zombiez.w, 498, 97, 7),
		new Location(Zombiez.w, 498, 91, 3),
		new Location(Zombiez.w, 520, 89, 7),
		new Location(Zombiez.w, 478, 89, 7),
		new Location(Zombiez.w, -27, 102, -7)),
	Kino(
		Material.HARD_CLAY,
		new Location(Zombiez.w, -1, 90, 520),
		new Location(Zombiez.w, -3, 86, 527),
		new Location(Zombiez.w, -10, 83, 471),
		new Location(Zombiez.w, -1, 84, 538),
		new Location(Zombiez.w, -26, 102, -7)),
	Mushroom(
		Material.MYCEL,
		new Location(Zombiez.w, -500, 91, 0),
		new Location(Zombiez.w, -503, 81, 12),
		new Location(Zombiez.w, -508, 80, -5),
		new Location(Zombiez.w, -502, 78, 30),
		new Location(Zombiez.w, -25, 102, -7));

	public List<Player>							players, voted = new ArrayList<Player>();
	public Location								spawn, start, villager, zombie;
	public boolean								started, starting, voting;
	public int									wave, votes;
	public Location[]							signs;

	public List<Zombie>							zombies;
	public Villager								v;
	public Objective[]							o				= new Objective[4];

	public HashMap<LivingEntity, Location>		towerLoc;
	public HashMap<LivingEntity, Tower>			towers;

	public HashMap<LivingEntity, Integer>		upg1, upg2;

	public HashMap<Player, Integer>				cash			= new HashMap<Player, Integer>();

	public static HashMap<Projectile, Integer>	dmg				= new HashMap<Projectile, Integer>();

	public HashMap<LivingEntity, Player>		towOwn			= new HashMap<LivingEntity, Player>();

	public Material								place;

	public BukkitTask							wtas			= null, wtas2 = null;

	public Random								rand			= new Random();

	public long									lastSaboTime	= 0;

	private Games(Material place, Location lob, Location spawn, Location villager, Location zombie, Location... signs) {
		this.players = new ArrayList<Player>();
		this.zombies = new ArrayList<Zombie>();
		this.upg1 = new HashMap<LivingEntity, Integer>();
		this.upg2 = new HashMap<LivingEntity, Integer>();
		this.towerLoc = new HashMap<LivingEntity, Location>();
		this.towers = new HashMap<LivingEntity, Tower>();
		this.spawn = lob.add(0.5, 0, 0.5);
		this.started = false;
		this.starting = false;
		this.wave = 0;
		this.start = spawn.add(0.5, 0, 0.5);
		this.signs = signs;
		this.villager = villager.add(0.5, 0, 0.5);
		this.zombie = zombie.add(0.5, 0, 0.5);
		this.place = place;
	}

	public static Games getGame(Player p) {
		for (Games g : Games.values()) {
			if (g.players.contains(p)) {
				return g;
			}
		}
		return null;
	}

	public void sendToAll(String pre, String mes) {
		for (Player p : players) {
			p.sendMessage(ChatColor.GRAY + pre + ChatColor.BLUE + " > " + ChatColor.WHITE + mes);
		}
	}

	public void updateSign() {
		String[] lines = { "", "", "" };
		if (started) {
			lines[0] = ChatColor.RED + "Started";
		} else {
			lines[0] = ChatColor.BLUE + "Click to";
			lines[1] = ChatColor.BLUE + "join game";
			lines[2] = ChatColor.DARK_GRAY + "Players: " + players.size();
		}

		for (Location l : signs) {
			Sign s = (Sign) l.getBlock().getState();
			s.setLine(1, lines[0]);
			s.setLine(2, lines[1]);
			s.setLine(3, lines[2]);
			s.update();
		}
	}

	int	minPlayers	= 1;

	public void onJoin(Player p) {
		if (players.size() >= minPlayers && !starting && !started) {
			countdown();
			sendToAll("Game", "Player joined: Starting...");
		}
		updateSign();
	}

	public void onLeave(Player p) {
		if (players.size() < minPlayers && starting && !started) {
			cancelStart();
			sendToAll("Game", "Player left: Cancelling...");
		}
		if (started && players.size() < 1) {
			stop();
		}
		updateSign();
	}

	private List<BukkitTask>	tasks	= new ArrayList<BukkitTask>();

	public void countdown() {
		starting = true;
		int maxtime = 90;
		int[] times = { 90, 60, 30, 15, 10, 5, 4, 3, 2, 1 };
		for (final int i : times) {
			tasks.add(Bukkit.getScheduler().runTaskLater(Zombiez.instance, new Runnable() {
				public void run() {
					sendToAll("Game", "Starting in " + i + " seconds...");
				}
			}, (maxtime - i) * 20));
		}
		tasks.add(Bukkit.getScheduler().runTaskLater(Zombiez.instance, new Runnable() {
			public void run() {
				start();
				starting = false;
			}
		}, maxtime * 20));
	}

	public void cancelStart() {
		for (BukkitTask bt : tasks) {
			bt.cancel();
		}
		starting = false;
	}

	BukkitTask							vil, reg;
	HashMap<LivingEntity, BukkitTask>	tow	= new HashMap<LivingEntity, BukkitTask>();

	public void start() {
		final Score[] sc = new Score[4];

		for (int x = 0; x < players.size(); x++) {
			Player p = players.get(x);
			Scoreboard s = Bukkit.getScoreboardManager().getNewScoreboard();
			o[x] = s.registerNewObjective("Stats", "dummy");
			o[x].setDisplaySlot(DisplaySlot.SIDEBAR);
			o[x].setDisplayName(ChatColor.GREEN + "§lStats");
			sc[x] = o[x].getScore(Bukkit.getOfflinePlayer(ChatColor.GOLD + "Health"));

			p.teleport(start);
			p.setScoreboard(s);
			p.setAllowFlight(true);
			p.getInventory()
					.setItem(
							8,
							ItemMetaUtils.setItemName(new ItemStack(Material.PAPER), ChatColor.AQUA
									+ "Click to Vote to Start"));

			cash.put(p, getStartingCash(p));
			p.getScoreboard().getObjective(DisplaySlot.SIDEBAR)
					.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Cash")).setScore(getStartingCash(p));
		}

		sendToAll("Game", "Game started!");
		v = (Villager) Zombiez.w.spawnEntity(villager, EntityType.VILLAGER);
		v.setMaxHealth(100);
		v.setHealth(100);
		vil = Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				v.teleport(villager);
				for (int x = 0; x < 4; x++) {
					if (sc[x] == null)
						continue;
					sc[x].setScore((int) Math.ceil(v.getHealth()));
				}
				if (v.getHealth() < 1) {
					sendToAll("Lose", "You lost the game!");
					stop();
				}

				for (LivingEntity le : towerLoc.keySet()) {
					if (!Zombiez.utilSameLoc(towerLoc.get(le), le.getLocation())) {
						le.teleport(towerLoc.get(le));
					}
					if (le.getEquipment().getHelmet() != null) {
						ItemStack it = le.getEquipment().getHelmet();
						it.setDurability((short) 0);
						le.getEquipment().setHelmet(it);
					}
				}

				Object[] zom = zombies.toArray().clone();
				for (Object o : zom) {
					Zombie z = (Zombie) o;
					if (z.isDead() || !z.isValid()) {
						zombies.remove(z);
					}
				}
			}
		}, 0, 5);

		sendToAll("Vote", "Type /start to vote to start the next wave");
		voting = true;

		reg = Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				for (Zombie z : zombies) {
					if (Type.REGEN.isType(z)) {
						if (z.getHealth() <= z.getMaxHealth() - 1) {
							z.setHealth(z.getHealth() + 1);
							HealthColors.update(z);
						}
					}
				}
			}
		}, 40, 40);

		started = true;
		updateSign();
	}

	public int getStartingCash(Player p) {
		return (int) LabItem.Starting_Cash.getPercent(p) + 650;
	}

	public int getMaxWave() {
		int d = getDifficulty();
		return d == 3 ? 85 : d == 2 ? 75 : 50;
	}

	public void onWaveEnd() {
		if (wave < getMaxWave() + 1) {
			addMoney(Wave.valueOf("w" + wave).cash);
			sendToAll("Wave", "Wave " + wave + " ended!");
			sendToAll("Vote", "Type /start to vote to start the next wave");
			voting = true;
		} else {
			sendToAll("Win!", "You have beat §b" + name() + "§f! Congrats!");
			stop();
		}
	}

	public int getDifficulty() {
		for (int i = 0; i < Games.values().length; i++) {
			Games g = Games.values()[i];
			if (g == this) {
				if (i >= 0 && i <= 4) {
					return 1;
				} else if (i >= 5 && i <= 9) {
					return 2;
				} else if (i >= 10 && i <= 14) {
					return 3;
				}
			}
		}
		return 0;
	}

	public void onVote(Player p) {
		if (!voted.contains(p) && voting) {
			sendToAll("Vote", p.getName() + " has voted to start the next wave");
			voted.add(p);
			votes = voted.size();
			if (votes >= ((players.size() / 2) + 1)) {
				nextWave();
				sendToAll("Wave", "Starting wave: " + wave);
			}
		}
	}

	public void nextWave() {
		votes = 0;
		voted.clear();
		voting = false;
		Wave.spawnWave(this, wave, zombie);

		wave++;
		for (int x = 0; x < 4; x++) {
			if (o[x] == null)
				continue;
			o[x].getScore(Bukkit.getOfflinePlayer(ChatColor.YELLOW + "Wave")).setScore(wave);
		}
	}

	public Zombie spawnZombie(Location l, int health, Type... types) {
		Zombie z = (Zombie) l.getWorld().spawnEntity(l, EntityType.ZOMBIE);
		zombies.add(z);
		health -= (int) (getMaxPercentDec(LabItem.Big_Bloon_Sabotage) * health);
		z.setMaxHealth(health);
		z.setHealth(health);
		z.getEquipment().clear();
		z.setBaby(false);
		z.setVillager(false);

		for (Type t : types) {
			if (t == null)
				continue;
			t.setType(z);
			HealthColors.update(z);
		}

		return z;
	}

	public void stop() {
		wave = 0;
		started = false;
		vil.cancel();
		reg.cancel();
		for (LivingEntity le : towers.keySet().toArray(new LivingEntity[0])) {
			if (le.isValid() && !le.isDead()) {
				le.remove();
			}
		}
		if (wtas != null) {
			wtas.cancel();
		}
		if (wtas2 != null) {
			wtas2.cancel();
		}
		upg1.clear();
		upg2.clear();
		for (BukkitTask bt : tow.values()) {
			bt.cancel();
		}
		for (Object o : zombies.toArray().clone()) {
			Zombie z = (Zombie) o;
			if (z.isValid() && !z.isDead()) {
				z.remove();
			}
		}
		for (Object o : players.toArray().clone()) {
			Player p = (Player) o;
			p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			LeaveCommand.leaveGame(p);
			p.getInventory().clear();
		}
		updateSign();
		v.remove();
		zombies.clear();
		towers.clear();
		towerLoc.clear();
	}

	@SuppressWarnings("deprecation")
	public void buyTower(Player p) {
		Inventory i = Bukkit.createInventory(null, 18, "Towers");

		for (Tower t : Tower.values()) {
			if (LevelManager.canUseTower(t, LevelManager.getLevel(p))) {
				i.addItem(ItemMetaUtils.setLore(ItemMetaUtils.setItemName(
						new ItemStack(Material.MONSTER_EGG, 1, t.mob.getTypeId()),
						ChatColor.GOLD + t.name().replace('_', ' ')), ChatColor.LIGHT_PURPLE + "Cost: " + t.cost));
			}
		}

		p.openInventory(i);
	}

	@SuppressWarnings("deprecation")
	public void purchaseTower(Player p, final Tower t) {
		if (cash.get(p) >= t.cost) {
			Location l = p.getTargetBlock(null, 10).getLocation();
			if (l.getBlock().getType() != place)
				return;
			addMoney(-t.cost, p);
			p.sendMessage(ChatColor.GREEN + "Purchased " + t.name().replace('_', ' ') + " for " + t.cost + " cash");
			l = l.add(0.5, 1.75, 0.5);
			final LivingEntity le = (LivingEntity) l.getWorld().spawnEntity(l, t.mob);
			towOwn.put(le, p);

			le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1000000000, 10));

			le.setCustomName(t.name().replace('_', ' '));
			le.setCustomNameVisible(true);

			le.setRemoveWhenFarAway(false);

			if (t.h != null) {
				le.getEquipment().setHelmet(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), t.h));
			}
			if (t.c != null) {
				le.getEquipment().setChestplate(
						ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), t.c));
			}
			if (t.l != null) {
				le.getEquipment().setLeggings(
						ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), t.l));
			}
			if (t.b != null) {
				le.getEquipment().setBoots(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), t.b));
			}

			towerLoc.put(le, l);
			towers.put(le, t);

			final Games g = this;

			putTowerTargetTask(t.fireRate, le, t, g);
		} else
			p.sendMessage(ChatColor.RED + "Not enough cash!");

		p.closeInventory();
	}

	public void putTowerTargetTask(long rate, final LivingEntity le, final Tower t, final Games g) {
		tow.put(le, Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				if (le.isDead() || !le.isValid())
					return;
				int r = t.getRange(g, le);
				List<Zombie> es = zombies;
				if (es.size() > 0) {
					for (int i = 0; i < es.size(); i++) {
						Zombie z = es.get(i);
						if (z.getEyeLocation().distance(le.getEyeLocation()) <= r) {
							if (!z.isDead() && z.isValid() && zombies.contains(z)) {
								if (!Type.CAMO.isType(z) || t.canSeeCamo(g, le)) {
									t.onTowerTarget(le, g, z);
									break;
								}
							}
						}
					}
				}
			}
		}, rate, rate));
	}

	public void addMoney(int amount) {
		for (Player p : players) {
			addMoney(amount, p);
		}
	}

	public void addMoney(int amount, Player p) {
		int c = cash.get(p) + amount;
		cash.remove(p);
		cash.put(p, c);
		p.getScoreboard().getObjective(DisplaySlot.SIDEBAR)
				.getScore(Bukkit.getOfflinePlayer(ChatColor.LIGHT_PURPLE + "Cash")).setScore(c);
	}

	public int getSellPrice(LivingEntity le) {
		Tower t = towers.get(le);
		int s = t.cost - (t.cost / 5);
		for (Upgrade u : t.getUpgrades()) {
			if (!t.hasUpgrade(this, le, u.s, u.id))
				continue;
			s += u.cost - (u.cost * getMaxPercentDec(LabItem.Sell_Price));
		}
		return s;
	}

	public HashMap<LivingEntity, Long>	abilcool	= new HashMap<LivingEntity, Long>();

	public void setAbilityCooldown(LivingEntity le, int cool) {
		abilcool.remove(le);
		abilcool.put(
				le,
				(long) (System.currentTimeMillis() + ((cool * 1000) - (cool * 1000 * getMaxPercentDec(LabItem.Hotter_Cooldown)))));
	}

	public boolean canUseAbility(LivingEntity le) {
		if (abilcool.containsKey(le)) {
			long cool = abilcool.get(le);
			return cool <= System.currentTimeMillis();
		} else
			return true;
	}

	public double getMaxPercentDec(LabItem li) {
		double ret = 0;
		for (Player p : players) {
			double add = li.getPercent(p);
			if (add >= ret) {
				ret = add;
			}
		}
		return ret / 100;
	}

	public double getMaxPercentAdd(LabItem li) {
		return getMaxPercentDec(li) + 1;
	}

	public static Arrow launchArrow(Games g, Location l, Vector v, float speed, float spread, int damage) {
		Arrow a = (Arrow) l.getWorld().spawnArrow(l, v, speed, spread);

		if (g != null) {
			damage *= g.getMaxPercentAdd(LabItem.Popping_Power);
		}

		dmg.put(a, damage);
		return a;
	}

	public static ArrayList<BukkitTask>	check	= new ArrayList<BukkitTask>();

	public static Random				random	= new Random();

	public static void sprayBlocks(final Location loc, final Material m, final Vector v, final int damage, int total,
			int inter, final int aat, final float spread) {
		for (int i = 0; i < total; i++) {
			Bukkit.getScheduler().runTaskLater(Zombiez.instance, new Runnable() {
				public void run() {
					for (int i2 = 0; i2 < aat; i2++) {
						throwBlock(
								loc,
								m,
								v.add(new Vector(random.nextGaussian() / spread, random.nextGaussian() / spread, random
										.nextGaussian() / spread)), damage);
					}
				}
			}, i * inter);
		}
	}

	public static void throwBlock(Location loc, Material m, Vector v, final int damage) {
		final FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, m, (byte) 0);
		fb.setVelocity(v);
		fb.setDropItem(false);

		final int i = check.size();
		check.add(Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				for (Entity e : fb.getNearbyEntities(1, 1, 1)) {
					if (e.getType() != EntityType.ZOMBIE)
						continue;
					Zombie z = (Zombie) e;
					fb.remove();
					z.damage(damage);
					check.get(i).cancel();
				}
				if (!fb.isValid() || fb.isDead()) {
					check.get(i).cancel();
				}
			}
		}, 1, 1));
	}
}
