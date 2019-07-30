package com.example.myapplication0;

public class ProductInfo {
    String product_Name;
    String desc;
    String price;
    String image;

    public ProductInfo() {
    }

    public ProductInfo(String product_Name, String desc, String price, String image) {
        this.product_Name = product_Name;
        this.desc = desc;
        this.price = price;
        this.image = image;
    }

    public String getProduct_Name() {
        return product_Name;
    }

    public void setProduct_Name(String product_Name) {
        this.product_Name = product_Name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
