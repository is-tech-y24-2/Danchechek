package ru.itmo.kotiks-spring-java.repository;

import ru.itmo.kotiks-spring-java.entity.CatEntity;
import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer> {
    @Query("select e from OwnerEntity e where e.passportCode = :code")
    OwnerEntity findCatByPassport(@Param("code") int salary);

    @Query("FROM OwnerEntity")
    List<OwnerEntity> findAll();
}
