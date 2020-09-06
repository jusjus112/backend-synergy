package usa.devrocoding.synergy.spigot.assets;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import org.jetbrains.annotations.NotNull;

public class RandomCollection<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random = new Random();
    private double total = 0;
   
    public void add(double weight, @NotNull E value) {
        if (weight > 0) {
            total += weight;
            map.put(total, value);
        }
    }

    @NotNull
    public E getRandomValue() {
      if (total == 0) {
        throw new RuntimeException("Trying to get a random value from an empty RandomCollection");
      }
      final double value = random.nextDouble() * total;
      return map.higherEntry(value).getValue();
    }

}