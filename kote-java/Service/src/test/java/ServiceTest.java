import models.Cat;
import models.Friends;
import models.Owner;
import org.junit.jupiter.api.*;
import service.CatService;
import service.OwnerService;
import service.solution.CatServiceImpl;
import service.solution.OwnerServiceImpl;

public class ServiceTest {
    private CatService catService = new CatServiceImpl();
    private OwnerService ownerService = new OwnerServiceImpl();

    @Test
    public void saveAndDeleteOwner() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        ownerService.saveOwner(owner);
        Assertions.assertEquals(1, ownerService.getAllOwners().size());
        ownerService.deleteOwner(owner);
        Assertions.assertEquals(0, ownerService.getAllOwners().size());
    }

    @Test
    public void saveAndDeleteCat() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        ownerService.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        catService.saveCat(cat);
        Assertions.assertEquals(1, catService.getAllCats().size());
        catService.deleteCat(cat);
        Assertions.assertEquals(0, catService.getAllCats().size());
        ownerService.deleteOwner(owner);
    }

    @Test
    public void getOwnerCats() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        ownerService.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        catService.saveCat(cat);
        Assertions.assertEquals(1, catService.getOwnerCats(1003204).size());
        ownerService.deleteOwner(owner);
        catService.deleteCat(cat);
    }

    @Test
    public void getCatFriends() {
        Owner owner = new Owner("Dan", "10.03.2002", 1003204);
        ownerService.saveOwner(owner);
        Cat cat = new Cat(1003204, "20.04.2020", "someBreed", "grey", 202020);
        catService.saveCat(cat);
        Cat cat2 = new Cat(1003204, "22.03.2019", "someBree2", "red", 2121211);
        catService.saveCat(cat2);
        catService.addPairFriend(new Friends(202020, 2121211));
        Assertions.assertEquals(1, catService.getFriendsCat(202020).size());
        catService.deletePairFriends(new Friends(202020, 2121211));
        catService.deleteCat(cat);
        catService.deleteCat(cat2);
        ownerService.deleteOwner(owner);
    }
}
