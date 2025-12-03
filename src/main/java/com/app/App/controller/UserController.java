package com.app.App.controller;

import com.app.App.dto.UserRequest;
import com.app.App.dto.UserResponse;
import com.app.App.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUsers(@RequestBody UserRequest userRequest){
         userService.addUser(userRequest);
         return ResponseEntity.ok("User Created Successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequest updateUserRequest,
                                             @PathVariable Long id){
        boolean update = userService.updateUser(id, updateUserRequest);
        if(update)
            return ResponseEntity.ok("User updated Successfully.");
        return ResponseEntity.notFound().build();
    }
}
