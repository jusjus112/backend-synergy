package usa.devrocoding.synergy.spigot.utilities;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author Hypixel LLC
 * @Website hypixel.net
 */
public class UtilVector {

	public static final Vector rotateAroundAxisX(Vector v, double angle) {
		angle = -1 * Math.toRadians(angle);
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static final Vector rotateAroundAxisY(Vector v, double angle) {
		angle = -1 * Math.toRadians(angle);
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static final Vector rotateAroundAxisZ(Vector v, double angle) {
		angle = -1 * Math.toRadians(angle);
		double x, y, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos - v.getY() * sin;
		y = v.getX() * sin + v.getY() * cos;
		return v.setX(x).setY(y);
	}

	public static Vector getVector(Location loc1, Location loc2) {
		return new Vector(loc2.getX() - loc1.getX(), loc2.getY() - loc1.getY(), loc2.getZ() - loc1.getZ());
	}

	public static Vector getRandomVector() {
		double x = (ThreadLocalRandom.current().nextInt(100) + 10 - 50) / 70.00;
		double z = (ThreadLocalRandom.current().nextInt(100) + 10 - 50) / 70.00;
		double y = 1;
		return new Vector(x, y, z);
	}

	public static Vector getVectorForPoints(Location l1, Location l2) {
		double g = -0.08;
		double d = l2.distance(l1);
		double t = d;
		double vX = (1.0 + 0.07 * t) * (l2.getX() - l1.getX()) / t;
		double vY = (1.0 + 0.03 * t) * (l2.getY() - l1.getY()) / t - 0.5 * g * t;
		double vZ = (1.0 + 0.07 * t) * (l2.getZ() - l1.getZ()) / t;
		return new Vector(vX, vY, vZ);
	}

	public static Vector getVectorForPointsNoGravity(Location l1, Location l2) {
		double d = l2.distance(l1);
		double t = d;
		double vX = (1.0 + 0.07 * t) * (l2.getX() - l1.getX()) / t;
		double vY = (1.0 + 0.03 * t) * (l2.getY() - l1.getY()) / t;
		double vZ = (1.0 + 0.07 * t) * (l2.getZ() - l1.getZ()) / t;
		return new Vector(vX, vY, vZ);
	}

	public static final Vector rotateVector(Vector v, double angleX, double angleY, double angleZ) {
		rotateAroundAxisX(v, angleX);
		rotateAroundAxisY(v, angleY);
		rotateAroundAxisZ(v, angleZ);
		return v;
	}

	public static final double angleToXAxis(Vector vector) {
		return Math.atan2(vector.getX(), vector.getY());
	}

}
