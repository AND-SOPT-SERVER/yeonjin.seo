package org.sopt.seminar3.member.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SigninMemberRequest {
    @NotBlank(message="user_name은 필수 입력입니다.")
    @Size(max=15, message = "user_name은 최대 15자까지 입력 가능합니다.")
    private String userName;

    @NotBlank(message = "password는 필수 입력입니다.")
    @Size(max=20, message = "password는 최대 20자까지 입력 가능합니다.")
    private String password;

    public  SigninMemberRequest() {
    }

    public SigninMemberRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}

