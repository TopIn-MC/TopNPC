package me.hsgamer.topin.npc;

import static me.hsgamer.topin.TopIn.getInstance;

import me.hsgamer.topin.addon.object.Addon;
import me.hsgamer.topin.npc.getter.TopNPCGetter;
import org.bukkit.Bukkit;

public final class TopNPCAddon extends Addon {

  private TopNPCGetter getter;

  @Override
  public boolean onLoad() {
    if (Bukkit.getPluginManager().getPlugin("Citizens") == null) {
      getPlugin().getLogger().warning("[TopNPC] Citizens is not found");
      return false;
    }
    return true;
  }

  @Override
  public void onEnable() {
    getter = new TopNPCGetter();
    getInstance().getGetterManager().register(getter);
  }

  @Override
  public void onDisable() {
    getInstance().getGetterManager().unregister(getter);
  }

  @Override
  public void onReload() {
    getter.saveNPC();
  }
}
