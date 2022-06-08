package ru.itmo.auth.app.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.auth.app.entity.OwnerEntity;

import java.util.List;

@Repository
public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer> {
    @Query("select e from OwnerEntity e where e.passportCode = :code")
    OwnerEntity findOwnerByPassport(@Param("code") int code);

    @Query("select e from OwnerEntity e where e.name = :name")
    OwnerEntity findByUsername(@Param("name") String username);

    @Query("FROM OwnerEntity")
    List<OwnerEntity> findAll();

    @Query("select e from OwnerEntity e where e.name = :name")
    OwnerEntity findOwnerByName(@Param("name") String name);
}
