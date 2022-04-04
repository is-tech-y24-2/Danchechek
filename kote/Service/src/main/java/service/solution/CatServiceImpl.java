package service.solution;

import dao.CatDAO;
import dao.FriendsDAO;
import dao.OwnerDAO;
import dao.solution.CatDAOImpl;
import dao.solution.FriendsDAOImpl;
import dao.solution.OwnerDAOImpl;
import models.Cat;
import models.Friends;
import models.Owner;
import service.CatService;
import tools.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CatServiceImpl implements CatService {
    private FriendsDAO friendsDAO = new FriendsDAOImpl();
    private OwnerDAO ownerDAO = new OwnerDAOImpl();
    private CatDAO catDAO = new CatDAOImpl();

    @Override
    public List<Cat> getAllCats() {
        return catDAO.getAll();
    }

    @Override
    public void saveCat(Cat cat) {
        IntStream.range(0, catDAO.getAll().size())
                .filter(i -> catDAO.getAll().get(i).getPassportCode() == cat.getPassportCode())
                .forEach(i -> new ServiceException("Not unique passport, arrest"));
        for (int i = 0; i < ownerDAO.getAll().size(); i++) {
            if (ownerDAO.getAll().get(i).getPassportCode() == cat.getPassportOwner()) {
                catDAO.save(cat);
                return;
            }
        }
        new ServiceException("Нет такого владельца");
    }

    @Override
    public Cat findByPassportCat(int passport) {
        for (int i = 0; i < catDAO.getAll().size(); i++) {
            if (catDAO.getAll().get(i).getPassportCode() == passport) {
                return catDAO.getAll().get(i);
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
        for (int i = 0; i < allPairs.size(); i++) {
            if (allPairs.get(i).getFirst() == passportCode) {
                friends.add(findByPassportCat(allPairs.get(i).getSecond()));
            }

            if (allPairs.get(i).getSecond() == passportCode) {
                friends.add(findByPassportCat(allPairs.get(i).getFirst()));
            }
        }

        return (ArrayList<Cat>) friends;
    }

    @Override
    public List<Cat> getOwnerCats(int passportCode) {
        Owner owner = findByPassportOwner(passportCode);
        var cats = catDAO.getAll();
        List<Cat> ownerCats = new ArrayList<>();
        for (int i = 0; i < cats.size(); i++) {
            if (cats.get(i).getPassportOwner() == owner.getPassportCode()) {
                ownerCats.add(cats.get(i));
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
        for (int i = 0; i < getAllFriends().size(); i++) {
            if ((getAllFriends().get(i).getSecond() == friends.getSecond()
                    && getAllFriends().get(i).getFirst() == friends.getFirst())
                    || (getAllFriends().get(i).getSecond() == friends.getFirst()
                    && getAllFriends().get(i).getFirst() == friends.getSecond())

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
        for (int i = 0; i < ownerDAO.getAll().size(); i++) {
            if (ownerDAO.getAll().get(i).getPassportCode() == passport) {
                return ownerDAO.getAll().get(i);
            }
        }

        return null;
    }
}
