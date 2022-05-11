package app.service;

import app.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public void saveOwner(OwnerEntity owner) {
        ownerRepository.save(owner);
    }

    public List<OwnerEntity> getAllOwners() {
        return ownerRepository.getAll();
    }

    public void deleteOwner(OwnerEntity owner) {
        ownerRepository.delete(owner);
    }

    public OwnerEntity findByPassportOwner(int passport) {
        return ownerRepository.findCatByPassport(passport);
    }
}
