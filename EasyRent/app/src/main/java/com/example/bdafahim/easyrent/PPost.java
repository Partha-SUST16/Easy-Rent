package com.example.bdafahim.easyrent;

public class PPost {
    private String userid,name,email,phone,address;

    public PPost(String userid, String name, String email, String phone, String address) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public PPost() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String toString(){
        return (getUserid()+" "+getEmail());
    }
}
