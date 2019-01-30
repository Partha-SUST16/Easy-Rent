package com.example.bdafahim.easyrent;

public class User_Info {
    public String uname;
    public String uaddress;
    public String uphoneNo;
    public String uemail;
    public String imageString;


    public  User_Info(){

    }


    public User_Info(String uname, String uaddress, String uphoneNo, String uemail, String imageString) {
        this.uname = uname;
        this.uaddress = uaddress;
        this.uphoneNo = uphoneNo;
        this.uemail = uemail;
        this.imageString = imageString;
    }

    /*public User_Info(String uname, String uaddress, String uphoneNo, String uemail) {
        this.uname = uname;
        this.uaddress = uaddress;
        this.uphoneNo = uphoneNo;
        this.uemail = uemail;
    }*/

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUphoneNo() {
        return uphoneNo;
    }

    public void setUphoneNo(String uphoneNo) {
        this.uphoneNo = uphoneNo;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        imageString = imageString;
    }
    public void pri(){
        System.out.print(imageString+" "+uname+" "+uemail);
    }
}
