package ru.itmo.kotiks-spring-java.repository;

import ru.itmo.kotiks-spring-java.entity.CatEntity;
import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatRepository extends CrudRepository<CatEntity, Integer> {
    @Query("select e from CatEntity e where e.passportCode = :code")
    CatEntity findCatByPassport(@Param("code") int salary);

    @Query("FROM CatEntity t")
    List<CatEntity> getAll();

    @Override
    @Query("from CatEntity")
    List<CatEntity> findAll();
}
