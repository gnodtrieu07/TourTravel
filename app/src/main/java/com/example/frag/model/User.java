package com.example.frag.model;

public class User {
    String name;
    String birthDay;
    String phone;
    String gender;
    String email;
    public User(){

    }

    public User(String name, String birthDay, String phone, String gender, String email) {
        this.name = name;
        this.birthDay = birthDay;
        this.phone = phone;
        this.gender = gender;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
