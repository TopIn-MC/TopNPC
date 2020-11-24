package me.hsgamer.topin.npc;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import static me.hsgamer.topin.Permissions.PREFIX;
import static me.hsgamer.topin.core.bukkit.utils.PermissionUtils.createPermission;

public class Permissions {

    public static final Permission NPC = createPermission(PREFIX + ".npc", null,
            PermissionDefault.OP);

    private Permissions() {
        // EMPTY
    }
}
