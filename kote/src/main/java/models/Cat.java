package models;


import jakarta.persistence.*;


@Entity
@Table(name = "cats")
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "passportowner")
    private int passportOwner;

    @Column(name = "date")
    private String date;
    @Column(name = "breed")
    private String breed;
    @Column(name = "color")
    private String color;
    @Column(name = "passport_code")
    private int passportCode;

    public Cat() {
    }

    public Cat(int passportOwner, String date, String breed, String color, int passportCode) {
        this.passportOwner = passportOwner;
        this.date = date;
        this.breed = breed;
        this.color = color;
        this.passportCode = passportCode;
    }

    public int getPassportCode() {
        return passportCode;
    }

    public String getDate() {
        return date;
    }

    public String getBreed() {
        return breed;
    }

//    public String[] getFriends()
//    {
//        return friends;
//    }

    public String getColor() {
        return color;
    }

    public int getPassportOwner() {
        return passportOwner;
    }

    public int getId() {
        return id;
    }
}
