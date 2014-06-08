package net.mcshockwave.ZTD.Games;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.mcshockwave.ZTD.LabItem;
import net.mcshockwave.ZTD.Zombiez;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public enum Tower {

	Skeleton(
		1,
		170,
		15,
		7,
		EntityType.SKELETON,
		null,
		null,
		null,
		null,
		"Long Range Arrows::75::1::1::Increases Range by 4m",
		"Enhanced Eyesight::100::1::2::Can see Camo Zombies",
		"Sharp Shots::120::2::1::Does 1 extra damage",
		"Razer Sharp Shots::145::2::2::Does 2 extra damage",
		"Triple Darts::280::2::3::Shoots 3 arrows",
		"Spike-o-pult::425::1::3::Does 18 damage, lower attack speed",
		"Juggernaut::1275::1::4::Does 100 damage, incredibly low attack speed"),
	Sniper_Skeleton(
		10,
		1250,
		40,
		100,
		EntityType.SKELETON,
		Color.GREEN,
		Color.GREEN,
		null,
		null,
		"Full Metal Jacket::300::1::1::Does +2 Damage",
		"Point 5 Oh::1870::1::2::Does 7 damage",
		"Deadly Precision::3400::1::3::Does 18 damage",
		"Faster Firing::340::2::1::Shoots 25% faster",
		"Night Vision Goggles::255::2::2::Allows tower to see camo",
		"Semi-automatic rifle::2975::2::3::Attacks 3x as fast",
		"Supply Drop::10200::2::4::Ability: Drops 100-150 cash"),
	Ninja_Skeleton(
		3,
		510,
		15,
		12,
		EntityType.SKELETON,
		Color.RED,
		Color.RED,
		null,
		null,
		"Ninja Discipline::255::1::1::Decreased attack speed, +5 Range",
		"Sharp Shurikens::300::1::2::Does 4 damage",
		"Double Shot::725::1::3::Shoots 2 arrows at once",
		"Zombiejitsu::2340::1::4::Shoots 5 arrows at once",
		"Seeking Shuriken::215::2::1::Increases accuracy",
		"Distraction::300::2::2::Stuns a zombie for 2.5 seconds",
		"Flash Bomb::2340::2::3::Stuns all zombies around the target",
		"Sabotage Supply Lines::1530::2::4::Ability: Slows all zombies that spawn for 15 seconds"),
	Bomb_Tower(
		2,
		555,
		50,
		5,
		EntityType.CREEPER,
		null,
		null,
		null,
		null,
		"Extra Range::170::1::1::Increases range",
		"Frag Bombs::255::1::2::Shoots arrows away from the explosion",
		"Cluster Bombs::680::1::3::Explodes targets around the blast zone",
		"Zombie Impact::2720::1::4::Stuns zombies around the blast zone",
		"Bigger Bombs::340::2::1::Increases blast power",
		"Missile Launcher::340::2::2::Increases: Range, Damage, Speed"),
	Skeleton_Apprentice(
		12,
		470,
		20,
		7,
		EntityType.WITCH,
		null,
		null,
		null,
		null,
		"Intense Magic::255::1::1::Explosions are more powerful",
		"Lightning Bolt::1065::1::2::Adds a lightning attack",
		"Summon Whirlwind::1700::1::3::Sends zombies back to the start",
		"Tempest Tornado::6800::1::4::Blows back to start more often, and damages them 1 dmg",
		"Fireball::255::2::1::Shoots larger fireballs",
		"Skeleton Sense::255::2::2::Allows tower to see camo",
		"Dragon's Breath::3570::2::3::Increases fire rate 100%"),
	Web_Shooter(
		5,
		230,
		30,
		7,
		EntityType.SPIDER,
		null,
		null,
		null,
		null,
		"Web Poison::170::1::1::Damages all zombies in the web 1 dmg",
		"Corrosive Web::255::1::2::Increases web damage by 1",
		"Zombie Dissolver::2805::1::3::Increases Web Damage by 5",
		"Zombie Liquefier::10625::1::4::Increases Web Damage by 10",
		"Stickier Webs::100::2::1::Makes webs last longer",
		"Web Splatter::1870::2::2::Makes 4 webs around the original web",
		"Web Hose::2765::2::3::Shoots 3x faster"),
	Tack_Shooter(
		2,
		305,
		10,
		6,
		EntityType.PIG_ZOMBIE,
		null,
		null,
		null,
		null,
		"Faster Shooting::180::1::1::Shoots arrows faster",
		"Even Faster Shooting::255::1::2::Shoots arrows even faster!",
		"Arrow Sprayer::425::1::3::Shoots 16 arrows instead of 8",
		"Ring Of Fire::2125::1::4::Shoots flaming arrows that do more damage",
		"Extra Range Arrows::85::2::1::Increases tower range",
		"Super Range Arrows::190::2::2::Increases tower range again",
		"Blade Shooter::580::2::3::Increases accuracy and damage",
		"Blade Maelstrom::2295::2::4::Ability: Spawns tacks above every zombie"),
	Ice_Tower(
		25,
		325,
		50,
		6,
		EntityType.SNOWMAN,
		null,
		null,
		null,
		null,
		"Enhanced Freeze::190::1::1::Freezes longer",
		"Snap Freeze::340::1::2::Does 1 damage upon freezing",
		"Arctic Wind::5525::1::3::Larger area, slows zombies that come near",
		"Viral Frost::5100::1::4::Freezes all zombies near already frozen ones",
		"Permafrost::85::2::1::Zombies move slower even after thawing",
		"Deep Freeze::300::2::2::Larger radius",
		"Ice Shards::1700::2::3::Any zombies killed while frozen shoot out shards of ice",
		"Absolute Zero::1700::2::4::Ability: Freezes all zombies for 4 seconds"),
	Super_Skeleton(
		15,
		3600,
		1,
		25,
		EntityType.SKELETON,
		Color.BLUE,
		Color.BLUE,
		Color.BLUE,
		Color.BLUE,
		"Laser Blasts::2975::1::1::Shoots flaming lasers that do more damage",
		"Plasma Blasts::4250::1::2::Shoots plasma that does a lot of damage",
		"Sun God::14065::1::3::Shoots pure sun at zombies that vaporize zombies",
		"Super Range::850::2::1::Increases range",
		"Epic Range::1275::2::2::Increases range",
		"Robo Skeleton::7650::2::3::Increases fire rate a lot, shoots 2 beams at once",
		"Technological Terror::21250::2::4::Ability: Destroys all zombies close to the tower"),
	Scout_Tower(
		30,
		1355,
		0,
		10,
		EntityType.SILVERFISH,
		null,
		null,
		null,
		null);

	public int			cost, range, fireRate, levReq;
	public EntityType	mob;
	public Color		h, c, l, b;
	public String[]		upg;

	Random				rand	= new Random();

	Tower(int levReq, int cost, int fireRate, int range, EntityType mob, Color h, Color c, Color l, Color b,
			String... upgrades) {
		this.cost = cost;
		this.range = range;
		this.mob = mob;
		this.h = h;
		this.c = c;
		this.l = l;
		this.b = b;
		this.fireRate = fireRate;
		this.upg = upgrades;
		this.levReq = levReq;
	}

	public void onTowerTarget(LivingEntity le, Games g, Zombie target) {
		final Location ta = le.getEyeLocation().add(0, 1, 0);
		final Location to = target.getEyeLocation();
		Vector v = new Vector(to.getX() - ta.getX(), to.getY() - ta.getY(), to.getZ() - ta.getZ());
		v = v.add(new Vector(0, ta.distance(to) / 20, 0));
		Vector add = v.clone().multiply(v.normalize());
		if (this == Skeleton) {
			int damage = hasUpgrade(g, le, 2, 4) ? 100 : hasUpgrade(g, le, 2, 3) ? 18 : hasUpgrade(g, le, 2, 2) ? 3
					: (hasUpgrade(g, le, 2, 1) ? 2 : 1);
			if (hasUpgrade(g, le, 2, 3)) {
				for (int i = 0; i < 3; i++) {
					Games.launchArrow(g, ta.add(add), v, 1, 25, damage);
				}
			} else {
				Games.launchArrow(g, ta.add(add), v, 1, 1, damage);
			}
		}
		if (this == Bomb_Tower) {
			float power = hasUpgrade(g, le, 2, 2) ? 2.1F : hasUpgrade(g, le, 2, 1) ? 1.7F : 1.2F;
			le.getWorld().createExplosion(to, power);
			for (Entity e : target.getNearbyEntities(5, 5, 5)) {
				if (e.getType() == EntityType.ZOMBIE) {
					Zombie z = (Zombie) e;
					z.damage(power, le);
				}
			}
			if (hasUpgrade(g, le, 1, 4)) {
				for (Entity e : target.getNearbyEntities(5, 5, 5)) {
					if (e.getType() == EntityType.ZOMBIE) {
						Zombie z = (Zombie) e;
						z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 30, 25));
						z.getWorld().playEffect(z.getEyeLocation(), Effect.STEP_SOUND, 46);
					}
				}
			}
			if (hasUpgrade(g, le, 1, 3)) {
				for (Entity e : target.getNearbyEntities(5, 5, 5)) {
					if (e.getType() == EntityType.ZOMBIE && e != target) {
						Zombie z = (Zombie) e;
						z.getWorld().createExplosion(z.getEyeLocation(), power / 10);
					}
				}
			} else if (hasUpgrade(g, le, 1, 2)) {
				double range = 1;
				double h = 0.05;
				Vector[] vs = { new Vector(range, h, 0), new Vector(range, h, range), new Vector(0, h, range),
						new Vector(-range, h, range), new Vector(-range, h, 0), new Vector(-range, h, -range),
						new Vector(0, h, -range), new Vector(range, h, -range) };

				for (Vector vec : vs) {
					Games.launchArrow(g, to.clone().add(0, 1, 0), vec.add(new Vector(0, 0.5F, 0)), 0.3F, 50, 2);
				}
			}
		}
		if (this == Skeleton_Apprentice) {
			if (!hasUpgrade(g, le, 1, 2) || rand.nextInt(15) != 1) {
				boolean hasTT = hasUpgrade(g, le, 1, 4);
				if (!hasUpgrade(g, le, 1, 3) || rand.nextInt(hasTT ? 12 : 20) != 1) {
					if (hasUpgrade(g, le, 2, 1)) {
						Fireball fire = (Fireball) le.getWorld().spawnEntity(ta.add(add), EntityType.FIREBALL);
						fire.setDirection(v);
						fire.setIsIncendiary(false);
						fire.setVelocity(v);
					} else {
						SmallFireball fire = (SmallFireball) le.getWorld().spawnEntity(ta.add(add),
								EntityType.SMALL_FIREBALL);
						fire.setDirection(v);
						fire.setIsIncendiary(false);
						fire.setVelocity(v);
					}
					to.getWorld().createExplosion(to, hasUpgrade(g, le, 1, 1) ? 0.7F : 0.5F);
				} else {
					int range = 3;
					for (Entity e : target.getNearbyEntities(range, 2, range)) {
						if (e.getType() == EntityType.ZOMBIE) {
							Zombie z = (Zombie) e;
							if (g.zombies.contains(z)) {
								z.teleport(g.zombie);
								to.getWorld().playEffect(to, Effect.ENDER_SIGNAL, 0);
								g.zombies.remove(z);
								g.zombies.add(z);
								if (hasTT) {
									z.damage(1, le);
								}
							}
						}
					}
				}
			} else {
				LightningStrike l = to.getWorld().strikeLightningEffect(to);
				int range = 4;
				for (Entity e : l.getNearbyEntities(range, 2, range)) {
					if (e.getType() == EntityType.ZOMBIE) {
						Zombie z = (Zombie) e;
						if (g.zombies.contains(z)) {
							z.damage(4, le);
						}
					}
				}
			}
		}
		if (this == Web_Shooter) {
			if (to.getBlock().getType() == Material.AIR) {
				final boolean hasWS = hasUpgrade(g, le, 2, 2);
				if (hasUpgrade(g, le, 1, 1)) {
					int range = hasWS ? 2 : 1;
					int damage = hasUpgrade(g, le, 1, 4) ? 17 : hasUpgrade(g, le, 1, 3) ? 7
							: hasUpgrade(g, le, 1, 2) ? 2 : 1;
					for (Entity e : target.getNearbyEntities(range, 2, range)) {
						if (e.getType() == EntityType.ZOMBIE) {
							Zombie z = (Zombie) e;
							if (g.zombies.contains(z)) {
								z.damage(damage);
							}
						}
					}
				}
				if (hasWS) {
					BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
					for (BlockFace f : faces) {
						Block b = to.getBlock().getRelative(f);
						if (b.getType() == Material.AIR) {
							b.setType(Material.WEB);
						}
					}
				}
				to.getBlock().setType(Material.WEB);
				Bukkit.getScheduler().runTaskLater(Zombiez.instance, new Runnable() {
					public void run() {
						to.getBlock().setType(Material.AIR);
						if (hasWS) {
							BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
							for (BlockFace f : faces) {
								Block b = to.getBlock().getRelative(f);
								if (b.getType() == Material.WEB) {
									b.setType(Material.AIR);
								}
							}
						}
					}
				}, hasUpgrade(g, le, 2, 1) ? 40 : 20);
			}
		}
		if (this == Super_Skeleton) {
			for (int rs = 0; rs < (hasUpgrade(g, le, 2, 3) ? 2 : 1); rs++) {
				boolean pb = hasUpgrade(g, le, 1, 2);
				if (pb) {
					boolean sg = hasUpgrade(g, le, 1, 3);
					Games.sprayBlocks(ta.add(add), sg ? Material.LAVA : Material.WATER, v.normalize().setY(0.1),
							sg ? 18 : 12, 1, 1, sg ? 10 : 7, 25);
				} else {
					boolean lb = hasUpgrade(g, le, 1, 1);
					Arrow a = Games.launchArrow(g, ta.add(add), v, 2F, 1, lb ? 6 : 3);
					if (lb) {
						a.setFireTicks(1000);
					}
				}
			}
		}
		if (this == Tack_Shooter) {
			double range = 1;
			double h = 0.05;
			Vector[] vs2 = {};
			if (hasUpgrade(g, le, 1, 3)) {
				Vector[] vs = { new Vector(range, h, 0), new Vector(range, h, range), new Vector(0, h, range),
						new Vector(-range, h, range), new Vector(-range, h, 0), new Vector(-range, h, -range),
						new Vector(0, h, -range), new Vector(range, h, -range), new Vector(-range / 2, h, range / 2),
						new Vector(-range / 2, h, 0), new Vector(-range / 2, h, -range / 2),
						new Vector(0, h, -range / 2), new Vector(range / 2, h, -range / 2) };
				vs2 = vs;
			} else {
				Vector[] vs = { new Vector(range, h, 0), new Vector(range, h, range), new Vector(0, h, range),
						new Vector(-range, h, range), new Vector(-range, h, 0), new Vector(-range, h, -range),
						new Vector(0, h, -range), new Vector(range, h, -range) };
				vs2 = vs;
			}

			for (Vector vec : vs2) {
				int damage = hasUpgrade(g, le, 2, 3) ? 10 : hasUpgrade(g, le, 1, 4) ? 7 : 1;
				Arrow a = Games.launchArrow(g, le.getEyeLocation().add(vec).add(0, -0.2, 0), vec, 0.3F, 50, damage);
				if (hasUpgrade(g, le, 1, 4)) {
					a.setFireTicks(1000);
				}
			}
		}
		if (this == Ice_Tower) {
			for (Entity e : le.getNearbyEntities(getRange(g, le), 2, getRange(g, le))) {
				if (e.getType() == EntityType.ZOMBIE) {
					final Zombie z = (Zombie) e;
					z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, hasUpgrade(g, le, 1, 1) ? 40 : 30,
							hasUpgrade(g, le, 2, 3) ? 21 : 20));
					if (hasUpgrade(g, le, 2, 1)) {
						Bukkit.getScheduler().runTaskLater(Zombiez.instance, new Runnable() {
							public void run() {
								z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000, 0));
							}
						}, 40);
					}
					z.getWorld().playEffect(z.getLocation(), Effect.STEP_SOUND, 80);
					if (hasUpgrade(g, le, 1, 2)) {
						z.damage(1);
					}
					if (hasUpgrade(g, le, 1, 3)) {
						int range = 3;
						for (Entity e2 : target.getNearbyEntities(range, 2, range)) {
							if (e2.getType() == EntityType.ZOMBIE) {
								Zombie z2 = (Zombie) e2;
								if (g.zombies.contains(z2)) {
									if (hasUpgrade(g, le, 1, 4)) {
										z2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 20));
									} else
										z2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 3));
								}
							}
						}
					}
				}
			}
		}
		if (this == Sniper_Skeleton) {
			int damage = hasUpgrade(g, le, 1, 3) ? 18 : hasUpgrade(g, le, 1, 2) ? 7 : hasUpgrade(g, le, 1, 1) ? 4 : 2;
			target.damage(damage);
			le.getWorld().playSound(ta, Sound.SHOOT_ARROW, 1, 1);
			target.getWorld().playEffect(to, Effect.STEP_SOUND, 152);
		}
		if (this == Ninja_Skeleton) {
			int damage = hasUpgrade(g, le, 1, 2) ? 4 : 2;
			for (int i = 0; i < (hasUpgrade(g, le, 1, 4) ? 5 : hasUpgrade(g, le, 1, 3) ? 2 : 1); i++) {
				Games.launchArrow(g, ta.add(add), v, 1.8F, hasUpgrade(g, le, 2, 1) ? 5 : 20, damage);
			}
			if (hasUpgrade(g, le, 2, 2)) {
				target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 25));
			}
			if (hasUpgrade(g, le, 2, 3)) {
				for (Entity e : target.getNearbyEntities(5, 5, 5)) {
					if (e.getType() == EntityType.ZOMBIE) {
						Zombie z = (Zombie) e;
						z.getLocation().getWorld().playEffect(z.getLocation(), Effect.STEP_SOUND, 121);
						z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10, 25));
					}
				}
			}
		}
	}

	public boolean canSeeCamo(Games g, LivingEntity le) {
		if (this == Ninja_Skeleton) {
			return true;
		}
		if (this == Skeleton) {
			if (hasUpgrade(g, le, 1, 2)) {
				return true;
			}
		}
		if (this == Sniper_Skeleton) {
			if (hasUpgrade(g, le, 2, 2)) {
				return true;
			}
		}
		if (this == Skeleton_Apprentice) {
			if (hasUpgrade(g, le, 2, 2)) {
				return true;
			}
		}
		int range = Tower.Scout_Tower.range;
		for (Entity e : le.getNearbyEntities(range, range, range)) {
			if (e.getType() == EntityType.SILVERFISH) {
				return true;
			}
		}
		return false;
	}

	public int getRange(Games g, LivingEntity le) {
		if (this == Skeleton) {
			if (hasUpgrade(g, le, 1, 1)) {
				return range + 4;
			}
		}
		if (this == Ninja_Skeleton) {
			if (hasUpgrade(g, le, 1, 1)) {
				return range + 5;
			}
		}
		if (this == Bomb_Tower) {
			int r = this.range;
			if (hasUpgrade(g, le, 1, 1)) {
				r += 3;
			}
			if (hasUpgrade(g, le, 2, 2)) {
				r += 2;
			}
			return r;
		}
		if (this == Tack_Shooter) {
			int r = this.range;
			if (hasUpgrade(g, le, 2, 1)) {
				r += 3;
			}
			if (hasUpgrade(g, le, 2, 2)) {
				r += 5;
			}
			return r;
		}
		if (this == Ice_Tower) {
			int r = this.range;
			if (hasUpgrade(g, le, 2, 2)) {
				r += 3;
			}
			if (hasUpgrade(g, le, 1, 3)) {
				r += 5;
			}
			return r;
		}
		if (this == Super_Skeleton) {
			int r = this.range;
			if (hasUpgrade(g, le, 2, 1)) {
				r += 25;
			}
			if (hasUpgrade(g, le, 2, 2)) {
				r += 15;
			}
			return r;
		}
		return range;
	}

	public void giveUpgrade(Games g, LivingEntity le, int upgS, int upgL) {
		if (upgS == 1) {
			g.upg1.remove(le);
			g.upg1.put(le, upgL);
		} else {
			g.upg2.remove(le);
			g.upg2.put(le, upgL);
		}

		onUpgrade(g, le, upgS, upgL);
	}

	public boolean hasUpgrade(Games g, LivingEntity le, int upgS, int upgL) {
		if (upgS == 1) {
			return g.upg1.containsKey(le) && g.upg1.get(le) >= upgL;
		} else {
			return g.upg2.containsKey(le) && g.upg2.get(le) >= upgL;
		}
	}

	public Upgrade getUpgrade(int upgS, int upgL) {
		for (String s : upg) {
			String[] ss = s.split("::");
			int us = Integer.parseInt(ss[2]);
			int ui = Integer.parseInt(ss[3]);
			if (us == upgS && ui == upgL) {
				return new Upgrade(Integer.parseInt(ss[1]), ui, us, ss[0], ss[4], this);
			}
		}
		return null;
	}

	public Upgrade getUpgrade(String name) {
		for (String s : upg) {
			String[] ss = s.split("::");
			int us = Integer.parseInt(ss[2]);
			int ui = Integer.parseInt(ss[3]);
			if (ss[0].equalsIgnoreCase(name)) {
				return new Upgrade(Integer.parseInt(ss[1]), ui, us, ss[0], ss[4], this);
			}
		}
		return null;
	}

	public List<Upgrade> getUpgrades() {
		ArrayList<Upgrade> upgs = new ArrayList<Upgrade>();

		for (String s : upg) {
			String[] ss = s.split("::");
			int us = Integer.parseInt(ss[2]);
			int ui = Integer.parseInt(ss[3]);
			upgs.add(new Upgrade(Integer.parseInt(ss[1]), ui, us, ss[0], ss[4], this));
		}

		return upgs;
	}

	public static class Upgrade {
		public int	cost, id, s;
		public String	name, desc;
		public Tower	t;

		Upgrade(int cost, int id, int s, String name, String desc, Tower t) {
			this.cost = cost;
			this.id = id;
			this.s = s;
			this.name = name;
			this.desc = desc;
		}
	}

	public void onUpgrade(Games g, LivingEntity le, int upgS, int upgL) {
		if (this == Skeleton) {
			if (upgS == 1) {
				if (upgL == 3) {
					setFireRate(g, le, 40);
				}
				if (upgL == 4) {
					setFireRate(g, le, 80);
				}
			}
		}
		if (this == Sniper_Skeleton) {
			if (upgS == 2) {
				if (upgL == 1) {
					setFireRate(g, le, 30);
				}
				if (upgL == 3) {
					setFireRate(g, le, 10);
				}
			}
		}
		if (this == Ninja_Skeleton) {
			if (upgS == 1) {
				if (upgL == 1) {
					setFireRate(g, le, 12);
				}
			}
		}
		if (this == Bomb_Tower) {
			if (upgS == 2) {
				if (upgL == 2) {
					setFireRate(g, le, 40);
				}
			}
		}
		if (this == Skeleton_Apprentice) {
			if (upgS == 2) {
				if (upgL == 3) {
					setFireRate(g, le, 10);
				}
			}
		}
		if (this == Web_Shooter) {
			if (upgS == 2) {
				if (upgL == 3) {
					setFireRate(g, le, 10);
				}
			}
		}
		if (this == Tack_Shooter) {
			if (upgS == 1) {
				if (upgL == 1) {
					setFireRate(g, le, 8);
				}
				if (upgL == 2) {
					setFireRate(g, le, 5);
				}
			}
		}
	}

	public void setFireRate(Games g, LivingEntity le, int firerate) {
		g.tow.get(le).cancel();
		g.tow.remove(le);
		g.putTowerTargetTask((long) (firerate * (g.getMaxPercentAdd(LabItem.Attack_Speed))), le, this, g);
	}

	public void onAbility(Games g, LivingEntity le) {
		if (this == Tack_Shooter) {
			for (Zombie z : g.zombies) {
				Games.launchArrow(g, z.getEyeLocation().add(0, 1, 0), new Vector(0, -0.2, 0), 0.5f, 0, 4);
			}
		}
		if (this == Sniper_Skeleton) {
			g.addMoney(rand.nextInt(50) + 100);
		}
		if (this == Ninja_Skeleton) {
			int cool = 15;
			g.lastSaboTime = (System.currentTimeMillis() / 1000) + cool;
		}
		if (this == Ice_Tower) {
			for (Zombie z : g.zombies) {
				z.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 80, 10));
			}
		}
		if (this == Super_Skeleton) {
			for (Zombie z : g.zombies) {
				if (z.getLocation().distance(le.getLocation()) <= 10) {
					z.damage(1000, le);
				}
			}
		}
	}

	public int getAbilCool() {
		if (this == Tack_Shooter) {
			return 20;
		}
		if (this == Sniper_Skeleton) {
			return 120;
		}
		if (this == Ninja_Skeleton) {
			return 60;
		}
		if (this == Ice_Tower) {
			return 90;
		}
		if (this == Super_Skeleton) {
			return 120;
		}
		return 0;
	}

}
