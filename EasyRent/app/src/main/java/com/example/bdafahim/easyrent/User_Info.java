package com.example.bdafahim.easyrent;

public class User_Info {
    public String UName,Uaddress,UphoneNo,Uemail;

    public  User_Info(){

    }
    public User_Info(String name, String address, String phoneNo, String email) {
        UName= name;
        this.Uaddress= address;
        this.UphoneNo = phoneNo;
        this.Uemail = email;
    }
}
