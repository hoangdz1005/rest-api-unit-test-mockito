package com.project.restfulapi.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReq {
    private String username;
    private String password;
    private String email;
}
