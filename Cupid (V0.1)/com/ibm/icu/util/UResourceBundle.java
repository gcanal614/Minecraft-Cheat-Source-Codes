package com.ibm.icu.util;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceBundleReader;
import com.ibm.icu.impl.ResourceBundleWrapper;
import com.ibm.icu.impl.SimpleCache;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public abstract class UResourceBundle extends ResourceBundle {
  public static UResourceBundle getBundleInstance(String baseName, String localeName) {
    return getBundleInstance(baseName, localeName, ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName, String localeName, ClassLoader root) {
    return getBundleInstance(baseName, localeName, root, false);
  }
  
  protected static UResourceBundle getBundleInstance(String baseName, String localeName, ClassLoader root, boolean disableFallback) {
    return instantiateBundle(baseName, localeName, root, disableFallback);
  }
  
  public UResourceBundle() {
    this.keys = null;
  }
  
  public static UResourceBundle getBundleInstance(ULocale locale) {
    if (locale == null)
      locale = ULocale.getDefault(); 
    return getBundleInstance("com/ibm/icu/impl/data/icudt51b", locale.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName) {
    if (baseName == null)
      baseName = "com/ibm/icu/impl/data/icudt51b"; 
    ULocale uloc = ULocale.getDefault();
    return getBundleInstance(baseName, uloc.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName, Locale locale) {
    if (baseName == null)
      baseName = "com/ibm/icu/impl/data/icudt51b"; 
    ULocale uloc = (locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale);
    return getBundleInstance(baseName, uloc.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName, ULocale locale) {
    if (baseName == null)
      baseName = "com/ibm/icu/impl/data/icudt51b"; 
    if (locale == null)
      locale = ULocale.getDefault(); 
    return getBundleInstance(baseName, locale.toString(), ICUResourceBundle.ICU_DATA_CLASS_LOADER, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName, Locale locale, ClassLoader loader) {
    if (baseName == null)
      baseName = "com/ibm/icu/impl/data/icudt51b"; 
    ULocale uloc = (locale == null) ? ULocale.getDefault() : ULocale.forLocale(locale);
    return getBundleInstance(baseName, uloc.toString(), loader, false);
  }
  
  public static UResourceBundle getBundleInstance(String baseName, ULocale locale, ClassLoader loader) {
    if (baseName == null)
      baseName = "com/ibm/icu/impl/data/icudt51b"; 
    if (locale == null)
      locale = ULocale.getDefault(); 
    return getBundleInstance(baseName, locale.toString(), loader, false);
  }
  
  public abstract ULocale getULocale();
  
  protected abstract String getLocaleID();
  
  protected abstract String getBaseName();
  
  protected abstract UResourceBundle getParent();
  
  public Locale getLocale() {
    return getULocale().toLocale();
  }
  
  private static ICUCache<ResourceCacheKey, UResourceBundle> BUNDLE_CACHE = (ICUCache<ResourceCacheKey, UResourceBundle>)new SimpleCache();
  
  public static void resetBundleCache() {
    BUNDLE_CACHE = (ICUCache<ResourceCacheKey, UResourceBundle>)new SimpleCache();
  }
  
  protected static UResourceBundle addToCache(ClassLoader cl, String fullName, ULocale defaultLocale, UResourceBundle b) {
    synchronized (cacheKey) {
      cacheKey.setKeyValues(cl, fullName, defaultLocale);
      UResourceBundle cachedBundle = (UResourceBundle)BUNDLE_CACHE.get(cacheKey);
      if (cachedBundle != null)
        return cachedBundle; 
      BUNDLE_CACHE.put(cacheKey.clone(), b);
      return b;
    } 
  }
  
  protected static UResourceBundle loadFromCache(ClassLoader cl, String fullName, ULocale defaultLocale) {
    synchronized (cacheKey) {
      cacheKey.setKeyValues(cl, fullName, defaultLocale);
      return (UResourceBundle)BUNDLE_CACHE.get(cacheKey);
    } 
  }
  
  private static final class ResourceCacheKey implements Cloneable {
    private SoftReference<ClassLoader> loaderRef;
    
    private String searchName;
    
    private ULocale defaultLocale;
    
    private int hashCodeCache;
    
    private ResourceCacheKey() {}
    
    public boolean equals(Object other) {
      if (other == null)
        return false; 
      if (this == other)
        return true; 
      try {
        ResourceCacheKey otherEntry = (ResourceCacheKey)other;
        if (this.hashCodeCache != otherEntry.hashCodeCache)
          return false; 
        if (!this.searchName.equals(otherEntry.searchName))
          return false; 
        if (this.defaultLocale == null) {
          if (otherEntry.defaultLocale != null)
            return false; 
        } else if (!this.defaultLocale.equals(otherEntry.defaultLocale)) {
          return false;
        } 
        if (this.loaderRef == null)
          return (otherEntry.loaderRef == null); 
        return (otherEntry.loaderRef != null && this.loaderRef.get() == otherEntry.loaderRef.get());
      } catch (NullPointerException e) {
        return false;
      } catch (ClassCastException e) {
        return false;
      } 
    }
    
    public int hashCode() {
      return this.hashCodeCache;
    }
    
    public Object clone() {
      try {
        return super.clone();
      } catch (CloneNotSupportedException e) {
        throw new IllegalStateException();
      } 
    }
    
    private synchronized void setKeyValues(ClassLoader root, String searchName, ULocale defaultLocale) {
      this.searchName = searchName;
      this.hashCodeCache = searchName.hashCode();
      this.defaultLocale = defaultLocale;
      if (defaultLocale != null)
        this.hashCodeCache ^= defaultLocale.hashCode(); 
      if (root == null) {
        this.loaderRef = null;
      } else {
        this.loaderRef = new SoftReference<ClassLoader>(root);
        this.hashCodeCache ^= root.hashCode();
      } 
    }
  }
  
  private static final ResourceCacheKey cacheKey = new ResourceCacheKey();
  
  private static final int ROOT_MISSING = 0;
  
  private static final int ROOT_ICU = 1;
  
  private static final int ROOT_JAVA = 2;
  
  private static SoftReference<ConcurrentHashMap<String, Integer>> ROOT_CACHE = new SoftReference<ConcurrentHashMap<String, Integer>>(new ConcurrentHashMap<String, Integer>());
  
  private Set<String> keys;
  
  public static final int NONE = -1;
  
  public static final int STRING = 0;
  
  public static final int BINARY = 1;
  
  public static final int TABLE = 2;
  
  public static final int INT = 7;
  
  public static final int ARRAY = 8;
  
  public static final int INT_VECTOR = 14;
  
  private static int getRootType(String baseName, ClassLoader root) {
    ConcurrentHashMap<String, Integer> m = null;
    m = ROOT_CACHE.get();
    if (m == null)
      synchronized (UResourceBundle.class) {
        m = ROOT_CACHE.get();
        if (m == null) {
          m = new ConcurrentHashMap<String, Integer>();
          ROOT_CACHE = new SoftReference<ConcurrentHashMap<String, Integer>>(m);
        } 
      }  
    Integer rootType = m.get(baseName);
    if (rootType == null) {
      String rootLocale = (baseName.indexOf('.') == -1) ? "root" : "";
      int rt = 0;
      try {
        ICUResourceBundle.getBundleInstance(baseName, rootLocale, root, true);
        rt = 1;
      } catch (MissingResourceException ex) {
        try {
          ResourceBundleWrapper.getBundleInstance(baseName, rootLocale, root, true);
          rt = 2;
        } catch (MissingResourceException e) {}
      } 
      rootType = Integer.valueOf(rt);
      m.putIfAbsent(baseName, rootType);
    } 
    return rootType.intValue();
  }
  
  private static void setRootType(String baseName, int rootType) {
    Integer rt = Integer.valueOf(rootType);
    ConcurrentHashMap<String, Integer> m = null;
    m = ROOT_CACHE.get();
    if (m == null)
      synchronized (UResourceBundle.class) {
        m = ROOT_CACHE.get();
        if (m == null) {
          m = new ConcurrentHashMap<String, Integer>();
          ROOT_CACHE = new SoftReference<ConcurrentHashMap<String, Integer>>(m);
        } 
      }  
    m.put(baseName, rt);
  }
  
  protected static UResourceBundle instantiateBundle(String baseName, String localeName, ClassLoader root, boolean disableFallback) {
    UResourceBundle b = null;
    int rootType = getRootType(baseName, root);
    ULocale defaultLocale = ULocale.getDefault();
    switch (rootType) {
      case 1:
        if (disableFallback) {
          String fullName = ICUResourceBundleReader.getFullName(baseName, localeName);
          b = loadFromCache(root, fullName, defaultLocale);
          if (b == null)
            b = ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback); 
        } else {
          b = ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
        } 
        return b;
      case 2:
        return ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
    } 
    try {
      b = ICUResourceBundle.getBundleInstance(baseName, localeName, root, disableFallback);
      setRootType(baseName, 1);
    } catch (MissingResourceException ex) {
      b = ResourceBundleWrapper.getBundleInstance(baseName, localeName, root, disableFallback);
      setRootType(baseName, 2);
    } 
    return b;
  }
  
  public ByteBuffer getBinary() {
    throw new UResourceTypeMismatchException("");
  }
  
  public String getString() {
    throw new UResourceTypeMismatchException("");
  }
  
  public String[] getStringArray() {
    throw new UResourceTypeMismatchException("");
  }
  
  public byte[] getBinary(byte[] ba) {
    throw new UResourceTypeMismatchException("");
  }
  
  public int[] getIntVector() {
    throw new UResourceTypeMismatchException("");
  }
  
  public int getInt() {
    throw new UResourceTypeMismatchException("");
  }
  
  public int getUInt() {
    throw new UResourceTypeMismatchException("");
  }
  
  public UResourceBundle get(String aKey) {
    UResourceBundle obj = findTopLevel(aKey);
    if (obj == null) {
      String fullName = ICUResourceBundleReader.getFullName(getBaseName(), getLocaleID());
      throw new MissingResourceException("Can't find resource for bundle " + fullName + ", key " + aKey, getClass().getName(), aKey);
    } 
    return obj;
  }
  
  protected UResourceBundle findTopLevel(String aKey) {
    for (UResourceBundle res = this; res != null; res = res.getParent()) {
      UResourceBundle obj = res.handleGet(aKey, (HashMap<String, String>)null, this);
      if (obj != null) {
        ((ICUResourceBundle)obj).setLoadingStatus(getLocaleID());
        return obj;
      } 
    } 
    return null;
  }
  
  public String getString(int index) {
    ICUResourceBundle temp = (ICUResourceBundle)get(index);
    if (temp.getType() == 0)
      return temp.getString(); 
    throw new UResourceTypeMismatchException("");
  }
  
  public UResourceBundle get(int index) {
    UResourceBundle uResourceBundle = handleGet(index, (HashMap<String, String>)null, this);
    if (uResourceBundle == null) {
      ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)getParent();
      if (iCUResourceBundle != null)
        uResourceBundle = iCUResourceBundle.get(index); 
      if (uResourceBundle == null)
        throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + getKey(), getClass().getName(), getKey()); 
    } 
    ((ICUResourceBundle)uResourceBundle).setLoadingStatus(getLocaleID());
    return uResourceBundle;
  }
  
  protected UResourceBundle findTopLevel(int index) {
    for (UResourceBundle res = this; res != null; res = res.getParent()) {
      UResourceBundle obj = res.handleGet(index, (HashMap<String, String>)null, this);
      if (obj != null) {
        ((ICUResourceBundle)obj).setLoadingStatus(getLocaleID());
        return obj;
      } 
    } 
    return null;
  }
  
  public Enumeration<String> getKeys() {
    return Collections.enumeration(keySet());
  }
  
  public Set<String> keySet() {
    if (this.keys == null)
      if (isTopLevelResource()) {
        TreeSet<String> newKeySet;
        if (this.parent == null) {
          newKeySet = new TreeSet<String>();
        } else if (this.parent instanceof UResourceBundle) {
          newKeySet = new TreeSet<String>(((UResourceBundle)this.parent).keySet());
        } else {
          newKeySet = new TreeSet<String>();
          Enumeration<String> parentKeys = this.parent.getKeys();
          while (parentKeys.hasMoreElements())
            newKeySet.add(parentKeys.nextElement()); 
        } 
        newKeySet.addAll(handleKeySet());
        this.keys = Collections.unmodifiableSet(newKeySet);
      } else {
        return handleKeySet();
      }  
    return this.keys;
  }
  
  protected Set<String> handleKeySet() {
    return Collections.emptySet();
  }
  
  public int getSize() {
    return 1;
  }
  
  public int getType() {
    return -1;
  }
  
  public VersionInfo getVersion() {
    return null;
  }
  
  public UResourceBundleIterator getIterator() {
    return new UResourceBundleIterator(this);
  }
  
  public String getKey() {
    return null;
  }
  
  protected UResourceBundle handleGet(String aKey, HashMap<String, String> table, UResourceBundle requested) {
    return null;
  }
  
  protected UResourceBundle handleGet(int index, HashMap<String, String> table, UResourceBundle requested) {
    return null;
  }
  
  protected String[] handleGetStringArray() {
    return null;
  }
  
  protected Enumeration<String> handleGetKeys() {
    return null;
  }
  
  protected Object handleGetObject(String aKey) {
    return handleGetObjectImpl(aKey, this);
  }
  
  private Object handleGetObjectImpl(String aKey, UResourceBundle requested) {
    Object obj = resolveObject(aKey, requested);
    if (obj == null) {
      UResourceBundle parentBundle = getParent();
      if (parentBundle != null)
        obj = parentBundle.handleGetObjectImpl(aKey, requested); 
      if (obj == null)
        throw new MissingResourceException("Can't find resource for bundle " + getClass().getName() + ", key " + aKey, getClass().getName(), aKey); 
    } 
    return obj;
  }
  
  private Object resolveObject(String aKey, UResourceBundle requested) {
    if (getType() == 0)
      return getString(); 
    UResourceBundle obj = handleGet(aKey, (HashMap<String, String>)null, requested);
    if (obj != null) {
      if (obj.getType() == 0)
        return obj.getString(); 
      try {
        if (obj.getType() == 8)
          return obj.handleGetStringArray(); 
      } catch (UResourceTypeMismatchException ex) {
        return obj;
      } 
    } 
    return obj;
  }
  
  protected abstract void setLoadingStatus(int paramInt);
  
  protected boolean isTopLevelResource() {
    return true;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\ic\\util\UResourceBundle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */