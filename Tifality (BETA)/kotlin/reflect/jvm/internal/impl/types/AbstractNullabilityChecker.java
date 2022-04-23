/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.types;

import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import org.jetbrains.annotations.NotNull;

public final class AbstractNullabilityChecker {
    public static final AbstractNullabilityChecker INSTANCE;

    public final boolean isPossibleSubtype(@NotNull AbstractTypeCheckerContext context, @NotNull SimpleTypeMarker subType, @NotNull SimpleTypeMarker superType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        return this.runIsPossibleSubtype(context, subType, superType);
    }

    private final boolean runIsPossibleSubtype(AbstractTypeCheckerContext $this$runIsPossibleSubtype, SimpleTypeMarker subType, SimpleTypeMarker superType) {
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            boolean bl = $this$runIsPossibleSubtype.isSingleClassifierType(subType) || $this$runIsPossibleSubtype.isIntersection($this$runIsPossibleSubtype.typeConstructor(subType)) || $this$runIsPossibleSubtype.isAllowedTypeVariable(subType);
            boolean bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean $i$a$-assert-AbstractNullabilityChecker$runIsPossibleSubtype$32 = false;
                String $i$a$-assert-AbstractNullabilityChecker$runIsPossibleSubtype$32 = "Not singleClassifierType and not intersection subType: " + subType;
                throw (Throwable)((Object)new AssertionError((Object)$i$a$-assert-AbstractNullabilityChecker$runIsPossibleSubtype$32));
            }
            bl = $this$runIsPossibleSubtype.isSingleClassifierType(superType) || $this$runIsPossibleSubtype.isAllowedTypeVariable(superType);
            bl2 = false;
            if (_Assertions.ENABLED && !bl) {
                boolean bl3 = false;
                String string = "Not singleClassifierType superType: " + superType;
                throw (Throwable)((Object)new AssertionError((Object)string));
            }
        }
        if ($this$runIsPossibleSubtype.isMarkedNullable(superType)) {
            return true;
        }
        if ($this$runIsPossibleSubtype.isDefinitelyNotNullType(subType)) {
            return true;
        }
        if (subType instanceof CapturedTypeMarker && $this$runIsPossibleSubtype.isProjectionNotNull((CapturedTypeMarker)subType)) {
            return true;
        }
        if (this.hasNotNullSupertype($this$runIsPossibleSubtype, subType, AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE)) {
            return true;
        }
        if ($this$runIsPossibleSubtype.isDefinitelyNotNullType(superType)) {
            return false;
        }
        if (this.hasNotNullSupertype($this$runIsPossibleSubtype, superType, AbstractTypeCheckerContext.SupertypesPolicy.UpperIfFlexible.INSTANCE)) {
            return false;
        }
        if ($this$runIsPossibleSubtype.isClassType(subType)) {
            return false;
        }
        return this.hasPathByNotMarkedNullableNodes($this$runIsPossibleSubtype, subType, $this$runIsPossibleSubtype.typeConstructor(superType));
    }

    /*
     * Unable to fully structure code
     */
    public final boolean hasNotNullSupertype(@NotNull AbstractTypeCheckerContext $this$hasNotNullSupertype, @NotNull SimpleTypeMarker type, @NotNull AbstractTypeCheckerContext.SupertypesPolicy supertypesPolicy) {
        block7: {
            Intrinsics.checkNotNullParameter($this$hasNotNullSupertype, "$this$hasNotNullSupertype");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(supertypesPolicy, "supertypesPolicy");
            this_$iv = $this$hasNotNullSupertype;
            $i$f$anySupertype = false;
            it = type;
            $i$a$-anySupertype-AbstractNullabilityChecker$hasNotNullSupertype$1 = false;
            if ($this$hasNotNullSupertype.isClassType(it) != false && $this$hasNotNullSupertype.isMarkedNullable(it) == false || $this$hasNotNullSupertype.isDefinitelyNotNullType(it) != false) {
                v0 = true;
            } else {
                this_$iv.initialize();
                v1 = this_$iv.getSupertypesDeque();
                Intrinsics.checkNotNull(v1);
                deque$iv = v1;
                v2 = this_$iv.getSupertypesSet();
                Intrinsics.checkNotNull(v2);
                visitedSupertypes$iv = v2;
                deque$iv.push(type);
                block0: while (true) {
                    var10_10 = deque$iv;
                    var11_11 = false;
                    if (!(var10_10.isEmpty() == false)) break;
                    if (visitedSupertypes$iv.size() > 1000) {
                        var10_10 = "Too many supertypes for type: " + type + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null);
                        var11_11 = false;
                        throw (Throwable)new IllegalStateException(var10_10.toString());
                    }
                    v3 = current$iv = deque$iv.pop();
                    Intrinsics.checkNotNullExpressionValue(v3, "current");
                    if (!visitedSupertypes$iv.add(v3)) continue;
                    it = current$iv;
                    $i$a$-anySupertype-AbstractNullabilityChecker$hasNotNullSupertype$2 = false;
                    var12_13 = $this$hasNotNullSupertype.isMarkedNullable(it) != false ? (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : supertypesPolicy;
                    var13_14 = false;
                    var14_16 = false;
                    it$iv = var12_13;
                    $i$a$-takeIf-AbstractTypeCheckerContext$anySupertype$policy$1$iv = false;
                    if (((Intrinsics.areEqual(it$iv, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true) != false ? var12_13 : null) == null) {
                        continue;
                    }
                    policy$iv = policy$iv;
                    var13_15 = this_$iv.supertypes(this_$iv.typeConstructor(current$iv)).iterator();
                    while (true) {
                        if (var13_15.hasNext()) ** break;
                        continue block0;
                        supertype$iv = var13_15.next();
                        it = newType$iv = policy$iv.transformType(this_$iv, supertype$iv);
                        $i$a$-anySupertype-AbstractNullabilityChecker$hasNotNullSupertype$1 = false;
                        if ($this$hasNotNullSupertype.isClassType(it) != false && $this$hasNotNullSupertype.isMarkedNullable(it) == false || $this$hasNotNullSupertype.isDefinitelyNotNullType(it) != false) {
                            this_$iv.clear();
                            v0 = true;
                            break block7;
                        }
                        deque$iv.add(newType$iv);
                    }
                    break;
                }
                this_$iv.clear();
                v0 = false;
            }
        }
        return v0;
    }

    /*
     * Unable to fully structure code
     */
    public final boolean hasPathByNotMarkedNullableNodes(@NotNull AbstractTypeCheckerContext $this$hasPathByNotMarkedNullableNodes, @NotNull SimpleTypeMarker start, @NotNull TypeConstructorMarker end) {
        block7: {
            Intrinsics.checkNotNullParameter($this$hasPathByNotMarkedNullableNodes, "$this$hasPathByNotMarkedNullableNodes");
            Intrinsics.checkNotNullParameter(start, "start");
            Intrinsics.checkNotNullParameter(end, "end");
            this_$iv = $this$hasPathByNotMarkedNullableNodes;
            $i$f$anySupertype = false;
            it = start;
            $i$a$-anySupertype-AbstractNullabilityChecker$hasPathByNotMarkedNullableNodes$1 = false;
            if (AbstractNullabilityChecker.INSTANCE.isApplicableAsEndNode($this$hasPathByNotMarkedNullableNodes, it, end)) {
                v0 = true;
            } else {
                this_$iv.initialize();
                v1 = this_$iv.getSupertypesDeque();
                Intrinsics.checkNotNull(v1);
                deque$iv = v1;
                v2 = this_$iv.getSupertypesSet();
                Intrinsics.checkNotNull(v2);
                visitedSupertypes$iv = v2;
                deque$iv.push(start);
                block0: while (true) {
                    var10_10 = deque$iv;
                    var11_11 = false;
                    if (!(var10_10.isEmpty() == false)) break;
                    if (visitedSupertypes$iv.size() > 1000) {
                        var10_10 = "Too many supertypes for type: " + start + ". Supertypes = " + CollectionsKt.joinToString$default(visitedSupertypes$iv, null, null, null, 0, null, null, 63, null);
                        var11_11 = false;
                        throw (Throwable)new IllegalStateException(var10_10.toString());
                    }
                    v3 = current$iv = deque$iv.pop();
                    Intrinsics.checkNotNullExpressionValue(v3, "current");
                    if (!visitedSupertypes$iv.add(v3)) continue;
                    it = current$iv;
                    $i$a$-anySupertype-AbstractNullabilityChecker$hasPathByNotMarkedNullableNodes$2 = false;
                    var12_13 = $this$hasPathByNotMarkedNullableNodes.isMarkedNullable(it) != false ? (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE : (AbstractTypeCheckerContext.SupertypesPolicy)AbstractTypeCheckerContext.SupertypesPolicy.LowerIfFlexible.INSTANCE;
                    var13_14 = false;
                    var14_16 = false;
                    it$iv = var12_13;
                    $i$a$-takeIf-AbstractTypeCheckerContext$anySupertype$policy$1$iv = false;
                    if (((Intrinsics.areEqual(it$iv, AbstractTypeCheckerContext.SupertypesPolicy.None.INSTANCE) ^ true) != false ? var12_13 : null) == null) {
                        continue;
                    }
                    policy$iv = policy$iv;
                    var13_15 = this_$iv.supertypes(this_$iv.typeConstructor(current$iv)).iterator();
                    while (true) {
                        if (var13_15.hasNext()) ** break;
                        continue block0;
                        supertype$iv = var13_15.next();
                        it = newType$iv = policy$iv.transformType(this_$iv, supertype$iv);
                        $i$a$-anySupertype-AbstractNullabilityChecker$hasPathByNotMarkedNullableNodes$1 = false;
                        if (AbstractNullabilityChecker.INSTANCE.isApplicableAsEndNode($this$hasPathByNotMarkedNullableNodes, it, end)) {
                            this_$iv.clear();
                            v0 = true;
                            break block7;
                        }
                        deque$iv.add(newType$iv);
                    }
                    break;
                }
                this_$iv.clear();
                v0 = false;
            }
        }
        return v0;
    }

    private final boolean isApplicableAsEndNode(AbstractTypeCheckerContext $this$isApplicableAsEndNode, SimpleTypeMarker type2, TypeConstructorMarker end) {
        if ($this$isApplicableAsEndNode.isNothing(type2)) {
            return true;
        }
        if ($this$isApplicableAsEndNode.isMarkedNullable(type2)) {
            return false;
        }
        if ($this$isApplicableAsEndNode.isStubTypeEqualsToAnything() && $this$isApplicableAsEndNode.isStubType(type2)) {
            return true;
        }
        return $this$isApplicableAsEndNode.isEqualTypeConstructors($this$isApplicableAsEndNode.typeConstructor(type2), end);
    }

    private AbstractNullabilityChecker() {
    }

    static {
        AbstractNullabilityChecker abstractNullabilityChecker;
        INSTANCE = abstractNullabilityChecker = new AbstractNullabilityChecker();
    }
}

