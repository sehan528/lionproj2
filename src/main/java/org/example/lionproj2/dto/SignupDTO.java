package org.example.lionproj2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{3,12}$", message = "아이디는 영문 대소문자와 숫자로 3~12자리여야 합니다.")
    private String userId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,40}$",
            message = "비밀번호는 영문 대소문자, 숫자, 특수문자를 포함하여 6~40자리여야 합니다.")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;
}

