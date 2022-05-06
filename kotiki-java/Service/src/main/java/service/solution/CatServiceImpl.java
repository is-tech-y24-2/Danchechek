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
        return catDAO.findByPassportCat(passport);
    }

    @Override
    public void deleteCat(Cat cat) {
        catDAO.delete(cat);
    }

    @Override
    public ArrayList<Cat> getFriendsCat(int passportCode) {
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
        return catDAO.getOwnerCats(passportCode);
    }

    @Override
    public void deletePairFriends(Friends friends) {
        friendsDAO.delete(friends);
    }

    @Override
    public void addPairFriend(Friends friends) {
        friendsDAO.save(friends);
    }
}
