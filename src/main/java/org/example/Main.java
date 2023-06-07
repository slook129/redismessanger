package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool("localhost", 6379);

        try (Jedis jedis = pool.getResource()) {
            Authorization auth = new Authorization();
            String login = auth.Autorize(jedis);
            Actions acts = new Actions(login, jedis);
            acts.Act();
        }
    }
}