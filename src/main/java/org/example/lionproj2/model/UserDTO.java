package org.example.lionproj2.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;
    private String name;
    private String email;
}

