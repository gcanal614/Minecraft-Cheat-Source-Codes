package net.java.games.input;

import java.io.IOException;

final class OSXKeyboard extends Keyboard {
  private final Controller.PortType port;
  
  private final OSXHIDQueue queue;
  
  protected OSXKeyboard(OSXHIDDevice device, OSXHIDQueue queue, Component[] components, Controller[] children, Rumbler[] rumblers) {
    super(device.getProductName(), components, children, rumblers);
    this.queue = queue;
    this.port = device.getPortType();
  }
  
  protected final boolean getNextDeviceEvent(Event event) throws IOException {
    return OSXControllers.getNextDeviceEvent(event, this.queue);
  }
  
  protected final void setDeviceEventQueueSize(int size) throws IOException {
    this.queue.setQueueDepth(size);
  }
  
  public final Controller.PortType getPortType() {
    return this.port;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\OSXKeyboard.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */