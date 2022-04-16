package ru.itmo.kotiki.models;

import jakarta.persistence.*;

@Entity
@Table(name = "owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;
    @Column(name = "passport_code")
    private int passportCode;

    public Owner() {
    }

    public Owner(String name, String date, int passportCode) {
        this.passportCode = passportCode;
        this.name = name;
        this.date = date;
    }

    public int getPassportCode() {
        return passportCode;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }
}
