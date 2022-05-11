package app.repository;

import app.entity.CatEntity;
import app.entity.OwnerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatRepository extends CrudRepository<CatEntity, Integer> {
    @Query("select e from CatEntity e where e.passportCode = :code")
    CatEntity findCatByPassport(@Param("code") int salary);

    @Query("FROM CatEntity t")
    List<CatEntity> getAll();
}
