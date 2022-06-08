package ru.itmo.auth.app.view;

public class OwnerView {
    public String name;
    public String date;
    public String role;

    public OwnerView(String name, String date, String role) {
        this.role = role;
        this.name = name;
        this.date = date;
    }
}
