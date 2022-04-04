package service;

import models.Cat;
import models.Friends;

import java.util.ArrayList;
import java.util.List;

public interface CatService {
    List<Cat> getAllCats();

    void saveCat(Cat cat);

    Cat findByPassportCat(int passport);

    void deleteCat(Cat cat);

    ArrayList<Cat> getFriendsCat(int passportCode);

    List<Cat> getOwnerCats(int passportCode);

    void deletePairFriends(Friends friends);

    void addPairFriend(Friends friends);
}
