/*
 * Decompiled with CFR 0.152.
 */
package org.objectweb.asm.tree;

import org.objectweb.asm.MethodVisitor;

public class ParameterNode {
    public String name;
    public int access;

    public ParameterNode(String name, int access) {
        this.name = name;
        this.access = access;
    }

    public void accept(MethodVisitor mv) {
        mv.visitParameter(this.name, this.access);
    }
}

