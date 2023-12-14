package com.project.restfulapi.mapper;
import com.project.restfulapi.entity.User;
import com.project.restfulapi.model.UserReq;
import com.project.restfulapi.model.UserRes;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    public UserRes mapUserResFromUser(User from) {
        UserRes to = new UserRes();
        to.setId(from.getId());
        to.setEmail(from.getEmail());
        to.setUsername(from.getUsername());
        return to;
    }

    public User mapUserFromUserReq(UserReq from) {
        User to = new User();
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());
        to.setEmail(from.getEmail());
        return to;
    }

    public List<UserRes> mapListUserResFromListUser(List<User> from) {
        return from.stream().map(this::mapUserResFromUser).collect(Collectors.toList());
    }
}
