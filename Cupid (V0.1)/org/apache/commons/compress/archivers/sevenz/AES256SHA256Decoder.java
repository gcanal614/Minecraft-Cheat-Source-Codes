package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class AES256SHA256Decoder extends CoderBase {
  AES256SHA256Decoder() {
    super(new Class[0]);
  }
  
  InputStream decode(final InputStream in, final Coder coder, final byte[] passwordBytes) throws IOException {
    return new InputStream() {
        private boolean isInitialized = false;
        
        private CipherInputStream cipherInputStream = null;
        
        private CipherInputStream init() throws IOException {
          byte[] aesKeyBytes;
          if (this.isInitialized)
            return this.cipherInputStream; 
          int byte0 = 0xFF & coder.properties[0];
          int numCyclesPower = byte0 & 0x3F;
          int byte1 = 0xFF & coder.properties[1];
          int ivSize = (byte0 >> 6 & 0x1) + (byte1 & 0xF);
          int saltSize = (byte0 >> 7 & 0x1) + (byte1 >> 4);
          if (2 + saltSize + ivSize > coder.properties.length)
            throw new IOException("Salt size + IV size too long"); 
          byte[] salt = new byte[saltSize];
          System.arraycopy(coder.properties, 2, salt, 0, saltSize);
          byte[] iv = new byte[16];
          System.arraycopy(coder.properties, 2 + saltSize, iv, 0, ivSize);
          if (passwordBytes == null)
            throw new IOException("Cannot read encrypted files without a password"); 
          if (numCyclesPower == 63) {
            aesKeyBytes = new byte[32];
            System.arraycopy(salt, 0, aesKeyBytes, 0, saltSize);
            System.arraycopy(passwordBytes, 0, aesKeyBytes, saltSize, Math.min(passwordBytes.length, aesKeyBytes.length - saltSize));
          } else {
            MessageDigest digest;
            try {
              digest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
              IOException ioe = new IOException("SHA-256 is unsupported by your Java implementation");
              ioe.initCause(noSuchAlgorithmException);
              throw ioe;
            } 
            byte[] extra = new byte[8];
            long j;
            for (j = 0L; j < 1L << numCyclesPower; j++) {
              digest.update(salt);
              digest.update(passwordBytes);
              digest.update(extra);
              for (int k = 0; k < extra.length; k++) {
                extra[k] = (byte)(extra[k] + 1);
                if (extra[k] != 0)
                  break; 
              } 
            } 
            aesKeyBytes = digest.digest();
          } 
          SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
          try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(2, aesKey, new IvParameterSpec(iv));
            this.cipherInputStream = new CipherInputStream(in, cipher);
            this.isInitialized = true;
            return this.cipherInputStream;
          } catch (GeneralSecurityException generalSecurityException) {
            IOException ioe = new IOException("Decryption error (do you have the JCE Unlimited Strength Jurisdiction Policy Files installed?)");
            ioe.initCause(generalSecurityException);
            throw ioe;
          } 
        }
        
        public int read() throws IOException {
          return init().read();
        }
        
        public int read(byte[] b, int off, int len) throws IOException {
          return init().read(b, off, len);
        }
        
        public void close() {}
      };
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\commons\compress\archivers\sevenz\AES256SHA256Decoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */