package com.project.restfulapi.validator;

import com.project.restfulapi.exception.BadRequestException;
import com.project.restfulapi.exception.EntityNotFoundException;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final int MIN_PASSWORD = 4;
    private static final String PASSWORD_NOT_STRONG = "Password length must more 4 characters!";
    private static final String USERNAME_EXISTED = "Username has been existed! Please choose another!";
    private static final String EMAIL_PATTERN_NOT_VALID = "Incorrect email pattern!";
    private static final String EMAIL_EXISTED = "Email has been existed!";
    private static final String USER_NOT_EXIST = "User not found";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void validatePassword(String password) {
        if(password.length() >= MIN_PASSWORD ) {return;}
        throw new BadRequestException(PASSWORD_NOT_STRONG);
    }

    public void validateUsername(String username) {
        if(userRepository.findByUsername(username).isEmpty()) {return;}
        throw new BadRequestException(USERNAME_EXISTED);
    }
    public void validateEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException(EMAIL_EXISTED);
        }
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        if(!EMAIL_PATTERN.matcher(email).matches()) {
            throw new BadRequestException(EMAIL_PATTERN_NOT_VALID);
        }
    }
    public void validateUserExist(Long id) {
        if(userRepository.findById(id).isPresent()) {return;}
        throw new EntityNotFoundException(USER_NOT_EXIST);
    }
    public void validateUserSignup(UserReq userReq) {
        validateEmail(userReq.getEmail());
        validateUsername(userReq.getUsername());
        validatePassword(userReq.getPassword());
    }

}
