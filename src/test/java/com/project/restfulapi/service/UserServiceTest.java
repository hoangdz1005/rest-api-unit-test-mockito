package com.project.restfulapi.service;

import com.project.restfulapi.entity.User;
import com.project.restfulapi.mapper.UserMapper;
import com.project.restfulapi.model.MessageResponse;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.model.UserRes;
import com.project.restfulapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    private UserRes userRes;
    private UserRes userUpdateRes;
    private UserReq userReq;
    private User userEntity;
    private Long id;
    private List<User> userList = new ArrayList<>();
    private List<UserRes> userResList = new ArrayList<>();
    private String usernameToUpdate;
    private MessageResponse deleteUserSuccess;
    private MessageResponse changePasswordSuccess;
    private String passwordReset;

    @BeforeEach
    void setUp() {
        // GIVEN
        id = 1L;
        userReq = new UserReq("devfullstack", "12345678", "devfullstack@gmail.com");
        userEntity = new User(1L, "devfullstack", "12345678", "devfullstack@gmail.com");
        userRes = new UserRes(1L, "devfullstack", "devfullstack@gmail.com");
        userUpdateRes = new UserRes(1L, "devbackend", "devfullstack@gmail.com");
        userList.add(userEntity);
        userResList.add(userRes);
        usernameToUpdate = "devbackend";
        passwordReset = "87654321";
        deleteUserSuccess = new MessageResponse("User has been deleted!");
        changePasswordSuccess = new MessageResponse("Password has been changed!");
    }

    @Test
    void addUser() {
        // WHEN
        when(userMapper.mapUserFromUserReq(userReq)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.mapUserResFromUser(userEntity)).thenReturn(userRes);
        // THEN
        UserRes result = userService.addUser(userReq);
        assertNotNull(result);
        verify(userMapper, times(1)).mapUserFromUserReq(userReq);
        verify(userRepository, times(1)).save(userEntity);
        assertThat(result).isEqualTo(userRes);
    }

    @Test
    void getUser() {
        // WHEN
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapUserResFromUser(userEntity)).thenReturn(userRes);
        // THEN
        UserRes result = userService.getUser(id);
        assertThat(result).isEqualTo(userRes);
    }

    @Test
    void getUsers() {
        // WHEN
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.mapListUserResFromListUser(userList)).thenReturn(userResList);
        // THEN
        List<UserRes> result = userService.getUsers();
        assertThat(result).isEqualTo(userResList);
    }

    @Test
    void deleteUser() {
        // THEN
        MessageResponse result = userService.deleteUser(id);
        verify(userRepository, times(1)).deleteById(id);
        assertEquals(result.getMessage(), deleteUserSuccess.getMessage());
    }

    @Test
    void changeUsername() {
        // WHEN
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        userEntity.setUsername(usernameToUpdate);
        when(userMapper.mapUserResFromUser(userEntity)).thenReturn(userUpdateRes);
        // THEN
        UserRes result = userService.changeUsername(id, usernameToUpdate);
        assertThat(result).isEqualTo(userUpdateRes);
        verify(userRepository,times(1)).save(userEntity);
    }

    @Test
    void resetPassword() {
        // WHEN
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        // THEN
        MessageResponse result = userService.resetPassword(id, passwordReset);
        assertEquals(result.getMessage(), changePasswordSuccess.getMessage());
        verify(userRepository, times(1)).save(userEntity);
    }
}