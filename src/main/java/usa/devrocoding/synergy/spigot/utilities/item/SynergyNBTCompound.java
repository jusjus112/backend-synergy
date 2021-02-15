package usa.devrocoding.synergy.spigot.utilities.item;

import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;

public class SynergyNBTCompound extends NBTTagCompound {

  @Override
  public void setBoolean(String s, boolean b) {
    super.setBoolean(s, b);
  }

  @Override
  public void setByte(String s, byte b) {
    super.setByte(s, b);
  }

  @Override
  public void setString(String s, String s1) {
    super.setString(s, s1);
  }

  @Override
  public void setDouble(String s, double v) {
    super.setDouble(s, v);
  }

  @Override
  public void setFloat(String s, float v) {
    super.setFloat(s, v);
  }

  @Override
  public void setInt(String s, int i) {
    super.setInt(s, i);
  }

  @Override
  public void setLong(String s, long l) {
    super.setLong(s, l);
  }

  @Override
  public void setShort(String s, short i) {
    super.setShort(s, i);
  }

  @Override
  public void set(String s, NBTBase nbtBase) {
    super.set(s, nbtBase);
  }
}
