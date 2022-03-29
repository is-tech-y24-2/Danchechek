package dao;

import models.Owner;

import java.util.List;

public interface OwnerDAO {

    public Owner findById(int id);

    public void save(Owner owner);

    public void update(Owner owner);

    public void delete(Owner owner);

    public List<Owner> getAll();
}
