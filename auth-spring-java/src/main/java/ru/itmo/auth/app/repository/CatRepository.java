package ru.itmo.auth.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.auth.app.entity.CatEntity;

import java.util.List;

@Repository
public interface CatRepository extends CrudRepository<CatEntity, Integer>{
    @Query("select e from CatEntity e where e.passportCode = :code")
    CatEntity findCatByPassport(@Param("code") int code);

    @Query("FROM CatEntity t")
    List<CatEntity> getAll();

    @Query("select e from CatEntity e where e.passportOwner = :code")
    List<CatEntity> findOwnerCats(@Param("code") int code);

    @Override
    @Query("from CatEntity")
    List<CatEntity> findAll();
}
