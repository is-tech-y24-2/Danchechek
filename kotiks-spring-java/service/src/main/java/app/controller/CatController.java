package ru.itmo.kotiks-spring-java.controller;

import ru.itmo.kotiks-spring-java.entity.CatEntity;
import ru.itmo.kotiks-spring-java.entity.FriendsEntity;
import ru.itmo.kotiks-spring-java.service.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiks-spring-java.service.CatServiceImpl;

@RestController
@RequestMapping("/cats")
public class CatController {

    @Autowired
    private CatService catService;

    @GetMapping()
    public ResponseEntity getAllCats() {
        try {
            return ResponseEntity.badRequest().body(catService.getAllCats());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping("/bypassport")
    public ResponseEntity findByPassportCat(@RequestParam int passport) {
        try {
            if (catService.findByPassportCat(passport) != null) {
                return ResponseEntity.badRequest().body(catService.findByPassportCat(passport));
            } else {
                return ResponseEntity.badRequest().body("нет такого пользователя");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @PostMapping
    public ResponseEntity saveCat(@RequestBody CatEntity cat) {
        try {
            if (catService.saveCat(cat)) {
                return ResponseEntity.badRequest().body(catService.findByPassportCat(cat.getPassportCode()));
            } else {
                return ResponseEntity.badRequest().body("нет такого пользователя");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteCat(@RequestBody CatEntity cat) {
        try {
            catService.deleteCat(cat);
            return ResponseEntity.badRequest().body("удалил)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping("/ownerscats")
    public ResponseEntity getOwnerCats(@RequestParam int passportCode) {
        try {
            if (catService.getOwnerCats(passportCode) != null) {
                return ResponseEntity.badRequest().body(catService.getOwnerCats(passportCode));
            } else {
                return ResponseEntity.badRequest().body("Нет котов_");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping("/friends")
    public ResponseEntity getFriendsCat(@RequestParam int passportCode) {
        try {
            if (catService.getFriendsCat(passportCode) != null) {
                return ResponseEntity.badRequest().body(catService.getFriendsCat(passportCode));
            } else {
                return ResponseEntity.badRequest().body("Нет друзей");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @PostMapping("/addpair")
    public ResponseEntity addPairFriend(@RequestBody FriendsEntity friends) {
        try {
            if (catService.addPairFriend(friends) == true) {
                return ResponseEntity.ok().body("добавили");
            } else {
                return ResponseEntity.ok().body("не добавили");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping("/allfriends")
    public ResponseEntity getAllFriends() {
        try {
            if (catService.getAllFriends() != null) {
                return ResponseEntity.ok().body(catService.getAllFriends());
            } else {
                return ResponseEntity.ok().body("нет пар");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }
}
