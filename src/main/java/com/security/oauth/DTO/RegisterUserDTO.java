package com.security.oauth.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterUserDTO {
    private String username;
    private String koreanname;
    private String birthday;
    private String password;

    @Builder
    public RegisterUserDTO(String username, String koreanname, String birthday, String password) {
        this.username = username;
        this.koreanname = koreanname;
        this.birthday = birthday;
        this.password = password;
    }
}
