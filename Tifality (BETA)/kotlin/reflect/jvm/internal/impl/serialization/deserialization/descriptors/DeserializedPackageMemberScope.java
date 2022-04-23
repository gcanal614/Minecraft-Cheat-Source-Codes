/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.incremental.UtilsKt;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.BinaryVersion;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.TypeTable;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.VersionRequirementTable;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationComponents;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializationContext;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedContainerSource;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedMemberScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DeserializedPackageMemberScope
extends DeserializedMemberScope {
    private final FqName packageFqName;
    private final PackageFragmentDescriptor packageDescriptor;

    /*
     * WARNING - void declaration
     */
    @NotNull
    public List<DeclarationDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        Iterable<ClassDescriptorFactory> iterable = this.getC().getComponents().getFictitiousClassDescriptorFactories();
        Collection<DeclarationDescriptor> collection = this.computeDescriptors(kindFilter, nameFilter, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
        boolean $i$f$flatMap = false;
        void var5_6 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            ClassDescriptorFactory it = (ClassDescriptorFactory)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = it.getAllContributedClassesIfPossible(this.packageFqName);
            CollectionsKt.addAll(destination$iv$iv, list$iv$iv);
        }
        List list = (List)destination$iv$iv;
        return CollectionsKt.plus(collection, (Iterable)list);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean hasClass(@NotNull Name name) {
        ClassDescriptorFactory it;
        Intrinsics.checkNotNullParameter(name, "name");
        if (super.hasClass(name)) return true;
        Iterable<ClassDescriptorFactory> $this$any$iv = this.getC().getComponents().getFictitiousClassDescriptorFactories();
        boolean $i$f$any = false;
        if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
            return false;
        }
        Iterator<ClassDescriptorFactory> iterator2 = $this$any$iv.iterator();
        do {
            ClassDescriptorFactory element$iv;
            if (!iterator2.hasNext()) return false;
            it = element$iv = iterator2.next();
            boolean bl = false;
        } while (!it.shouldCreateClass(this.packageFqName, name));
        return true;
    }

    @Override
    @NotNull
    protected ClassId createClassId(@NotNull Name name) {
        Intrinsics.checkNotNullParameter(name, "name");
        return new ClassId(this.packageFqName, name);
    }

    @Override
    @Nullable
    public ClassifierDescriptor getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        this.recordLookup(name, location);
        return super.getContributedClassifier(name, location);
    }

    @Override
    public void recordLookup(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(this.getC().getComponents().getLookupTracker(), location, this.packageDescriptor, name);
    }

    @Override
    @NotNull
    protected Set<Name> getNonDeclaredFunctionNames() {
        return SetsKt.emptySet();
    }

    @Override
    @NotNull
    protected Set<Name> getNonDeclaredVariableNames() {
        return SetsKt.emptySet();
    }

    @Override
    @Nullable
    protected Set<Name> getNonDeclaredClassifierNames() {
        return SetsKt.emptySet();
    }

    @Override
    protected void addEnumEntryDescriptors(@NotNull Collection<DeclarationDescriptor> result2, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(result2, "result");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
    }

    public DeserializedPackageMemberScope(@NotNull PackageFragmentDescriptor packageDescriptor, @NotNull ProtoBuf.Package proto, @NotNull NameResolver nameResolver, @NotNull BinaryVersion metadataVersion, @Nullable DeserializedContainerSource containerSource, @NotNull DeserializationComponents components, @NotNull Function0<? extends Collection<Name>> classNames2) {
        Intrinsics.checkNotNullParameter(packageDescriptor, "packageDescriptor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(classNames2, "classNames");
        ProtoBuf.TypeTable typeTable = proto.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "proto.typeTable");
        TypeTable typeTable2 = new TypeTable(typeTable);
        ProtoBuf.VersionRequirementTable versionRequirementTable = proto.getVersionRequirementTable();
        Intrinsics.checkNotNullExpressionValue(versionRequirementTable, "proto.versionRequirementTable");
        DeserializationContext deserializationContext = components.createContext(packageDescriptor, nameResolver, typeTable2, VersionRequirementTable.Companion.create(versionRequirementTable), metadataVersion, containerSource);
        List<ProtoBuf.Function> list = proto.getFunctionList();
        Intrinsics.checkNotNullExpressionValue(list, "proto.functionList");
        Collection collection = list;
        List<ProtoBuf.Property> list2 = proto.getPropertyList();
        Intrinsics.checkNotNullExpressionValue(list2, "proto.propertyList");
        Collection collection2 = list2;
        List<ProtoBuf.TypeAlias> list3 = proto.getTypeAliasList();
        Intrinsics.checkNotNullExpressionValue(list3, "proto.typeAliasList");
        super(deserializationContext, collection, collection2, (Collection<ProtoBuf.TypeAlias>)list3, classNames2);
        this.packageDescriptor = packageDescriptor;
        this.packageFqName = this.packageDescriptor.getFqName();
    }
}

