package com.crystal.main.crystal.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by hp on 2017-05-17.
 */
public class RedisJava {
    public static void main(String[] args){
        //连接redis服务
        Jedis jedis = new Jedis("192.168.213.128",6379);
        //权限认证
        jedis.auth("123456");
        System.out.println("Connection to server successfully");

        //查看服务是否允许
        System.out.println("Server is running: "+jedis.ping());
    }
}
