package com.ibm.icu.impl;

import java.io.InputStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;

public final class ICUData {
  public static boolean exists(final String resourceName) {
    URL i = null;
    if (System.getSecurityManager() != null) {
      i = AccessController.<URL>doPrivileged(new PrivilegedAction<URL>() {
            public URL run() {
              return ICUData.class.getResource(resourceName);
            }
          });
    } else {
      i = ICUData.class.getResource(resourceName);
    } 
    return (i != null);
  }
  
  private static InputStream getStream(final Class<?> root, final String resourceName, boolean required) {
    InputStream i = null;
    if (System.getSecurityManager() != null) {
      i = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
              return root.getResourceAsStream(resourceName);
            }
          });
    } else {
      i = root.getResourceAsStream(resourceName);
    } 
    if (i == null && required)
      throw new MissingResourceException("could not locate data " + resourceName, root.getPackage().getName(), resourceName); 
    return i;
  }
  
  private static InputStream getStream(final ClassLoader loader, final String resourceName, boolean required) {
    InputStream i = null;
    if (System.getSecurityManager() != null) {
      i = AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
            public InputStream run() {
              return loader.getResourceAsStream(resourceName);
            }
          });
    } else {
      i = loader.getResourceAsStream(resourceName);
    } 
    if (i == null && required)
      throw new MissingResourceException("could not locate data", loader.toString(), resourceName); 
    return i;
  }
  
  public static InputStream getStream(ClassLoader loader, String resourceName) {
    return getStream(loader, resourceName, false);
  }
  
  public static InputStream getRequiredStream(ClassLoader loader, String resourceName) {
    return getStream(loader, resourceName, true);
  }
  
  public static InputStream getStream(String resourceName) {
    return getStream(ICUData.class, resourceName, false);
  }
  
  public static InputStream getRequiredStream(String resourceName) {
    return getStream(ICUData.class, resourceName, true);
  }
  
  public static InputStream getStream(Class<?> root, String resourceName) {
    return getStream(root, resourceName, false);
  }
  
  public static InputStream getRequiredStream(Class<?> root, String resourceName) {
    return getStream(root, resourceName, true);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\icu\impl\ICUData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */