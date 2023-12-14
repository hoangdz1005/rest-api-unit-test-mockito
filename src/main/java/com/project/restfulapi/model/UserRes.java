package com.project.restfulapi.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRes {
    private Long id;
    private String username;
    private String email;
}
