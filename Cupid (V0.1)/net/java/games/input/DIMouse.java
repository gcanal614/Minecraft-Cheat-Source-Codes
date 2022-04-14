package net.java.games.input;

import java.io.IOException;

final class DIMouse extends Mouse {
  private final IDirectInputDevice device;
  
  protected DIMouse(IDirectInputDevice device, Component[] components, Controller[] children, Rumbler[] rumblers) {
    super(device.getProductName(), components, children, rumblers);
    this.device = device;
  }
  
  public final void pollDevice() throws IOException {
    this.device.pollAll();
  }
  
  protected final boolean getNextDeviceEvent(Event event) throws IOException {
    return DIControllers.getNextDeviceEvent(event, this.device);
  }
  
  protected final void setDeviceEventQueueSize(int size) throws IOException {
    this.device.setBufferSize(size);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\DIMouse.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */