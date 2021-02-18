package com.example.myshop.Model;

public class Products
{


    String pid,date,time,description,image,price,productName,gender,discountPrice,category,sellerName,sellerAddress,sellerPhone,sellerEmail,sid;

    Boolean discountAvailable;
    Boolean OFS;




    public Products()
    {
    }

    public Products(String pid, String date, String time, String description, String image, String price, String productName, String gender, String discountPrice, String category, String sellerName, String sellerAddress, String sellerPhone, String sellerEmail, String sid, Boolean discountAvailable, Boolean OFS) {
        this.pid = pid;
        this.date = date;
        this.time = time;
        this.description = description;
        this.image = image;
        this.price = price;
        this.productName = productName;
        this.gender = gender;
        this.discountPrice = discountPrice;
        this.category = category;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
        this.sellerPhone = sellerPhone;
        this.sellerEmail = sellerEmail;
        this.sid = sid;
        this.discountAvailable = discountAvailable;
        this.OFS = OFS;
    }


    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Boolean getDiscountAvailable() {
        return discountAvailable;
    }

    public void setDiscountAvailable(Boolean discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public Boolean getOFS() {
        return OFS;
    }

    public void setOFS(Boolean OFS) {
        this.OFS = OFS;
    }
}

