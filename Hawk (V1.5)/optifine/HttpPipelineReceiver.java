package optifine;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;

public class HttpPipelineReceiver extends Thread {
   private static final char LF = '\n';
   private HttpPipelineConnection httpPipelineConnection = null;
   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
   private static final Charset ASCII = Charset.forName("ASCII");
   private static final char CR = '\r';

   public HttpPipelineReceiver(HttpPipelineConnection var1) {
      super("HttpPipelineReceiver");
      this.httpPipelineConnection = var1;
   }

   private HttpResponse readResponse(InputStream var1) throws IOException {
      String var2 = this.readLine(var1);
      String[] var3 = Config.tokenize(var2, " ");
      if (var3.length < 3) {
         throw new IOException(String.valueOf((new StringBuilder("Invalid status line: ")).append(var2)));
      } else {
         String var4 = var3[0];
         int var5 = Config.parseInt(var3[1], 0);
         String var6 = var3[2];
         LinkedHashMap var7 = new LinkedHashMap();

         while(true) {
            String var8 = this.readLine(var1);
            String var9;
            String var11;
            if (var8.length() <= 0) {
               byte[] var13 = null;
               var11 = (String)var7.get("Content-Length");
               if (var11 != null) {
                  int var12 = Config.parseInt(var11, -1);
                  if (var12 > 0) {
                     var13 = new byte[var12];
                     this.readFull(var13, var1);
                  }
               } else {
                  var9 = (String)var7.get("Transfer-Encoding");
                  if (Config.equals(var9, "chunked")) {
                     var13 = this.readContentChunked(var1);
                  }
               }

               return new HttpResponse(var5, var2, var7, var13);
            }

            int var10 = var8.indexOf(":");
            if (var10 > 0) {
               var9 = var8.substring(0, var10).trim();
               var11 = var8.substring(var10 + 1).trim();
               var7.put(var9, var11);
            }
         }
      }
   }

   private byte[] readContentChunked(InputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      int var3;
      do {
         String var4 = this.readLine(var1);
         String[] var5 = Config.tokenize(var4, "; ");
         var3 = Integer.parseInt(var5[0], 16);
         byte[] var6 = new byte[var3];
         this.readFull(var6, var1);
         var2.write(var6);
         this.readLine(var1);
      } while(var3 != 0);

      return var2.toByteArray();
   }

   public void run() {
      while(!Thread.interrupted()) {
         HttpPipelineRequest var1 = null;

         try {
            var1 = this.httpPipelineConnection.getNextRequestReceive();
            InputStream var2 = this.httpPipelineConnection.getInputStream();
            HttpResponse var3 = this.readResponse(var2);
            this.httpPipelineConnection.onResponseReceived(var1, var3);
         } catch (InterruptedException var4) {
            return;
         } catch (Exception var5) {
            this.httpPipelineConnection.onExceptionReceive(var1, var5);
         }
      }

   }

   private String readLine(InputStream var1) throws IOException {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      int var3 = -1;
      boolean var4 = false;

      while(true) {
         int var5 = var1.read();
         if (var5 < 0) {
            break;
         }

         var2.write(var5);
         if (var3 == 13 && var5 == 10) {
            var4 = true;
            break;
         }

         var3 = var5;
      }

      byte[] var7 = var2.toByteArray();
      String var6 = new String(var7, ASCII);
      if (var4) {
         var6 = var6.substring(0, var6.length() - 2);
      }

      return var6;
   }

   private void readFull(byte[] var1, InputStream var2) throws IOException {
      int var3;
      for(int var4 = 0; var4 < var1.length; var4 += var3) {
         var3 = var2.read(var1, var4, var1.length - var4);
         if (var3 < 0) {
            throw new EOFException();
         }
      }

   }
}
