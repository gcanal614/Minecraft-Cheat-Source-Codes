package io.netty.buffer;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

public class PooledByteBufAllocator extends AbstractByteBufAllocator {
  private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
  
  private static final int DEFAULT_NUM_HEAP_ARENA;
  
  private static final int DEFAULT_NUM_DIRECT_ARENA;
  
  private static final int DEFAULT_PAGE_SIZE;
  
  private static final int DEFAULT_MAX_ORDER;
  
  private static final int DEFAULT_TINY_CACHE_SIZE;
  
  private static final int DEFAULT_SMALL_CACHE_SIZE;
  
  private static final int DEFAULT_NORMAL_CACHE_SIZE;
  
  private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
  
  private static final int DEFAULT_CACHE_TRIM_INTERVAL;
  
  private static final int MIN_PAGE_SIZE = 4096;
  
  private static final int MAX_CHUNK_SIZE = 1073741824;
  
  public static final PooledByteBufAllocator DEFAULT;
  
  private final PoolArena<byte[]>[] heapArenas;
  
  private final PoolArena<ByteBuffer>[] directArenas;
  
  private final int tinyCacheSize;
  
  private final int smallCacheSize;
  
  private final int normalCacheSize;
  
  final PoolThreadLocalCache threadCache;
  
  static {
    int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
    Throwable pageSizeFallbackCause = null;
    try {
      validateAndCalculatePageShifts(defaultPageSize);
    } catch (Throwable t) {
      pageSizeFallbackCause = t;
      defaultPageSize = 8192;
    } 
    DEFAULT_PAGE_SIZE = defaultPageSize;
    int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
    Throwable maxOrderFallbackCause = null;
    try {
      validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
    } catch (Throwable t) {
      maxOrderFallbackCause = t;
      defaultMaxOrder = 11;
    } 
    DEFAULT_MAX_ORDER = defaultMaxOrder;
    Runtime runtime = Runtime.getRuntime();
    int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
    DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min(runtime.availableProcessors(), Runtime.getRuntime().maxMemory() / defaultChunkSize / 2L / 3L)));
    DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min(runtime.availableProcessors(), PlatformDependent.maxDirectMemory() / defaultChunkSize / 2L / 3L)));
    DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
    DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
    DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
    DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
    DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
    if (logger.isDebugEnabled()) {
      logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
      logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
      if (pageSizeFallbackCause == null) {
        logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
      } else {
        logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), pageSizeFallbackCause);
      } 
      if (maxOrderFallbackCause == null) {
        logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
      } else {
        logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), maxOrderFallbackCause);
      } 
      logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
      logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", Integer.valueOf(DEFAULT_TINY_CACHE_SIZE));
      logger.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
      logger.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
      logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
      logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
    } 
    DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
  }
  
  public PooledByteBufAllocator() {
    this(false);
  }
  
  public PooledByteBufAllocator(boolean preferDirect) {
    this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
  }
  
  public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
    this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
  }
  
  public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
    this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
  }
  
  public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize) {
    super(preferDirect);
    this.threadCache = new PoolThreadLocalCache();
    this.tinyCacheSize = tinyCacheSize;
    this.smallCacheSize = smallCacheSize;
    this.normalCacheSize = normalCacheSize;
    int chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
    if (nHeapArena < 0)
      throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: >= 0)"); 
    if (nDirectArena < 0)
      throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: >= 0)"); 
    int pageShifts = validateAndCalculatePageShifts(pageSize);
    if (nHeapArena > 0) {
      this.heapArenas = newArenaArray(nHeapArena);
      for (int i = 0; i < this.heapArenas.length; i++)
        this.heapArenas[i] = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, chunkSize); 
    } else {
      this.heapArenas = null;
    } 
    if (nDirectArena > 0) {
      this.directArenas = newArenaArray(nDirectArena);
      for (int i = 0; i < this.directArenas.length; i++)
        this.directArenas[i] = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, chunkSize); 
    } else {
      this.directArenas = null;
    } 
  }
  
  @Deprecated
  public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, long cacheThreadAliveCheckInterval) {
    this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize, smallCacheSize, normalCacheSize);
  }
  
  private static <T> PoolArena<T>[] newArenaArray(int size) {
    return (PoolArena<T>[])new PoolArena[size];
  }
  
  private static int validateAndCalculatePageShifts(int pageSize) {
    if (pageSize < 4096)
      throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 'က' + "+)"); 
    if ((pageSize & pageSize - 1) != 0)
      throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)"); 
    return 31 - Integer.numberOfLeadingZeros(pageSize);
  }
  
  private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
    if (maxOrder > 14)
      throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)"); 
    int chunkSize = pageSize;
    for (int i = maxOrder; i > 0; i--) {
      if (chunkSize > 536870912)
        throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[] { Integer.valueOf(pageSize), Integer.valueOf(maxOrder), Integer.valueOf(1073741824) })); 
      chunkSize <<= 1;
    } 
    return chunkSize;
  }
  
  protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
    ByteBuf buf;
    PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
    PoolArena<byte[]> heapArena = cache.heapArena;
    if (heapArena != null) {
      buf = heapArena.allocate(cache, initialCapacity, maxCapacity);
    } else {
      buf = new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
    } 
    return toLeakAwareBuffer(buf);
  }
  
  protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
    ByteBuf buf;
    PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
    PoolArena<ByteBuffer> directArena = cache.directArena;
    if (directArena != null) {
      buf = directArena.allocate(cache, initialCapacity, maxCapacity);
    } else if (PlatformDependent.hasUnsafe()) {
      buf = new UnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
    } else {
      buf = new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
    } 
    return toLeakAwareBuffer(buf);
  }
  
  public boolean isDirectBufferPooled() {
    return (this.directArenas != null);
  }
  
  @Deprecated
  public boolean hasThreadLocalCache() {
    return this.threadCache.isSet();
  }
  
  @Deprecated
  public void freeThreadLocalCache() {
    this.threadCache.remove();
  }
  
  final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
    private final AtomicInteger index = new AtomicInteger();
    
    protected PoolThreadCache initialValue() {
      PoolArena<byte[]> heapArena;
      PoolArena<ByteBuffer> directArena;
      int idx = this.index.getAndIncrement();
      if (PooledByteBufAllocator.this.heapArenas != null) {
        heapArena = PooledByteBufAllocator.this.heapArenas[Math.abs(idx % PooledByteBufAllocator.this.heapArenas.length)];
      } else {
        heapArena = null;
      } 
      if (PooledByteBufAllocator.this.directArenas != null) {
        directArena = PooledByteBufAllocator.this.directArenas[Math.abs(idx % PooledByteBufAllocator.this.directArenas.length)];
      } else {
        directArena = null;
      } 
      return new PoolThreadCache(heapArena, directArena, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
    }
    
    protected void onRemoval(PoolThreadCache value) {
      value.free();
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\buffer\PooledByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */