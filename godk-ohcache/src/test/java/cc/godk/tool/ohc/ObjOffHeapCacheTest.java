package cc.godk.tool.ohc;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.Serializable;

public class ObjOffHeapCacheTest extends TestCase {

    @Test
    public void test(){

        Person person = new Person();
        person.setAge("23");
        person.setName("godk");
        ObjOffHeapCache<Person> instance = new ObjOffHeapCache<>();
        instance.put("测试",person);

        Person cachePerson = instance.get("测试");
        System.out.println(cachePerson);
        ObjOffHeapCache<Person> instance2 = new ObjOffHeapCache<>();
        Person cachePerson2 = instance2.get("测试");
        System.out.println(cachePerson2);


    }



static  class  Person  implements Serializable {

        private String name;

        private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

}


