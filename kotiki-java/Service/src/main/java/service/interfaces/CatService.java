package ru.itmo.kotiki.service.interfaces;

import models.Cat;
import models.Friends;
import models.Owner;

import java.util.ArrayList;
import java.util.List;

public interface CatService {
    List<Cat> getAllCats();

    void saveCat(Cat cat, Owner owner);

    Cat findByPassportCat(int passport);

    void deleteCat(Cat cat);

    ArrayList<Cat> getFriendsCat(int passportCode);

    List<Cat> getOwnerCats(int passportCode);

    void deletePairFriends(Friends friends);

    void addPairFriend(Friends friends);
}
