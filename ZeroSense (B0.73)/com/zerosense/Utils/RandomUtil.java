package com.zerosense.Utils;


import com.zerosense.Methods;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil implements Methods {
  private final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
  
  private static RandomUtil randomUtil;
  
  public double getRandomDouble(double paramDouble1, double paramDouble2) {
    return this.threadLocalRandom.nextDouble(paramDouble1, paramDouble2);
  }
  
  public int getRandomInteger(int paramInt1, int paramInt2) {
    return this.threadLocalRandom.nextInt(paramInt1, paramInt2);
  }
  
  public double getRandomGaussian(double paramDouble) {
    return this.threadLocalRandom.nextGaussian() * paramDouble;
  }
  
  public float getRandomFloat(float paramFloat1, float paramFloat2) {
    return (float)this.threadLocalRandom.nextDouble(paramFloat1, paramFloat2);
  }
  
  public static RandomUtil getInstance() {
    if (randomUtil == null)
      randomUtil = new RandomUtil(); 
    return randomUtil;
  }
  public final int RandomIn(int min, int max) {
    return ThreadLocalRandom.current().nextInt(min, max);
  }



}
