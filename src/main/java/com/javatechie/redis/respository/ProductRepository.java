package com.javatechie.redis.respository;

import java.time.temporal.ChronoUnit;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javatechie.redis.entity.Product;

@Repository
public class ProductRepository{
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    public ProductRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void save(Product user) {
        hashOperations.put("USERS", user.getId(), user);
        redisTemplate.expire("USERS", 1, java.util.concurrent.TimeUnit.of(ChronoUnit.DAYS));
    }

    public Product findById(String id) {
        return (Product) hashOperations.get("USERS", id);
    }

    public Map<String, Product> findAll() {
        return hashOperations.entries("USERS");
    }

    public void update(Product user) {
        save(user);
    }

    public void deleteById(String id) {
        hashOperations.delete("USERS", id);
    }
}