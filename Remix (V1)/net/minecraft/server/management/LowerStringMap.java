package net.minecraft.server.management;

import com.google.common.collect.*;
import java.util.*;

public class LowerStringMap implements Map
{
    private final Map internalMap;
    
    public LowerStringMap() {
        this.internalMap = Maps.newLinkedHashMap();
    }
    
    @Override
    public int size() {
        return this.internalMap.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object p_containsKey_1_) {
        return this.internalMap.containsKey(p_containsKey_1_.toString().toLowerCase());
    }
    
    @Override
    public boolean containsValue(final Object p_containsValue_1_) {
        return this.internalMap.containsKey(p_containsValue_1_);
    }
    
    @Override
    public Object get(final Object p_get_1_) {
        return this.internalMap.get(p_get_1_.toString().toLowerCase());
    }
    
    public Object put(final String p_put_1_, final Object p_put_2_) {
        return this.internalMap.put(p_put_1_.toLowerCase(), p_put_2_);
    }
    
    @Override
    public Object remove(final Object p_remove_1_) {
        return this.internalMap.remove(p_remove_1_.toString().toLowerCase());
    }
    
    @Override
    public void putAll(final Map p_putAll_1_) {
        for (final Entry var3 : p_putAll_1_.entrySet()) {
            this.put(var3.getKey(), var3.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.internalMap.clear();
    }
    
    @Override
    public Set keySet() {
        return this.internalMap.keySet();
    }
    
    @Override
    public Collection values() {
        return this.internalMap.values();
    }
    
    @Override
    public Set entrySet() {
        return this.internalMap.entrySet();
    }
    
    @Override
    public Object put(final Object p_put_1_, final Object p_put_2_) {
        return this.put((String)p_put_1_, p_put_2_);
    }
}
