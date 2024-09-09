package enigma.estore.utils;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    private static final int DEFAULT_EXPIRATION = 3600; // 1 hour

    public <T> T getCachedData(String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            String cachedData = jedis.get(key);
            if (cachedData != null) {
                return objectMapper.readValue(cachedData, clazz);
            }
        } catch (Exception e) {
            log.error("Error retrieving data from cache: {}", e.getMessage());
        }
        return null;
    }

    public void cacheData(String key, Object data) {
        cacheData(key, data, DEFAULT_EXPIRATION);
    }

    public void cacheData(String key, Object data, int expiration) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, expiration, objectMapper.writeValueAsString(data));
        } catch (JsonProcessingException e) {
            log.error("Error serializing object to JSON: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error caching data: {}", e.getMessage());
        }
    }

    public void invalidateCache(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        } catch (Exception e) {
            log.error("Error invalidating cache: {}", e.getMessage());
        }
    }

    public void invalidateCaches(String pattern) {
        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> keys = jedis.keys(pattern);
            if (!keys.isEmpty()) {
                jedis.del(keys.toArray(new String[0]));
            }
        } catch (Exception e) {
            log.error("Error invalidating caches: {}", e.getMessage());
        }
    }
}
