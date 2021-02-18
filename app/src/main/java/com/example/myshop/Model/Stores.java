package com.example.myshop.Model;

public class Stores
{

    String sid,storeName,storeAddress,storePhone,storeEmail,storeGST,image,category;

    public Stores()
    {

    }

    public Stores(String sid, String storeName, String storeAddress, String storePhone, String storeEmail, String storeGST, String image, String category) {
        this.sid = sid;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeEmail = storeEmail;
        this.storeGST = storeGST;
        this.image = image;
        this.category = category;
    }


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getStoreGST() {
        return storeGST;
    }

    public void setStoreGST(String storeGST) {
        this.storeGST = storeGST;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
