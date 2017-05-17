package com.crystal.redis;

import com.crystal.main.crystal.redis.RedisUtil;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by hp on 2017-05-17.
 */
public class TestRedis {
    private Jedis jedis;

    @Before
    public void setup(){
        jedis = new Jedis("192.168.213.128",6379);
        jedis.auth("123456");
    }

    /**
     * 存储字符串
     */
    @Test
    public void testString(){
        //添加数据
        jedis.set("name","xinxin");
        System.out.println(jedis.get("name"));

        jedis.append("name"," is my lover");
        System.out.println(jedis.get("name"));

        jedis.del("name");
        System.out.println(jedis.get("name"));

        jedis.mset("name","liuling","age","23","qq","3333333");
        jedis.incr("age"); //加1操作

        System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("qq"));
    }

    /**
     * 操作Map
     */
    @Test
    public void testMap(){
        //-------添加数据
        Map<String,String> map = new HashMap<String, String>();
        map.put("name","xinxinxin");
        map.put("age","23");
        map.put("qq","123455");
        jedis.hmset("user",map);

        List<String> rsmap = jedis.hmget("user","name","age","qq");
        System.out.println(rsmap);

        //删除map中某个键值
        jedis.hdel("user","age");
        System.out.println("因为删除了，所以返回的是null  "+jedis.hmget("user","age"));
        System.out.println("返回key为user的键中存放的值的个数:"+jedis.hlen("user"));
        System.out.println("是否存在key为user的记录:"+jedis.exists("user"));
        System.out.println("返回map对象中的所有key :"+jedis.hkeys("user"));
        System.out.println("返回map对象中的所有value:"+jedis.hvals("user"));

        Iterator<String> iter = jedis.hkeys("user").iterator();
        while(iter.hasNext()){
            String key = iter.next();
            System.out.println(key+":"+jedis.hmget("user",key));
        }
    }

    /**
     * jedis操作List
     */
    @Test
    public void testList(){
        //开始前先移除内容
        jedis.del("java list");
        System.out.println(jedis.lrange("java list",0,-1));

        //先保存数据
        jedis.lpush("java list","spring");
        jedis.lpush("java list","strust");
        jedis.lpush("java list","hibernate");

        //再取出所有数据jedis.lrange是按范围取出，
        // 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
        System.out.println(jedis.lrange("java list",0,-1));

        jedis.del("java list");
        jedis.rpush("java list","spring");
        jedis.rpush("java list","strust");
        jedis.rpush("java list","hibernate");
        System.out.println(jedis.lrange("java list",0,-1));
    }

    /**
     * jedis操作Set
     */
    @Test
    public void testSet(){
        //添加
        jedis.del("user");
        jedis.sadd("user","liuliu");
        jedis.sadd("user","xinxin");
        jedis.sadd("user","zhangmmm");
        jedis.sadd("user","who");
        System.out.println(jedis.smembers("user"));

        jedis.srem("user","who");
        System.out.println(jedis.smembers("user"));
        System.out.println(jedis.sismember("user","who"));
        System.out.println(jedis.srandmember("user"));
        System.out.println(jedis.scard("user"));
    }

    @Test
    public void test() throws InterruptedException{
        //jedis 排序
        jedis.del("a");
        jedis.rpush("a","1");
        jedis.rpush("a","4");
        jedis.rpush("a","3");
        jedis.rpush("a","9");
        jedis.rpush("a","6");

        System.out.println(jedis.lrange("a",0,-1));
        System.out.println(jedis.sort("a"));
        System.out.println(jedis.lrange("a",0,-1));

    }


    @Test
    public void testRedisPool(){
        RedisUtil.getJedis().set("newname","中文测试");
        System.out.println(RedisUtil.getJedis().get("newname"));
    }
}
