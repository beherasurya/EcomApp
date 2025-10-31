package com.app.ecom;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

//    @Autowired
    private final UserService userService;
    private List<User> userList = new ArrayList<>();

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers(){

        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
//        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
         userService.createUser(user);
         return ResponseEntity.ok("User added");
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){

        User user = userService.fetchUser(id);
        if(user == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user);
    }
}
