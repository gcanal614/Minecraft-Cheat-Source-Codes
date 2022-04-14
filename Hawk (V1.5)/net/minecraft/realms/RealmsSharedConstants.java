package net.minecraft.realms;

import net.minecraft.util.ChatAllowedCharacters;

public class RealmsSharedConstants {
   public static String VERSION_STRING = "1.8";
   public static char[] ILLEGAL_FILE_CHARACTERS;
   private static final String __OBFID = "CL_00001866";
   public static int NETWORK_PROTOCOL_VERSION = 47;
   public static int TICKS_PER_SECOND = 20;

   static {
      ILLEGAL_FILE_CHARACTERS = ChatAllowedCharacters.allowedCharactersArray;
   }
}
