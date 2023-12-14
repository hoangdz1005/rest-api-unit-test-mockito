package com.project.restfulapi.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.restfulapi.exception.BadRequestException;
import com.project.restfulapi.exception.Error;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.model.UserRes;
import com.project.restfulapi.service.UserService;
import com.project.restfulapi.validator.UserValidator;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserValidator userValidator;

    private UserRes userRes;
    private UserReq userReq;
    private BadRequestException badRequestException;
    private Error errorAddUser;
    @BeforeEach
    void setUp() {
        // GIVEN
        userReq = new UserReq("devfullstack", "12345678", "devfullstack@gmail.com");
        userRes = new UserRes(1L, "devfullstack", "devfullstack@gmail.com");
        badRequestException = new BadRequestException("Invalid user data");
        errorAddUser = new Error(Integer.toString(HttpStatus.BAD_REQUEST.value()),"Invalid user data" ,"Bad Request Exception");
    }

    @Test
    void addUser_shouldReturnSuccessfully() throws Exception {
        given(userService.addUser(ArgumentMatchers.any(UserReq.class))).willReturn(userRes);
        doNothing().when(userValidator).validateUserSignup(userReq);
        ResultActions response = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userRes.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userRes.getEmail())));
    }
    @Test
    void addUser_shouldReturnBadRequestWhenInvalidUserData() throws Exception {
        willThrow(badRequestException).given(userValidator).validateUserSignup(ArgumentMatchers.any(UserReq.class));
        ResultActions response = mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userReq)));
        response.andExpect(status().isBadRequest());
        response.andExpect(MockMvcResultMatchers.jsonPath("$.code", CoreMatchers.is(errorAddUser.getCode())));
    }
}