package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

@NotThreadSafe
public abstract class RFC2617Scheme extends AuthSchemeBase {
  private final Map<String, String> params;
  
  private final Charset credentialsCharset;
  
  @Deprecated
  public RFC2617Scheme(ChallengeState challengeState) {
    super(challengeState);
    this.params = new HashMap<String, String>();
    this.credentialsCharset = Consts.ASCII;
  }
  
  public RFC2617Scheme(Charset credentialsCharset) {
    this.params = new HashMap<String, String>();
    this.credentialsCharset = (credentialsCharset != null) ? credentialsCharset : Consts.ASCII;
  }
  
  public RFC2617Scheme() {
    this(Consts.ASCII);
  }
  
  public Charset getCredentialsCharset() {
    return this.credentialsCharset;
  }
  
  String getCredentialsCharset(HttpRequest request) {
    String charset = (String)request.getParams().getParameter("http.auth.credential-charset");
    if (charset == null)
      charset = getCredentialsCharset().name(); 
    return charset;
  }
  
  protected void parseChallenge(CharArrayBuffer buffer, int pos, int len) throws MalformedChallengeException {
    BasicHeaderValueParser basicHeaderValueParser = BasicHeaderValueParser.INSTANCE;
    ParserCursor cursor = new ParserCursor(pos, buffer.length());
    HeaderElement[] elements = basicHeaderValueParser.parseElements(buffer, cursor);
    if (elements.length == 0)
      throw new MalformedChallengeException("Authentication challenge is empty"); 
    this.params.clear();
    for (HeaderElement element : elements)
      this.params.put(element.getName(), element.getValue()); 
  }
  
  protected Map<String, String> getParameters() {
    return this.params;
  }
  
  public String getParameter(String name) {
    if (name == null)
      return null; 
    return this.params.get(name.toLowerCase(Locale.ENGLISH));
  }
  
  public String getRealm() {
    return getParameter("realm");
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\http\impl\auth\RFC2617Scheme.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */