/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.List;
import java.util.Set;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.annotations.NotNull;

public interface ModuleDependencies {
    @NotNull
    public List<ModuleDescriptorImpl> getAllDependencies();

    @NotNull
    public Set<ModuleDescriptorImpl> getModulesWhoseInternalsAreVisible();

    @NotNull
    public List<ModuleDescriptorImpl> getExpectedByDependencies();
}

