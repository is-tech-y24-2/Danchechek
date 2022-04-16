package ru.itmo.kotiki.dao;

import models.Cat;

import java.util.List;

public interface CatDAO {

    Cat findById(int id);

    void save(Cat cat);

    void update(Cat cat);

    void delete(Cat cat);

    List<Cat> getAll();

    Cat getAll(int i);
}
