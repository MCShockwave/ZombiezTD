package net.mcshockwave.ZTD.Games;

import net.mcshockwave.ZTD.Zombiez;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Zombie;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public enum Wave {

	w0(
		20,
		"1x20"),
	w1(
		15,
		"1x30"),
	w2(
		10,
		"1x20:2x5"),
	w3(
		10,
		"1x30:2x15"),
	w4(
		10,
		"1x5:2x25"),
	w5(
		8,
		"1x15:2x15:3x4"),
	w6(
		8,
		"1x20:2x25:3x5"),
	w7(
		8,
		"1x10:2x20:3x14"),
	w8(
		8,
		"3x30"),
	w9(
		6,
		"2x102"),
	w10(
		7,
		"1x10:2x10:3x12:4x2"),
	w11(
		7,
		"2x15:3x10:4x5"),
	w12(
		5,
		"1x100:3x23:4x4"),
	w13(
		7,
		"1x49:2x15:3x10:4x9"),
	w14(
		7,
		"1x20:3x12:4x5:5x3"),
	w15(
		7,
		"3x20:4x8:5x4"),
	w16(
		7,
		"4--x8"),
	w17(
		6,
		"3x80"),
	w18(
		7,
		"3x10:4x4:4--x5:5x7"),
	w19(
		7,
		"11x6"),
	w20(
		6,
		"5x14"),
	w21(
		6,
		"11x8"),
	w22(
		6,
		"11x9"),
	w23(
		6,
		"3-"),
	w24(
		6,
		"4--x31"),
	w25(
		6,
		"5x23:11x4"),
	w26(
		3,
		"1x120:2x55:3x45:4x45"),
	w27(
		6,
		"23x4"),
	w28(
		6,
		"5x25:5--x12"),
	w29(
		6,
		"23x9"),
	w30(
		6,
		"11x8:11--x2"),
	w31(
		6,
		"11x53:23x8"),
	w32(
		6,
		"4-x20"),
	w33(
		6,
		"4x140:11x5"),
	w34(
		6,
		"5x35:11x25:47x5"),
	w35(
		6,
		"5x81"),
	w36(
		6,
		"11x40:11-x7:23x15:11x10"),
	w37(
		6,
		"5--x42:11x17:23x14:11x10:47x4"),
	w38(
		6,
		"11x20:23x20:11x20:43x18"),
	w39(
		6,
		"47x10:104x4"),
	w40(
		4,
		"11x120"),
	w41(
		6,
		"47-x6:47--x6"),
	w42(
		6,
		"47x10:104x7"),
	w43(
		6,
		"11x50"),
	w44(
		2,
		"5x200:11x8:47x25"),
	w45(
		6,
		"616"),
	w46(
		6,
		"5-x70:104x12"),
	w47(
		6,
		"5--x120:47x50"),
	w48(
		2,
		"3x343:11x20:47x20:47--x10:104x18"),
	w49(
		7,
		"1x20:23x8:104x20:616x2"),
	w50(
		6,
		"47--x10:104x28"),
	w51(
		6,
		"47x25:104x10:616x2"),
	w52(
		6,
		"5-x80:616x3"),
	w53(
		6,
		"104x35:616x2"),
	w54(
		6,
		"104x45:616"),
	w55(
		6,
		"47x40:616"),
	w56(
		6,
		"47x40:616x4"),
	w57(
		6,
		"104x29:616x5"),
	w58(
		6,
		"23-x28:104x50"),
	w59(
		6,
		"3164"),
	w60(
		6,
		"11--x150:616x5"),
	w61(
		2,
		"5x300:47---x15:616x6"),
	w62(
		6,
		"23x75:104x122"),
	w63(
		6,
		"616x9"),
	w64(
		8,
		"11x100:47x70:104x50:616x3:3164x2"),
	w65(
		8,
		"616x12"),
	w66(
		8,
		"104x15:616x10"),
	w67(
		7,
		"616x4:3164"),
	w68(
		6,
		"23x60:104--x70"),
	w69(
		3,
		"47-x200:616x4"),
	w70(
		6,
		"104x30:616x10"),
	w71(
		6,
		"104x38:3164x2"),
	w72(
		8,
		"616x9:3164x2"),
	w73(
		4,
		"104x200:3164"),
	w74(
		8,
		"23x28:616x4:3164x3"),
	w75(
		6,
		"104--x60"),
	w76(
		8,
		"616x14:3164x5"),
	w77(
		4,
		"47x150:104x75:104-x72:3164"),
	w78(
		2,
		"47--x500:3164x7"),
	w79(
		8,
		"616x31"),
	w80(
		8,
		"3164x9"),
	w81(
		3,
		"47---x400:3164x10"),
	w82(
		6,
		"104x150:616x30"),
	w83(
		10,
		"616x50:3164x10"),
	w84(
		10,
		"16656");

	int[]						spawn;

	public int					delay, cash;

	HashMap<Integer, Integer>	special;

	Wave(int delay, String w) {

		special = new HashMap<Integer, Integer>();
		this.delay = delay;
		this.cash = 100 + ordinal();
		String[] wa = w.split(":");

		int aSize = 0;

		for (String s : wa) {
			String[] ts = s.split("x");
			if (ts.length > 1) {
				aSize += Integer.parseInt(ts[1]);
			} else
				aSize += 1;
		}

		spawn = new int[aSize];

		int id = 0;
		for (String s : wa) {

			int times = 1;

			String[] mul = s.split("x");
			if (mul.length > 1) {
				times = Integer.parseInt(mul[1]);
			}

			for (int t = 0; t < times; t++) {

				int count = StringUtils.countMatches(s, "-");
				if (count == 1) {
					special.put(id, 1);
				} else if (count == 2) {
					special.put(id, 2);
				} else if (count == 3) {
					special.put(id, 3);
				} else {
					special.put(id, 0);
				}

				if (mul.length > 1) {
					spawn[id] = Integer.parseInt(mul[0].replace("-", ""));
				} else {
					spawn[id] = Integer.parseInt(s.replace("-", ""));
				}

				id++;

			}
		}

	}

	public static void spawnWave(final Games g, int wave, final Location l) {
		final Wave w = Wave.valueOf("w" + wave);
		g.wave_i = 0;
		g.wave_isInProgress = true;

		g.wtas = Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				if (w.special.containsKey(g.wave_i)) {
					int x = w.special.get(g.wave_i);
					Type ca = (x == 1 || x == 3 ? Type.CAMO : null);
					Type re = (x == 2 || x == 3 ? Type.REGEN : null);

					Zombie z = g.spawnZombie(l, w.spawn[g.wave_i], ca, re);
					HealthColors.update(z);
				}

				if (g.wave_i < w.spawn.length) {
					g.wave_i++;
				} else if (g.wave_isInProgress) {
					checkForEnd(g, w);
					g.wtas.cancel();
					g.wtas = null;
				}
			}
		}, w.delay, w.delay);
	}

	public static void checkForEnd(final Games g, final Wave w) {
		g.wtas2 = Bukkit.getScheduler().runTaskTimer(Zombiez.instance, new Runnable() {
			public void run() {
				if (g.zombies.size() <= 0) {
					g.onWaveEnd();
					g.wave_isInProgress = false;
					g.wtas2.cancel();
					g.wtas2 = null;
				}
			}
		}, 5, 5);
	}

}
