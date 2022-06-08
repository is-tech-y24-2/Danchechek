package ru.itmo.auth.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.auth.app.entity.OwnerEntity;
import ru.itmo.auth.app.service.OwnerService;

import static ru.itmo.auth.app.security.RoleUsers.USER;

@RestController
@RequestMapping("/owners")
public class OwnerController {


    @Autowired
    private OwnerService ownerService;

    @PostMapping("/post")
    public ResponseEntity saveOwner(@RequestBody OwnerEntity owner) {
        UserDetails user = User.builder()
                .username("annasmith")
                .password(owner.getPassword())
                .authorities(USER.getGrantedAuthorities())
                .build();
        ownerService.saveOwner(owner);
        return ResponseEntity.ok().body(ownerService.convert(ownerService.findByPassportOwner(owner.getPassportCode())));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteOwner(@RequestParam int passportCode) {
        ownerService.deleteOwner(passportCode);
        return ResponseEntity.ok().body("Удалил)");
    }

    @GetMapping("/bypassport")
    public ResponseEntity findByPassportOwner(@RequestParam int passportCode) {
        if (ownerService.findByPassportOwner(passportCode) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(ownerService.convert(ownerService.findByPassportOwner(passportCode)));
    }

    @GetMapping("/all")
    public ResponseEntity getOwners() {
        return ResponseEntity.ok().body(ownerService.getAllOwners());
    }
}