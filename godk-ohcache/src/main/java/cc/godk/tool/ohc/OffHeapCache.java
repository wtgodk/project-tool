package cc.godk.tool.ohc;

/**
 * @author godk
 * @project project-tool
 * @date 2022/9/21 14:34
 */
public interface OffHeapCache<T> {

    /**
     *  添加缓存
     * @param key
     * @param value
     */
    void  put(String key,T  value);

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param expireAt
     */
    void put(String key,T value ,long expireAt);

    /**
     * 获取对象
     * @param key
     * @return
     */
    T get(String key);


    void flush();

}
