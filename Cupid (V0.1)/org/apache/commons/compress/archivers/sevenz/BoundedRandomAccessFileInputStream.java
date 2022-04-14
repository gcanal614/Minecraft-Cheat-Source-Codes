package org.apache.commons.compress.archivers.sevenz;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

class BoundedRandomAccessFileInputStream extends InputStream {
  private final RandomAccessFile file;
  
  private long bytesRemaining;
  
  public BoundedRandomAccessFileInputStream(RandomAccessFile file, long size) {
    this.file = file;
    this.bytesRemaining = size;
  }
  
  public int read() throws IOException {
    if (this.bytesRemaining > 0L) {
      this.bytesRemaining--;
      return this.file.read();
    } 
    return -1;
  }
  
  public int read(byte[] b, int off, int len) throws IOException {
    if (this.bytesRemaining == 0L)
      return -1; 
    int bytesToRead = len;
    if (bytesToRead > this.bytesRemaining)
      bytesToRead = (int)this.bytesRemaining; 
    int bytesRead = this.file.read(b, off, bytesToRead);
    if (bytesRead >= 0)
      this.bytesRemaining -= bytesRead; 
    return bytesRead;
  }
  
  public void close() {}
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\commons\compress\archivers\sevenz\BoundedRandomAccessFileInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */