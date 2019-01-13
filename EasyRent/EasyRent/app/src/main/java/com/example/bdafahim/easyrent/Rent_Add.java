package com.example.bdafahim.easyrent;

public class Rent_Add {
    private String house_no,owner_name,key;
    private String road_no,area,phone_no,email,type;
    private int fee;
    private double lati,longi;
    public Rent_Add(String type,String house_no, String road_no, String area, String phone_no, String email,double lati,double longi) {
        this.type = type;
        this.house_no = house_no;
        this.road_no = road_no;
        this.area = area;
        this.phone_no = phone_no;
        this.email = email;
        this.lati = lati;
        this.longi = longi;
    }

    public Rent_Add(String house_no, String owner_name, String road_no, String area, String phone_no, String email, String type, int fee, double lati, double longi,String key) {
        this.house_no = house_no;
        this.owner_name = owner_name;
        this.road_no = road_no;
        this.area = area;
        this.phone_no = phone_no;
        this.email = email;
        this.type = type;
        this.fee = fee;
        this.lati = lati;
        this.longi = longi;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public Rent_Add(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getRoad_no() {
        return road_no;
    }

    public void setRoad_no(String road_no) {
        this.road_no = road_no;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLati() {
        return lati;
    }

    public double getLongi() {
        return longi;
    }
}
