package org.apache.http.conn.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.Immutable;
import org.apache.http.conn.util.InetAddressUtils;

@Immutable
public abstract class AbstractVerifier implements X509HostnameVerifier {
  private static final String[] BAD_COUNTRY_2LDS = new String[] { 
      "ac", "co", "com", "ed", "edu", "go", "gouv", "gov", "info", "lg", 
      "ne", "net", "or", "org" };
  
  static {
    Arrays.sort((Object[])BAD_COUNTRY_2LDS);
  }
  
  private final Log log = LogFactory.getLog(getClass());
  
  public final void verify(String host, SSLSocket ssl) throws IOException {
    if (host == null)
      throw new NullPointerException("host to verify is null"); 
    SSLSession session = ssl.getSession();
    if (session == null) {
      InputStream in = ssl.getInputStream();
      in.available();
      session = ssl.getSession();
      if (session == null) {
        ssl.startHandshake();
        session = ssl.getSession();
      } 
    } 
    Certificate[] certs = session.getPeerCertificates();
    X509Certificate x509 = (X509Certificate)certs[0];
    verify(host, x509);
  }
  
  public final boolean verify(String host, SSLSession session) {
    try {
      Certificate[] certs = session.getPeerCertificates();
      X509Certificate x509 = (X509Certificate)certs[0];
      verify(host, x509);
      return true;
    } catch (SSLException e) {
      return false;
    } 
  }
  
  public final void verify(String host, X509Certificate cert) throws SSLException {
    String[] cns = getCNs(cert);
    String[] subjectAlts = getSubjectAlts(cert, host);
    verify(host, cns, subjectAlts);
  }
  
  public final void verify(String host, String[] cns, String[] subjectAlts, boolean strictWithSubDomains) throws SSLException {
    LinkedList<String> names = new LinkedList<String>();
    if (cns != null && cns.length > 0 && cns[0] != null)
      names.add(cns[0]); 
    if (subjectAlts != null)
      for (String subjectAlt : subjectAlts) {
        if (subjectAlt != null)
          names.add(subjectAlt); 
      }  
    if (names.isEmpty()) {
      String msg = "Certificate for <" + host + "> doesn't contain CN or DNS subjectAlt";
      throw new SSLException(msg);
    } 
    StringBuilder buf = new StringBuilder();
    String hostName = normaliseIPv6Address(host.trim().toLowerCase(Locale.US));
    boolean match = false;
    for (Iterator<String> it = names.iterator(); it.hasNext(); ) {
      String cn = it.next();
      cn = cn.toLowerCase(Locale.US);
      buf.append(" <");
      buf.append(cn);
      buf.append('>');
      if (it.hasNext())
        buf.append(" OR"); 
      String[] parts = cn.split("\\.");
      boolean doWildcard = (parts.length >= 3 && parts[0].endsWith("*") && validCountryWildcard(cn) && !isIPAddress(host));
      if (doWildcard) {
        String firstpart = parts[0];
        if (firstpart.length() > 1) {
          String prefix = firstpart.substring(0, firstpart.length() - 1);
          String suffix = cn.substring(firstpart.length());
          String hostSuffix = hostName.substring(prefix.length());
          match = (hostName.startsWith(prefix) && hostSuffix.endsWith(suffix));
        } else {
          match = hostName.endsWith(cn.substring(1));
        } 
        if (match && strictWithSubDomains)
          match = (countDots(hostName) == countDots(cn)); 
      } else {
        match = hostName.equals(normaliseIPv6Address(cn));
      } 
      if (match)
        break; 
    } 
    if (!match)
      throw new SSLException("hostname in certificate didn't match: <" + host + "> !=" + buf); 
  }
  
  @Deprecated
  public static boolean acceptableCountryWildcard(String cn) {
    String[] parts = cn.split("\\.");
    if (parts.length != 3 || parts[2].length() != 2)
      return true; 
    return (Arrays.binarySearch((Object[])BAD_COUNTRY_2LDS, parts[1]) < 0);
  }
  
  boolean validCountryWildcard(String cn) {
    String[] parts = cn.split("\\.");
    if (parts.length != 3 || parts[2].length() != 2)
      return true; 
    return (Arrays.binarySearch((Object[])BAD_COUNTRY_2LDS, parts[1]) < 0);
  }
  
  public static String[] getCNs(X509Certificate cert) {
    LinkedList<String> cnList = new LinkedList<String>();
    String subjectPrincipal = cert.getSubjectX500Principal().toString();
    StringTokenizer st = new StringTokenizer(subjectPrincipal, ",+");
    while (st.hasMoreTokens()) {
      String tok = st.nextToken().trim();
      if (tok.length() > 3 && 
        tok.substring(0, 3).equalsIgnoreCase("CN="))
        cnList.add(tok.substring(3)); 
    } 
    if (!cnList.isEmpty()) {
      String[] cns = new String[cnList.size()];
      cnList.toArray(cns);
      return cns;
    } 
    return null;
  }
  
  private static String[] getSubjectAlts(X509Certificate cert, String hostname) {
    int subjectType;
    if (isIPAddress(hostname)) {
      subjectType = 7;
    } else {
      subjectType = 2;
    } 
    LinkedList<String> subjectAltList = new LinkedList<String>();
    Collection<List<?>> c = null;
    try {
      c = cert.getSubjectAlternativeNames();
    } catch (CertificateParsingException cpe) {}
    if (c != null)
      for (List<?> aC : c) {
        List<?> list = aC;
        int type = ((Integer)list.get(0)).intValue();
        if (type == subjectType) {
          String s = (String)list.get(1);
          subjectAltList.add(s);
        } 
      }  
    if (!subjectAltList.isEmpty()) {
      String[] subjectAlts = new String[subjectAltList.size()];
      subjectAltList.toArray(subjectAlts);
      return subjectAlts;
    } 
    return null;
  }
  
  public static String[] getDNSSubjectAlts(X509Certificate cert) {
    return getSubjectAlts(cert, (String)null);
  }
  
  public static int countDots(String s) {
    int count = 0;
    for (int i = 0; i < s.length(); i++) {
      if (s.charAt(i) == '.')
        count++; 
    } 
    return count;
  }
  
  private static boolean isIPAddress(String hostname) {
    return (hostname != null && (InetAddressUtils.isIPv4Address(hostname) || InetAddressUtils.isIPv6Address(hostname)));
  }
  
  private String normaliseIPv6Address(String hostname) {
    if (hostname == null || !InetAddressUtils.isIPv6Address(hostname))
      return hostname; 
    try {
      InetAddress inetAddress = InetAddress.getByName(hostname);
      return inetAddress.getHostAddress();
    } catch (UnknownHostException uhe) {
      this.log.error("Unexpected error converting " + hostname, uhe);
      return hostname;
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\http\conn\ssl\AbstractVerifier.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */