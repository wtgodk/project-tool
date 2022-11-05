package cc.godk.tool.ohc;

import cc.godk.tool.ohc.serializer.obj.ObjHessianSerializer;
import cc.godk.tool.ohc.serializer.string.StringSerializer;
import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.Eviction;

/**
 * @author godk
 * @project project-tool
 * @date 2022/9/21 15:31
 */
public class ObjOffHeapCache<T>  extends AbstractOffHeapCache<T> {

    public ObjOffHeapCache(Eviction eviction){
        this(eviction,new ObjHessianSerializer<T>());
    }

    public ObjOffHeapCache(Eviction eviction, CacheSerializer<T> cacheSerializer){
        super(eviction,cacheSerializer);
    }
    public ObjOffHeapCache(){
        this(Eviction.LRU);
    }
    public ObjOffHeapCache(CacheSerializer<T> cacheSerializer){
        this(Eviction.LRU,cacheSerializer);
    }

}
