import models.Cat;
import models.Friends;
import models.Owner;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Service service = new Service();
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        service.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        service.saveCat(cat);
        System.out.println(service.getOwnerCats(1003204).size());
        service.deleteOwner(owner);
        service.deleteCat(cat);
    }
}
