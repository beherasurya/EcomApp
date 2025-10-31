package com.app.ecom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private long id =1L;
//    private List<User> userList = new ArrayList<>();

    public List<User> fetchAllUsers(){

        return userRepository.findAll();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id) {

        return userRepository.findById(id);
    }

    public boolean updateUser(Long id, User updateUser) {

        return userRepository.findById(id)
                .map(existingUser ->{
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
}
