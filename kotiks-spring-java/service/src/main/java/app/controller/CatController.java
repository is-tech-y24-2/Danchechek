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

    @GetMapping("/all")
    public ResponseEntity getAllCats() {
        return ResponseEntity.ok().body(catService.convert(catService.getAllCats()));
    }

    @GetMapping()
    public ResponseEntity findByPassportCat(@RequestParam int passport) {
        if (catService.findByPassportCat(passport) != null) {
            return ResponseEntity.ok().body(catService.convert(catService.findByPassportCat(passport)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity saveCat(@RequestBody CatEntity cat) {
        if (catService.saveCat(cat)) {
            return ResponseEntity.ok().body(catService.convert(catService.findByPassportCat(cat.getPassportCode())));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity deleteCat(@RequestParam int passportCode) {
        catService.deleteCat(passportCode);
        return ResponseEntity.ok().body("удалил)");
    }

    @GetMapping("/ownerscats")
    public ResponseEntity getOwnerCats(@RequestParam int passportCode) {
        if (catService.getOwnerCats(passportCode) != null) {
            return ResponseEntity.ok().body(catService.convert(catService.getOwnerCats(passportCode)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/friends")
    public ResponseEntity getFriendsCat(@RequestParam int passportCode) {
        if (catService.getFriendsCat(passportCode) != null) {
            return ResponseEntity.ok().body(catService.convert(catService.getFriendsCat(passportCode)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addpairfriend")
    public ResponseEntity addPairFriend(@RequestBody FriendsEntity friends) {
        if (catService.addPairFriend(friends)) {
            return ResponseEntity.ok().body("добавили");
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/allfriends")
    public ResponseEntity getAllFriends() {
        if (catService.getAllFriends() != null) {
            return ResponseEntity.ok().body(catService.getAllFriends());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
