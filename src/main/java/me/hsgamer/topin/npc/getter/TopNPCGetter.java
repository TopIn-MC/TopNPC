package me.hsgamer.topin.npc.getter;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.hsgamer.topin.core.config.PluginConfig;
import me.hsgamer.topin.core.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TopNPCGetter implements Getter {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
  private final List<TopNPC> topNPCList = new ArrayList<>();
  private PluginConfig npcConfig;
  private BukkitTask updateTask;

  @Override
  public void register() {
    ConfigurationSerialization.registerClass(TopNPC.class);
    npcConfig = new PluginConfig(getInstance(), "skull.yml");
    npcConfig.getConfig().options().copyDefaults(true);
    UPDATE_PERIOD.setConfig(npcConfig);
    npcConfig.saveConfig();
    loadNPC();

    final BukkitRunnable updateRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        topNPCList.forEach(TopNPC::update);
      }
    };
    updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
  }

  @Override
  public void unregister() {
    updateTask.cancel();
    saveNPC();
    ConfigurationSerialization.unregisterClass(TopNPC.class);
  }

  public void addNPC(TopNPC topNPC) {
    removeNPC(topNPC.getId());
    topNPCList.add(topNPC);
  }

  public void removeNPC(int id) {
    topNPCList.removeIf(topNPC -> topNPC.getId() == id);
  }

  public boolean containsSkull(int id) {
    return topNPCList.stream().anyMatch(topNPC -> topNPC.getId() == id);
  }

  @Override
  public String getName() {
    return "CitizensNPC";
  }

  @SuppressWarnings("unchecked")
  private void loadNPC() {
    FileConfiguration config = npcConfig.getConfig();
    if (config.isSet("data")) {
      topNPCList.addAll((Collection<? extends TopNPC>) config.getList("data"));
    }
  }

  public void saveNPC() {
    FileConfiguration config = npcConfig.getConfig();
    config.set("data", topNPCList);
    npcConfig.saveConfig();
  }
}
