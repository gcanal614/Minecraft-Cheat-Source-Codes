/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Function
 *  com.google.common.base.Objects
 *  com.google.common.collect.HashBasedTable
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableTable
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Table
 */
package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
    private static final Function<IProperty, String> GET_NAME_FUNC = p_apply_1_ -> p_apply_1_ == null ? "<NULL>" : p_apply_1_.getName();
    private final Block block;
    private final ImmutableList<IProperty> properties;
    private final ImmutableList<IBlockState> validStates;

    public BlockState(Block blockIn, IProperty ... properties) {
        this.block = blockIn;
        Arrays.sort(properties, Comparator.comparing(IProperty::getName));
        this.properties = ImmutableList.copyOf((Object[])properties);
        LinkedHashMap map = Maps.newLinkedHashMap();
        ArrayList list = Lists.newArrayList();
        for (List<Comparable> list1 : Cartesian.cartesianProduct(this.getAllowedValues())) {
            Map<IProperty, Comparable> map1 = MapPopulator.createMap(this.properties, list1);
            StateImplementation blockstate$stateimplementation = new StateImplementation(blockIn, ImmutableMap.copyOf(map1));
            map.put(map1, blockstate$stateimplementation);
            list.add(blockstate$stateimplementation);
        }
        for (StateImplementation blockstate$stateimplementation1 : list) {
            blockstate$stateimplementation1.buildPropertyValueTable(map);
        }
        this.validStates = ImmutableList.copyOf((Collection)list);
    }

    public ImmutableList<IBlockState> getValidStates() {
        return this.validStates;
    }

    private List<Iterable<Comparable>> getAllowedValues() {
        ArrayList list = Lists.newArrayList();
        for (int i = 0; i < this.properties.size(); ++i) {
            list.add(((IProperty)this.properties.get(i)).getAllowedValues());
        }
        return list;
    }

    public IBlockState getBaseState() {
        return (IBlockState)this.validStates.get(0);
    }

    public Block getBlock() {
        return this.block;
    }

    public Collection<IProperty> getProperties() {
        return this.properties;
    }

    public String toString() {
        return Objects.toStringHelper((Object)this).add("block", Block.blockRegistry.getNameForObject(this.block)).add("properties", (Object)Iterables.transform(this.properties, GET_NAME_FUNC)).toString();
    }

    static class StateImplementation
    extends BlockStateBase {
        private final Block block;
        private final ImmutableMap<IProperty, Comparable> properties;
        private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;

        private StateImplementation(Block blockIn, ImmutableMap<IProperty, Comparable> propertiesIn) {
            this.block = blockIn;
            this.properties = propertiesIn;
        }

        @Override
        public Collection<IProperty> getPropertyNames() {
            return Collections.unmodifiableCollection(this.properties.keySet());
        }

        @Override
        public <T extends Comparable<T>> T getValue(IProperty<T> property) {
            if (!this.properties.containsKey(property)) {
                throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            return (T)((Comparable)property.getValueClass().cast(this.properties.get(property)));
        }

        @Override
        public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> property, V value) {
            if (!this.properties.containsKey(property)) {
                throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.block.getBlockState());
            }
            if (!property.getAllowedValues().contains(value)) {
                throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on block " + Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
            }
            return this.properties.get(property) == value ? this : (IBlockState)this.propertyValueTable.get(property, value);
        }

        @Override
        public ImmutableMap<IProperty, Comparable> getProperties() {
            return this.properties;
        }

        @Override
        public Block getBlock() {
            return this.block;
        }

        public boolean equals(Object p_equals_1_) {
            return this == p_equals_1_;
        }

        public int hashCode() {
            return this.properties.hashCode();
        }

        public void buildPropertyValueTable(Map<Map<IProperty, Comparable>, StateImplementation> map) {
            if (this.propertyValueTable != null) {
                throw new IllegalStateException();
            }
            HashBasedTable table = HashBasedTable.create();
            for (IProperty iproperty : this.properties.keySet()) {
                for (Comparable comparable : iproperty.getAllowedValues()) {
                    if (comparable == this.properties.get((Object)iproperty)) continue;
                    table.put((Object)iproperty, (Object)comparable, (Object)map.get(this.setPropertyValue(iproperty, comparable)));
                }
            }
            this.propertyValueTable = ImmutableTable.copyOf((Table)table);
        }

        private Map<IProperty, Comparable> setPropertyValue(IProperty property, Comparable value) {
            HashMap map = Maps.newHashMap(this.properties);
            map.put(property, value);
            return map;
        }
    }
}

