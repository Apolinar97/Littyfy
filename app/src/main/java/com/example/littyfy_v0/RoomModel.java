package com.example.littyfy_v0;

public class RoomModel {

    String dj, name, picture, status;

    public RoomModel () {}

    public RoomModel(String dj, String name, String picture, String status) {
        this.dj = dj;
        this.name = name;
        this.picture = picture;
        this.status = status;
    }

    public String getDj() {
        return dj;
    }

    public void setDj(String dj) {
        this.dj = dj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
