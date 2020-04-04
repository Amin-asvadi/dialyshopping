package com.exa.dailyshoppinglist;

public class Data {
    String type;
    int amount;
    String note;
    String date;
    String id;

    public Data(){

    }
    public Data(String type,int amount ,String note ,String date,String id){

        this.type = type;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public String getType() {
        return type;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setType(String type) {
        this.type = type;
    }
}
