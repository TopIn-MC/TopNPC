package me.hsgamer.topin.npc;

import static me.hsgamer.topin.Permissions.PREFIX;
import static me.hsgamer.topin.utils.PermissionUtils.createPermission;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Permissions {

  public static final Permission NPC = createPermission(PREFIX + ".npc", null,
      PermissionDefault.OP);

  private Permissions() {
    // EMPTY
  }
}
