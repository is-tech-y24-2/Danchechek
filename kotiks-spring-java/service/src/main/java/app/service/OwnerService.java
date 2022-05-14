package ru.itmo.kotiks-spring-java.service;

import ru.itmo.kotiks-spring-java.entity.OwnerEntity;

import java.util.List;

public interface OwnerService {

    void saveOwner(OwnerEntity owner);

    List<OwnerEntity> getAllOwners();

    void deleteOwner(int passportCode);

    OwnerEntity findByPassportOwner(int passport);

    List<OwnerView> convert(List<OwnerEntity> owners);

    OwnerView convert(OwnerEntity owner);
}