package br.com.personreg.services;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public <T> void setCache(String key, List<T> value, long duration,
			TimeUnit unit) {
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		ops.set(key, value, duration, unit);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getCache(String key, Class<T> clazz) {
		ValueOperations<String, Object> ops = redisTemplate.opsForValue();
		Object cached = ops.get(key);
		if (cached == null)
			return null;
		if (cached instanceof List<?>) {
			return (List<T>) cached;
		}
		return null;
	}

	public void evict(String key) {
		redisTemplate.delete(key);
	}
}
