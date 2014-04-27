package net.mcshockwave.ZTD.Games;

import java.util.List;
import java.util.UUID;

import net.mcshockwave.MCS.Utils.ItemMetaUtils;
import net.minecraft.server.v1_7_R2.AttributeInstance;
import net.minecraft.server.v1_7_R2.AttributeModifier;
import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.GenericAttributes;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftLivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public enum HealthColors {

	ZOMG(16656, null, 1),
	BFB(3164, null, 1),
	MOAB(616, null, 1),
	CERAMIC(104, Color.fromRGB(187, 97, 0), 2.5),
	RAINBOW(47, Color.MAROON, 2.2),
	LEAD(23, Color.GRAY, 1),
	BLACK(11, Color.BLACK, 1.8),
	PINK(5, Color.fromRGB(255, 128, 128), 3.5),
	YELLOW(4, Color.YELLOW, 3.2),
	GREEN(3, Color.LIME, 1.8),
	BLUE(2, Color.BLUE, 1.4),
	RED(1, Color.RED, 1),
	DEAD(-1, Color.RED, 1);

	public int health;
	public Color c;
	public double speed;

	private HealthColors(int damage, Color c, double speed) {
		this.health = damage;
		this.c = c;
		this.speed = speed;
	}

	public static HealthColors getColor(double h) {
		HealthColors[] hcs = HealthColors.values();
		for (int id = 0; id < hcs.length; id++) {
			HealthColors hc = hcs[id];
			if (hc.health >= h && hcs[id + 1].health < h) {
				return hc;
			}
		}
		return null;
	}

	public static void update(Zombie z) {
		HealthColors hc = getColor(z.getHealth());
		Games g = null;
		for (Games ga : Games.values()) {
			if (ga.zombies.contains(z)) {
				g = ga;
			}
		}
		if (g != null) {
			setSpeed(z, hc.speed / (System.currentTimeMillis() / 1000 <= g.lastSaboTime ? 2 : 1));
		} else
			setSpeed(z, hc.speed);
		EntityEquipment ee = z.getEquipment();
		List<Type> lt = Type.getTypes(z);
		for (Type t : lt) {
			t.types.remove(z);
			t.setType(z);
		}
		if (hc == ZOMG) {
			ee.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		} else if (hc == BFB) {
			ee.setChestplate(new ItemStack(Material.GOLD_CHESTPLATE));
		} else if (hc == MOAB) {
			ee.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		} else
			ee.setChestplate(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_CHESTPLATE), hc.c));
	}

	private static final UUID followRangeUID = UUID.fromString("1737400d-3c18-41ba-8314-49a158481e1e");
	private static final UUID knockbackResistanceUID = UUID.fromString("8742c557-fcd5-4079-a462-b58db99b0f2c");
	private static final UUID movementSpeedUID = UUID.fromString("206a89dc-ae78-4c4d-b42c-3b31db3f5a7c");

	public static void setSpeed(Zombie z, double speed) {
		EntityLiving nmsE = ((CraftLivingEntity) z).getHandle();

		AttributeInstance ati = nmsE.getAttributeInstance(GenericAttributes.d);
		AttributeModifier atm = new AttributeModifier(movementSpeedUID, "Zombiez speed", speed / 6, 1);
		ati.b(atm);
		ati.a(atm);

		AttributeInstance ari = nmsE.getAttributeInstance(GenericAttributes.b);
		AttributeModifier arm = new AttributeModifier(followRangeUID, "Zombiez range", 1000, 1);
		ari.b(arm);
		ari.a(arm);

		AttributeInstance akb = nmsE.getAttributeInstance(GenericAttributes.c);
		AttributeModifier akm = new AttributeModifier(knockbackResistanceUID, "Zombiez kb resist", 10, 1);
		akb.b(akm);
		akb.a(akm);
	}

}
