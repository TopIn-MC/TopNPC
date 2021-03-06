package me.hsgamer.topin.npc.getter.command;

import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.npc.Permissions;
import me.hsgamer.topin.npc.getter.TopNPCGetter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Collections;
import java.util.Objects;

import static me.hsgamer.topin.core.bukkit.utils.MessageUtils.sendMessage;

public class RemoveTopNPCCommand extends BukkitCommand {

    private final TopNPCGetter getter;

    public RemoveTopNPCCommand(TopNPCGetter getter) {
        super("removetopnpc", "Remove the top NPC", "/removetopnpc",
                Collections.emptyList());
        this.getter = getter;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission(Permissions.NPC)) {
            sendMessage(sender, Objects.requireNonNull(MessageConfig.NO_PERMISSION.getValue()));
            return false;
        }

        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (npc == null) {
            sendMessage(sender, "&cYou must select an NPC");
            return false;
        }

        if (!getter.containsNPC(npc.getId())) {
            sendMessage(sender, "&cThat's not an Top NPC");
            return false;
        }

        getter.removeNPC(npc.getId());
        sendMessage(sender, Objects.requireNonNull(MessageConfig.SUCCESS.getValue()));
        return true;
    }
}
