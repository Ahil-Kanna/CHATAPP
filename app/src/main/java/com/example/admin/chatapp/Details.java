package com.example.admin.chatapp;

/**
 * Created by Admin on 20/03/2018.
 */

public class Details {
    String Email;
    String Name;
    long age;
    String Phno;
    long balance;

    public Details(String email, String name, long age, String phno, long balance) {
        Email = email;
        Name = name;
        this.age = age;
        Phno = phno;
        this.balance = balance;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getPhno() {
        return Phno;
    }

    public void setPhno(String phno) {
        Phno = phno;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
