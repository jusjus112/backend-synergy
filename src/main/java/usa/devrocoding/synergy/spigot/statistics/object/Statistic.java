package usa.devrocoding.synergy.spigot.statistics.object;

import usa.devrocoding.synergy.assets.Synergy;

public class Statistic {

  private double data;

  public Statistic(double data){
    this.data = data;
  }

  public void overwrite(double data){
    this.data = data;
  }

  public void add(double data){
    this.data += data;
  }

  public double get(){
    return this.data;
  }

}
