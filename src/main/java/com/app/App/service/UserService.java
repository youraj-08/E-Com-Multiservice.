package com.app.App.service;

import com.app.App.dto.AddressDTO;
import com.app.App.dto.UserRequest;
import com.app.App.dto.UserResponse;
import com.app.App.model.Address;
import com.app.App.repository.UserRepository;
import com.app.App.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponse> fetchAllUser() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    public Optional<UserResponse> fetchUser(Long id) {
       return userRepository.findById(id)
               .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequest updateUserRequest){
        return userRepository.findById(id)
                .map(existingUser -> {
                   updateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    // Previous code to find the user in the arraylist and update the values using Streams
        /* return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updateUser.getFirstName());
                    existingUser.setLastName(updateUser.getLastName());
                    return true;
                }).orElse(false); */


    private void updateUserFromRequest(User user, UserRequest userRequest){
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setCity(userRequest.getAddress().getCity());
            user.setAddress(address);
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
