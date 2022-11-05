package cc.godk.tool.ohc;

import cc.godk.tool.ohc.serializer.string.StringSerializer;
import org.caffinitas.ohc.Eviction;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;

/**
 * @author godk
 * @project project-tool
 * @date 2022/9/21 14:25
 */
public class StringOffHeapCache extends AbstractOffHeapCache<String>{

    private static StringOffHeapCache INSTANCE = null;

    private StringOffHeapCache(Eviction eviction){
        super(eviction,new StringSerializer());
    }

    public static StringOffHeapCache getInstance(Eviction eviction){
        if( INSTANCE== null){
            synchronized (StringOffHeapCache.class){
                if(INSTANCE == null){
                    INSTANCE = new StringOffHeapCache(eviction == null ? Eviction.LRU : eviction);
                }
            }
        }
        return INSTANCE;
    }


}
