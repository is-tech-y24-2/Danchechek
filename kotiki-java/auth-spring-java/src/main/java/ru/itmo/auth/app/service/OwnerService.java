package ru.itmo.auth.app.service;

import ru.itmo.auth.app.entity.OwnerEntity;
import ru.itmo.auth.app.view.OwnerView;

import java.util.List;

public interface OwnerService {

    void saveOwner(OwnerEntity owner);

    List<OwnerEntity> getAllOwners();

    void deleteOwner(int passportCode);

    OwnerEntity findByPassportOwner(int passport);

    List<OwnerView> convert(List<OwnerEntity> owners);

    OwnerView convert(OwnerEntity owner);
}
