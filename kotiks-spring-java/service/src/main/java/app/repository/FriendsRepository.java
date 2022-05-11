package app.repository;

import app.entity.FriendsEntity;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.repository.CrudRepository;

@EntityScan("app.entity.CatEntity")
public interface FriendsRepository extends CrudRepository<FriendsEntity, Integer> {
}
