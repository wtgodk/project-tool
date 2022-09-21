package cc.godk.tool.dynamicloader;
import groovy.lang.GroovyClassLoader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author weitao47
 * @project xxl-job
 * @date 2022/9/19 10:43
 */
public class DynamicClassLoader {

    /**
     *  groovy加载器
     */
    private final static GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader();
    /**
     *  缓存
     */
    private final static  ConcurrentMap<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();



    public static <T> T instanceBySourceCode(String sourceCode,Class<T> clazz){
        Object obj = instanceBySourceCode(sourceCode);
        try {
            return (T) obj;
        }catch (Exception e){
            throw new RuntimeException("source code type error : [sourceCode]" + sourceCode  +"\n class:" + clazz );
        }
    }



    public static Object instanceBySourceCode(String sourceCode){
        if(sourceCode!=null && !"".equals(sourceCode)){
            try {
                Class<?> codeSourceClass = getCodeSourceClass(sourceCode);
                if(codeSourceClass!=null){
                    return  codeSourceClass.newInstance();
                }
            }catch (Exception e){
                throw new RuntimeException("source code error :  " + sourceCode );
            }
        }{
            throw new RuntimeException("source code empty");
        }
    }

    private static Class<?> getCodeSourceClass(String codeSource){
        try {
            // md5
            byte[] md5 = MessageDigest.getInstance("MD5").digest(codeSource.getBytes());
            String md5Str = new BigInteger(1, md5).toString(16);
            Class<?> clazz = CLASS_CACHE.get(md5Str);
            if(clazz == null){
                clazz = GROOVY_CLASS_LOADER.parseClass(codeSource);
                CLASS_CACHE.putIfAbsent(md5Str, clazz);
            }
            return clazz;
        } catch (Exception e) {
            return GROOVY_CLASS_LOADER.parseClass(codeSource);
        }
    }
}
