package ru.itmo.kotiki.service.interfaces;

import models.Cat;
import models.Owner;

import java.util.List;

public interface OwnerService {
    void saveOwner(Owner owner);

    List<Owner> getAllOwners();

    void deleteOwner(Owner owner);

    Owner findByPassportOwner(int passport);
}
