package net.java.games.input;

public final class Version {
  private static final String apiVersion = "2.0.5";
  
  private static final String buildNumber = "1088";
  
  private static final String antBuildNumberToken = "@BUILD_NUMBER@";
  
  private static final String antAPIVersionToken = "@API_VERSION@";
  
  public static String getVersion() {
    String version = "Unversioned";
    if (!"@API_VERSION@".equals("2.0.5"))
      version = "2.0.5"; 
    if (!"@BUILD_NUMBER@".equals("1088"))
      version = version + "-b1088"; 
    return version;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\java\games\input\Version.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */