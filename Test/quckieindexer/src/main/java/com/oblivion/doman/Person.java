package com.oblivion.doman;

import java.util.Comparator;

/**
 * Created by oblivion on 2016/10/27.
 */
public class Person implements Comparable<Person> {
    private String name;
    private String pinyin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }


    @Override
    public int compareTo(Person o) {
        return this.pinyin.compareTo(o.pinyin);
    }
}
