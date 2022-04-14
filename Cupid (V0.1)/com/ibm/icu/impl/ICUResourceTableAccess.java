package com.ibm.icu.impl;

import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;

public class ICUResourceTableAccess {
  public static String getTableString(String path, ULocale locale, String tableName, String itemName) {
    ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(path, locale.getBaseName());
    return getTableString(bundle, tableName, (String)null, itemName);
  }
  
  public static String getTableString(ICUResourceBundle bundle, String tableName, String subtableName, String item) {
    String result = null;
    try {
      while (true) {
        if ("currency".equals(subtableName)) {
          ICUResourceBundle iCUResourceBundle = bundle.getWithFallback("Currencies");
          iCUResourceBundle = iCUResourceBundle.getWithFallback(item);
          return iCUResourceBundle.getString(1);
        } 
        ICUResourceBundle table = lookup(bundle, tableName);
        if (table == null)
          return item; 
        ICUResourceBundle stable = table;
        if (subtableName != null)
          stable = lookup(table, subtableName); 
        if (stable != null) {
          ICUResourceBundle sbundle = lookup(stable, item);
          if (sbundle != null) {
            result = sbundle.getString();
            break;
          } 
        } 
        if (subtableName == null) {
          String currentName = null;
          if (tableName.equals("Countries")) {
            currentName = LocaleIDs.getCurrentCountryID(item);
          } else if (tableName.equals("Languages")) {
            currentName = LocaleIDs.getCurrentLanguageID(item);
          } 
          ICUResourceBundle sbundle = lookup(table, currentName);
          if (sbundle != null) {
            result = sbundle.getString();
            break;
          } 
        } 
        ICUResourceBundle fbundle = lookup(table, "Fallback");
        if (fbundle == null)
          return item; 
        String fallbackLocale = fbundle.getString();
        if (fallbackLocale.length() == 0)
          fallbackLocale = "root"; 
        if (fallbackLocale.equals(table.getULocale().getName()))
          return item; 
        bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance(bundle.getBaseName(), fallbackLocale);
      } 
    } catch (Exception e) {}
    return (result != null && result.length() > 0) ? result : item;
  }
  
  private static ICUResourceBundle lookup(ICUResourceBundle bundle, String resName) {
    return ICUResourceBundle.findResourceWithFallback(resName, bundle, (UResourceBundle)null);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\ibm\icu\impl\ICUResourceTableAccess.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */