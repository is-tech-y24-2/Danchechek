package ru.itmo.kotiki.models;

import jakarta.persistence.*;

import java.util.concurrent.RecursiveTask;

@Entity
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first")
    private int first;
    @Column(name = "second")
    private int second;

    public Friends() {
    }

    public Friends(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getId() {
        return id;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
