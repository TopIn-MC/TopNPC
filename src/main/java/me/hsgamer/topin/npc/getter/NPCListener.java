package me.hsgamer.topin.npc.getter;

import net.citizensnpcs.api.event.NPCRemoveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {

  private final TopNPCGetter getter;

  public NPCListener(TopNPCGetter getter) {
    this.getter = getter;
  }

  @EventHandler
  public void onDelete(NPCRemoveEvent event) {
    getter.removeNPC(event.getNPC().getId());
  }
}
