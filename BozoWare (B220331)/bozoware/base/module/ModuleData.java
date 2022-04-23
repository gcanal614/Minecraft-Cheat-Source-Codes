// 
// Decompiled by Procyon v0.5.36
// 

package bozoware.base.module;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.Annotation;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleData {
    String moduleName();
    
    ModuleCategory moduleCategory();
}
