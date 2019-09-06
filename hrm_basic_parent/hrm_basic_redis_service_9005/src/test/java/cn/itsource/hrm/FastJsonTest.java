package cn.itsource.hrm;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FastJsonTest {

    //导入jar
    //JSONObject.toJSONString
    //JSONObject.parseObject
    //JSONArray.toJSONString
    //JSONArray.parseArray

    @Test
    public void test()throws Exception{

        //1 对象转换为字符串
        //1.1 一个对象
        Person person = new Person(1L,"zs");
        String jsonObjStr = JSONObject.toJSONString(person);
        System.out.println(jsonObjStr);
        //1.2 集合
        List<Person> personList = Arrays.asList(person, person, person);
        String jsonArrayStr = JSONArray.toJSONString(personList);
        System.out.println(jsonArrayStr);
        //2 字符串转换为对象
        //2.1 对象json字符串
        Person person1 = JSONObject.parseObject(jsonObjStr, Person.class);
        System.out.println(person1);
        //2.2 数组json字符串
        List<Person> pers = JSONArray.parseArray(jsonArrayStr, Person.class);
        System.out.println(pers);


    }
}
