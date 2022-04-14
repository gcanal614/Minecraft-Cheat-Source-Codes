package net.java.games.input;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;
import net.java.games.util.plugins.Plugin;

public class WinTabEnvironmentPlugin extends ControllerEnvironment implements Plugin {
  private static boolean supported = false;
  
  private final Controller[] controllers;
  
  static void loadLibrary(final String lib_name) {
    AccessController.doPrivileged(new PrivilegedAction() {
          private final String val$lib_name;
          
          public final Object run() {
            try {
              String lib_path = System.getProperty("net.java.games.input.librarypath");
              if (lib_path != null) {
                System.load(lib_path + File.separator + System.mapLibraryName(lib_name));
              } else {
                System.loadLibrary(lib_name);
              } 
            } catch (UnsatisfiedLinkError e) {
              e.printStackTrace();
              WinTabEnvironmentPlugin.supported = false;
            } 
            return null;
          }
        });
  }
  
  static String getPrivilegedProperty(final String property) {
    return AccessController.<String>doPrivileged(new PrivilegedAction() {
          private final String val$property;
          
          public Object run() {
            return System.getProperty(property);
          }
        });
  }
  
  static String getPrivilegedProperty(final String property, final String default_value) {
    return AccessController.<String>doPrivileged(new PrivilegedAction() {
          private final String val$property;
          
          private final String val$default_value;
          
          public Object run() {
            return System.getProperty(property, default_value);
          }
        });
  }
  
  static {
    String osName = getPrivilegedProperty("os.name", "").trim();
    if (osName.startsWith("Windows")) {
      supported = true;
      loadLibrary("jinput-wintab");
    } 
  }
  
  private final List active_devices = new ArrayList();
  
  private final WinTabContext winTabContext;
  
  public WinTabEnvironmentPlugin() {
    if (isSupported()) {
      DummyWindow window = null;
      WinTabContext winTabContext = null;
      Controller[] controllers = new Controller[0];
      try {
        window = new DummyWindow();
        winTabContext = new WinTabContext(window);
        try {
          winTabContext.open();
          controllers = winTabContext.getControllers();
        } catch (Exception e) {
          window.destroy();
          throw e;
        } 
      } catch (Exception e) {
        logln("Failed to enumerate devices: " + e.getMessage());
        e.printStackTrace();
      } 
      this.controllers = controllers;
      this.winTabContext = winTabContext;
      AccessController.doPrivileged(new PrivilegedAction() {
            private final WinTabEnvironmentPlugin this$0;
            
            public final Object run() {
              Runtime.getRuntime().addShutdownHook(new WinTabEnvironmentPlugin.ShutdownHook());
              return null;
            }
          });
    } else {
      this.winTabContext = null;
      this.controllers = new Controller[0];
    } 
  }
  
  public boolean isSupported() {
    return supported;
  }
  
  public Controller[] getControllers() {
    return this.controllers;
  }
  
  private final class ShutdownHook extends Thread {
    private final WinTabEnvironmentPlugin this$0;
    
    private ShutdownHook() {}
    
    public final void run() {
      for (int i = 0; i < WinTabEnvironmentPlugin.this.active_devices.size(); i++);
      WinTabEnvironmentPlugin.this.winTabContext.close();
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\WinTabEnvironmentPlugin.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */