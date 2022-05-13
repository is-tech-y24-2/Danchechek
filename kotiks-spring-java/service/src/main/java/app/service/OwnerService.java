package ru.itmo.kotiks-spring-java.service;

import ru.itmo.kotiks-spring-java.entity.OwnerEntity;

import java.util.List;

public interface OwnerService {

    void saveOwner(OwnerEntity owner);

    List<OwnerEntity> getAllOwners();

    void deleteOwner(OwnerEntity owner);

    OwnerEntity findByPassportOwner(int passport);
}
