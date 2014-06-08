package net.mcshockwave.ZTD;

import java.util.HashMap;

import net.mcshockwave.MCS.SQLTable;
import net.mcshockwave.MCS.Utils.ItemMetaUtils;
import net.mcshockwave.ZTD.Games.Games;
import net.mcshockwave.ZTD.Games.HealthColors;
import net.mcshockwave.ZTD.Games.Tower;
import net.mcshockwave.ZTD.Games.Tower.Upgrade;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DefaultListener implements Listener {

	Zombiez	plugin;

	public DefaultListener(Zombiez instance) {
		plugin = instance;
	}

	HashMap<Player, String>			lastMessage	= new HashMap<Player, String>();

	HashMap<Player, LivingEntity>	selected	= new HashMap<Player, LivingEntity>();

	@EventHandler
	public void onSignPlace(SignChangeEvent event) {
		if (event.getLine(0).startsWith("[") && event.getLine(0).endsWith("]")) {
			String s = event.getLine(0).substring(1, event.getLine(0).length() - 1);
			if (Games.valueOf(s) != null) {
				Games g = Games.valueOf(s);
				event.setLine(0, ChatColor.AQUA + "[" + g.name() + "]");
				event.setLine(1, ChatColor.BLUE + "Click to");
				event.setLine(2, ChatColor.BLUE + "join game");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void updateLabInterface(Player p, Inventory i) {
		int cred = LevelManager.getCredits(p);
		i.setItem(
				0,
				ItemMetaUtils.setItemName(new ItemStack(Material.GOLD_INGOT), ChatColor.GRAY + "Current Credits: "
						+ ChatColor.GOLD + cred));

		for (int x = 0; x < LabItem.values().length; x++) {
			LabItem li = LabItem.values()[x];
			boolean cgn = li.canGetNext(p);
			int slot = x + 9;
			int lvl = SQLTable.Zombiez.getInt("Username", p.getName(), li.sql);

			if (li.isAnotherLevel(p)) {
				i.setItem(slot, ItemMetaUtils.setLore(ItemMetaUtils.setItemName(new ItemStack(Material.WOOL, 1,
						(short) (cgn ? 5 : 14)), ChatColor.GOLD + li.name().replace('_', ' ')), ChatColor.DARK_AQUA
						+ li.desc.replace("%p", li.getPercent(lvl + 1) + ""),
						ChatColor.AQUA + "Cost: " + li.getUpgradeCost(p), "",
						ChatColor.YELLOW + "Current: " + li.getPercent(lvl), "", cgn ? ChatColor.GREEN + "Click to buy"
								: ChatColor.RED + "Not enough credits!"));
			} else {
				i.setItem(slot, ItemMetaUtils.setLore(
						ItemMetaUtils.setItemName(new ItemStack(Material.WOOL, 1, (short) 4), ChatColor.GOLD
								+ li.name().replace('_', ' ')),
						ChatColor.DARK_AQUA + li.desc.replace("%p", li.getPercent(lvl + 1) + ""), "", ChatColor.YELLOW
								+ "Current: " + li.getPercent(lvl)));
			}
		}

		p.updateInventory();
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		Action a = event.getAction();
		Block b = event.getClickedBlock();
		ItemStack it = p.getItemInHand();

		if (a.name().contains("RIGHT_CLICK") && it.getType() == Material.PAPER) {

			Bukkit.dispatchCommand(p, "start");

			event.setCancelled(true);
			return;
		}

		if (a == Action.RIGHT_CLICK_BLOCK && b.getType() == Material.BREWING_STAND) {

			Inventory i = Bukkit.createInventory(null, 18, "Skeleton Lab");
			p.openInventory(i);

			updateLabInterface(p, i);

			event.setCancelled(true);
			return;
		}

		if (a == Action.RIGHT_CLICK_BLOCK && (b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST)) {
			Sign s = (Sign) b.getState();
			if (s.getLine(0).startsWith(ChatColor.AQUA + "[") && s.getLine(0).endsWith("]")) {
				String g = s.getLine(0).substring(3, s.getLine(0).length() - 1);
				if (Games.valueOf(g) != null) {
					if (Games.getGame(p) == null) {
						Games ga = Games.valueOf(g);
						if (LevelManager.canPlayGame(ga, LevelManager.getLevel(p))) {
							if (ga.started) {
								p.sendMessage(ChatColor.RED + "Game is already started!");
								return;
							}

							if (ga.players.size() >= 4) {
								p.sendMessage(ChatColor.RED + "Game is full!");
								return;
							}

							p.sendMessage(ChatColor.AQUA + "You joined " + ga.name() + "\n/leave to leave the game");

							for (Player p2 : Bukkit.getOnlinePlayers()) {
								if (p2 == p)
									continue;
								Games ga2 = Games.getGame(p2);
								if (ga != ga2) {
									p.hidePlayer(p2);
									p2.hidePlayer(p);
								} else {
									p.showPlayer(p2);
									p2.showPlayer(p);
								}
							}

							if (p.getGameMode() != GameMode.ADVENTURE) {
								p.setGameMode(GameMode.ADVENTURE);
							}

							p.teleport(ga.spawn);
							ga.players.add(p);
							ga.sendToAll("Join", p.getName() + " joined the game");
							ga.onJoin(p);
						} else {
							p.sendMessage(ChatColor.RED + "You do not have this difficulty unlocked!");
						}
					} else {
						p.sendMessage(ChatColor.RED + "You are already in a game!");
					}
				}
			}
		}

		if (a == Action.RIGHT_CLICK_BLOCK && Games.getGame(p) != null && b != null
				&& b.getType() == Games.getGame(p).place && b.getRelative(BlockFace.UP).getType() == Material.AIR
				&& !Games.getGame(p).starting) {
			event.setCancelled(true);
			Games.getGame(p).buyTower(p);
		}

		if (p.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		event.setJoinMessage("");

		for (Player p2 : Bukkit.getOnlinePlayers()) {
			if (p2 == p)
				continue;
			if (Games.getGame(p2) != null) {
				p.hidePlayer(p2);
				p2.hidePlayer(p);
			} else {
				p2.showPlayer(p);
			}
		}

		if (!LevelManager.isInTable(p)) {
			LevelManager.addToTable(p);
		}

		p.getInventory().clear();
		p.teleport(p.getWorld().getSpawnLocation());
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		Games ga = Games.getGame(p);
		event.getRecipients().clear();
		event.setCancelled(true);
		if (lastMessage.containsKey(p) && lastMessage.get(p).equalsIgnoreCase(event.getMessage())
				|| event.getMessage().startsWith("@")) {
			return;
		} else {
			lastMessage.remove(p);
			lastMessage.put(p, event.getMessage());
		}
		for (Player p2 : Bukkit.getOnlinePlayers()) {
			Games ga2 = Games.getGame(p2);
			if (ga == ga2) {
				p2.sendMessage(p.getDisplayName() + ": " + event.getMessage());
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		onQuit(event.getPlayer());
		event.setQuitMessage("");
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		onQuit(event.getPlayer());
		event.setLeaveMessage("");
	}

	public void onQuit(Player p) {
		Games ga = Games.getGame(p);
		if (ga != null) {
			ga.players.remove(p);
			ga.sendToAll("Quit", p.getName() + " has quit");
			ga.onLeave(p);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.PLAYER) {
			event.setCancelled(true);
		}
		if (event.getDamager().getType() == EntityType.PLAYER) {
			Player p = (Player) event.getDamager();

			if (Games.getGame(p) != null) {
				event.setCancelled(true);
				Games g = Games.getGame(p);

				if (g.towers.containsKey(event.getEntity())) {
					LivingEntity le = (LivingEntity) event.getEntity();

					Inventory i = Bukkit.createInventory(null, 9, "Tower Info");

					Tower t = g.towers.get(le);

					boolean ownsTow = g.towOwn.containsKey(le) && g.towOwn.get(le) == p;

					int sId = 4;

					if (t.getUpgrade(2, 4) != null && t.hasUpgrade(g, le, 2, 4)) {
						sId = 5;
						Upgrade u = t.getUpgrade(2, 4);

						boolean canUse = g.canUseAbility(le);
						long timeUntilAbil = g.abilcool.containsKey(le) ? (g.abilcool.get(le) - System
								.currentTimeMillis()) / 1000 : 0;

						i.setItem(ownsTow ? 3 : 4, ItemMetaUtils.setLore(ItemMetaUtils.setItemName(new ItemStack(
								canUse ? Material.BEACON : Material.OBSIDIAN), ChatColor.GOLD + "Use Ability: "
								+ u.name), canUse ? ChatColor.AQUA + u.desc : ChatColor.RED
								+ "Ability on cooldown for " + timeUntilAbil + " more seconds!"));
					}

					if (ownsTow) {

						i.setItem(sId, ItemMetaUtils.setLore(
								ItemMetaUtils.setItemName(new ItemStack(Material.WOOL, 1, (short) 14), ChatColor.RED
										+ "Sell Tower"), ChatColor.AQUA + "Sell Price: " + g.getSellPrice(le)));

					}

					selected.put(p, (LivingEntity) event.getEntity());
					p.openInventory(i);
				}
			}
		}
		if (event.getEntityType() == EntityType.VILLAGER) {
			if (event.getDamager().getType() != EntityType.PLAYER) {
				event.setDamage(((LivingEntity) event.getDamager()).getHealth());
				event.getDamager().remove();
			} else
				event.setCancelled(true);
		}
		if (event.getDamager() instanceof Projectile && Games.dmg.containsKey(event.getDamager())) {
			event.setDamage(Games.dmg.get(event.getDamager()));
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if ((event.getEntityType() == EntityType.ZOMBIE || event.getEntityType() == EntityType.SKELETON)
				&& event.getCause() == DamageCause.FIRE_TICK) {
			event.setCancelled(true);
			event.getEntity().setFireTicks(0);
		}

		if (event.getEntityType() == EntityType.ZOMBIE) {
			Zombie z = (Zombie) event.getEntity();
			HealthColors.update(z);
			event.setDamage((int) Math.ceil(event.getDamage()) + 1);
			if (HealthColors.getColor(z.getHealth()) == HealthColors.BLACK
					&& (event.getCause() == DamageCause.ENTITY_EXPLOSION || event.getCause() == DamageCause.BLOCK_EXPLOSION)) {
				event.setCancelled(true);
			}
		}

		if (event.getEntityType() == EntityType.PLAYER && event.getCause() != DamageCause.VOID) {
			event.setCancelled(true);
		}

		if (event.getEntityType() != EntityType.ZOMBIE
				&& (event.getCause() != DamageCause.ENTITY_ATTACK && event.getCause() != DamageCause.VOID)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		event.setDroppedExp(0);
		LivingEntity le = event.getEntity();
		Games g = null;
		for (Games gam : Games.values()) {
			if (gam.zombies.contains(le)) {
				g = gam;
				break;
			}
		}
		if (g == null)
			return;
		g.addMoney((int) le.getMaxHealth());
		for (Player p : g.players) {
			LevelManager.upgXP(p, 1);
		}
		for (PotionEffect po : event.getEntity().getActivePotionEffects()) {
			if (po.getType() == PotionEffectType.SLOW && po.getAmplifier() == 21) {
				int range = 2;
				float h = 0.3F;
				Vector[] vs = { new Vector(range, h, 0), new Vector(range, h, range), new Vector(0, h, range),
						new Vector(-range, h, range), new Vector(-range, h, 0), new Vector(-range, h, -range),
						new Vector(0, h, -range), new Vector(range, h, -range) };

				for (Vector vec : vs) {
					int damage = 1;
					Arrow a = Games.launchArrow(g, le.getLocation().clone().add(vec).add(0, 1, 0), vec, 0.3F, 50,
							damage);
					a.setTicksLived(0);
				}

			}
		}
	}

	@EventHandler
	public void onEntityTarget(EntityTargetEvent event) {
		if (event.getTarget() != null
				&& (event.getTarget().getType() == EntityType.PLAYER || event.getTarget().getType() == EntityType.ZOMBIE)
				|| event.getEntityType() == EntityType.SNOWMAN) {
			event.setCancelled(true);
		}
		for (Games g : Games.values()) {
			if (g.zombies.contains(event.getEntity())) {
				if (event.getTarget() != null && event.getTarget() != g.v) {
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Inventory i = event.getInventory();
		ItemStack it = event.getCurrentItem();
		if (event.getWhoClicked() instanceof Player) {
			Player p = (Player) event.getWhoClicked();
			Games g = Games.getGame(p);

			if (i.getName().equalsIgnoreCase("Towers")) {
				event.setCancelled(true);

				Tower t = Tower.valueOf(ChatColor.stripColor(ItemMetaUtils.getItemName(it)).replace(' ', '_'));
				if (g != null) {
					g.purchaseTower(p, t);
				}
			}

			if (i.getName().equalsIgnoreCase("Skeleton Lab")) {
				event.setCancelled(true);

				if (it.getType() == Material.WOOL) {
					LabItem li = LabItem.valueOf(ChatColor.stripColor(ItemMetaUtils.getItemName(it).replace(' ', '_')));

					if (li != null && LevelManager.getCredits(p) >= li.getUpgradeCost(p)) {

						int cost = li.getUpgradeCost(p);
						if (cost > -1) {
							LevelManager.addCredits(p, -cost);
							li.setUpgrade(p, SQLTable.Zombiez.getInt("Username", p.getName(), li.sql) + 1);

							p.sendMessage(ChatColor.DARK_AQUA + "Bought " + li.name().replace('_', ' ') + " for "
									+ cost + " credits!");
						} else {
							p.sendMessage("§3That item is fully upgraded!");
						}
						// p.closeInventory();

					}
				}

				updateLabInterface(p, i);
			}

			if (i.getName().equalsIgnoreCase("Tower Info")) {
				event.setCancelled(true);

				if (it.getType() == Material.WOOL) {
					LivingEntity e = selected.get(p);
					selected.remove(p);

					g.addMoney(g.getSellPrice(e), p);

					e.remove();

					p.closeInventory();
				}

				if (it.getType() == Material.BEACON) {
					LivingEntity le = selected.get(p);
					selected.remove(p);

					if (g.canUseAbility(le)) {
						p.sendMessage(ChatColor.RED + "Used Ability!");

						Tower t = g.towers.get(le);
						t.onAbility(g, le);
						g.setAbilityCooldown(le, t.getAbilCool());
					} else {
						p.sendMessage(ChatColor.RED + "You cannot use that ability right now!");
					}

					p.closeInventory();
				}
			}

			if (i.getName().equalsIgnoreCase("Upgrades")) {
				event.setCancelled(true);

				String upgN = ItemMetaUtils.getItemName(it);
				upgN = ChatColor.stripColor(upgN);

				Tower t = g.towers.get(selected.get(p));
				Upgrade u = t.getUpgrade(upgN);

				if (g.cash.get(p) >= u.cost) {

					g.addMoney(-u.cost, p);
					t.giveUpgrade(g, selected.get(p), u.s, u.id);
					p.sendMessage(ChatColor.RED + "Bought " + upgN + "!");

				} else
					p.sendMessage(ChatColor.RED + "Not enough cash!");
				p.closeInventory();
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (selected.containsKey(event.getPlayer())) {
			selected.remove(event.getPlayer());
		}
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player p = event.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}

		if (Games.getGame(p) != null) {
			event.setCancelled(true);
			Games g = Games.getGame(p);

			if (g.towers.containsKey(event.getRightClicked())) {
				LivingEntity le = (LivingEntity) event.getRightClicked();

				selected.put(p, le);

				if (!g.upg1.containsKey(le)) {
					g.upg1.put(le, 0);
				}
				if (!g.upg2.containsKey(le)) {
					g.upg2.put(le, 0);
				}

				String towName = le.getCustomName().replace(' ', '_');
				Tower t = Tower.valueOf(towName);

				Inventory i = Bukkit.createInventory(null, 9, "Upgrades");

				Upgrade u1 = t.getUpgrade(1, g.upg1.get(le) + 1);
				if (u1 != null && !(g.upg2.get(le) > 2 && g.upg1.get(le) == 2)) {
					i.setItem(1, ItemMetaUtils.setLore(
							ItemMetaUtils.setItemName(new ItemStack(Material.WOOL, u1.id, (short) 14), ChatColor.GOLD
									+ u1.name), ChatColor.AQUA + "Cost: " + u1.cost, ChatColor.LIGHT_PURPLE + u1.desc));
				} else {
					i.setItem(
							1,
							ItemMetaUtils.setItemName(new ItemStack(Material.OBSIDIAN), ChatColor.RED
									+ "Upgrade Path Closed"));
				}

				Upgrade u2 = t.getUpgrade(2, g.upg2.get(le) + 1);
				if (u2 != null && !(g.upg1.get(le) > 2 && g.upg2.get(le) == 2)) {
					i.setItem(7, ItemMetaUtils.setLore(
							ItemMetaUtils.setItemName(new ItemStack(Material.WOOL, u2.id, (short) 11), ChatColor.GOLD
									+ u2.name), ChatColor.AQUA + "Cost: " + u2.cost, ChatColor.LIGHT_PURPLE + u2.desc));
				} else {
					i.setItem(
							7,
							ItemMetaUtils.setItemName(new ItemStack(Material.OBSIDIAN), ChatColor.RED
									+ "Upgrade Path Closed"));
				}

				p.openInventory(i);
			}
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		event.getEntity().remove();
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		event.blockList().clear();
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
	}

	@EventHandler
	public void onEntityFormBlock(EntityBlockFormEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityCreateBlock(EntityChangeBlockEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		event.setCancelled(Games.getGame(event.getPlayer()) != null);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player p = event.getPlayer();
		if (Games.getGame(p) != null) {
			Games g = Games.getGame(p);

			if (event.getTo().distance(g.spawn) > 150) {
				event.setTo(event.getFrom());
			}
		}
	}
}
