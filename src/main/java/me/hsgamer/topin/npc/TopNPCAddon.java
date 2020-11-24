package me.hsgamer.topin.npc;

import me.hsgamer.topin.addon.TopInAddon;
import me.hsgamer.topin.npc.getter.TopNPCGetter;
import org.bukkit.Bukkit;

import static me.hsgamer.topin.TopIn.getInstance;

public final class TopNPCAddon extends TopInAddon {

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
