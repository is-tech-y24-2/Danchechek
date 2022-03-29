package dao;

import models.Cat;
import models.Friends;

import java.util.List;

public interface FriendsDAO {
    public Friends findById(int id);

    public void save(Friends friend);

    public void update(Friends friend);

    public void delete(Friends friend);

    public List<Friends> getAll();
}
