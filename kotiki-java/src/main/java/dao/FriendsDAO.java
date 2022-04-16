package ru.itmo.kotiki.dao;

import models.Cat;
import models.Friends;

import java.util.List;

public interface FriendsDAO {
    Friends findById(int id);

    void save(Friends friend);

    void update(Friends friend);

    void delete(Friends friend);

    List<Friends> getAll();

    Friends getAll(int i);
}
