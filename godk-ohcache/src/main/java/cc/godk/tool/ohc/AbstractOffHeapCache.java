package cc.godk.tool.ohc;

import cc.godk.tool.ohc.serializer.string.StringSerializer;
import com.sun.jna.Native;
import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;
import sun.misc.Unsafe;

/**
 * @author godk
 * @project project-tool
 * @date 2022/9/21 14:36
 */
public abstract class AbstractOffHeapCache<T> implements OffHeapCache<T>{

    /**
     *  堆外内存分配
     * @param size
     */
    public static void  allocateMemory(long size){
        Unsafe.getUnsafe().allocateMemory(size);
    }
    /**
     *  堆外内存分配
     * @param size
     */
    public static void  malloc(long size){
        Native.malloc(size);
    }

    protected final OHCache<String, T> OFF_HEAP_CACHE;

    protected AbstractOffHeapCache(Eviction eviction, CacheSerializer<T> valueSerializer){
        OFF_HEAP_CACHE = OHCacheBuilder.<String, T>newBuilder()
                .keySerializer(new StringSerializer())
                .valueSerializer(valueSerializer)
                .eviction(eviction)
                .build();
    }

    @Override
    public void put(String key, T value) {
        OFF_HEAP_CACHE.put(key,value);

    }

    @Override
    public void put(String key, T value, long expireAt) {
        OFF_HEAP_CACHE.put(key,value,expireAt);
    }

    @Override
    public T get(String key) {
        return OFF_HEAP_CACHE.get(key);
    }

    @Override
    public void flush() {
        OFF_HEAP_CACHE.clear();
    }
}
