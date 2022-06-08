package ru.itmo.auth.app.service;

import ru.itmo.auth.app.entity.CatEntity;
import ru.itmo.auth.app.entity.FriendsEntity;
import ru.itmo.auth.app.view.CatView;

import java.util.ArrayList;
import java.util.List;

public interface CatService {
    List<CatEntity> getAllCats();

    boolean saveCat(CatEntity cat);

    CatEntity findByPassportCat(int passport);

    void deleteCat(int passportCode);

    ArrayList<CatEntity> getFriendsCat(int passportCode);

    List<CatEntity> getOwnerCats(int passportCode);

    void deletePairFriends(FriendsEntity friends);

    boolean addPairFriend(FriendsEntity friends);

    List<FriendsEntity> getAllFriends();

    List<CatView> convert(List<CatEntity> cats);

    CatView convert(CatEntity cat);
}
