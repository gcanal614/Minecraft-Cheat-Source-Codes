/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.LinkedList;
import java.util.List;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolver;
import kotlin.reflect.jvm.internal.impl.metadata.deserialization.NameResolverImpl$WhenMappings;
import org.jetbrains.annotations.NotNull;

public final class NameResolverImpl
implements NameResolver {
    private final ProtoBuf.StringTable strings;
    private final ProtoBuf.QualifiedNameTable qualifiedNames;

    @Override
    @NotNull
    public String getString(int index) {
        String string = this.strings.getString(index);
        Intrinsics.checkNotNullExpressionValue(string, "strings.getString(index)");
        return string;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public String getQualifiedClassName(int index) {
        void packageFqNameSegments;
        Triple<List<String>, List<String>, Boolean> triple = this.traverseIds(index);
        List<String> list = triple.component1();
        List<String> relativeClassNameSegments = triple.component2();
        String className = CollectionsKt.joinToString$default(relativeClassNameSegments, ".", null, null, 0, null, null, 62, null);
        return packageFqNameSegments.isEmpty() ? className : CollectionsKt.joinToString$default((Iterable)packageFqNameSegments, "/", null, null, 0, null, null, 62, null) + '/' + className;
    }

    @Override
    public boolean isLocalClassName(int index) {
        return this.traverseIds(index).getThird();
    }

    private final Triple<List<String>, List<String>, Boolean> traverseIds(int startingIndex) {
        int index = startingIndex;
        LinkedList<String> packageNameSegments = new LinkedList<String>();
        LinkedList<String> relativeClassNameSegments = new LinkedList<String>();
        boolean local = false;
        while (index != -1) {
            ProtoBuf.QualifiedNameTable.QualifiedName proto;
            ProtoBuf.QualifiedNameTable.QualifiedName qualifiedName2 = proto = this.qualifiedNames.getQualifiedName(index);
            Intrinsics.checkNotNullExpressionValue(qualifiedName2, "proto");
            String shortName = this.strings.getString(qualifiedName2.getShortName());
            ProtoBuf.QualifiedNameTable.QualifiedName.Kind kind = proto.getKind();
            Intrinsics.checkNotNull(kind);
            switch (NameResolverImpl$WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1: {
                    relativeClassNameSegments.addFirst(shortName);
                    break;
                }
                case 2: {
                    packageNameSegments.addFirst(shortName);
                    break;
                }
                case 3: {
                    relativeClassNameSegments.addFirst(shortName);
                    local = true;
                    break;
                }
            }
            index = proto.getParentQualifiedName();
        }
        return new Triple<List<String>, List<String>, Boolean>(packageNameSegments, relativeClassNameSegments, local);
    }

    public NameResolverImpl(@NotNull ProtoBuf.StringTable strings, @NotNull ProtoBuf.QualifiedNameTable qualifiedNames) {
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(qualifiedNames, "qualifiedNames");
        this.strings = strings;
        this.qualifiedNames = qualifiedNames;
    }
}

