package cc.godk.tool.ohc;

import junit.framework.TestCase;
import org.junit.Test;

public class StringOffHeapCacheTest extends TestCase {

    @Test
    public void test(){
        StringOffHeapCache.getInstance(null).put("测试","测试");

        String value = StringOffHeapCache.getInstance(null).get("测试");
        System.out.println(value);

        StringOffHeapCache.getInstance(null).get("测试");
        System.out.println(value);


    }
}