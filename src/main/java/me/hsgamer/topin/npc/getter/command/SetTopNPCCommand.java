package me.hsgamer.topin.npc.getter.command;

import static me.hsgamer.topin.utils.MessageUtils.sendMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.npc.Permissions;
import me.hsgamer.topin.npc.getter.TopNPC;
import me.hsgamer.topin.npc.getter.TopNPCGetter;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class SetTopNPCCommand extends BukkitCommand {

  private final TopNPCGetter getter;

  public SetTopNPCCommand(TopNPCGetter getter) {
    super("settopnpc", "Set the npc for top players", "/settopnpc <data_list> <index>",
        Collections.singletonList("topnpc"));
    this.getter = getter;
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.NPC)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }
    if (args.length < 2) {
      sendMessage(sender, "&c" + getUsage());
      return false;
    }
    if (!TopIn.getInstance().getDataListManager().getDataList(args[0]).isPresent()) {
      sendMessage(sender, MessageConfig.DATA_LIST_NOT_FOUND.getValue());
      return false;
    }
    int index;
    try {
      index = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      sendMessage(sender, MessageConfig.NUMBER_REQUIRED.getValue());
      return false;
    }

    NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
    if (npc == null) {
      sendMessage(sender, "&cYou must select an NPC");
      return false;
    }

    getter.addNPC(new TopNPC(npc.getId(), args[0], index));
    sendMessage(sender, MessageConfig.SUCCESS.getValue());
    return true;
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
    List<String> list = new ArrayList<>();
    if (args.length == 1) {
      list.addAll(TopIn.getInstance().getDataListManager().getSuggestedDataListNames(args[0]));
    }
    return list;
  }
}
