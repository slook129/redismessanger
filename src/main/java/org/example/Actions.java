package org.example;

import redis.clients.jedis.Jedis;

import java.util.*;

public class Actions {
    private String login;
    private Jedis jedis;
    public Actions(String s, Jedis j){
        login = s;
        jedis = j;
    }
    public void Act(){
        Scanner in = new Scanner(System.in);
        String input = in.next();
        switch(input){
            case("users"):
                ShowUsers();
                Act();
                break;
            case("send"):
                SendMessage();
                Act();
                break;
            case("show"):
                ShowMessages();
                Act();
                break;
            case("exit"):
                break;
            default:
                Act();
                break;
        }
    }
    public void ShowUsers(){
        Set<String> names=jedis.keys("*");
        System.out.println("There is " + names.size() + " users");
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String s = it.next();
            System.out.println(s);
        }
    }
    public void SendMessage(){
        Scanner in = new Scanner(System.in);
        System.out.println("Insert user login");
        String user = in.next();
        while(!jedis.exists(user)){
            System.out.println("User with this username was not found, try again");
            user = in.next();
        }
        System.out.println("Insert message");
        String message = new Scanner(System.in).nextLine();
        jedis.select(1);
        String  key;
        if(user.compareTo(this.login) < 0)
            key = user + this.login;
        else
            key = this.login + user;
        Map<String, String> messages = new HashMap<>();
        if(jedis.exists(key))
            messages = jedis.hgetAll(key);
        String messageKey = Integer.toString(messages.size() + 1) + ":" + login;
        messages.put(messageKey, message);
        jedis.hset(key, messages);
        jedis.select(0);
    }
    public void ShowMessages(){
        Scanner in = new Scanner(System.in);
        System.out.println("Insert user login");
        String user = in.next();
        while(!jedis.exists(user)){
            System.out.println("User with this username was not found, try again");
            user = in.next();
        }
        jedis.select(1);
        String  key;
        if(user.compareTo(this.login) < 0)
            key = user + this.login;
        else
            key = this.login + user;
        Map<String, String> messages = new HashMap<>();
        if(jedis.exists(key))
            messages = jedis.hgetAll(key);
        for(String messageKey : messages.keySet()){
            System.out.println(messageKey);
            System.out.println(messages.get(messageKey));
        }
        jedis.select(0);
    }
}
