package ru.itmo.kotiks-spring-java.service;

import ru.itmo.kotiks-spring-java.entity.CatEntity;
import ru.itmo.kotiks-spring-java.entity.FriendsEntity;
import ru.itmo.kotiks-spring-java.entity.OwnerEntity;

import java.util.ArrayList;
import java.util.List;

public interface CatService {
    List<CatEntity> getAllCats();

    boolean saveCat(CatEntity cat);

    CatEntity findByPassportCat(int passport);

    void deleteCat(CatEntity cat);

    ArrayList<CatEntity> getFriendsCat(int passportCode);

    List<CatEntity> getOwnerCats(int passportCode);

    void deletePairFriends(FriendsEntity friends);

    boolean addPairFriend(FriendsEntity friends);

    List<FriendsEntity> getAllFriends();
}
