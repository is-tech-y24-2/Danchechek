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
        try {
            ownerRepository.save(owner);
        } catch (Exception e) {
            throw new RuntimeException("Exception " + e.getMessage());
        }
    }

    public List<OwnerEntity> getAllOwners() {
        try {
            return ownerRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Exception " + e.getMessage());
        }
    }

    public void deleteOwner(int passportCode) {
        try {
            ownerRepository.delete(findByPassportOwner(passportCode));
        } catch (Exception e) {
            throw new RuntimeException("Exception " + e.getMessage());
        }
    }

    public OwnerEntity findByPassportOwner(int passport) {
        try {
            return ownerRepository.findOwnerByPassport(passport);
        } catch (Exception e) {
            throw new RuntimeException("Exception " + e.getMessage());
        }
    }

    @Override
    public List<OwnerView> convert(List<OwnerEntity> owners) {
        List<OwnerView> catViews = new ArrayList<>();
        for (int i = 0; i < owners.size(); i++) {
            OwnerEntity owner = owners.get(i);
            catViews.add(new OwnerView(owner.getName(), owner.getDate()));
        }
        return catViews;
    }

    @Override
    public OwnerView convert(OwnerEntity owner) {
        OwnerView ownerView = new OwnerView(owner.getName(), owner.getDate());
        return ownerView;
    }
}