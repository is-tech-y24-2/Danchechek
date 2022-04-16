package ru.itmo.kotiki.service.solution;

import dao.CatDAO;
import dao.FriendsDAO;
import dao.OwnerDAO;
import dao.solution.CatDAOImpl;
import dao.solution.FriendsDAOImpl;
import dao.solution.OwnerDAOImpl;
import models.Cat;
import models.Friends;
import models.Owner;
import service.interfaces.CatService;
import tools.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class CatServiceImpl implements CatService {
    private FriendsDAO friendsDAO = new FriendsDAOImpl();
    private OwnerDAO ownerDAO = new OwnerDAOImpl();
    private CatDAO catDAO = new CatDAOImpl();

    @Override
    public List<Cat> getAllCats() {
        return catDAO.getAll();
    }

    @Override
    public void saveCat(Cat cat, Owner owner) {
        if(owner.getPassportCode()==cat.getPassportOwner())
        {
            catDAO.save(cat);
        }
        new ServiceException("Нет такого владельца");
    }

    @Override
    public Cat findByPassportCat(int passport) {
        var all = catDAO.getAll();
        for (Cat cat : all) {
            if (cat.getPassportCode() == passport) {
                return cat;
            }
        }

        return null;
    }

    @Override
    public void deleteCat(Cat cat) {
        catDAO.delete(cat);
    }

    @Override
    public ArrayList<Cat> getFriendsCat(int passportCode) {
        Cat cat = findByPassportCat(passportCode);
        List<Cat> friends = new ArrayList<>();
        List<Friends> allPairs = friendsDAO.getAll();
        for (Friends allPair : allPairs) {
            if (allPair.getFirst() == passportCode) {
                friends.add(findByPassportCat(allPair.getSecond()));
            }

            if (allPair.getSecond() == passportCode) {
                friends.add(findByPassportCat(allPair.getFirst()));
            }
        }

        return (ArrayList<Cat>) friends;
    }

    @Override
    public List<Cat> getOwnerCats(int passportCode) {
        Owner owner = findByPassportOwner(passportCode);
        var cats = catDAO.getAll();
        List<Cat> ownerCats = new ArrayList<>();
        for (Cat cat : cats) {
            if (cat.getPassportOwner() == owner.getPassportCode()) {
                ownerCats.add(cat);
            }
        }

        return ownerCats;
    }

    @Override
    public void deletePairFriends(Friends friends) {
        friendsDAO.delete(friends);
    }

    @Override
    public void addPairFriend(Friends friends) {
        var all = getAllFriends();
        for (Friends value : all) {
            if ((value.getSecond() == friends.getSecond()
                    && value.getFirst() == friends.getFirst())
                    || (value.getSecond() == friends.getFirst()
                    && value.getFirst() == friends.getSecond())

            ) {
                new ServiceException("Уже есть аналогичная пара");
            }
        }

        friendsDAO.save(friends);
    }

    public List<Friends> getAllFriends() {
        return friendsDAO.getAll();
    }

    private Owner findByPassportOwner(int passport) {
        var all = ownerDAO.getAll();
        for (Owner owner : all) {
            if (owner.getPassportCode() == passport) {
                return owner;
            }
        }

        return null;
    }
}
