package net.java.games.input;

import java.io.IOException;

final class OSXHIDElement {
  private final OSXHIDDevice device;
  
  private final UsagePair usage_pair;
  
  private final long element_cookie;
  
  private final ElementType element_type;
  
  private final int min;
  
  private final int max;
  
  private final Component.Identifier identifier;
  
  private final boolean is_relative;
  
  public OSXHIDElement(OSXHIDDevice device, UsagePair usage_pair, long element_cookie, ElementType element_type, int min, int max, boolean is_relative) {
    this.device = device;
    this.usage_pair = usage_pair;
    this.element_cookie = element_cookie;
    this.element_type = element_type;
    this.min = min;
    this.max = max;
    this.identifier = computeIdentifier();
    this.is_relative = is_relative;
  }
  
  private final Component.Identifier computeIdentifier() {
    if (this.usage_pair.getUsagePage() == UsagePage.GENERIC_DESKTOP)
      return ((GenericDesktopUsage)this.usage_pair.getUsage()).getIdentifier(); 
    if (this.usage_pair.getUsagePage() == UsagePage.BUTTON)
      return ((ButtonUsage)this.usage_pair.getUsage()).getIdentifier(); 
    if (this.usage_pair.getUsagePage() == UsagePage.KEYBOARD_OR_KEYPAD)
      return ((KeyboardUsage)this.usage_pair.getUsage()).getIdentifier(); 
    return null;
  }
  
  final Component.Identifier getIdentifier() {
    return this.identifier;
  }
  
  final long getCookie() {
    return this.element_cookie;
  }
  
  final ElementType getType() {
    return this.element_type;
  }
  
  final boolean isRelative() {
    return (this.is_relative && this.identifier instanceof Component.Identifier.Axis);
  }
  
  final boolean isAnalog() {
    return (this.identifier instanceof Component.Identifier.Axis && this.identifier != Component.Identifier.Axis.POV);
  }
  
  private UsagePair getUsagePair() {
    return this.usage_pair;
  }
  
  final void getElementValue(OSXEvent event) throws IOException {
    this.device.getElementValue(this.element_cookie, event);
  }
  
  final float convertValue(float value) {
    if (this.identifier == Component.Identifier.Axis.POV) {
      switch ((int)value) {
        case 0:
          return 0.25F;
        case 1:
          return 0.375F;
        case 2:
          return 0.5F;
        case 3:
          return 0.625F;
        case 4:
          return 0.75F;
        case 5:
          return 0.875F;
        case 6:
          return 1.0F;
        case 7:
          return 0.125F;
        case 8:
          return 0.0F;
      } 
      return 0.0F;
    } 
    if (this.identifier instanceof Component.Identifier.Axis && !this.is_relative) {
      if (this.min == this.max)
        return 0.0F; 
      if (value > this.max) {
        value = this.max;
      } else if (value < this.min) {
        value = this.min;
      } 
      return 2.0F * (value - this.min) / (this.max - this.min) - 1.0F;
    } 
    return value;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\OSXHIDElement.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */