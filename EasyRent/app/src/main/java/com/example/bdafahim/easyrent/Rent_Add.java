package com.example.bdafahim.easyrent;

public class Rent_Add {
    private String house_no;
    private String road_no,area,phone_no,email,type;
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
