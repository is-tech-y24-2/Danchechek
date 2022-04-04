package dao;

import models.Cat;
import models.Friends;

import java.util.List;

public interface CatDAO {

    Cat findById(int id);

    void save(Cat cat);

    void update(Cat cat);

    void delete(Cat cat);

    List<Cat> getAll();
}
