package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;

public class ThreadedFileIOBase implements Runnable {
   private List threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());
   private boolean isThreadWaiting;
   private long writeQueuedCounter;
   private static final String __OBFID = "CL_00000605";
   private static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
   private long savedIOCounter;

   public static ThreadedFileIOBase func_178779_a() {
      return threadedIOInstance;
   }

   private void processQueue() {
      for(int var1 = 0; var1 < this.threadedIOQueue.size(); ++var1) {
         IThreadedFileIO var2 = (IThreadedFileIO)this.threadedIOQueue.get(var1);
         boolean var3 = var2.writeNextIO();
         if (!var3) {
            this.threadedIOQueue.remove(var1--);
            ++this.savedIOCounter;
         }

         try {
            Thread.sleep(this.isThreadWaiting ? 0L : 10L);
         } catch (InterruptedException var6) {
            var6.printStackTrace();
         }
      }

      if (this.threadedIOQueue.isEmpty()) {
         try {
            Thread.sleep(25L);
         } catch (InterruptedException var5) {
            var5.printStackTrace();
         }
      }

   }

   public void queueIO(IThreadedFileIO var1) {
      if (!this.threadedIOQueue.contains(var1)) {
         ++this.writeQueuedCounter;
         this.threadedIOQueue.add(var1);
      }

   }

   public void run() {
      while(true) {
         this.processQueue();
      }
   }

   public void waitForFinish() throws InterruptedException {
      this.isThreadWaiting = true;

      while(this.writeQueuedCounter != this.savedIOCounter) {
         Thread.sleep(10L);
      }

      this.isThreadWaiting = false;
   }

   private ThreadedFileIOBase() {
      Thread var1 = new Thread(this, "File IO Thread");
      var1.setPriority(1);
      var1.start();
   }
}
