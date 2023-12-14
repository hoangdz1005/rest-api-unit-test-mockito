package com.project.restfulapi.controller;
import com.project.restfulapi.model.MessageResponse;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.model.UserRes;
import com.project.restfulapi.service.UserService;
import com.project.restfulapi.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/users")
    public ResponseEntity<List<UserRes>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserRes> addUser(@RequestBody UserReq userReq) {
        userValidator.validateUserSignup(userReq);
        return new ResponseEntity<>(userService.addUser(userReq), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRes> getUser(@PathVariable Long id) {
        userValidator.validateUserExist(id);
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/username")
    public ResponseEntity<UserRes> changeUsername(@PathVariable Long id, @RequestParam(name = "username") String username) {
        userValidator.validateUsername(username);
        return new ResponseEntity<>(userService.changeUsername(id, username), HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/password")
    public ResponseEntity<MessageResponse> resetPassword(@PathVariable Long id, @RequestParam(name = "password") String password) {
        userValidator.validatePassword(password);
        return new ResponseEntity<>(userService.resetPassword(id, password), HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        userValidator.validateUserExist(id);
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }
}
