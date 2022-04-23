/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import org.jetbrains.annotations.NotNull;

public final class ScopesHolderForClass<T extends MemberScope> {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private final NotNullLazyValue scopeForOwnerModule$delegate;
    private final ClassDescriptor classDescriptor;
    private final Function1<KotlinTypeRefiner, T> scopeFactory;
    private final KotlinTypeRefiner kotlinTypeRefinerForOwnerModule;
    public static final Companion Companion;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ScopesHolderForClass.class), "scopeForOwnerModule", "getScopeForOwnerModule()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;"))};
        Companion = new Companion(null);
    }

    private final T getScopeForOwnerModule() {
        return (T)((MemberScope)StorageKt.getValue(this.scopeForOwnerModule$delegate, (Object)this, $$delegatedProperties[0]));
    }

    @NotNull
    public final T getScope(@NotNull KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        if (!kotlinTypeRefiner.isRefinementNeededForModule(DescriptorUtilsKt.getModule(this.classDescriptor))) {
            return this.getScopeForOwnerModule();
        }
        TypeConstructor typeConstructor2 = this.classDescriptor.getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor2, "classDescriptor.typeConstructor");
        if (!kotlinTypeRefiner.isRefinementNeededForTypeConstructor(typeConstructor2)) {
            return this.getScopeForOwnerModule();
        }
        return (T)kotlinTypeRefiner.getOrPutScopeForClass(this.classDescriptor, new Function0<T>(this, kotlinTypeRefiner){
            final /* synthetic */ ScopesHolderForClass this$0;
            final /* synthetic */ KotlinTypeRefiner $kotlinTypeRefiner;

            @NotNull
            public final T invoke() {
                return (T)((MemberScope)ScopesHolderForClass.access$getScopeFactory$p(this.this$0).invoke(this.$kotlinTypeRefiner));
            }
            {
                this.this$0 = scopesHolderForClass;
                this.$kotlinTypeRefiner = kotlinTypeRefiner;
                super(0);
            }
        });
    }

    private ScopesHolderForClass(ClassDescriptor classDescriptor, StorageManager storageManager, Function1<? super KotlinTypeRefiner, ? extends T> scopeFactory, KotlinTypeRefiner kotlinTypeRefinerForOwnerModule) {
        this.classDescriptor = classDescriptor;
        this.scopeFactory = scopeFactory;
        this.kotlinTypeRefinerForOwnerModule = kotlinTypeRefinerForOwnerModule;
        this.scopeForOwnerModule$delegate = storageManager.createLazyValue(new Function0<T>(this){
            final /* synthetic */ ScopesHolderForClass this$0;

            @NotNull
            public final T invoke() {
                return (T)((MemberScope)ScopesHolderForClass.access$getScopeFactory$p(this.this$0).invoke(ScopesHolderForClass.access$getKotlinTypeRefinerForOwnerModule$p(this.this$0)));
            }
            {
                this.this$0 = scopesHolderForClass;
                super(0);
            }
        });
    }

    public static final /* synthetic */ Function1 access$getScopeFactory$p(ScopesHolderForClass $this) {
        return $this.scopeFactory;
    }

    public static final /* synthetic */ KotlinTypeRefiner access$getKotlinTypeRefinerForOwnerModule$p(ScopesHolderForClass $this) {
        return $this.kotlinTypeRefinerForOwnerModule;
    }

    public /* synthetic */ ScopesHolderForClass(ClassDescriptor classDescriptor, StorageManager storageManager, Function1 scopeFactory, KotlinTypeRefiner kotlinTypeRefinerForOwnerModule, DefaultConstructorMarker $constructor_marker) {
        this(classDescriptor, storageManager, scopeFactory, kotlinTypeRefinerForOwnerModule);
    }

    public static final class Companion {
        @NotNull
        public final <T extends MemberScope> ScopesHolderForClass<T> create(@NotNull ClassDescriptor classDescriptor, @NotNull StorageManager storageManager, @NotNull KotlinTypeRefiner kotlinTypeRefinerForOwnerModule, @NotNull Function1<? super KotlinTypeRefiner, ? extends T> scopeFactory) {
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(kotlinTypeRefinerForOwnerModule, "kotlinTypeRefinerForOwnerModule");
            Intrinsics.checkNotNullParameter(scopeFactory, "scopeFactory");
            return new ScopesHolderForClass(classDescriptor, storageManager, scopeFactory, kotlinTypeRefinerForOwnerModule, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

