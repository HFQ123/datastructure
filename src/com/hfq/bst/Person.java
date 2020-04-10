package com.hfq.bst;

/**
 * @Created by hfq on 2020/4/10 18:41
 * @used to:
 * @return:
 */
public class Person implements Comparable{
    private int age;
    private int height;
    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", height=" + height +
                ", name='" + name + '\'' +
                '}';
    }

    public Person(int age, int height, String name) {
        this.age = age;
        this.height = height;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public int compareTo(Object o) {
        return this.age-((Person)o).age;
    }
}
