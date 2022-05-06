package ru.itmo.kotiki.dao;

import models.Owner;

import java.util.List;

public interface OwnerDAO {

    Owner findById(int id);

    void save(Owner owner);

    void update(Owner owner);

    void delete(Owner owner);

    Owner findByPassportOwner(int passport);

    List<Owner> getAll();

    Owner getAll(int i);
}
