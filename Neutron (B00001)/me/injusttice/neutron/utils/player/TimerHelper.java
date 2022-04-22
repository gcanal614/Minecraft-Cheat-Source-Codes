package me.injusttice.neutron.utils.player;

public class TimerHelper {
  public long lastMs = System.currentTimeMillis();
  
  public void reset() {
    this.lastMs = System.currentTimeMillis();
  }
  
  public void resetWithOffset(long offset) {
    this.lastMs = System.currentTimeMillis() + offset;
  }
  
  public boolean hasTimeElapsed(long time, boolean reset) {
    if (System.currentTimeMillis() - this.lastMs > time) {
      if (reset)
        reset(); 
      return true;
    } 
    return false;
  }
  
  public boolean timeElapsed(long time) {
    return (System.currentTimeMillis() - this.lastMs > time);
  }
}
