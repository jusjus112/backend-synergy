package usa.devrocoding.synergy.spigot.economy.object;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import usa.devrocoding.synergy.spigot.statistics.object.StatisticType;
import usa.devrocoding.synergy.spigot.user.object.MessageModification;
import usa.devrocoding.synergy.spigot.user.object.SynergyUser;
import usa.devrocoding.synergy.spigot.utilities.UtilMath;

@Getter
public class Economy{

  @Setter
  private SynergyUser synergyUser;

  private BigDecimal coins;
  private int shards;
  private long experience;

  public Economy(BigDecimal coins, int shards, long experience){
    this.coins = coins;
    this.shards = shards;
    this.experience = experience;
  }

  public void addCoins(double coins){
    this.coins = this.coins.add(new BigDecimal(coins));
    synergyUser.getStatistic(StatisticType.EARNED_COINS).overwrite(this.coins.doubleValue());
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§e+ " + coins + " Coin(s).");
  }

  public void setCoins(double coins){
    this.coins = new BigDecimal(coins);
    if (getCoins() < 0){
      this.coins = new BigDecimal("0");
    }
    synergyUser.getStatistic(StatisticType.EARNED_COINS).overwrite(this.coins.doubleValue());
  }

  public void addShards(int shards){
    this.shards += shards;
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§e+ " + shards + " Crystal(s).");
  }

  public void addXP(long experience){
    this.experience += experience;
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§e+ " + experience + " XP.");
  }

  public double getCoins() {
    return this.coins.doubleValue();
  }

  public BigDecimal getCoinsDecimal(){
    return this.coins;
  }

  public void addXP(long experience, boolean hologram){
    this.experience += experience;
    if (hologram){
      // TODO: Spawn hologram
    }
  }

  public void removeCoins(double coins){
    this.coins = this.coins.subtract(new BigDecimal(coins));
    if (getCoins() < 0){
      this.coins = new BigDecimal("0");
      coins = getCoins();
    }
    synergyUser.getStatistic(StatisticType.EARNED_COINS).overwrite(this.coins.doubleValue());
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§c- " + coins + " Coin(s).");
  }
  public void removeShards(int shards){
    this.shards -= shards;
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§c- " + shards + " Crystal(s).");
  }
  public void removeXP(long experience){
    this.experience -= experience;
    synergyUser.sendModifactionMessage(MessageModification.RAW, "§c- " + experience + " XP.");
  }

}
