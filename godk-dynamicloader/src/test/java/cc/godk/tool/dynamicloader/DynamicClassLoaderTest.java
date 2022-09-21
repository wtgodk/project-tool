package cc.godk.tool.dynamicloader;


import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

public class DynamicClassLoaderTest {


    /**
     *  已知类型
     */
    @Test
    public void instanceBySourceCode() {

        final String javaCode = "package cc.godk.tool.dynamicloader;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "\n" +
                " \n" +
                "public class TestDynamic implements BaseTest {\n" +
                "    public Date getTime() {\n" +
                "        return new Date();\n" +
                "    }\n" +
                "}\n";
        BaseTest test = DynamicClassLoader.instanceBySourceCode(javaCode, BaseTest.class);
        Date time = test.getTime();
        System.out.println(time);
    }

    /**
     *  未知类型
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    @Test
   public void testInstanceBySourceCode() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        final String javaCode = "package cc.godk.tool.dynamicloader;\n" +
                "\n" +
                "import java.util.Date;\n" +
                "\n" +
                " \n" +
                "public class TestDynamic {\n" +
                "    public Date getTime() {\n" +
                "        return new Date();\n" +
                "    }\n" +
                "}\n";
        Object o = DynamicClassLoader.instanceBySourceCode(javaCode);
        Method getTime = o.getClass().getMethod("getTime");
        Object invoke = getTime.invoke(o);
        System.out.println(invoke);
    }
}