package com.example.myshop.Model;

public class CatProducts
{
    private String pid,description,image,price,productName,gender,discountPrice,category,sellerName,sid;

    boolean discountAvailable;

    public CatProducts(boolean discountAvailable)
    {
        this.discountAvailable = discountAvailable;
    }

    public CatProducts()
    {

    }



    public CatProducts(String pid, String description, String image, String price, String productName, String gender, String discountPrice, String discountAvailable, String category, String sellerName, String sid) {
        this.pid = pid;
        this.description = description;
        this.image = image;
        this.price = price;
        this.productName = productName;
        this.gender = gender;
        this.discountPrice = discountPrice;
        this.category = category;
        this.sellerName = sellerName;
        this.sid = sid;

    }

    public boolean isDiscountAvailable() {
        return discountAvailable;
    }

    public void setDiscountAvailable(boolean discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
