/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltIns$WhenMappings;
import kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsSettings;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.AdditionalClassPartsProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.ClassDescriptorFactory;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.PlatformDependentDeclarationFilter;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public final class JvmBuiltIns
extends KotlinBuiltIns {
    static final /* synthetic */ KProperty[] $$delegatedProperties;
    private ModuleDescriptor ownerModuleDescriptor;
    private boolean isAdditionalBuiltInsFeatureSupported;
    @NotNull
    private final NotNullLazyValue settings$delegate;

    static {
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(JvmBuiltIns.class), "settings", "getSettings()Lorg/jetbrains/kotlin/builtins/jvm/JvmBuiltInsSettings;"))};
    }

    public final void initialize(@NotNull ModuleDescriptor moduleDescriptor, boolean isAdditionalBuiltInsFeatureSupported) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        boolean bl = this.ownerModuleDescriptor == null;
        boolean bl2 = false;
        if (_Assertions.ENABLED && !bl) {
            boolean bl3 = false;
            String string = "JvmBuiltins repeated initialization";
            throw (Throwable)((Object)new AssertionError((Object)string));
        }
        this.ownerModuleDescriptor = moduleDescriptor;
        this.isAdditionalBuiltInsFeatureSupported = isAdditionalBuiltInsFeatureSupported;
    }

    @NotNull
    public final JvmBuiltInsSettings getSettings() {
        return (JvmBuiltInsSettings)StorageKt.getValue(this.settings$delegate, (Object)this, $$delegatedProperties[0]);
    }

    @Override
    @NotNull
    protected PlatformDependentDeclarationFilter getPlatformDependentDeclarationFilter() {
        return this.getSettings();
    }

    @Override
    @NotNull
    protected AdditionalClassPartsProvider getAdditionalClassPartsProvider() {
        return this.getSettings();
    }

    @NotNull
    protected List<ClassDescriptorFactory> getClassDescriptorFactories() {
        Iterable<ClassDescriptorFactory> iterable = super.getClassDescriptorFactories();
        Intrinsics.checkNotNullExpressionValue(iterable, "super.getClassDescriptorFactories()");
        StorageManager storageManager = this.getStorageManager();
        Intrinsics.checkNotNullExpressionValue(storageManager, "storageManager");
        ModuleDescriptorImpl moduleDescriptorImpl = this.getBuiltInsModule();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptorImpl, "builtInsModule");
        return CollectionsKt.plus(iterable, new JvmBuiltInClassDescriptorFactory(storageManager, moduleDescriptorImpl, null, 4, null));
    }

    public JvmBuiltIns(@NotNull StorageManager storageManager, @NotNull Kind kind) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter((Object)kind, "kind");
        super(storageManager);
        this.isAdditionalBuiltInsFeatureSupported = true;
        this.settings$delegate = storageManager.createLazyValue((Function0)new Function0<JvmBuiltInsSettings>(this, storageManager){
            final /* synthetic */ JvmBuiltIns this$0;
            final /* synthetic */ StorageManager $storageManager;

            @NotNull
            public final JvmBuiltInsSettings invoke() {
                ModuleDescriptorImpl moduleDescriptorImpl = this.this$0.getBuiltInsModule();
                Intrinsics.checkNotNullExpressionValue(moduleDescriptorImpl, "builtInsModule");
                return new JvmBuiltInsSettings(moduleDescriptorImpl, this.$storageManager, (Function0<? extends ModuleDescriptor>)new Function0<ModuleDescriptor>(this){
                    final /* synthetic */ settings.2 this$0;

                    @NotNull
                    public final ModuleDescriptor invoke() {
                        ModuleDescriptor $this$sure$iv = JvmBuiltIns.access$getOwnerModuleDescriptor$p(this.this$0.this$0);
                        boolean $i$f$sure = false;
                        ModuleDescriptor moduleDescriptor = $this$sure$iv;
                        if (moduleDescriptor == null) {
                            String string;
                            boolean bl = false;
                            String string2 = string = "JvmBuiltins has not been initialized properly";
                            throw (Throwable)((Object)new AssertionError((Object)string2));
                        }
                        return moduleDescriptor;
                    }
                    {
                        this.this$0 = var1_1;
                        super(0);
                    }
                }, new Function0<Boolean>(this){
                    final /* synthetic */ settings.2 this$0;

                    public final boolean invoke() {
                        ModuleDescriptor $this$sure$iv = JvmBuiltIns.access$getOwnerModuleDescriptor$p(this.this$0.this$0);
                        boolean $i$f$sure = false;
                        if ($this$sure$iv == null) {
                            String string;
                            boolean bl = false;
                            String string2 = string = "JvmBuiltins has not been initialized properly";
                            throw (Throwable)((Object)new AssertionError((Object)string2));
                        }
                        return JvmBuiltIns.access$isAdditionalBuiltInsFeatureSupported$p(this.this$0.this$0);
                    }
                    {
                        this.this$0 = var1_1;
                        super(0);
                    }
                });
            }
            {
                this.this$0 = jvmBuiltIns;
                this.$storageManager = storageManager;
                super(0);
            }
        });
        switch (JvmBuiltIns$WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
            case 1: {
                break;
            }
            case 2: {
                this.createBuiltInsModule(false);
                break;
            }
            case 3: {
                this.createBuiltInsModule(true);
                break;
            }
        }
    }

    public static final /* synthetic */ ModuleDescriptor access$getOwnerModuleDescriptor$p(JvmBuiltIns $this) {
        return $this.ownerModuleDescriptor;
    }

    public static final /* synthetic */ boolean access$isAdditionalBuiltInsFeatureSupported$p(JvmBuiltIns $this) {
        return $this.isAdditionalBuiltInsFeatureSupported;
    }

    public static final class Kind
    extends Enum<Kind> {
        public static final /* enum */ Kind FROM_DEPENDENCIES;
        public static final /* enum */ Kind FROM_CLASS_LOADER;
        public static final /* enum */ Kind FALLBACK;
        private static final /* synthetic */ Kind[] $VALUES;

        static {
            Kind[] kindArray = new Kind[3];
            Kind[] kindArray2 = kindArray;
            kindArray[0] = FROM_DEPENDENCIES = new Kind();
            kindArray[1] = FROM_CLASS_LOADER = new Kind();
            kindArray[2] = FALLBACK = new Kind();
            $VALUES = kindArray;
        }

        public static Kind[] values() {
            return (Kind[])$VALUES.clone();
        }

        public static Kind valueOf(String string) {
            return Enum.valueOf(Kind.class, string);
        }
    }
}

