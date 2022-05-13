package ru.itmo.kotiks-spring-java.repository;

import ru.itmo.kotiks-spring-java.entity.FriendsEntity;
import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FriendsRepository extends CrudRepository<FriendsEntity, Integer> {
    @Query("FROM FriendsEntity ")
    List<FriendsEntity> findAll();
}
