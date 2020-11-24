package me.hsgamer.topin.npc;

import me.hsgamer.topin.npc.getter.TopNPCGetter;
import org.bukkit.plugin.java.JavaPlugin;

import static me.hsgamer.topin.TopIn.getInstance;

public class TopNPCPlugin extends JavaPlugin {

    private final TopNPCGetter getter = new TopNPCGetter();

    @Override
    public void onEnable() {
        getInstance().getGetterManager().register(getter);
    }

    @Override
    public void onDisable() {
        getInstance().getGetterManager().unregister(getter);
    }
}
