package org.apache.http.impl.conn;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.pool.AbstractConnPool;
import org.apache.http.pool.ConnFactory;
import org.apache.http.pool.PoolEntry;

@ThreadSafe
class CPool extends AbstractConnPool<HttpRoute, ManagedHttpClientConnection, CPoolEntry> {
  private static final AtomicLong COUNTER = new AtomicLong();
  
  private final Log log = LogFactory.getLog(CPool.class);
  
  private final long timeToLive;
  
  private final TimeUnit tunit;
  
  public CPool(ConnFactory<HttpRoute, ManagedHttpClientConnection> connFactory, int defaultMaxPerRoute, int maxTotal, long timeToLive, TimeUnit tunit) {
    super(connFactory, defaultMaxPerRoute, maxTotal);
    this.timeToLive = timeToLive;
    this.tunit = tunit;
  }
  
  protected CPoolEntry createEntry(HttpRoute route, ManagedHttpClientConnection conn) {
    String id = Long.toString(COUNTER.getAndIncrement());
    return new CPoolEntry(this.log, id, route, conn, this.timeToLive, this.tunit);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\http\impl\conn\CPool.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */