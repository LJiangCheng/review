package com.ljc.review.common.test;

import org.junit.Test;

public class ReferenceTest {

    @Test
    public void referenceTest() {
        String s = null;
        System.out.println("" + s);
    }

    private void changeName(Person person) {
        person.setName("Rose");
        person.setAge(20);
    }

    private Person changePerson(Person person) {
        person = new Person("Bili", 20);
        person.setName("s");
        return person;
    }

}

class Person{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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
}































