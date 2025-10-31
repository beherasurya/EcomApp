package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private long id =1L;
    private List<User> userList = new ArrayList<>();

    public List<User> fetchAllUsers(){
        return userList;
    }

    public void createUser(User user){
        user.setId(id++);
        userList.add(user);
    }

    public Optional<User> fetchUser(Long id) {

        return userList.stream()
                .filter(user-> user.getId().equals(id))
                .findFirst();
    }

    public boolean updateUser(Long id, User updateUser) {

        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .map(existingUser ->{
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    return true;
                }).findAny().orElse(false);
    }
}
