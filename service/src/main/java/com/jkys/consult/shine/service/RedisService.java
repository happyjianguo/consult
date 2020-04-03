package com.jkys.consult.shine.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * redis服务
 *
 */
@Service
public class RedisService {
	@Autowired
	private JedisPool pool;

	private static Logger logger = LoggerFactory.getLogger("serverRedis");
	private long TIEM = 500;// 500毫秒


	public Logger getLoger() {
		return logger;
	}

	public String get(String key) {
		try {
			long before = System.currentTimeMillis();
			String res = execute((con) -> con.get(key));
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,get方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
			return res;
		} catch(Exception e) {
			logger.error("RedisService.get error,key {}", key, e);
			return "";
		}
		
	}
	
	/**
	 * 
	 * @param key key
	 * @param value value
	 * @param secondsTimeout	秒
	 */
	public void set(final String key, final String value, int secondsTimeout) {
		try {
			long before = System.currentTimeMillis();
			execute(new RedisCallback<String>() {
				public String doInRedis(Jedis connection) throws DataAccessException {
					connection.setex(key.getBytes(), secondsTimeout, value.getBytes());
					return null;
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,set方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			throw e;
		}
	}


	/**
	 * 单个添加hash
	 * @param key redis key
	 * @param code map key
	 * @param value map value
	 */
	public void hmset( String key, String code,String value) {
		Map<String,String> map = new HashMap<>();
		map.put(code,value);
		hmset( key, map);
	}

	/**
	 * hash set方法
	 * @param key key
	 * @param value value
	 */
	public void hmset(final String key, final Map<String,String> value) {
		try {
			long before = System.currentTimeMillis();
			execute(new RedisCallback<String>() {
				public String doInRedis(Jedis connection) throws DataAccessException {
					String dd = connection.hmset(key, value);
					logger.info("redis缓存结果：{}",dd);
					return null;
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,hmset方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			throw e;
		}
	}

	/**
	 * hash get方法
	 * @param key key
	 * @param value value
	 */
	public List<String> hmget(final String key, final String value) {
		try {
			long before = System.currentTimeMillis();
			List<String> result = execute(new RedisCallback<List<String>>() {
				public List<String> doInRedis(Jedis connection) throws DataAccessException {
					return connection.hmget(key, value);
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,hmset方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			return null;
		}
	}


	/**
	 * hash set方法
	 * @param key key
	 * @param value value
	 */
	public void hset(final String key, String code,String value) {
		try {
			long before = System.currentTimeMillis();
			execute(new RedisCallback<String>() {
				public String doInRedis(Jedis connection) throws DataAccessException {
					Long dd = connection.hset(key,code, value);
					logger.info("redis缓存结果：{}",dd);
					return null;
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,hmset方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			throw e;
		}
	}

	/**
	 * hash get方法
	 * @param key key
	 * @param value value
	 */
	public String hget(final String key, final String value) {
		try {
			long before = System.currentTimeMillis();
			String result = execute(new RedisCallback<String>() {
				public String doInRedis(Jedis connection) throws DataAccessException {
					return connection.hget(key, value);
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,hmset方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			return null;
		}
	}


	/**
	 * hash delete方法
	 * @param key key
	 * @param value value
	 */
	public Long hdel(final String key, final String value) {
		try {
			long before = System.currentTimeMillis();
			Long result = execute(new RedisCallback<Long>() {
				public Long doInRedis(Jedis connection) throws DataAccessException {
					return connection.hdel(key, value);
				}
			});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,hmset方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
			return result;
		} catch (Exception e) {
			logger.error("RedisService.get put,key {}", key, e);
			return null;
		}
	}


	public void delete(String key) {
		try {
			long before = System.currentTimeMillis();
			execute((con)->{
					Long row = con.del(key.getBytes());
					return row != null ? row.toString() : null;
				});
			long exeTime = System.currentTimeMillis() - before;
			if (exeTime > TIEM) {
				logger.warn("执行redis,delete方法超过阀值，key:{}，耗时：阀值：{},{}", key, exeTime, TIEM);
			}
		} catch (Exception e) {
			logger.error("RedisService.delete error,key {}", key, e);
			throw e;
		}
	}


	private <T> T execute(RedisCallback<T> action) {
    	 T value;
        
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            value = action.doInRedis(jedis);
        } catch (Throwable e) {
			logger.error("RedisService.execute error,key", e);
            throw e;
        } finally {
        	 if (jedis != null) {
				 jedis.close();
             }
        }
        return value;
    }

    private interface RedisCallback<T>{
    	T doInRedis(Jedis connection) throws DataAccessException;
    }

}
