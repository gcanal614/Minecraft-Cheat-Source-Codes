/*
 * Decompiled with CFR 0.152.
 */
package net.optifine.expr;

import net.optifine.expr.TokenType;

public class Token {
    private TokenType type;
    private String text;

    public Token(TokenType type2, String text) {
        this.type = type2;
        this.text = text;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getText() {
        return this.text;
    }

    public String toString() {
        return this.text;
    }
}

