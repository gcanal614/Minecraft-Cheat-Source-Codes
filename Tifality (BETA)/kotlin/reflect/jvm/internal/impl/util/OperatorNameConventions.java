/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.util;

import java.util.Set;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;

public final class OperatorNameConventions {
    @JvmField
    @NotNull
    public static final Name GET_VALUE;
    @JvmField
    @NotNull
    public static final Name SET_VALUE;
    @JvmField
    @NotNull
    public static final Name PROVIDE_DELEGATE;
    @JvmField
    @NotNull
    public static final Name EQUALS;
    @JvmField
    @NotNull
    public static final Name COMPARE_TO;
    @JvmField
    @NotNull
    public static final Name CONTAINS;
    @JvmField
    @NotNull
    public static final Name INVOKE;
    @JvmField
    @NotNull
    public static final Name ITERATOR;
    @JvmField
    @NotNull
    public static final Name GET;
    @JvmField
    @NotNull
    public static final Name SET;
    @JvmField
    @NotNull
    public static final Name NEXT;
    @JvmField
    @NotNull
    public static final Name HAS_NEXT;
    @JvmField
    @NotNull
    public static final Regex COMPONENT_REGEX;
    @JvmField
    @NotNull
    public static final Name AND;
    @JvmField
    @NotNull
    public static final Name OR;
    @JvmField
    @NotNull
    public static final Name INC;
    @JvmField
    @NotNull
    public static final Name DEC;
    @JvmField
    @NotNull
    public static final Name PLUS;
    @JvmField
    @NotNull
    public static final Name MINUS;
    @JvmField
    @NotNull
    public static final Name NOT;
    @JvmField
    @NotNull
    public static final Name UNARY_MINUS;
    @JvmField
    @NotNull
    public static final Name UNARY_PLUS;
    @JvmField
    @NotNull
    public static final Name TIMES;
    @JvmField
    @NotNull
    public static final Name DIV;
    @JvmField
    @NotNull
    public static final Name MOD;
    @JvmField
    @NotNull
    public static final Name REM;
    @JvmField
    @NotNull
    public static final Name RANGE_TO;
    @JvmField
    @NotNull
    public static final Name TIMES_ASSIGN;
    @JvmField
    @NotNull
    public static final Name DIV_ASSIGN;
    @JvmField
    @NotNull
    public static final Name MOD_ASSIGN;
    @JvmField
    @NotNull
    public static final Name REM_ASSIGN;
    @JvmField
    @NotNull
    public static final Name PLUS_ASSIGN;
    @JvmField
    @NotNull
    public static final Name MINUS_ASSIGN;
    @JvmField
    @NotNull
    public static final Set<Name> UNARY_OPERATION_NAMES;
    @JvmField
    @NotNull
    public static final Set<Name> SIMPLE_UNARY_OPERATION_NAMES;
    @JvmField
    @NotNull
    public static final Set<Name> BINARY_OPERATION_NAMES;
    @JvmField
    @NotNull
    public static final Set<Name> ASSIGNMENT_OPERATIONS;
    @JvmField
    @NotNull
    public static final Set<Name> DELEGATED_PROPERTY_OPERATORS;
    public static final OperatorNameConventions INSTANCE;

    private OperatorNameConventions() {
    }

