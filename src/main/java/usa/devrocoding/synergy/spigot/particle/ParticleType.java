package usa.devrocoding.synergy.spigot.particle;

import org.bukkit.Location;

import java.util.*;

/**
 * @Author JusJusOneOneTwo
 * @Website devrocoding.com
 * @Created 20-01-2016
 */
public enum ParticleType {
	
	Enchanted(
			"Enchanted"),
	FlameRings(
			"Flame Rings"),
	Hearts(
			"In Love"),
	Rainbow(
			"Rainbow Swirl"),
	RainCloud(
			"Rain Cloud"),
	Sparklez(
			"Sparklez"),
	Vortex(
			"Vortex");

	private String name;
	public final static Map<ParticleType, List<Location>> locs = new HashMap<>();

	private ParticleType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void Activate(Location loc) {
		List<Location> list = getLocations();
		if (this==Rainbow)
			list.clear();
		if (!isActive(loc)){
			list.add(new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ()));
			locs.put(this, list);
		}
	}
	
	public void deActivate(){
		locs.remove(this);
	}

	public static String serialize(List<ParticleType> particles) {
		String s = "";
		for (int i = 0; i < particles.size(); i++) {
			String add = ":";
			if (i == 0) {
				add = "";
			}
			s = s + add + particles.get(i);
		}
		return s;
	}

	public boolean isActive(Location loc) {
		return (locs.containsKey(this)&&locs.get(this).contains(loc));
	}
	
	public List<Location> getLocations(){
		if (!locs.containsKey(this)||locs.get(this)==null)
			return new ArrayList<>();
		return locs.get(this);
	}
	
	public static List<ParticleType> deserialize(String s) {
		List<ParticleType> l = new ArrayList<>();
		List<String> tmp = Arrays.asList(s.split(":"));
		for (String cur : tmp) {
			if (cur == null)
				continue;
			if (cur.equals(""))
				continue;
			ParticleType particleType = ParticleType.zylemValueOf(cur);
			if (particleType == null)
				continue;
			l.add(particleType);
		}
		return l;
	}

	public static ParticleType zylemValueOf(String name) {
		for (ParticleType particleType : ParticleType.values()) {
			if (particleType.name().equals(name))
				return particleType;
		}
		return null;
	}

}
