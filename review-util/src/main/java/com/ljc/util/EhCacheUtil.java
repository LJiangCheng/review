package com.ljc.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhCacheUtil {

    public static final Integer COMMON_CACHE_TIME = 30 * 60;

    private static final CacheManager cacheManager = CacheManager.getInstance();

    /**
     * 不传过期时间，使用默认过期时间（当前配置为不过期）
     */
    public static void put(String cacheName, String key, Object value) {
        put(cacheName, key, value, null);
    }

    public static void put(String cacheName, String key, Object value, Integer cacheTimeSeconds) {
        makeSureCacheExists(cacheName);
        Cache cache = cacheManager.getCache(cacheName);
        Element element = new Element(key, value);
        if (cacheTimeSeconds != null) {
            element.setTimeToLive(cacheTimeSeconds);
        }
        cache.put(element);
    }

    public static Object get(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            return element == null ? null : element.getObjectValue();
        }
        return null;
    }

    public static Cache get(String cacheName) {
        return cacheManager.getCache(cacheName);
    }

    public static void removeKey(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }
    }

    public static void removeCache(String cacheName) {
        cacheManager.removeCache(cacheName);
    }

    private static void makeSureCacheExists(String cacheName) {
        if (!cacheManager.cacheExists(cacheName)) {
            //多线程同步
            synchronized (EhCacheUtil.class) {
                if (!cacheManager.cacheExists(cacheName)) {
                    cacheManager.addCache(cacheName);
                }
            }
        }
    }

}
