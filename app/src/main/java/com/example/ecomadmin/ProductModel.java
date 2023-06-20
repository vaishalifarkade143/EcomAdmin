package com.example.ecomadmin;

public class ProductModel
{
    private String id;
    private  String title;
    private  String decription;
    //private String price;
    private String image;
    private boolean show;

    public ProductModel()
    {

    }

    public ProductModel(String id, String title, String decription, String image, boolean show) {//string price
        this.id = id;
        this.title = title;
        this.decription = decription;
       // this.price = price;
        this.image = image;
        this.show = show;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

//    public String getPrice() {
//        return price;
//    }
//
//    public void setPrice(String price) {
//        this.price = price;
//    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
