package com.BugBazaar.ui.addresses;

public class AddressItem {
    private int id;
    private String nickname;
    private String address;

    public AddressItem() {
        // Default constructor
    }
    public String toString() {
        return address;
    }

    public AddressItem(int id, String nickname, String address) {
        this.id = id;
        this.nickname = nickname;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

