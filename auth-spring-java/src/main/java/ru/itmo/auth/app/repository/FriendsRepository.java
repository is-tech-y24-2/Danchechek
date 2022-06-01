package ru.itmo.auth.app.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.itmo.auth.app.entity.FriendsEntity;

import java.util.List;

public interface FriendsRepository extends CrudRepository<FriendsEntity, Integer> {
    @Query("FROM FriendsEntity ")
    List<FriendsEntity> findAll();
}
