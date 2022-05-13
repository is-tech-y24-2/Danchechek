package ru.itmo.kotiks-spring-java.controller;

import ru.itmo.kotiks-spring-java.entity.OwnerEntity;
import ru.itmo.kotiks-spring-java.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmo.kotiks-spring-java.service.OwnerServiceImpl;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @PostMapping
    public ResponseEntity saveOwner(@RequestBody OwnerEntity owner) {
        try {
            ownerService.saveOwner(owner);
            return ResponseEntity.badRequest().body(ownerService.findByPassportOwner(owner.getPassportCode()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @DeleteMapping
    public ResponseEntity deleteOwner(@RequestBody OwnerEntity owner) {
        try {
            ownerService.deleteOwner(owner);
            return ResponseEntity.ok().body("Удалил)");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping
    public ResponseEntity findByPassportOwner(@RequestParam int passport) {
        try {
            return ResponseEntity.ok().body(ownerService.findByPassportOwner(passport));
        } catch (Exception e) {
            if (ownerService.findByPassportOwner(passport) == null) {
                return ResponseEntity.badRequest().body("null");
            }
            return ResponseEntity.badRequest().body("Неуспешная группа");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getUsers() {
        try {
            return ResponseEntity.badRequest().body(ownerService.getAllOwners());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad");
        }
    }
}