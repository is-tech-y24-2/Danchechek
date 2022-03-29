package dao;

import models.Cat;
import models.Friends;

import java.util.List;

public interface CatDAO {

    public Cat findById(int id);

    public void save(Cat cat);

    public void update(Cat cat);

    public void delete(Cat cat);

    public List<Cat> getAll();
}
