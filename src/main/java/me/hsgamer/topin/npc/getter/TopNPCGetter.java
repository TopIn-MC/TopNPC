package me.hsgamer.topin.npc.getter;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.hsgamer.topin.core.config.PluginConfig;
import me.hsgamer.topin.core.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import me.hsgamer.topin.npc.getter.command.RemoveTopNPCCommand;
import me.hsgamer.topin.npc.getter.command.SetTopNPCCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TopNPCGetter implements Getter {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
  private final List<TopNPC> topNPCList = new ArrayList<>();
  private final NPCListener listener = new NPCListener(this);
  private final SetTopNPCCommand setTopNPCCommand = new SetTopNPCCommand(this);
  private final RemoveTopNPCCommand removeTopNPCCommand = new RemoveTopNPCCommand(this);
  private PluginConfig npcConfig;
  private BukkitTask updateTask;

  @Override
  public void register() {
    ConfigurationSerialization.registerClass(TopNPC.class);
    npcConfig = new PluginConfig(getInstance(), "npc.yml");
    npcConfig.getConfig().options().copyDefaults(true);
    UPDATE_PERIOD.setConfig(npcConfig);
    npcConfig.saveConfig();
    loadNPC();

    Bukkit.getPluginManager().registerEvents(listener, getInstance());
    final BukkitRunnable updateRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        topNPCList.forEach(TopNPC::update);
      }
    };
    updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
    getInstance().getCommandManager().register(setTopNPCCommand);
    getInstance().getCommandManager().register(removeTopNPCCommand);
  }

  @Override
  public void unregister() {
    updateTask.cancel();
    HandlerList.unregisterAll(listener);
    getInstance().getCommandManager().unregister(setTopNPCCommand);
    getInstance().getCommandManager().unregister(removeTopNPCCommand);
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

  public boolean containsNPC(int id) {
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
