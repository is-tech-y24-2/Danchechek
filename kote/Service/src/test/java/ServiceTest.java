import models.Cat;
import models.Friends;
import models.Owner;
import org.junit.jupiter.api.*;

public class ServiceTest {

    private Service service = new Service();

    @Test
    public void saveAndDeleteOwner() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        service.saveOwner(owner);
        Assertions.assertEquals(1, service.getAllOwners().size());
        service.deleteOwner(owner);
        Assertions.assertEquals(0, service.getAllOwners().size());
    }

    @Test
    public void saveAndDeleteCat() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        service.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        service.saveCat(cat);
        Assertions.assertEquals(1, service.getAllCats().size());
        service.deleteCat(cat);
        Assertions.assertEquals(0, service.getAllCats().size());
        service.deleteOwner(owner);
    }

    @Test
    public void getOwnerCats() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        service.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        service.saveCat(cat);
        Assertions.assertEquals(1, service.getOwnerCats(1003204).size());
        service.deleteOwner(owner);
        service.deleteCat(cat);
    }

    @Test
    public void getCatFriends() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        service.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        service.saveCat(cat);
        Cat cat2 = new Cat(1003204, "22.03.2019", "someBree2", "red", 2121211);
        service.saveCat(cat2);
        service.addPairFriend(new Friends(202020, 2121211));
        Assertions.assertEquals(1, service.getFriendsCat(202020).size());
        service.deletePairFriends(new Friends(202020, 2121211));
        service.deleteCat(cat);
        service.deleteCat(cat2);
        service.deleteOwner(owner);
    }
}
