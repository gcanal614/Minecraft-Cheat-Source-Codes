package org.apache.commons.compress.archivers.zip;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum ZipMethod {
  STORED(0),
  UNSHRINKING(1),
  EXPANDING_LEVEL_1(2),
  EXPANDING_LEVEL_2(3),
  EXPANDING_LEVEL_3(4),
  EXPANDING_LEVEL_4(5),
  IMPLODING(6),
  TOKENIZATION(7),
  DEFLATED(8),
  ENHANCED_DEFLATED(9),
  PKWARE_IMPLODING(10),
  BZIP2(12),
  LZMA(14),
  JPEG(96),
  WAVPACK(97),
  PPMD(98),
  AES_ENCRYPTED(99),
  UNKNOWN(-1);
  
  private final int code;
  
  private static final Map<Integer, ZipMethod> codeToEnum;
  
  static {
    Map<Integer, ZipMethod> cte = new HashMap<Integer, ZipMethod>();
    for (ZipMethod method : values())
      cte.put(Integer.valueOf(method.getCode()), method); 
    codeToEnum = Collections.unmodifiableMap(cte);
  }
  
  ZipMethod(int code) {
    this.code = code;
  }
  
  public int getCode() {
    return this.code;
  }
  
  public static ZipMethod getMethodByCode(int code) {
    return codeToEnum.get(Integer.valueOf(code));
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\commons\compress\archivers\zip\ZipMethod.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */