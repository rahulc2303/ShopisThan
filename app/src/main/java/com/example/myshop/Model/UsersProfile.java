package com.example.myshop.Model;

public class UsersProfile
{
    private String name,phone,email,address,profileImg;

    public UsersProfile()
    {

    }

    public UsersProfile(String name, String phone, String email, String address, String profileImg) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
