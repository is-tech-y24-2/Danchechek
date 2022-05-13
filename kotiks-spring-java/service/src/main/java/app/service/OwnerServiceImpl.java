package ru.itmo.kotiks-spring-java.service;

import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotiks-spring-java.repository.OwnerRepository;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public void saveOwner(OwnerEntity owner) {
        ownerRepository.save(owner);
    }

    public List<OwnerEntity> getAllOwners() {
        return ownerRepository.findAll();
    }

    public void deleteOwner(OwnerEntity owner) {
        ownerRepository.delete(owner);
    }

    public OwnerEntity findByPassportOwner(int passport) {
        return ownerRepository.findCatByPassport(passport);
    }
}
