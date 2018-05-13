package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author junjun
 * @date 2018/4/16
 **/
@Slf4j
public class RedisPoolUtil {

    /**
     * 设置key的有效期，单位是秒
     * */
    public static Long expire(String key, int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e){
            log.error("expire key:{} error", key, e);
            RedisPool.returnBrokenResorce(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e){
            log.error("expire key:{} error", key, e);
            RedisPool.returnBrokenResorce(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
}
