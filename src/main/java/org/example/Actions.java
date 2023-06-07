package org.example;

import redis.clients.jedis.Jedis;

import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;

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
            case("select"):
                SelectUser();
                Act();
                break;
            case("exit"):
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
    public void SelectUser(){

    }
}
