package ru.itmo.kotiki.service.solution;

import dao.CatDAO;
import dao.FriendsDAO;
import dao.OwnerDAO;
import dao.solution.CatDAOImpl;
import dao.solution.FriendsDAOImpl;
import dao.solution.OwnerDAOImpl;
import models.Cat;
import models.Owner;
import service.OwnerService;
import tools.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class OwnerServiceImpl implements OwnerService {
    private OwnerDAO ownerDAO = new OwnerDAOImpl();

    @Override
    public void saveOwner(Owner owner) {
        IntStream.range(0, ownerDAO.getAll().size())
                .filter(i -> ownerDAO.getAll().get(i).getPassportCode() == owner.getPassportCode())
                .forEach(i -> new ServiceException("Not unique passport, arrest"));
        ownerDAO.save(owner);
    }

    @Override
    public List<Owner> getAllOwners() {
        return ownerDAO.getAll();
    }

    @Override
    public void deleteOwner(Owner owner) {
        ownerDAO.delete(owner);
    }

    @Override
    public Owner findByPassportOwner(int passport) {
        return ownerDAO.findByPassportOwner(passport);
    }
}
