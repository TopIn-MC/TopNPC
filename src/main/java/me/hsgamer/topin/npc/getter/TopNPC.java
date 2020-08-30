package me.hsgamer.topin.npc.getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.DataList;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class TopNPC implements ConfigurationSerializable {

  private final int id;
  private final String dataListName;
  private final int index;

  public TopNPC(int id, String dataListName, int index) {
    this.id = id;
    this.dataListName = dataListName;
    this.index = index;
  }

  public static TopNPC deserialize(Map<String, Object> args) {
    return new TopNPC((int) args.get("id"), (String) args.get("data-list"),
        (int) args.get("index"));
  }

  public void update() {
    NPC npc = CitizensAPI.getNPCRegistry().getById(id);
    if (npc == null) {
      return;
    }

    Optional<DataList> optionalDataList = TopIn.getInstance().getDataListManager()
        .getDataList(dataListName);
    if (!optionalDataList.isPresent()) {
      return;
    }

    DataList dataList = optionalDataList.get();
    if (index < 0 || index >= dataList.getSize()) {
      return;
    }

    String name = Bukkit.getOfflinePlayer(dataList.getPair(index).getUniqueId()).getName();
    SkinTrait skinTrait = npc.getTrait(SkinTrait.class);

    if (skinTrait.getSkinName() == null || !skinTrait.getSkinName().equalsIgnoreCase(name)) {
      skinTrait.setSkinName(name);
    }
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("data-list", dataListName);
    map.put("index", index);
    return map;
  }

  public int getId() {
    return id;
  }
}
