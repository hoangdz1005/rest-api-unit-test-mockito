package com.project.restfulapi.service;

import com.project.restfulapi.entity.User;
import com.project.restfulapi.mapper.UserMapper;
import com.project.restfulapi.model.MessageResponse;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.model.UserRes;
import com.project.restfulapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserRes addUser (UserReq userReq) {
        User user = userMapper.mapUserFromUserReq(userReq);
        return userMapper.mapUserResFromUser(userRepository.save(user));
    }
    public UserRes getUser(Long id) {
        return userMapper.mapUserResFromUser(userRepository.findById(id).get());
    }
    public List<UserRes> getUsers() {
        return userMapper.mapListUserResFromListUser(userRepository.findAll());
    }
    public MessageResponse deleteUser(Long id) {
        userRepository.deleteById(id);
        return new MessageResponse("User has been deleted!");
    }
    public UserRes changeUsername(Long id, String username) {
        User user = userRepository.findById(id).get();
        user.setUsername(username);
        userRepository.save(user);
        return userMapper.mapUserResFromUser(user);
    }
    public MessageResponse resetPassword (Long id, String password) {
        User userReset = userRepository.findById(id).get();
        userReset.setPassword(password);
        userRepository.save(userReset);
        return new MessageResponse("Password has been changed!");
    }
}
