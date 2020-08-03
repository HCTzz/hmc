package com.ctt.web.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author HHF
 * @Description
 * @create 2020-06-12 上午 11:36
 */
@Slf4j
@Component
public class RedisPersistentTokenRepository implements PersistentTokenRepository {

    private final static String SERIES_TOKEN = "hmc:security:rememberMe:series:";

    private final static String USER_TOKEN = "hmc:security:rememberMe:username:";

    private final static int TOKEN_VALID_DAYS = 2;

    @Resource
    RedisTemplate<String,PersistentRememberMeToken> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        String series = persistentRememberMeToken.getSeries();
        String key = generateKey(series,SERIES_TOKEN);
        String usernameKey = generateKey(persistentRememberMeToken.getUsername(),USER_TOKEN);
        //用户只要采用账户密码重新登录，那么为了安全就有必要清除之前的token信息。
        //username查找到用户对应的series，然后删除旧的token信息。
        deleteIfPresent(usernameKey);
        HashMap<String,String > hashMap = new HashMap<>();
        hashMap.put("username",persistentRememberMeToken.getUsername());
        hashMap.put("token",persistentRememberMeToken.getTokenValue());
        hashMap.put("date",String.valueOf(persistentRememberMeToken.getDate().getTime()));
        HashOperations<String ,String ,String> hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key,hashMap);
        redisTemplate.expire(key,TOKEN_VALID_DAYS, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(usernameKey,series);
        redisTemplate.expire(usernameKey,TOKEN_VALID_DAYS, TimeUnit.DAYS);
    }

    @Override
    public void updateToken(String s, String s1, Date date) {
        String key = generateKey(s,SERIES_TOKEN);
        if(redisTemplate.hasKey(key)) {
            redisTemplate.opsForHash().put(key,"token",s1);
        }
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        String key = generateKey(s,SERIES_TOKEN);
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        String username =  entries.getOrDefault("username","").toString();
        String tokenValue = entries.getOrDefault("token","").toString();
        String date = entries.getOrDefault("date","").toString();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(tokenValue) || StringUtils.isEmpty(date) ) {
            return null;
        }
        Long timestamp = Long.valueOf(date);
        Date time = new Date(timestamp);
        return new PersistentRememberMeToken(username, s, tokenValue, time);
    }

    /**
     * 移除用户token
     * @param s 用户名
     */
    @Override
    public void removeUserTokens(String s) {
        //rememberMeService实现类中调用这个方法传入的参数是username，因此我们必须通过username查找到
        //对应的series，然后再通过series查找到对应的token信息再删除。
        String key = generateKey(s,USER_TOKEN);
        deleteIfPresent(key);
    }

    private void deleteIfPresent(String key){
        //删除token时应该同时删除token信息，以及保存了对应的username与series对照数据。
        if(redisTemplate.hasKey(key)){
            String series = generateKey(stringRedisTemplate.opsForValue().get(key),SERIES_TOKEN);
            if(series!=null && redisTemplate.hasKey(series)){
                redisTemplate.delete(series);
                redisTemplate.delete(key);
            }
        }
    }

    public String generateKey(String key, String prefix){
        return prefix + key;
    }

}
