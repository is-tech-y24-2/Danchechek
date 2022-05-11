package app.entity;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class FriendsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first")
    private int first;
    @Column(name = "second")
    private int second;

    public FriendsEntity() {
    }

    public FriendsEntity(int first, int second) {
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
