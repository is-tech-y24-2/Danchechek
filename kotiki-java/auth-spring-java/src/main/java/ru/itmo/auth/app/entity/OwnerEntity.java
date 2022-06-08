package ru.itmo.auth.app.entity;

import javax.persistence.*;

@Entity
@Table(name = "owner2")
public class OwnerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private String role;
    @Column(name = "date")
    private String date;
    @Column(name = "passport_code")
    private int passportCode;
    @Column(name = "password")
    private String password;

    public OwnerEntity(String name, String date, int passportCode, String role, String password) {
        this.password = password;
        this.passportCode = passportCode;
        this.role = role;
        this.name = name;
        this.date = date;
    }

    public OwnerEntity() {

    }

    public String getPassword() {
        return password;
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

    public String getRoles() {
        return role;
    }
}
