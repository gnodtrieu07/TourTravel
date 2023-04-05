package com.example.frag.model;

import java.util.Date;

public class Ticket {
    private String nameCustom, phoneCustom, emailCustom,image;
    private int people_amount, child_amount;
    String nameTour;
    String timeTour;
    String placeTour;
    String placeStart;
    String pricePeople ;
    String priceTotal;
    String time;

    public Ticket() {

    }

    public String getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(String priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Ticket(String image,String nameTour, String placeTour, String priceTotal, String timeTour, String phoneCustom, String emailCustom, String placeStart, String time, int people_amount, int child_amount) {
        this.image = image;
        this.nameTour = nameTour;
        this.placeTour = placeTour;
        this.priceTotal = priceTotal;
        this.timeTour = timeTour;

        this.phoneCustom = phoneCustom;
        this.emailCustom = emailCustom;
        this.placeStart = placeStart;
        this.nameCustom = nameCustom;
        this.time = time;
        this.people_amount = people_amount;
        this.child_amount = child_amount;
    }

    /*public Ticket(String name, int people_amount, int child_amount, String nameTour, String timeTour, String placeTour, String placeStart, String pricePeople, String priceChild, String phoneCustom, String emailCustom, String time) {
        this.name = name;
        this.people_amount = people_amount;
        this.child_amount = child_amount;
        this.nameTour = nameTour;
        this.timeTour = timeTour;
        this.placeTour = placeTour;
        this.placeStart = placeStart;
        this.pricePeople = pricePeople;
        this.priceChild = priceChild;
        this.phoneCustom = phoneCustom;
        this.emailCustom = emailCustom;
        this.time = time;

    }*/

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameCustom() {
        return nameCustom;
    }

    public void setNameCustom(String nameCustom) {
        this.nameCustom = nameCustom;
    }

    public String getPhoneCustom() {
        return phoneCustom;
    }

    public void setPhoneCustom(String phoneCustom) {
        this.phoneCustom = phoneCustom;
    }

    public String getEmailCustom() {
        return emailCustom;
    }

    public void setEmailCustom(String emailCustom) {
        this.emailCustom = emailCustom;
    }

    public int getPeople_amount() {
        return people_amount;
    }

    public void setPeople_amount(int people_amount) {
        this.people_amount = people_amount;
    }

    public int getChild_amount() {
        return child_amount;
    }

    public void setChild_amount(int child_amount) {
        this.child_amount = child_amount;
    }

    public String getNameTour() {
        return nameTour;
    }

    public void setNameTour(String nameTour) {
        this.nameTour = nameTour;
    }

    public String getTimeTour() {
        return timeTour;
    }

    public void setTimeTour(String timeTour) {
        this.timeTour = timeTour;
    }

    public String getPlaceTour() {
        return placeTour;
    }

    public void setPlaceTour(String placeTour) {
        this.placeTour = placeTour;
    }

    public String getPlaceStart() {
        return placeStart;
    }

    public void setPlaceStart(String placeStart) {
        this.placeStart = placeStart;
    }

    public String getPricePeople() {
        return pricePeople;
    }

    public void setPricePeople(String pricePeople) {
        this.pricePeople = pricePeople;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
