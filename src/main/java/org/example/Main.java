package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool("localhost", 6379);

        try (Jedis jedis = pool.getResource()) {
            System.out.println("Create new user? /n");
            Scanner in = new Scanner(System.in);
            String ans = in.next();
            if(Objects.equals(ans, "yes")){
                System.out.println("Insert login");
                String login = in.next();
                while(jedis.exists(login)){
                    System.out.println("This login is already used, insert another one");
                    login = in.next();
                }
                System.out.println("Insert password");
                String password = in.next();
                System.out.println("Repeat your password");
                ans = in.next();
                while(!Objects.equals(ans, password)){
                    System.out.println("Passwords don't match, repeat your password");
                    ans = in.next();
                }
                Map <String, String> userInfo = new HashMap<>();
                userInfo.put("password", password);
                jedis.hset(login, userInfo);
                System.out.println("User created, please log in");
                jedis.save();
            }
            System.out.println("Insert login");
            String login = in.next();
            System.out.println("Insert password");
            String password = in.next();
            while(!Objects.equals(jedis.hget(login, "password"), password)){
                System.out.println("Wrong password, please try again");
                password = in.next();
            }
            System.out.println("Welcome, " + login);
        }
    }
}