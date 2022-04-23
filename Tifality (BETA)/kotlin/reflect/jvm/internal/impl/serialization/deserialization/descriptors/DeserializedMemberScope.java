/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptorWithTypeParameters;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.protobuf.AbstractMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.MessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.Parser;
import kotlin.reflect.jvm.internal.impl.resolve.MemberComparator;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.NameResolverUtilKt;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.NullableLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DeserializedMemberScope
extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final Map<Name, byte[]> functionProtosBytes;
    private final Map<Name, byte[]> propertyProtosBytes;
    private final Map<Name, byte[]> typeAliasBytes;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final MemoizedFunctionToNotNull<Name, Collection<PropertyDescriptor>> properties;
    private final MemoizedFunctionToNullable<Name, TypeAliasDescriptor> typeAliasByName;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final NotNullLazyValue variableNamesLazy$delegate;
    @NotNull
    private final NotNullLazyValue classNames$delegate;
    private final NullableLazyValue<Set<Name>> classifierNamesLazy;
    @NotNull
    private final DeserializationContext c;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "variableNamesLazy", "getVariableNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(DeserializedMemberScope.class), "classNames", "getClassNames$deserialization()Ljava/util/Set;"))};
    }

    /*
     * WARNING - void declaration
     */
    private final Map<Name, byte[]> packToByteArray(Map<Name, ? extends Collection<? extends AbstractMessageLite>> $this$packToByteArray) {
        void $this$mapValuesTo$iv$iv;
        Map<Name, ? extends Collection<? extends AbstractMessageLite>> $this$mapValues$iv = $this$packToByteArray;
        boolean $i$f$mapValues = false;
        Map<Name, ? extends Collection<? extends AbstractMessageLite>> map2 = $this$mapValues$iv;
        Map destination$iv$iv = new LinkedHashMap(MapsKt.mapCapacity($this$mapValues$iv.size()));
        boolean $i$f$mapValuesTo = false;
        Iterable $this$associateByTo$iv$iv$iv = $this$mapValuesTo$iv$iv.entrySet();
        boolean $i$f$associateByTo = false;
        for (Object element$iv$iv$iv : $this$associateByTo$iv$iv$iv) {
            void $this$mapTo$iv$iv;
            void entry;
            void it$iv$iv;
            Map.Entry entry2 = (Map.Entry)element$iv$iv$iv;
            Map map3 = destination$iv$iv;
            boolean bl = false;
            Object k = it$iv$iv.getKey();
            Map.Entry entry3 = (Map.Entry)element$iv$iv$iv;
            Object k2 = k;
            Map map4 = map3;
            boolean bl2 = false;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Iterable $this$map$iv = (Iterable)entry.getValue();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void proto;
                AbstractMessageLite abstractMessageLite = (AbstractMessageLite)item$iv$iv;
                Collection collection = destination$iv$iv2;
                boolean bl3 = false;
                proto.writeDelimitedTo(byteArrayOutputStream);
                Unit unit = Unit.INSTANCE;
                collection.add(unit);
            }
            List cfr_ignored_0 = (List)destination$iv$iv2;
            byte[] byArray = byteArrayOutputStream.toByteArray();
            map4.put(k2, byArray);
        }
        return destination$iv$iv;
    }

    private final Set<Name> getFunctionNamesLazy() {
        return (Set)StorageKt.getValue(this.functionNamesLazy$delegate, (Object)this, $$delegatedProperties[0]);
    }

    private final Set<Name> getVariableNamesLazy() {
        return (Set)StorageKt.getValue(this.variableNamesLazy$delegate, (Object)this, $$delegatedProperties[1]);
    }

    private final Set<Name> getTypeAliasNames() {
        return this.typeAliasBytes.keySet();
    }

    @NotNull
    public final Set<Name> getClassNames$deserialization() {
        return (Set)StorageKt.getValue(this.classNames$delegate, (Object)this, $$delegatedProperties[2]);
    }

    @Override
    @NotNull
    public Set<Name> getFunctionNames() {
        return this.getFunctionNamesLazy();
    }

    @Override
    @NotNull
    public Set<Name> getVariableNames() {
        return this.getVariableNamesLazy();
    }

    @Override
    @Nullable
    public Set<Name> getClassifierNames() {
        return (Set)this.classifierNamesLazy.invoke();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Collection<SimpleFunctionDescriptor> computeFunctions(Name name) {
        var2_2 = this;
        var3_3 = this.functionProtosBytes;
        v0 = ProtoBuf.Function.PARSER;
        Intrinsics.checkNotNullExpressionValue(v0, "ProtoBuf.Function.PARSER");
        parser$iv = v0;
        $i$f$computeDescriptors = false;
        var6_6 = this_$iv;
        v1 /* !! */  = (byte[])bytesByName$iv.get(name);
        if (v1 /* !! */  == null) ** GOTO lbl-1000
        var7_7 = v1 /* !! */ ;
        var8_8 = false;
        var9_9 = false;
        it$iv = var7_7;
        $i$a$-let-DeserializedMemberScope$computeDescriptors$1$iv = false;
        inputStream$iv = new ByteArrayInputStream(it$iv);
        v2 = SequencesKt.toList(SequencesKt.generateSequence((Function0)new Function0<M>(inputStream$iv, (DeserializedMemberScope)this_$iv, parser$iv){
            final /* synthetic */ ByteArrayInputStream $inputStream;
            final /* synthetic */ DeserializedMemberScope this$0;
            final /* synthetic */ Parser $parser$inlined;
            {
                this.$inputStream = byteArrayInputStream;
                this.this$0 = deserializedMemberScope;
                this.$parser$inlined = parser;
                super(0);
            }

            @Nullable
            public final M invoke() {
                return (M)((MessageLite)this.$parser$inlined.parseDelimitedFrom(this.$inputStream, this.this$0.getC().getComponents().getExtensionRegistryLite()));
            }
        }));
        v1 /* !! */  = (byte[])v2;
        if (v2 != null) {
            v3 = (Collection)v1 /* !! */ ;
        } else lbl-1000:
        // 2 sources

        {
            v3 = CollectionsKt.emptyList();
        }
        protos$iv$iv = v3;
        $i$f$computeDescriptors = false;
        var9_10 = protos$iv$iv;
        it$iv = false;
        destination$iv$iv$iv = new ArrayList<E>();
        $i$f$mapTo = false;
        for (T item$iv$iv$iv : $this$mapTo$iv$iv$iv) {
            var14_17 = (ProtoBuf.Function)item$iv$iv$iv;
            var17_19 = destination$iv$iv$iv;
            $i$a$-computeDescriptors-DeserializedMemberScope$computeFunctions$1 = false;
            v4 = this.c.getMemberDeserializer();
            v5 = it;
            Intrinsics.checkNotNullExpressionValue(v5, "it");
            var18_20 = v4.loadFunction((ProtoBuf.Function)v5);
            var17_19.add(var18_20);
        }
        descriptors$iv$iv = (ArrayList)destination$iv$iv$iv;
        it = descriptors$iv$iv;
        $i$a$-computeDescriptors-DeserializedMemberScope$computeFunctions$2 = false;
        this.computeNonDeclaredFunctions(name, it);
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(descriptors$iv$iv);
    }

    protected void computeNonDeclaredFunctions(@NotNull Name name, @NotNull Collection<SimpleFunctionDescriptor> functions2) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(functions2, "functions");
    }

    @NotNull
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!this.getFunctionNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return (Collection)this.functions.invoke(name);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private final Collection<PropertyDescriptor> computeProperties(Name name) {
        var2_2 = this;
        var3_3 = this.propertyProtosBytes;
        v0 = ProtoBuf.Property.PARSER;
        Intrinsics.checkNotNullExpressionValue(v0, "ProtoBuf.Property.PARSER");
        parser$iv = v0;
        $i$f$computeDescriptors = false;
        var6_6 = this_$iv;
        v1 /* !! */  = (byte[])bytesByName$iv.get(name);
        if (v1 /* !! */  == null) ** GOTO lbl-1000
        var7_7 = v1 /* !! */ ;
        var8_8 = false;
        var9_9 = false;
        it$iv = var7_7;
        $i$a$-let-DeserializedMemberScope$computeDescriptors$1$iv = false;
        inputStream$iv = new ByteArrayInputStream(it$iv);
        v2 = SequencesKt.toList(SequencesKt.generateSequence((Function0)new Function0<M>(inputStream$iv, (DeserializedMemberScope)this_$iv, parser$iv){
            final /* synthetic */ ByteArrayInputStream $inputStream;
            final /* synthetic */ DeserializedMemberScope this$0;
            final /* synthetic */ Parser $parser$inlined;
            {
                this.$inputStream = byteArrayInputStream;
                this.this$0 = deserializedMemberScope;
                this.$parser$inlined = parser;
                super(0);
            }

            @Nullable
            public final M invoke() {
                return (M)((MessageLite)this.$parser$inlined.parseDelimitedFrom(this.$inputStream, this.this$0.getC().getComponents().getExtensionRegistryLite()));
            }
        }));
        v1 /* !! */  = (byte[])v2;
        if (v2 != null) {
            v3 = (Collection)v1 /* !! */ ;
        } else lbl-1000:
        // 2 sources

        {
            v3 = CollectionsKt.emptyList();
        }
        protos$iv$iv = v3;
        $i$f$computeDescriptors = false;
        var9_10 = protos$iv$iv;
        it$iv = false;
        destination$iv$iv$iv = new ArrayList<E>();
        $i$f$mapTo = false;
        for (T item$iv$iv$iv : $this$mapTo$iv$iv$iv) {
            var14_17 = (ProtoBuf.Property)item$iv$iv$iv;
            var17_19 = destination$iv$iv$iv;
            $i$a$-computeDescriptors-DeserializedMemberScope$computeProperties$1 = false;
            v4 = this.c.getMemberDeserializer();
            v5 = it;
            Intrinsics.checkNotNullExpressionValue(v5, "it");
            var18_20 = v4.loadProperty((ProtoBuf.Property)v5);
            var17_19.add(var18_20);
        }
        descriptors$iv$iv = (ArrayList)destination$iv$iv$iv;
        it = descriptors$iv$iv;
        $i$a$-computeDescriptors-DeserializedMemberScope$computeProperties$2 = false;
        this.computeNonDeclaredProperties(name, it);
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(descriptors$iv$iv);
    }

    protected void computeNonDeclaredProperties(@NotNull Name name, @NotNull Collection<PropertyDescriptor> descriptors) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(descriptors, "descriptors");
    }

    private final TypeAliasDescriptor createTypeAlias(Name name) {
        byte[] byArray = this.typeAliasBytes.get(name);
        if (byArray == null) {
            return null;
        }
        byte[] byteArray = byArray;
        ProtoBuf.TypeAlias typeAlias = ProtoBuf.TypeAlias.parseDelimitedFrom(new ByteArrayInputStream(byteArray), this.c.getComponents().getExtensionRegistryLite());
        if (typeAlias == null) {
            return null;
        }
        ProtoBuf.TypeAlias proto = typeAlias;
        return this.c.getMemberDeserializer().loadTypeAlias(proto);
    }

    @NotNull
    public Collection<PropertyDescriptor> getContributedVariables(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        if (!this.getVariableNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return (Collection)this.properties.invoke(name);
    }

    @NotNull
    protected final Collection<DeclarationDescriptor> computeDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Intrinsics.checkNotNullParameter(location, "location");
        ArrayList result2 = new ArrayList(0);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getSINGLETON_CLASSIFIERS_MASK())) {
            this.addEnumEntryDescriptors(result2, nameFilter);
        }
        this.addFunctionsAndProperties(result2, kindFilter, nameFilter, location);
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name className : this.getClassNames$deserialization()) {
                if (!nameFilter.invoke(className).booleanValue()) continue;
                kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result2, this.deserializeClass(className));
            }
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getTYPE_ALIASES_MASK())) {
            for (Name typeAliasName : this.getTypeAliasNames()) {
                if (!nameFilter.invoke(typeAliasName).booleanValue()) continue;
                kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(result2, this.typeAliasByName.invoke(typeAliasName));
            }
        }
        return kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.compact(result2);
    }

    private final void addFunctionsAndProperties(Collection<DeclarationDescriptor> result2, DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter, LookupLocation location) {
        Collection<CallableMemberDescriptor> collection;
        Name it;
        ArrayList<PropertyDescriptor> arrayList;
        ArrayList<PropertyDescriptor> subResult$iv;
        boolean $i$f$addMembers;
        Collection names$iv;
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK())) {
            DeserializedMemberScope deserializedMemberScope = this;
            names$iv = this.getVariableNames();
            $i$f$addMembers = false;
            subResult$iv = new ArrayList<PropertyDescriptor>();
            for (Name name$iv : names$iv) {
                if (!nameFilter.invoke(name$iv).booleanValue()) continue;
                Name name = name$iv;
                arrayList = subResult$iv;
                boolean bl = false;
                collection = this.getContributedVariables(it, location);
                arrayList.addAll(collection);
            }
            List list = subResult$iv;
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(nameAndTypeMemberComparator, "MemberComparator.NameAnd\u2026MemberComparator.INSTANCE");
            CollectionsKt.sortWith(list, nameAndTypeMemberComparator);
            result2.addAll((Collection<DeclarationDescriptor>)subResult$iv);
        }
        if (kindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK())) {
            DeserializedMemberScope this_$iv = this;
            names$iv = this.getFunctionNames();
            $i$f$addMembers = false;
            subResult$iv = new ArrayList();
            for (Name name$iv : names$iv) {
                if (!nameFilter.invoke(name$iv).booleanValue()) continue;
                it = name$iv;
                arrayList = subResult$iv;
                boolean bl = false;
                collection = this.getContributedFunctions(it, location);
                arrayList.addAll(collection);
            }
            List list = subResult$iv;
            MemberComparator.NameAndTypeMemberComparator nameAndTypeMemberComparator = MemberComparator.NameAndTypeMemberComparator.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(nameAndTypeMemberComparator, "MemberComparator.NameAnd\u2026MemberComparator.INSTANCE");
            CollectionsKt.sortWith(list, nameAndTypeMemberComparator);
            result2.addAll((Collection<DeclarationDescriptor>)subResult$iv);
        }
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.hasClass(name) ? (ClassifierDescriptorWithTypeParameters)this.deserializeClass(name) : (this.getTypeAliasNames().contains(name) ? (ClassifierDescriptorWithTypeParameters)this.typeAliasByName.invoke(name) : null);
    }

    private final ClassDescriptor deserializeClass(Name name) {
        return this.c.getComponents().deserializeClass(this.createClassId(name));
    }

    protected boolean hasClass(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return this.getClassNames$deserialization().contains(name);
    }

    @NotNull
    protected abstract ClassId createClassId(@NotNull Name var1);

    @NotNull
    protected abstract Set<Name> getNonDeclaredFunctionNames();

    @NotNull
    protected abstract Set<Name> getNonDeclaredVariableNames();

    @Nullable
    protected abstract Set<Name> getNonDeclaredClassifierNames();

    protected abstract void addEnumEntryDescriptors(@NotNull Collection<DeclarationDescriptor> var1, @NotNull Function1<? super Name, Boolean> var2);

    @NotNull
    protected final DeserializationContext getC() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    protected DeserializedMemberScope(@NotNull DeserializationContext c, @NotNull Collection<ProtoBuf.Function> functionList, @NotNull Collection<ProtoBuf.Property> propertyList, @NotNull Collection<ProtoBuf.TypeAlias> typeAliasList, @NotNull Function0<? extends Collection<Name>> classNames2) {
        Map<Object, Object> map2;
        Object list$iv$iv$iv;
        List answer$iv$iv$iv$iv;
        Object value$iv$iv$iv$iv;
        boolean $i$f$getOrPut;
        Map $this$getOrPut$iv$iv$iv$iv;
        Name key$iv$iv$iv;
        int n;
        void it;
        Object object;
        DeserializedMemberScope this_$iv;
        boolean bl;
        MessageLite it$iv;
        Iterable $this$groupByTo$iv$iv$iv;
        Collection<GeneratedMessageLite.ExtendableMessage> $this$groupByName$iv;
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(functionList, "functionList");
        Intrinsics.checkNotNullParameter(propertyList, "propertyList");
        Intrinsics.checkNotNullParameter(typeAliasList, "typeAliasList");
        Intrinsics.checkNotNullParameter(classNames2, "classNames");
        this.c = c;
        Collection<ProtoBuf.Function> collection = functionList;
        DeserializedMemberScope deserializedMemberScope = this;
        DeserializedMemberScope deserializedMemberScope2 = this;
        DeserializedMemberScope deserializedMemberScope3 = this;
        boolean $i$f$groupByName = false;
        Iterable $this$groupBy$iv$iv = $this$groupByName$iv;
        boolean $i$f$groupBy = false;
        Iterable iterable = $this$groupBy$iv$iv;
        Map destination$iv$iv$iv = new LinkedHashMap();
        boolean $i$f$groupByTo = false;
        for (Object element$iv$iv$iv : $this$groupByTo$iv$iv$iv) {
            Object object2;
            it$iv = (MessageLite)element$iv$iv$iv;
            bl = false;
            ProtoBuf.Function function = (ProtoBuf.Function)it$iv;
            object = this_$iv.c.getNameResolver();
            boolean bl2 = false;
            n = it.getName();
            key$iv$iv$iv = NameResolverUtilKt.getName((NameResolver)object, n);
            $this$getOrPut$iv$iv$iv$iv = destination$iv$iv$iv;
            $i$f$getOrPut = false;
            value$iv$iv$iv$iv = $this$getOrPut$iv$iv$iv$iv.get(key$iv$iv$iv);
            if (value$iv$iv$iv$iv == null) {
                boolean bl3 = false;
                answer$iv$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv$iv.put(key$iv$iv$iv, answer$iv$iv$iv$iv);
                object2 = answer$iv$iv$iv$iv;
            } else {
                object2 = value$iv$iv$iv$iv;
            }
            list$iv$iv$iv = (List)object2;
            list$iv$iv$iv.add(element$iv$iv$iv);
        }
        object = destination$iv$iv$iv;
        deserializedMemberScope3.functionProtosBytes = deserializedMemberScope2.packToByteArray((Map<Name, ? extends Collection<? extends AbstractMessageLite>>)object);
        $this$groupByName$iv = propertyList;
        this_$iv = this;
        deserializedMemberScope2 = this;
        deserializedMemberScope3 = this;
        $i$f$groupByName = false;
        $this$groupBy$iv$iv = $this$groupByName$iv;
        $i$f$groupBy = false;
        $this$groupByTo$iv$iv$iv = $this$groupBy$iv$iv;
        destination$iv$iv$iv = new LinkedHashMap();
        $i$f$groupByTo = false;
        for (Object element$iv$iv$iv : $this$groupByTo$iv$iv$iv) {
            Object object3;
            it$iv = (MessageLite)element$iv$iv$iv;
            bl = false;
            list$iv$iv$iv = (ProtoBuf.Property)it$iv;
            object = this_$iv.c.getNameResolver();
            boolean bl4 = false;
            n = it.getName();
            key$iv$iv$iv = NameResolverUtilKt.getName((NameResolver)object, n);
            $this$getOrPut$iv$iv$iv$iv = destination$iv$iv$iv;
            $i$f$getOrPut = false;
            value$iv$iv$iv$iv = $this$getOrPut$iv$iv$iv$iv.get(key$iv$iv$iv);
            if (value$iv$iv$iv$iv == null) {
                boolean bl5 = false;
                answer$iv$iv$iv$iv = new ArrayList();
                $this$getOrPut$iv$iv$iv$iv.put(key$iv$iv$iv, answer$iv$iv$iv$iv);
                object3 = answer$iv$iv$iv$iv;
            } else {
                object3 = value$iv$iv$iv$iv;
            }
            list$iv$iv$iv = (List)object3;
            list$iv$iv$iv.add(element$iv$iv$iv);
        }
        object = destination$iv$iv$iv;
        deserializedMemberScope3.propertyProtosBytes = deserializedMemberScope2.packToByteArray((Map<Name, ? extends Collection<? extends AbstractMessageLite>>)object);
        DeserializedMemberScope deserializedMemberScope4 = this;
        if (this.c.getComponents().getConfiguration().getTypeAliasesAllowed()) {
            $this$groupByName$iv = typeAliasList;
            this_$iv = this;
            deserializedMemberScope2 = this;
            deserializedMemberScope3 = deserializedMemberScope4;
            $i$f$groupByName = false;
            $this$groupBy$iv$iv = $this$groupByName$iv;
            $i$f$groupBy = false;
            $this$groupByTo$iv$iv$iv = $this$groupBy$iv$iv;
            destination$iv$iv$iv = new LinkedHashMap();
            $i$f$groupByTo = false;
            for (Object element$iv$iv$iv : $this$groupByTo$iv$iv$iv) {
                Object object4;
                it$iv = (MessageLite)element$iv$iv$iv;
                bl = false;
                list$iv$iv$iv = (ProtoBuf.TypeAlias)it$iv;
                object = this_$iv.c.getNameResolver();
                boolean bl6 = false;
                n = it.getName();
                key$iv$iv$iv = NameResolverUtilKt.getName((NameResolver)object, n);
                $this$getOrPut$iv$iv$iv$iv = destination$iv$iv$iv;
                $i$f$getOrPut = false;
                value$iv$iv$iv$iv = $this$getOrPut$iv$iv$iv$iv.get(key$iv$iv$iv);
                if (value$iv$iv$iv$iv == null) {
                    boolean bl7 = false;
                    answer$iv$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv$iv.put(key$iv$iv$iv, answer$iv$iv$iv$iv);
                    object4 = answer$iv$iv$iv$iv;
                } else {
                    object4 = value$iv$iv$iv$iv;
                }
                list$iv$iv$iv = (List)object4;
                list$iv$iv$iv.add(element$iv$iv$iv);
            }
            object = destination$iv$iv$iv;
            deserializedMemberScope4 = deserializedMemberScope3;
            map2 = deserializedMemberScope2.packToByteArray((Map<Name, ? extends Collection<? extends AbstractMessageLite>>)object);
        } else {
            map2 = MapsKt.emptyMap();
        }
        deserializedMemberScope4.typeAliasBytes = map2;
        this.functions = this.c.getStorageManager().createMemoizedFunction((Function1)new Function1<Name, Collection<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @NotNull
            public final Collection<SimpleFunctionDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializedMemberScope.access$computeFunctions(this.this$0, it);
            }
            {
                this.this$0 = deserializedMemberScope;
                super(1);
            }
        });
        this.properties = this.c.getStorageManager().createMemoizedFunction((Function1)new Function1<Name, Collection<? extends PropertyDescriptor>>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @NotNull
            public final Collection<PropertyDescriptor> invoke(@NotNull Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializedMemberScope.access$computeProperties(this.this$0, it);
            }
            {
                this.this$0 = deserializedMemberScope;
                super(1);
            }
        });
        this.typeAliasByName = this.c.getStorageManager().createMemoizedFunctionWithNullableValues((Function1)new Function1<Name, TypeAliasDescriptor>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @Nullable
            public final TypeAliasDescriptor invoke(@NotNull Name it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DeserializedMemberScope.access$createTypeAlias(this.this$0, it);
            }
            {
                this.this$0 = deserializedMemberScope;
                super(1);
            }
        });
        this.functionNamesLazy$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return SetsKt.plus(DeserializedMemberScope.access$getFunctionProtosBytes$p(this.this$0).keySet(), (Iterable)this.this$0.getNonDeclaredFunctionNames());
            }
            {
                this.this$0 = deserializedMemberScope;
                super(0);
            }
        });
        this.variableNamesLazy$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @NotNull
            public final Set<Name> invoke() {
                return SetsKt.plus(DeserializedMemberScope.access$getPropertyProtosBytes$p(this.this$0).keySet(), (Iterable)this.this$0.getNonDeclaredVariableNames());
            }
            {
                this.this$0 = deserializedMemberScope;
                super(0);
            }
        });
        this.classNames$delegate = this.c.getStorageManager().createLazyValue((Function0)new Function0<Set<? extends Name>>(classNames2){
            final /* synthetic */ Function0 $classNames;

            @NotNull
            public final Set<Name> invoke() {
                return CollectionsKt.toSet((Iterable)this.$classNames.invoke());
            }
            {
                this.$classNames = function0;
                super(0);
            }
        });
        this.classifierNamesLazy = this.c.getStorageManager().createNullableLazyValue((Function0)new Function0<Set<? extends Name>>(this){
            final /* synthetic */ DeserializedMemberScope this$0;

            @Nullable
            public final Set<Name> invoke() {
                Set<Name> set = this.this$0.getNonDeclaredClassifierNames();
                if (set == null) {
                    return null;
                }
                Set<Name> nonDeclaredNames = set;
                return SetsKt.plus(SetsKt.plus(this.this$0.getClassNames$deserialization(), DeserializedMemberScope.access$getTypeAliasNames$p(this.this$0)), (Iterable)nonDeclaredNames);
            }
            {
                this.this$0 = deserializedMemberScope;
                super(0);
            }
        });
    }

    public static final /* synthetic */ Collection access$computeFunctions(DeserializedMemberScope $this, Name name) {
        return $this.computeFunctions(name);
    }

    public static final /* synthetic */ Collection access$computeProperties(DeserializedMemberScope $this, Name name) {
        return $this.computeProperties(name);
    }

    public static final /* synthetic */ TypeAliasDescriptor access$createTypeAlias(DeserializedMemberScope $this, Name name) {
        return $this.createTypeAlias(name);
    }

    public static final /* synthetic */ Map access$getFunctionProtosBytes$p(DeserializedMemberScope $this) {
        return $this.functionProtosBytes;
    }

    public static final /* synthetic */ Map access$getPropertyProtosBytes$p(DeserializedMemberScope $this) {
        return $this.propertyProtosBytes;
    }

    public static final /* synthetic */ Set access$getTypeAliasNames$p(DeserializedMemberScope $this) {
        return $this.getTypeAliasNames();
    }
}