    static {
        OperatorNameConventions operatorNameConventions;
        INSTANCE = operatorNameConventions = new OperatorNameConventions();
        Name name = Name.identifier("getValue");
        Intrinsics.checkNotNullExpressionValue(name, "Name.identifier(\"getValue\")");
        GET_VALUE = name;
        Name name2 = Name.identifier("setValue");
        Intrinsics.checkNotNullExpressionValue(name2, "Name.identifier(\"setValue\")");
        SET_VALUE = name2;
        Name name3 = Name.identifier("provideDelegate");
        Intrinsics.checkNotNullExpressionValue(name3, "Name.identifier(\"provideDelegate\")");
        PROVIDE_DELEGATE = name3;
        Name name4 = Name.identifier("equals");
        Intrinsics.checkNotNullExpressionValue(name4, "Name.identifier(\"equals\")");
        EQUALS = name4;
        Name name5 = Name.identifier("compareTo");
        Intrinsics.checkNotNullExpressionValue(name5, "Name.identifier(\"compareTo\")");
        COMPARE_TO = name5;
        Name name6 = Name.identifier("contains");
        Intrinsics.checkNotNullExpressionValue(name6, "Name.identifier(\"contains\")");
        CONTAINS = name6;
        Name name7 = Name.identifier("invoke");
        Intrinsics.checkNotNullExpressionValue(name7, "Name.identifier(\"invoke\")");
        INVOKE = name7;
        Name name8 = Name.identifier("iterator");
        Intrinsics.checkNotNullExpressionValue(name8, "Name.identifier(\"iterator\")");
        ITERATOR = name8;
        Name name9 = Name.identifier("get");
        Intrinsics.checkNotNullExpressionValue(name9, "Name.identifier(\"get\")");
        GET = name9;
        Name name10 = Name.identifier("set");
        Intrinsics.checkNotNullExpressionValue(name10, "Name.identifier(\"set\")");
        SET = name10;
        Name name11 = Name.identifier("next");
        Intrinsics.checkNotNullExpressionValue(name11, "Name.identifier(\"next\")");
        NEXT = name11;
        Name name12 = Name.identifier("hasNext");
        Intrinsics.checkNotNullExpressionValue(name12, "Name.identifier(\"hasNext\")");
        HAS_NEXT = name12;
        COMPONENT_REGEX = new Regex("component\\d+");
        Name name13 = Name.identifier("and");
        Intrinsics.checkNotNullExpressionValue(name13, "Name.identifier(\"and\")");
        AND = name13;
        Name name14 = Name.identifier("or");
        Intrinsics.checkNotNullExpressionValue(name14, "Name.identifier(\"or\")");
        OR = name14;
        Name name15 = Name.identifier("inc");
        Intrinsics.checkNotNullExpressionValue(name15, "Name.identifier(\"inc\")");
        INC = name15;
        Name name16 = Name.identifier("dec");
        Intrinsics.checkNotNullExpressionValue(name16, "Name.identifier(\"dec\")");
        DEC = name16;
        Name name17 = Name.identifier("plus");
        Intrinsics.checkNotNullExpressionValue(name17, "Name.identifier(\"plus\")");
        PLUS = name17;
        Name name18 = Name.identifier("minus");
        Intrinsics.checkNotNullExpressionValue(name18, "Name.identifier(\"minus\")");
        MINUS = name18;
        Name name19 = Name.identifier("not");
        Intrinsics.checkNotNullExpressionValue(name19, "Name.identifier(\"not\")");
        NOT = name19;
        Name name20 = Name.identifier("unaryMinus");
        Intrinsics.checkNotNullExpressionValue(name20, "Name.identifier(\"unaryMinus\")");
        UNARY_MINUS = name20;
        Name name21 = Name.identifier("unaryPlus");
        Intrinsics.checkNotNullExpressionValue(name21, "Name.identifier(\"unaryPlus\")");
        UNARY_PLUS = name21;
        Name name22 = Name.identifier("times");
        Intrinsics.checkNotNullExpressionValue(name22, "Name.identifier(\"times\")");
        TIMES = name22;
        Name name23 = Name.identifier("div");
        Intrinsics.checkNotNullExpressionValue(name23, "Name.identifier(\"div\")");
        DIV = name23;
        Name name24 = Name.identifier("mod");
        Intrinsics.checkNotNullExpressionValue(name24, "Name.identifier(\"mod\")");
        MOD = name24;
        Name name25 = Name.identifier("rem");
        Intrinsics.checkNotNullExpressionValue(name25, "Name.identifier(\"rem\")");
        REM = name25;
        Name name26 = Name.identifier("rangeTo");
        Intrinsics.checkNotNullExpressionValue(name26, "Name.identifier(\"rangeTo\")");
        RANGE_TO = name26;
        Name name27 = Name.identifier("timesAssign");
        Intrinsics.checkNotNullExpressionValue(name27, "Name.identifier(\"timesAssign\")");
        TIMES_ASSIGN = name27;
        Name name28 = Name.identifier("divAssign");
        Intrinsics.checkNotNullExpressionValue(name28, "Name.identifier(\"divAssign\")");
        DIV_ASSIGN = name28;
        Name name29 = Name.identifier("modAssign");
        Intrinsics.checkNotNullExpressionValue(name29, "Name.identifier(\"modAssign\")");
        MOD_ASSIGN = name29;
        Name name30 = Name.identifier("remAssign");
        Intrinsics.checkNotNullExpressionValue(name30, "Name.identifier(\"remAssign\")");
        REM_ASSIGN = name30;
        Name name31 = Name.identifier("plusAssign");
        Intrinsics.checkNotNullExpressionValue(name31, "Name.identifier(\"plusAssign\")");
        PLUS_ASSIGN = name31;
        Name name32 = Name.identifier("minusAssign");
        Intrinsics.checkNotNullExpressionValue(name32, "Name.identifier(\"minusAssign\")");
        MINUS_ASSIGN = name32;
        UNARY_OPERATION_NAMES = SetsKt.setOf(INC, DEC, UNARY_PLUS, UNARY_MINUS, NOT);
        SIMPLE_UNARY_OPERATION_NAMES = SetsKt.setOf(UNARY_PLUS, UNARY_MINUS, NOT);
        BINARY_OPERATION_NAMES = SetsKt.setOf(TIMES, PLUS, MINUS, DIV, MOD, REM, RANGE_TO);
        ASSIGNMENT_OPERATIONS = SetsKt.setOf(TIMES_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, REM_ASSIGN, PLUS_ASSIGN, MINUS_ASSIGN);
        DELEGATED_PROPERTY_OPERATORS = SetsKt.setOf(GET_VALUE, SET_VALUE, PROVIDE_DELEGATE);
    }
}

