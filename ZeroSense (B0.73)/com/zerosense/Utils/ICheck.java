package com.zerosense.Utils;

import net.minecraft.entity.Entity;

@FunctionalInterface
public interface ICheck {
   boolean validate(Entity var1);
}
