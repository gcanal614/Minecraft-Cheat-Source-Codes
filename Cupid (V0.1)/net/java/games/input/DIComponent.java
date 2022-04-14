package net.java.games.input;

import java.io.IOException;

final class DIComponent extends AbstractComponent {
  private final DIDeviceObject object;
  
  public DIComponent(Component.Identifier identifier, DIDeviceObject object) {
    super(object.getName(), identifier);
    this.object = object;
  }
  
  public final boolean isRelative() {
    return this.object.isRelative();
  }
  
  public final boolean isAnalog() {
    return this.object.isAnalog();
  }
  
  public final float getDeadZone() {
    return this.object.getDeadzone();
  }
  
  public final DIDeviceObject getDeviceObject() {
    return this.object;
  }
  
  protected final float poll() throws IOException {
    return DIControllers.poll(this, this.object);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\DIComponent.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */