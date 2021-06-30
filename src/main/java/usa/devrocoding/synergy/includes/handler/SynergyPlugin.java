package usa.devrocoding.synergy.includes.handler;

public interface SynergyPlugin {

  String pluginName();

  void preInit();
  void init();
  void preDeInit();
  void deinit();


}
