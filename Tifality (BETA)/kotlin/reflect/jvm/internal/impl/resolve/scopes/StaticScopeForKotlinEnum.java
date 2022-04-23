/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.utils.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class StaticScopeForKotlinEnum
extends MemberScopeImpl {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final NotNullLazyValue functions$delegate;
    private final ClassDescriptor containingClass;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(StaticScopeForKotlinEnum.class), "functions", "getFunctions()Ljava/util/List;"))};
    }

    @Nullable
    public Void getContributedClassifier(@NotNull Name name, @NotNull LookupLocation location) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return null;
    }

    private final List<SimpleFunctionDescriptor> getFunctions() {
        return (List)StorageKt.getValue(this.functions$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @NotNull
    public List<SimpleFunctionDescriptor> getContributedDescriptors(@NotNull DescriptorKindFilter kindFilter, @NotNull Function1<? super Name, Boolean> nameFilter) {
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        return this.getFunctions();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public SmartList<SimpleFunctionDescriptor> getContributedFunctions(@NotNull Name name, @NotNull LookupLocation location) {
        void $this$filterTo$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterable iterable = this.getFunctions();
        Collection destination$iv = new SmartList();
        boolean $i$f$filterTo = false;
        for (Object element$iv : $this$filterTo$iv) {
            SimpleFunctionDescriptor it = (SimpleFunctionDescriptor)element$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getName(), name)) continue;
            destination$iv.add(element$iv);
        }
        return (SmartList)destination$iv;
    }

    public StaticScopeForKotlinEnum(@NotNull StorageManager storageManager, @NotNull ClassDescriptor containingClass) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        this.containingClass = containingClass;
        boolean bl = this.containingClass.getKind() == ClassKind.ENUM_CLASS;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "Class should be an enum: " + this.containingClass;
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.functions$delegate = storageManager.createLazyValue((Function0)new Function0<List<? extends SimpleFunctionDescriptor>>(this){
            final /* synthetic */ StaticScopeForKotlinEnum this$0;

            @NotNull
            public final List<SimpleFunctionDescriptor> invoke() {
                return CollectionsKt.listOf(DescriptorFactory.createEnumValueOfMethod(StaticScopeForKotlinEnum.access$getContainingClass$p(this.this$0)), DescriptorFactory.createEnumValuesMethod(StaticScopeForKotlinEnum.access$getContainingClass$p(this.this$0)));
            }
            {
                this.this$0 = staticScopeForKotlinEnum;
                super(0);
            }
        });
    }

    public static final /* synthetic */ ClassDescriptor access$getContainingClass$p(StaticScopeForKotlinEnum $this) {
        return $this.containingClass;
    }
}

