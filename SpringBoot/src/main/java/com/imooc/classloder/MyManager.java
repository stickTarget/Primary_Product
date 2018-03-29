package com.imooc.classloder;

/**
 * BaseManager子类，次类需要实现java类的热加载功能
 */
public class MyManager implements BaseManager {

    public void logic() {
        System.out.println("今天天气不错！");
        System.out.println("瞎说！");
    }
}
