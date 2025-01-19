package org.vinchenzo.ecommerce.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserInfoResponse {
    private long userId;
    private String jwtCookie;

    private String username;
    private List<String> roles;


    public UserInfoResponse(String username, List<String> roles, long userId) {
        this.username = username;
        this.roles = roles;
        this.userId = userId;
    }

    public UserInfoResponse(String username, List<String> roles, Long id, String value) {
        this.username = username;
        this.roles = roles;
        this.userId = id;
        this.jwtCookie = value;
    }
}


