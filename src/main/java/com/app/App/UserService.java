package com.app.App;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> userList = new ArrayList<>();

    public List<User> fetchAllUser() {
        return userList;
    }

    public void addUser(User user){
        userList.add(user);
    }

    public User fetchUser(Long id) {
        for(User user: userList){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
}
