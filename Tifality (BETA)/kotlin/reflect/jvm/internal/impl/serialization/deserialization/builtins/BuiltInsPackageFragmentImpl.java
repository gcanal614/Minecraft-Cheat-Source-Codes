/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import java.io.Closeable;
import java.io.InputStream;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.BuiltInsPackageFragment;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.builtins.BuiltInsBinaryVersion;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.DeserializedPackageFragmentImpl;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import kotlin.reflect.jvm.internal.impl.storage.StorageManager;
import org.jetbrains.annotations.NotNull;

public final class BuiltInsPackageFragmentImpl
extends DeserializedPackageFragmentImpl
implements BuiltInsPackageFragment {
    private final boolean isFallback;
    public static final Companion Companion = new Companion(null);

    private BuiltInsPackageFragmentImpl(FqName fqName2, StorageManager storageManager, ModuleDescriptor module, ProtoBuf.PackageFragment proto, BuiltInsBinaryVersion metadataVersion, boolean isFallback) {
        super(fqName2, storageManager, module, proto, metadataVersion, null);
        this.isFallback = isFallback;
    }

    public /* synthetic */ BuiltInsPackageFragmentImpl(FqName fqName2, StorageManager storageManager, ModuleDescriptor module, ProtoBuf.PackageFragment proto, BuiltInsBinaryVersion metadataVersion, boolean isFallback, DefaultConstructorMarker $constructor_marker) {
        this(fqName2, storageManager, module, proto, metadataVersion, isFallback);
    }

    public static final class Companion {
        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @NotNull
        public final BuiltInsPackageFragmentImpl create(@NotNull FqName fqName2, @NotNull StorageManager storageManager, @NotNull ModuleDescriptor module, @NotNull InputStream inputStream, boolean isFallback) {
            ProtoBuf.PackageFragment proto;
            ProtoBuf.PackageFragment packageFragment;
            Intrinsics.checkNotNullParameter(fqName2, "fqName");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(inputStream, "inputStream");
            BuiltInsBinaryVersion version = null;
            Closeable closeable = inputStream;
            boolean bl = false;
            boolean bl2 = false;
            Throwable throwable = null;
            try {
                InputStream stream = (InputStream)closeable;
                boolean bl3 = false;
                BuiltInsBinaryVersion builtInsBinaryVersion = version = BuiltInsBinaryVersion.Companion.readFrom(stream);
                if (builtInsBinaryVersion == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("version");
                }
                if (!builtInsBinaryVersion.isCompatible()) {
                    throw (Throwable)new UnsupportedOperationException("Kotlin built-in definition format version is not supported: " + "expected " + BuiltInsBinaryVersion.INSTANCE + ", actual " + version + ". " + "Please update Kotlin");
                }
                packageFragment = ProtoBuf.PackageFragment.parseFrom(stream, BuiltInSerializerProtocol.INSTANCE.getExtensionRegistry());
            }
            catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            }
            finally {
                CloseableKt.closeFinally(closeable, throwable);
            }
            ProtoBuf.PackageFragment packageFragment2 = proto = packageFragment;
            Intrinsics.checkNotNullExpressionValue(packageFragment2, "proto");
            return new BuiltInsPackageFragmentImpl(fqName2, storageManager, module, packageFragment2, version, isFallback, null);
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

