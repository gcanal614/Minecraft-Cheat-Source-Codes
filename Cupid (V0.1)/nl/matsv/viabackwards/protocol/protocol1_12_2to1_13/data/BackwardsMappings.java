package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.data.BackwardsMappings;
import nl.matsv.viabackwards.api.data.VBMappings;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.ViaVersion.api.data.Mappings;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.StatisticMappings;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectMap;
import us.myles.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntMap;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonPrimitive;

public class BackwardsMappings extends BackwardsMappings {
  private final Int2ObjectMap<String> statisticMappings = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap();
  
  private final Map<String, String> translateMappings = new HashMap<>();
  
  private Mappings enchantmentMappings;
  
  public BackwardsMappings() {
    super("1.13", "1.12", Protocol1_13To1_12_2.class, true);
  }
  
  public void loadVBExtras(JsonObject oldMappings, JsonObject newMappings) {
    this.enchantmentMappings = (Mappings)new VBMappings(oldMappings.getAsJsonObject("enchantments"), newMappings.getAsJsonObject("enchantments"), false);
    for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>)StatisticMappings.CUSTOM_STATS.entrySet())
      this.statisticMappings.put(((Integer)entry.getValue()).intValue(), entry.getKey()); 
    for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)Protocol1_13To1_12_2.MAPPINGS.getTranslateMapping().entrySet())
      this.translateMappings.put(entry.getValue(), entry.getKey()); 
  }
  
  private static void mapIdentifiers(short[] output, JsonObject newIdentifiers, JsonObject oldIdentifiers, JsonObject mapping) {
    Object2IntMap newIdentifierMap = MappingDataLoader.indexedObjectToMap(oldIdentifiers);
    for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)newIdentifiers.entrySet()) {
      String key = ((JsonElement)entry.getValue()).getAsString();
      int value = newIdentifierMap.getInt(key);
      short hardId = -1;
      if (value == -1) {
        JsonPrimitive replacement = mapping.getAsJsonPrimitive(key);
        int propertyIndex;
        if (replacement == null && (propertyIndex = key.indexOf('[')) != -1)
          replacement = mapping.getAsJsonPrimitive(key.substring(0, propertyIndex)); 
        if (replacement != null)
          if (replacement.getAsString().startsWith("id:")) {
            String id = replacement.getAsString().replace("id:", "");
            hardId = Short.parseShort(id);
            value = newIdentifierMap.getInt(oldIdentifiers.getAsJsonPrimitive(id).getAsString());
          } else {
            value = newIdentifierMap.getInt(replacement.getAsString());
          }  
        if (value == -1) {
          if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug()) {
            if (replacement != null) {
              ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + "/" + replacement.getAsString() + " :( ");
              continue;
            } 
            ViaBackwards.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( ");
          } 
          continue;
        } 
      } 
      output[Integer.parseInt((String)entry.getKey())] = (hardId != -1) ? hardId : (short)value;
    } 
  }
  
  @Nullable
  protected Mappings loadFromObject(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
    if (key.equals("blockstates")) {
      short[] oldToNew = new short[8582];
      Arrays.fill(oldToNew, (short)-1);
      mapIdentifiers(oldToNew, oldMappings.getAsJsonObject("blockstates"), newMappings.getAsJsonObject("blocks"), diffMappings.getAsJsonObject("blockstates"));
      return new Mappings(oldToNew);
    } 
    return super.loadFromObject(oldMappings, newMappings, diffMappings, key);
  }
  
  protected int checkValidity(int id, int mappedId, String type) {
    return mappedId;
  }
  
  public Int2ObjectMap<String> getStatisticMappings() {
    return this.statisticMappings;
  }
  
  public Map<String, String> getTranslateMappings() {
    return this.translateMappings;
  }
  
  public Mappings getEnchantmentMappings() {
    return this.enchantmentMappings;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\nl\matsv\viabackwards\protocol\protocol1_12_2to1_13\data\BackwardsMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */