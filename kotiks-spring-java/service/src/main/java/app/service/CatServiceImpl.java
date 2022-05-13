package ru.itmo.kotiks-spring-java.service;

import ru.itmo.kotiks-spring-java.entity.CatEntity;
import ru.itmo.kotiks-spring-java.entity.FriendsEntity;
import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiks-spring-java.repository.CatRepository;
import ru.itmo.kotiks-spring-java.repository.FriendsRepository;
import ru.itmo.kotiks-spring-java.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatServiceImpl implements CatService{

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    public List<CatEntity> getAllCats() {
        return catRepository.getAll();
    }

    public boolean saveCat(CatEntity cat) {
        List<OwnerEntity> owners =  ownerRepository.findAll();
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getPassportCode() == cat.getPassportOwner()) {
                catRepository.save(cat);
                return true;
            }
        }
        return false;
    }

    public CatEntity findByPassportCat(int passport) {
        return catRepository.findCatByPassport(passport);
    }

    public void deleteCat(CatEntity cat) {
        catRepository.delete(cat);
    }

    public ArrayList<CatEntity> getFriendsCat(int passportCode) {
        CatEntity cat = findByPassportCat(passportCode);
        List<CatEntity> friends = new ArrayList<>();
        List<FriendsEntity> allPairs = friendsRepository.findAll();
        for (FriendsEntity allPair : allPairs) {
            if (allPair.getFirst() == passportCode) {
                friends.add(findByPassportCat(allPair.getSecond()));
            }

            if (allPair.getSecond() == passportCode) {
                friends.add(findByPassportCat(allPair.getFirst()));
            }
        }

        return (ArrayList<CatEntity>) friends;
    }

    public List<CatEntity> getOwnerCats(int passportCode) {
        OwnerEntity owner = new OwnerEntity();
        List<OwnerEntity> owners = ownerRepository.findAll();
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getPassportCode() == passportCode) {
                owner = owners.get(i);
            }
        }
        ArrayList<CatEntity> cats = new ArrayList<>();
        catRepository.findAll().forEach(cats::add);
        List<CatEntity> ownerCats = new ArrayList<>();
        for (CatEntity cat : cats) {
            if (cat.getPassportOwner() == owner.getPassportCode()) {
                ownerCats.add(cat);
            }
        }

        return ownerCats;
    }

    public void deletePairFriends(FriendsEntity friends) {
        friendsRepository.delete(friends);
    }

    public boolean addPairFriend(FriendsEntity friends) {
        var all = getAllFriends();
        for (FriendsEntity value : all) {
            if ((value.getSecond() == friends.getSecond()
                    && value.getFirst() == friends.getFirst())
                    || (value.getSecond() == friends.getFirst()
                    && value.getFirst() == friends.getSecond())

            ) {
                return false;
            }
        }

        friendsRepository.save(friends);
        return true;
    }

    public List<FriendsEntity> getAllFriends() {
        List<FriendsEntity> friends = friendsRepository.findAll();
        return friends;
    }
}
