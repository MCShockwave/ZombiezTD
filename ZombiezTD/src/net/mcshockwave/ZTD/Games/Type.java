package net.mcshockwave.ZTD.Games;

import java.util.ArrayList;
import java.util.List;

import net.mcshockwave.MCS.Utils.ItemMetaUtils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public enum Type {

	REGEN(false, true, true, null, Color.RED, Color.RED),
	CAMO(true, false, false, Color.GREEN, null, null);

	public boolean helm, legs, boots;
	public Color helmColor, legColor, bootsColor;
	public ArrayList<Zombie> types;

	private Type(boolean helm, boolean legs, boolean boots, Color helmColor, Color legColor, Color bootsColor) {
		this.helm = helm;
		this.legs = legs;
		this.boots = boots;
		this.helmColor = helmColor;
		this.legColor = legColor;
		this.bootsColor = bootsColor;
		types = new ArrayList<Zombie>();
	}

	public boolean isType(Zombie z) {
		return types.contains(z);
	}

	public void setType(Zombie z) {
		types.add(z);
		EntityEquipment ee = z.getEquipment();
		if (helm && ee.getHelmet() != null) {
			ee.setHelmet(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_HELMET), helmColor));
		}
		if (legs && ee.getLeggings() != null) {
			ee.setLeggings(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_LEGGINGS), legColor));
		}
		if (boots && ee.getBoots() != null) {
			ee.setBoots(ItemMetaUtils.setLeatherColor(new ItemStack(Material.LEATHER_BOOTS), bootsColor));
		}
	}
	
	public static List<Type> getTypes(Zombie z) {
		List<Type> ret = new ArrayList<Type>();
		for (Type t : Type.values()) {
			if (t.isType(z)) {
				ret.add(t);
			}
		}
		return ret;
	}

}
