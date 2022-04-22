package wtf.astronicy.IMPL.module.registery;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import wtf.astronicy.IMPL.module.ModuleCategory;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Category {
   ModuleCategory value();
}
