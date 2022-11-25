package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringDataRedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //operate string data
    @Test
    public void testString(){
        stringRedisTemplate.opsForValue().set("city","beijing");
        String city = stringRedisTemplate.opsForValue().get("city");
        System.out.println(city);
        Boolean aBoolean = stringRedisTemplate.opsForValue().setIfAbsent("city1", "beijing", 10, TimeUnit.SECONDS);
        System.out.println(aBoolean);
    }
    //operate hash data
    @Test
    public void testHash() {
        HashOperations<String, Object, Object> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put("002", "name", "irene");
        hashOperations.put("002", "age", "30");
        String age = (String) hashOperations.get("002", "age");
        System.out.println(age);
        Set<Object> keys = hashOperations.keys("002");
        for (Object key : keys) {
            System.out.println(key);
        }
        List<Object> values = hashOperations.values("002");
        for (Object value : values) {
            System.out.println(value);
        }
    }
    //operate list
    @Test
    public void testList(){
        ListOperations<String, String> listOperations = stringRedisTemplate.opsForList();
        //input
        listOperations.leftPush("mylist","a");
        listOperations.leftPushAll("mylist","b","c","d");
        //output
        List<String> mylist = listOperations.range("mylist", 0, -1);
        for(String s:mylist){
            System.out.println(s);
        }
        //get size
        Long length = listOperations.size("mylist");
        int i = length.intValue();
        //remove
        String mylist1 = listOperations.rightPop("mylist");
    }
    //operate set
    @Test
    public void testSet(){
        SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        //input
        setOperations.add("myset","a","b","c","a");
        //output
        Set<String> myset = setOperations.members("myset");
        for(String s:myset){
            System.out.println(s);
        }
        //remove
        setOperations.remove("myset","a","b");

        myset = setOperations.members("myset");
        for(String s:myset){
            System.out.println(s);
        }
    }
    //operate ZSet
    @Test
    public void testZset(){
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        //input
        zSetOperations.add("myZSet","a",10.0);
        zSetOperations.add("myZSet","b",9.0);
        zSetOperations.add("myZSet","c",12.0);
        zSetOperations.add("myZSet","c",10.0);
        //output
        Set<String> myZSet = zSetOperations.range("myZSet", 0, -1);
        for(String s:myZSet){
            System.out.println(s);
        }
        //change score
        zSetOperations.incrementScore("myZSet","b",20.0);

        //delete element
        zSetOperations.remove("myZSet","a");
        myZSet = zSetOperations.range("myZSet", 0, -1);
        for(String s:myZSet){
            System.out.println(s);
        }
    }
    //methods for all kinds of data
    @Test
    public void testCommon(){
       //get keys
        Set<String> keys = stringRedisTemplate.keys("*");
        for(String key:keys){
            System.out.println(key);
        }
        //whether key exists?
        Boolean name = stringRedisTemplate.hasKey("name");
        System.out.println(name);
        //delete certain key
        stringRedisTemplate.delete("name");
        //get the value of certain key
        DataType myset = stringRedisTemplate.type("myset");
        System.out.println(myset);
    }



}
